package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.example.demo.bean.mapper")
public class DemoApplication {

	/**
	 *开 发 者：连磊
	 *开发时间：2018/11/6 15:43
	 *描    述：开始
	 **/
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
