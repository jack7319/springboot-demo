package com.bizideal.mn.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author : liulq
 * @date: 创建时间：2017年8月30日 上午9:28:20
 * @version: 1.0
 * @Description:
 */
@Component
public class ScheduledTasks {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	// @Scheduled(fixedRate = 5000) // 上一次开始执行时间点之后5秒再执行
	// @Scheduled(fixedDelay = 5000) // 上一次执行完毕时间点之后5秒再执行
	// @Scheduled(initialDelay = 3000, fixedRate = 5000) //
	// 初始化延迟1秒，之后按fixedRate执行。
	@Scheduled(cron = "0/20 45-50 9 * * *") // 每天9:45-9:50，每20秒执行一次
	public void print() {
		System.out.println(sdf.format(new Date()));
	}
}
