package com.dylonz.shop.shop_order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dylonz.entity.Address;
import com.dylonz.entity.Cart;
import com.dylonz.entity.Orders;
import com.dylonz.entity.User;
import com.dylonz.shop.service.IAddressService;
import com.dylonz.shop.service.ICartService;
import com.dylonz.shop.service.IOrderService;
import com.dylonz.util.IsLogin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Reference
    private ICartService cartService;

    @Reference
    private IAddressService addressService;

    @Reference
    private IOrderService orderService;

    //编辑订单--如果未登录，必须跳转到登录页面
    @IsLogin(tologin = true)
    @RequestMapping("/editororder")
    public String editorOrder(Integer[] gid, User user,Model model){
        //System.out.println("编辑商品信息"+ Arrays.toString(gid));
       // System.out.println("用户信息"+user);
        //通过商品id+用户id 去购物车表中查询需要购买哪些购物车的记录
        List<Cart> cartList = cartService.queryCartByGids(gid, user.getId());
        System.out.println(cartList);

        //通过用户id查询该用户的收货地址
        List<Address> addresses = addressService.queryByUid(user.getId());
        model.addAttribute("address",addresses);
        model.addAttribute("cartList",cartList);

        return "editororder";
    }

    /**
     * 新增收货地址
     * @return
     */
    @IsLogin
    @RequestMapping("/addaddress")
    @ResponseBody
    public Address addAddress(Address address,User user){

        address.setUid(user.getId());
        System.out.println("address地址"+address);
        addressService.addAddress(address);

        return address;
    }

    /**
     * 下单
     * @param cid
     * @param aid
     * @param user
     * @return
     */
    @IsLogin
    @RequestMapping("/addorder")
    @ResponseBody
    public String addOrder(Integer[] cid,Integer aid,User user){
        System.out.println("下单的购物车"+Arrays.toString(cid));
        System.out.println("收货地址的"+aid);
//        1.生成订单-------->订单详情；
        //2.生成购物车；
//        3.去支付。
        String orderid =null;
        try {
            orderid = orderService.addOrdersAndOrderdetils(cid, aid, user.getId());
        }catch (Exception e){
            e.printStackTrace();
        }
        return orderid;
    }

    @IsLogin
    @RequestMapping("/orderlist")
    public String orderlist(User user,Model model){

        List<Orders> orders = orderService.queryByUid(user.getId());
        model.addAttribute("orders",orders);
        System.out.println("orders----->"+orders);
        return "orderlist";
    }
}
