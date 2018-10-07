package com.dylonz.shop.shop_order;

import com.dylonz.util.LoginAop;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ShopOrderApplication {

	public static void main(String[] args) {

		SpringApplication.run(ShopOrderApplication.class, args);
	}

	@Bean
	public LoginAop getLoginAop(){
		return new LoginAop();
	}
}
