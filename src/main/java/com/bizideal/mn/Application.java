package com.bizideal.mn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableStateMachine //启用Spring StateMachine状态机功能
@EnableAsync // 允许异步调用
@EnableScheduling // 允许定时任务
@EnableTransactionManagement
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
