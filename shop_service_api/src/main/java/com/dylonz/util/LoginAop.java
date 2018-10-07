package com.dylonz.util;

import com.dylonz.entity.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
public class LoginAop {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 环绕增强
     * @param proceedingJoinPoint
     * @return
     */
    @Around("execution(* *..*Controller.*(..)) && @annotation(com.dylonz.util.IsLogin)")
    public Object isLogin(ProceedingJoinPoint proceedingJoinPoint){
        
        //获取目标方法上的自定义注解
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        IsLogin isLogin = method.getAnnotation(IsLogin.class);
        System.out.println("当前是否需要跳转到登录页面"+isLogin.tologin());


        String token=null;
        //判断当前是否登录 --通过cookie
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals(Constact.LOGIN_TOKEN)){
                    token=cookie.getValue();
                    break;
                }
            }
        }

        User user=null;
        if(token!=null){
            //查询redis
            user= (User) redisTemplate.opsForValue().get(token);
           // System.out.println("diyi--"+user);
        }

        //判断是否需要跳转到登录页面----有些情况会强制要求登录
        if(user==null && isLogin.tologin()){
            String returnUrl = request.getRequestURL() + "?" + request.getQueryString();
            //System.out.println("returnUrl--->"+returnUrl);
             returnUrl=returnUrl.replace("&","*");

            return "redirect:http://localhost:8085/sso/tologin?returnUrl"+returnUrl;
        }

        Object[] args = proceedingJoinPoint.getArgs();
        //已经登录，获取目标方法的实参列表
        for(int i=0;i<args.length;i++){
            if(args[i]!=null && args[i].getClass()==User.class){
                args[i]=user;
            }

        }


        Object object=null;
        try {
            //放行
            object=proceedingJoinPoint.proceed(args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return object;
    }
}
