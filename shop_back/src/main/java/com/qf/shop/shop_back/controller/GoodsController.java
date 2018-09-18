package com.qf.shop.shop_back.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.shop.service.IGoodsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Reference
    private IGoodsService goodsService;

    @RequestMapping("/goodslist")
    public String goodsManager(Model model){

        //通过service获得商品列表
        List<Goods> goods = goodsService.queryAll();
        model.addAttribute("goods",goods);
        System.out.println("商品详情"+goods);
        return "goodslist";
    }
}
