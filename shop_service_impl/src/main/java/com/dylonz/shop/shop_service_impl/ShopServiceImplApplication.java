package com.dylonz.shop.shop_service_impl;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@DubboComponentScan("com.dylonz.shop.shop_service_impl.serviceimpl")
@MapperScan("com.dylonz.shop.dao")
public class ShopServiceImplApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopServiceImplApplication.class, args);
	}
}
