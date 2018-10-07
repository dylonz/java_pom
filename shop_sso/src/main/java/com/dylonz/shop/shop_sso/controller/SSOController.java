package com.dylonz.shop.shop_sso.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dylonz.entity.ResultData;
import com.dylonz.entity.User;
import com.dylonz.shop.service.IUserService;
import com.dylonz.util.Constact;
import com.dylonz.util.HttpClientUtil;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

//单点登录工程
@Controller
@RequestMapping("/sso")
public class SSOController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Reference
    private IUserService userService;

    /**
     * 跳转到登录页面
     * @return
     */
    @RequestMapping("/tologin")
    public String toLogin(String returnUrl, Model model){
       // System.out.println("当前路径---->"+returnUrl);
        model.addAttribute("returnUrl",returnUrl);
        return "login";
    }


    /**
     * 统一进行登录
     * @param username
     * @param password
     * @param model
     * @param response
     * @return
     */
    @RequestMapping("/login")
    public String login(String username,
                        String password,
                        Model model,
                        String returnUrl,
                        HttpServletResponse response,
                        @CookieValue(value = "cart_token",required = false) String carts
                       ){


        ResultData<User> data = userService.login(username, password);
        //System.out.println("--->--"+returnUrl);

        switch (data.getCode()){
            case 0:
                //登录成功的处理
                if(returnUrl == null || returnUrl.equals("")){
                    returnUrl = "http://localhost:8082";
                } else {
                    returnUrl = returnUrl.replace("*", "&");
                }
                //登录成功，处理登陆成功的业务
                //1.生成uuid作为redis的key值
                String token = UUID.randomUUID().toString();
              //  System.out.println("uuid--->"+token);
                //2.调用redis，将uuid设置为key值，user设置为value
                redisTemplate.opsForValue().set(token,data.getData());
                redisTemplate.expire(token,7, TimeUnit.DAYS);
                //3.将uuid写入redis的cookie
                Cookie cookie=new Cookie(Constact.LOGIN_TOKEN,token);
                cookie.setMaxAge(30 * 24 * 60 * 60 );
                //解决跨域路径问题
                cookie.setPath("/");
                //解决跨域域名问题
                //cookie.setDomain(".shop.com");
                response.addCookie(cookie );

                //登录成功后进行购物车的合并
                if(carts!=null){
                    Map<String,String> params=new HashMap<>();
                    params.put("uid",data.getData().getId()+"");
                    //仿照cookie，仿照出来一个请求头
                    Map<String,String> header=new HashMap<>();
                    try {
                        header.put("Cookie","cart_token="+ URLEncoder.encode(carts,"utf-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    String result = HttpClientUtil.sendPostParamsAndHeader("http://localhost:8086/cart/hebing",
                            params,
                            header);
                    //System.out.println("返回结果"+result);
                    if("succ".equals(result)){
                        //购物车合并成功则清空临时购物车
                        Cookie cookie1=new Cookie(Constact.CART_TOKEN,null);
                        cookie1.setMaxAge(0);
                        cookie1.setPath("/");
                        response.addCookie(cookie1);
                    }
                }


                return "redirect:"+returnUrl;
                default:
                    model.addAttribute("msg",data.getMsg());

                    return "login";
        }
    }

    //认证登录
    @RequestMapping("/islogin")
    @ResponseBody
    public String islogin(@CookieValue(value = "login_token",required = false) String login_token){
       // System.out.println("login_token"+login_token);
        //获取cookie------>uuid(token)
        //token------>redis----->user
        User user=null;
        if(login_token!=null){
            user = (User) redisTemplate.opsForValue().get(login_token);
        }
       // System.out.println("user--->"+user);
        return "callback("+new Gson().toJson(user)+")";
    }

    //注销登录
    @RequestMapping("/logout")
    public String logout(@CookieValue("login_token") String login_token,HttpServletResponse response){
        //清空redis缓存的数据
       // System.out.println("----->"+login_token);
        redisTemplate.delete(login_token);
        //清空cookie
        Cookie cookie=new Cookie(Constact.LOGIN_TOKEN,null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/sso/tologin";
    }
}