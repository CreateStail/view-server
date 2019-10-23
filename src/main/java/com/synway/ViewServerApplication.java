package com.synway;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.synway.dao")
public class ViewServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ViewServerApplication.class, args);
	}

}
