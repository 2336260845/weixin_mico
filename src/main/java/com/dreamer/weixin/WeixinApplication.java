package com.dreamer.weixin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
@Slf4j
public class WeixinApplication {

	public static void main(String[] args) {
		log.info("叶莹测试");
		SpringApplication.run(WeixinApplication.class, args);
	}

}
