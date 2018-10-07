package com.dylonz.shop.shop_item.controller;

import com.dylonz.entity.Goods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Value("${image.path}")
    private String path;

    @Autowired
    private Configuration configuration;

    //生成静态页面---在添加商品时生成
    @RequestMapping("/createhtml")
    public String createHtml(@RequestBody Goods goods, HttpServletRequest request){
        Writer writer=null;
        try {
            //1.加载静态模版
            Template template = configuration.getTemplate("item.ftl");
            //2.加载数据
            Map<String,Object> map=new HashMap<>();
            map.put("path",path);
            System.out.println("---"+path);
            map.put("goods",goods);
            map.put("context",request.getContextPath());
            System.out.println("当前项目根路径"+request.getContextPath());

            //3.生成静态页面
            String path=this.getClass().getResource("/").getPath()+"static/page/"+goods.getId()+".html";
            writer=new FileWriter(path);
            System.out.println("classpath路径"+this.getClass().getResource("/").getPath());
            System.out.println("存储路径"+path);
            template.process(map,writer);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(writer!=null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
