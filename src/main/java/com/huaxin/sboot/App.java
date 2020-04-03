package com.huaxin.sboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication //@Configuration+ @EnableAutoConfiguration + @ComponentScan 
@MapperScan("com.huaxin.sboot.dao")
//启注解事务管理
//@EnableTransactionManagement(默认开启)
@EnableScheduling//开启定时任务
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
