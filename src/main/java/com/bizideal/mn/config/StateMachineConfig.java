package com.bizideal.mn.config;

import java.util.EnumSet;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import com.bizideal.mn.enums.Events;
import com.bizideal.mn.enums.States;

/**
 * @author : liulq
 * @date: 创建时间：2017年8月30日 上午10:50:44
 * @version: 1.0
 * @Description:
 */
@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

	@Override
	public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
		// 定义状态机中的状态
		states
			.withStates()
				.initial(States.UNPAID) // 指定初始状态
				.states(EnumSet.allOf(States.class)); // 指定使用的所有状态
	}

	@Override
	public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
		transitions
			.withExternal()
				.source(States.UNPAID).target(States.WAITING_FOR_RECEIVE) // 指定状态来源和目标
				.event(Events.PAY).and() // 绑定事件
			.withExternal()
				.source(States.WAITING_FOR_RECEIVE).target(States.DONE)
				.event(Events.RECEIVE);
	}

}
