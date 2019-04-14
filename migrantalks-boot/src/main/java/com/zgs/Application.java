package com.zgs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 主入口
 * @author zgs
 */
@EnableTransactionManagement
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,SecurityAutoConfiguration.class})
@MapperScan(basePackages = {"com.zgs.dao"})
public class Application {

	public static void main(String[] args) {
		SpringApplication context = new SpringApplication(Application.class);
		context.setBannerMode(Banner.Mode.OFF);
		context.run(args);
	}
}
