package com.bizideal.mn.utils;

/**
 * @author : liulq
 * @date: 创建时间：2017年8月30日 下午3:08:48
 * @version: 1.0
 * @Description:
 */
public class RandomUtils {

	public static String get() {
		return ((int) ((Math.random() * 9 + 1) * 100000)) + "";
	}
}
