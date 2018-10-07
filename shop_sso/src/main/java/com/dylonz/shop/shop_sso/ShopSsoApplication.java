package com.dylonz.shop.shop_sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ShopSsoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopSsoApplication.class, args);
	}
}
