package com.bizideal.mn.rabbitmq.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : liulq
 * @date: 创建时间：2017年8月29日 下午7:04:08
 * @version: 1.0
 * @Description:rabbitmq队列生产者
 */
@Component
public class Sender {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	public void send(String message) {
		System.out.println("Sender : " + message);
		this.rabbitTemplate.convertAndSend("hello", message);
	}
}
