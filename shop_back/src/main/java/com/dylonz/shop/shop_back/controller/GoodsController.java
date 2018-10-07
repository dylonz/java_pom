package com.dylonz.shop.shop_back.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dylonz.entity.Goods;
import com.dylonz.shop.service.IGoodsService;
import com.dylonz.util.HttpClientUtil;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Value("${image.path}")
    private String path;

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Reference
    private IGoodsService goodsService;

    @RequestMapping("/goodslist")
    public String goodsManager(Model model){

        //通过service获得商品列表
        List<Goods> goods = goodsService.queryAll();
        model.addAttribute("goods",goods);
        model.addAttribute("path",path);
        //System.out.println("商品详情"+goods);
        return "goodslist";
    }

    //添加商品
    @RequestMapping("/goodsadd")
    public String insertGoods(@RequestParam("file") MultipartFile file, Goods goods) throws IOException {
        //1.文件上传---->fastdfs---->获得fastdfs回显的url
        StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(), "JPG", null);
        String fullPath = storePath.getFullPath();
       // System.out.println("图片上传的路径"+fullPath);

        //2.将url放入goods对象中
        goods.setGimage(fullPath);

        //3.调用service层保存到数据库
        goods = goodsService.insert(goods);
        //System.out.println("controller层主键回填为"+goods.getId());

        //调用索引工程同步索引库（URL----->HttpClient）
        //传参：对象json
        HttpClientUtil.sendJsonPost("http://localhost:8083/solr/add",new Gson().toJson(goods));
        HttpClientUtil.sendJsonPost("http://localhost:8084/item/createhtml",new Gson().toJson(goods));

        return "redirect:/goods/goodslist";
    }

    /**新品上架查询最后4条数据并展示出来
    直接采用springmvc的跨域解决办法 - springmvc会在响应头中设置一个参数，
    浏览器看到这个参数以后就不会拦截跨域请求了
     直接加上注解 @CrossOrgin 即可*/
    @RequestMapping("/querynew")
    @ResponseBody
    @CrossOrigin
    public List<Goods> querynew(){
        List<Goods> goods = goodsService.querynew();
       // System.out.println("最新四件商品"+goods);
        return goods;
    }

    /**jquery的jsonp方式实现跨域的解决（利用了浏览器的漏洞：Link/Script）*/
//    @RequestMapping("/querynew")
//    @ResponseBody
//    public String querynew(){
//        List<Goods> goods = goodsService.querynew();
//        System.out.println("最新四件商品"+goods);
//        return "hello('"+new Gson().toJson(goods)+"')";
//    }

}
