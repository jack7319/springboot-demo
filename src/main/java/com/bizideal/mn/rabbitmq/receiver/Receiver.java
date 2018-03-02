package com.bizideal.mn.rabbitmq.receiver;

import java.io.IOException;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.bizideal.mn.entity.UserInfo;
import com.bizideal.mn.utils.JsonUtils;

/**
 * @author : liulq
 * @date: 创建时间：2017年8月29日 下午7:05:36
 * @version: 1.0
 * @Description: rabbitmq队列消费者
 */
@Component
public class Receiver {

	@RabbitHandler
	@RabbitListener(queues = "hello")
	public void process(String hello) throws IOException {
		System.out.println("Receiver : " + hello);
		UserInfo userInfo = (UserInfo)JsonUtils.fromJson(hello, UserInfo.class);
		System.out.println(userInfo);
	}

}
