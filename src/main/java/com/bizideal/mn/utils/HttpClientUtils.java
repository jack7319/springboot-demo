package com.bizideal.mn.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * http请求工具类
 * 
 * @author pc
 *
 */
public class HttpClientUtils {

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JsonObject httpRequest(String requestUrl,
			String requestMethod, String outputStr) throws Exception {
		JsonObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		// 创建SSLContext对象，并使用我们指定的信任管理器初始化
		TrustManager[] tm = { new HttpsX509TrustManager() };
		SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
		sslContext.init(null, tm, new java.security.SecureRandom());
		// 从上述SSLContext对象中得到SSLSocketFactory对象
		SSLSocketFactory ssf = sslContext.getSocketFactory();

		URL url = new URL(requestUrl);
		HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
				.openConnection();
		httpUrlConn.setSSLSocketFactory(ssf);

		httpUrlConn.setDoOutput(true);
		httpUrlConn.setDoInput(true);
		httpUrlConn.setUseCaches(false);
		// 设置请求方式（GET/POST）
		httpUrlConn.setRequestMethod(requestMethod);
		// 设置请求头Accept属性，github登陆时会返回json信息
		httpUrlConn.setRequestProperty("Accept", "application/json");

		if ("GET".equalsIgnoreCase(requestMethod))
			httpUrlConn.connect();

		// 当有数据需要提交时
		if (null != outputStr) {
			OutputStream outputStream = httpUrlConn.getOutputStream();
			// 注意编码格式，防止中文乱码
			outputStream.write(outputStr.getBytes("UTF-8"));
			outputStream.close();
		}

		// 将返回的输入流转换成字符串
		InputStream inputStream = httpUrlConn.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(
				inputStream, "utf-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

		String str = null;
		while ((str = bufferedReader.readLine()) != null) {
			buffer.append(str);
		}
		bufferedReader.close();
		inputStreamReader.close();
		// 释放资源
		inputStream.close();
		inputStream = null;
		httpUrlConn.disconnect();
		jsonObject = (JsonObject) new JsonParser().parse(buffer.toString());
		return jsonObject;
	}

	/**
	 * 模拟form表单的形式 ，上传文件 以输出流的形式把文件写入到url中，然后用输入流来获取url的响应
	 * 
	 * @param requestUrl
	 *            请求地址 form表单url地址
	 * @param file
	 *            文件路径
	 * @param fileMap
	 *            文件信息
	 * @return JSONObject url的响应信息返回值
	 */
	// public static JSONObject httpRequestPostForm(String requestUrl, File
	// file, String filename, String ContentType,String title,String
	// introduction)
	public static JsonObject httpRequestPostForm(String requestUrl, File file,
			JsonObject parameters) throws Exception {
		JsonObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		// 创建SSLContext对象，并使用我们指定的信任管理器初始化
		TrustManager[] tm = { new HttpsX509TrustManager() };
		SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
		sslContext.init(null, tm, new java.security.SecureRandom());
		// 从上述SSLContext对象中得到SSLSocketFactory对象
		SSLSocketFactory ssf = sslContext.getSocketFactory();
		// 连接
		URL url = new URL(requestUrl);
		HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
				.openConnection();
		httpUrlConn.setSSLSocketFactory(ssf);
		httpUrlConn.setDoOutput(true);
		httpUrlConn.setDoInput(true);
		httpUrlConn.setUseCaches(false);
		// 设置请求方式（GET/POST）
		httpUrlConn.setRequestMethod("POST");
		// 设置请求头信息
		httpUrlConn.setRequestProperty("Connection", "Keep-Alive");
		httpUrlConn.setRequestProperty("Charset", "UTF-8");

		String boundary = "-----------------------------"
				+ System.currentTimeMillis();
		httpUrlConn.setRequestProperty("Content-Type",
				"multipart/form-data; boundary=" + boundary);

		OutputStream outputStream = httpUrlConn.getOutputStream();
		outputStream.write(("--" + boundary + "\r\n").getBytes("UTF-8"));
		outputStream
				.write(String
						.format("Content-Disposition: form-data; name=\"media\"; filename=\"%s\"\r\n",
								parameters.get("filename").getAsString())
						.getBytes("UTF-8"));
		outputStream.write(String.format("Content-Type:\"%s\" \r\n\r\n",
				parameters.get("contenttype").getAsString()).getBytes("UTF-8"));
		byte[] data = new byte[1024];
		int len = 0;
		FileInputStream inputStream = new FileInputStream(file);
		while ((len = inputStream.read(data)) > -1) {
			outputStream.write(data, 0, len);
		}
		inputStream.close();

		// 如果上传的是video第二个form
		String mediaType = parameters.get("type").getAsString();
		if (mediaType.equals("video")) {
			outputStream
					.write(("\r\n--" + boundary + "\r\n").getBytes("UTF-8"));
			outputStream
					.write("Content-Disposition: form-data; name=\"description\";\r\n\r\n"
							.getBytes());
			outputStream.write(String.format(
					"{\"title\":\"%s\", \"introduction\":\"%s\"}",
					parameters.get("title").getAsString(),
					parameters.get("introduction").getAsString()).getBytes(
					"UTF-8"));
		}

		outputStream.write(("\r\n--" + boundary + "--\r\n\r\n")
				.getBytes("UTF-8"));
		outputStream.flush();
		outputStream.close();
		outputStream.close();
		// 将返回的输入流转换成字符串
		InputStream inputStreamRequest = httpUrlConn.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(
				inputStreamRequest, "utf-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

		String str = null;
		while ((str = bufferedReader.readLine()) != null) {
			buffer.append(str);
		}
		bufferedReader.close();
		inputStreamReader.close();
		// 释放资源
		inputStreamRequest.close();
		inputStreamRequest = null;
		httpUrlConn.disconnect();
		jsonObject = (JsonObject) new JsonParser().parse(buffer.toString());
		return jsonObject;
	}

	/**
	 * 通过HttpServletRequest返回IP地址
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return ip String
	 * @throws Exception
	 */
	public static String getIpAddr(HttpServletRequest request) throws Exception {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	// 判断获取到的host是否是域名
	public static boolean isDomain(String host) {
		boolean flag = false;
		String[] array = host.split("\\.");
		for (String num : array) {
			if (!StringUtils.isNumeric(num)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	public static boolean isWeixin(HttpServletRequest request) {
		String agent = request.getHeader("User-Agent").toLowerCase();
		if (agent.indexOf("micromessenger") > 0) {
			return true;
		} else {
			return false;
		}
	}

	public static String isApp(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		String httpAccept = request.getHeader("Accept");
		UAgentInfo detector = new UAgentInfo(userAgent, httpAccept);
		String ismobile = "app";
		if (detector.detectMobileQuick()) {
			// 移动端浏览器
			ismobile = "app";
		} else {
			// PC浏览器
			ismobile = "pc";
		}
		return ismobile;
	}

	/**
	 * 返回 http://qx.bizideal.cn/whoami 域名或ip加项目名
	 * 
	 * @param request
	 * @return
	 */
	public static String getHttpAddress(HttpServletRequest request) {
		// allurl http://qx.bizideal.cn/whoami/meet/byhall/1
		// 非rest风格只获取url ?号后面的获取不到
		String allurl = request.getRequestURL().toString();
		// url /whoami/meet/byhall/1
		String url = request.getRequestURI();
		// contenpath /whoami
		String contenpath = request.getContextPath();
		// url /meet/byhall/1
		url = url.replace(contenpath, "");
		// redirect_uri http://qx.bizideal.cn/whoami
		String redirect_uri = allurl.replace(url, "");
		return redirect_uri;

	}

}