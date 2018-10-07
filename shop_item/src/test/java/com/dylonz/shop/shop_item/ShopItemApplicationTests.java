package com.dylonz.shop.shop_item;

import com.dylonz.entity.Goods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopItemApplicationTests {

	@Autowired
	private Configuration configuration;

	//使用freemarker生成静态页面
	@Test
	public void contextLoads() throws IOException, TemplateException {
		//1.加载指定模版
		Template template = configuration.getTemplate("hello.ftl");

		Goods goods=new Goods();
		goods.setId(1);
		goods.setTitle("freemarker测试");
		goods.setGimage("http:www.baidu.com");
		List<Goods> list=new ArrayList<Goods>();
		list.add(new Goods(1,"小米12","小米号搜集",11,1,11,11,"www.baidu.com"));
		list.add(new Goods(2,"小米13","小米号搜集",11,1,11,11,"www.baidu.com"));
		list.add(new Goods(3,"小米14","小米号搜集",11,1,11,11,"www.baidu.com"));

		//2.准备数据
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("key","world");
		map.put("goods",goods);
		map.put("age",50);
		map.put("lists",list);
		map.put("money",1234567.89);
		map.put("obj",null);






		//3.生成静态页面
		Writer writer=new FileWriter("C:\\Users\\Administrator\\Desktop\\hello.html");
		template.process(map,writer);
		writer.close();
	}

}
