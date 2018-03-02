package com.bizideal.mn.utils;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author : liulq
 * @date: 创建时间：2017年8月29日 下午7:15:44
 * @version: 1.0
 * @Description:
 */
public class JsonUtils {

	public static final ObjectMapper mapper = new ObjectMapper();

	public static String toJsonString(Object obj) throws IOException {
		return mapper.writeValueAsString(obj);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object fromJson(String string, Class clazz) throws IOException {
		return mapper.readValue(string, clazz);
	}

}
