package com.bizideal.mn;

import java.io.IOException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.test.context.junit4.SpringRunner;

import com.bizideal.mn.async.Task;
import com.bizideal.mn.entity.UserInfo;
import com.bizideal.mn.enums.Events;
import com.bizideal.mn.enums.States;
import com.bizideal.mn.rabbitmq.producer.Sender;
import com.bizideal.mn.repository.UserInfoRepository;
import com.bizideal.mn.service.UserInfoService;
import com.bizideal.mn.utils.JsonUtils;

@EnableStateMachine // 启用Spring StateMachine状态机功能
@EnableAsync // 允许异步调用
@EnableScheduling // 允许定时任务
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AppTest {

	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private CacheManager cacheManager;
	@Autowired
	private Sender sender;
	@Autowired
	private Task task;
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private StateMachine<States, Events> stateMachine;

	@Test
	public void get() throws Exception {
		UserInfo findOne = userInfoRepository.findOne(2); // 返回实体
		UserInfo one = userInfoRepository.getOne(2); // 返回引用
		System.out.println(one.getUserId()); // 抛出异常
	}

	@SuppressWarnings("unused")
	@Test
	public void t() throws Exception {
		System.out.println(userInfoService.findByUserId(2));
		System.out.println(userInfoService.findByUserId(2));
		System.out.println(userInfoService.findByUserId(2));
		System.out.println(userInfoService.findByUserId(2));
	}

	@Test
	public void s() throws IOException, InterruptedException {
		sender.send(JsonUtils.toJsonString(new UserInfo(3, "k", 0)));
		TimeUnit.SECONDS.sleep(3);
	}

	@Test
	public void asyncTest() throws Exception {
		long start = System.currentTimeMillis();

		Future<String> doTaskOne = task.doTaskOne();
		Future<String> doTaskTwo = task.doTaskTwo();
		Future<String> doTaskThree = task.doTaskThree();

		while (true) {
			if (doTaskOne.isDone() && doTaskTwo.isDone() && doTaskThree.isDone()) {
				break;
			}
			Thread.sleep(100);
		}
		long end = System.currentTimeMillis();
		System.out.println("任务完成");
		System.out.println(end - start);
	}

	@Test
	public void sendEmail() {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("166574330@qq.com");
		message.setTo("2112407533@qq.com");
		message.setSubject("谁知我心惶恐");
		message.setText("也许我应沉醉装疯");
		javaMailSender.send(message);
	}

	@Test
	public void stateMachine() {
		stateMachine.start(); // 状态机初始化
		stateMachine.sendEvent(Events.PAY); // 进行支付动作
		stateMachine.sendEvent(Events.RECEIVE); // 进行收货动作
	}

}
