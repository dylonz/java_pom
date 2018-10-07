package com.dylonz.shop.shop_cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.dylonz.entity.Cart;
import com.dylonz.entity.Goods;
import com.dylonz.entity.User;
import com.dylonz.shop.service.ICartService;
import com.dylonz.shop.service.IGoodsService;
import com.dylonz.util.Constact;
import com.dylonz.util.IsLogin;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Reference
    private ICartService cartService;

    @Reference
    private IGoodsService goodsService;

    /**
     * 添加购物车
     *
     * @return
     */
    @IsLogin
    @RequestMapping("/addCart")
    public String addCart(Cart cart, User user,
                          HttpServletResponse response,
                          @CookieValue(value = "cart_token", required = false) String carts) {
        //System.out.println("登录信息" + user);
        //判断当前是否登录
        if (user != null) {
            //如果登录了，添加到数据库
            cart.setUid(user.getId());
            cartService.addCart(cart);
        } else {
            //否则添加到cookie
            List<Cart> cartList = null;
            if (carts != null) {
                //cookie已经有购物车信息
                TypeToken<List<Cart>> tt = new TypeToken<List<Cart>>() {
                };
                cartList = new Gson().fromJson(carts, tt.getType());
                cartList.add(cart);
            } else {
                //cookie没有购物车信息
                //cookie - key/value必须是string  cookie中不能有中文  cookie最多只能放4K的数据
                cartList = Collections.singletonList(cart);
                cartList = new ArrayList<>();
                cartList.add(cart);
            }
           // System.out.println("购物车的信息" + cartList);
            String json = new Gson().toJson(cartList);
            try {
                //处理中文的问题
                json = URLEncoder.encode(json, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Cookie cookie = new Cookie(Constact.CART_TOKEN, json);
            cookie.setPath("/");
            cookie.setMaxAge(30 * 24 * 60 * 60);
            response.addCookie(cookie);
        }
        return "addsucc";
    }

    @RequestMapping("/hebing")
    @ResponseBody
    public String hebing(Integer uid,
                         @CookieValue(value = "cart_token", required = false) String carts) {
        // System.out.println("合并购物车"+uid+"--->"+carts);
        TypeToken<List<Cart>> tt = new TypeToken<List<Cart>>() {};
        List<Cart> cartList = new Gson().fromJson(carts, tt.getType());
        for (Cart cart : cartList) {
            cart.setUid(uid);
            cartService.addCart(cart);
        }
        return "succ";
    }

    /**
     * ajax获得购物车列表
     * @return
     */
    @IsLogin
    @RequestMapping("/getcarts")
    @ResponseBody
    public String getCarts(@CookieValue(value = "cart_token",required = false) String carts,User user){
        List<Cart> cartList = cartService.getCarts(user, carts);

        return "getCart("+new Gson().toJson(cartList)+")";
    }

    /**
     * 去到购物车列表
     * @return
     */
    @IsLogin
    @RequestMapping("/cartlist")
    public String cartlist(@CookieValue(value = "cart_token",required = false) String carts,
                           User user,
                           Model model){
        List<Cart> cartList = cartService.getCarts(user, carts);
        System.out.println("购物列表"+cartList);
        model.addAttribute("cartList",cartList);

        return "cartlist";
    }
}
