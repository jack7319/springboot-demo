package com.bizideal.mn.statemachine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

/**
 * @author : liulq
 * @date: 创建时间：2017年8月30日 上午10:49:33
 * @version: 1.0
 * @Description: 状态机的状态变化监听
 */
@WithStateMachine
public class EventConfig {

	private Logger logger = LoggerFactory.getLogger(getClass());

	// 监听初始化状态
	@OnTransition(target = "UNPAID")
	public void create() {
		logger.info("订单创建，待支付");
	}

	// 监听从UNPAID到WAITING_FOR_RECEIVE的变化
	@OnTransition(source = "UNPAID", target = "WAITING_FOR_RECEIVE")
	public void pay() {
		logger.info("用户完成支付，待收货");
	}

	// 监听从WAITING_FOR_RECEIVE到DONE的变化
	@OnTransition(source = "WAITING_FOR_RECEIVE", target = "DONE")
	public void receive() {
		logger.info("用户已收货，订单完成");
	}

}
