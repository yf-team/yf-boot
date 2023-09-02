package com.yf.base.utils.http;


import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * HTTP请求工具类
 * @author bool
 * @date 2016-07-20 15:31
 */
public class HttpClientUtil {

	/**
	 * 常规URL的连接符号
	 */
	private static final String PARAM_STARTER = "?";
	private static final String PARAM_CONCAT = "&";
	private static final String ENCODING = "UTF-8";


	/**
	 * 使用POST方式提交数据并获得JSON
	 * @param url
	 * @param params
	 * @return
	 */
	public static String postRestJson(String url, Map<String,String> params) {

		CloseableHttpClient client = HttpClients.createDefault();
		try {

			HttpPost httpPost = new HttpPost(url);

			// 构造参数
			List<NameValuePair> list = new ArrayList<>();
			for (String key : params.keySet()) {
				BasicNameValuePair vp = new BasicNameValuePair(key, params.get(key));
				list.add(vp);
			}

			// 转换并传入参数
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list);
			httpPost.setEntity(formEntity);

			CloseableHttpResponse response = client.execute(httpPost);
			HttpEntity entity = response.getEntity();
			String str = EntityUtils.toString(entity, "UTF-8");
			// 关闭
			response.close();
			return str;

		}catch (Exception e){
			e.printStackTrace();
		}

		return null;
	}


	/**
	 * GET方法返回JSON数据
	 *
	 * @param url
	 * @return
	 */
	public static String getJson(String url, Map<String, String> headers, Map<String, String> params) {
		CloseableHttpClient client = HttpClients.createDefault();
		try {

			String fullUrl = buildParamsUrl(url, params);
			HttpGet httpGet = new HttpGet(fullUrl);

			// 循环加入文件头
			if (headers != null && !headers.isEmpty()) {
				for (String key : headers.keySet()) {
					httpGet.addHeader(key, headers.get(key));
				}
			}

			CloseableHttpResponse response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			String body = EntityUtils.toString(entity, ENCODING);
			return body;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}


	/**
	 * 构造一个带参数的GET形式URL
	 * @param url
	 * @param params
	 * @return
	 */
	public static String buildParamsUrl(String url, Map<String, String> params) {

		// 拼接参数
		if (params != null && !params.isEmpty()) {

			StringBuffer sb = new StringBuffer(url);

			//判断URL是否已经有问题号了
			if (url.indexOf(PARAM_STARTER) == -1) {
				sb.append(PARAM_STARTER);
			}

			for (String key : params.keySet()) {

				if(!sb.toString().endsWith(PARAM_STARTER)) {
					sb.append(PARAM_CONCAT);
				}

				String value = params.get(key);
				if (StringUtils.isBlank(value)) {
					value = "";
				} else {
					// 值做一下URL转码
					try {
						value = URLEncoder.encode(value, ENCODING);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
						continue;
					}
				}
				sb.append(key).append("=").append(value);
			}

			return sb.toString();
		}

		return url;
	}

	/**
	 * 判断URL资源是否存在
	 * @param input
	 * @return
	 */
	public static boolean exist(String input, String type) {
		try {
			URL url = new URL(input);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			int code = conn.getResponseCode();
			System.out.println("++++++检测URL："+input+"\n响应代码："+code);
			if(code == 200){
				String contentType = conn.getContentType();
				System.out.println("++++数据类型："+contentType);
				return contentType.contains(type);
			}
			return code==200;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public static void main(String[] args) {
		boolean sss = exist("https://cdn.jeegen.com/2022/6/17/1655436586635-3aeecedc.jpg", "image");
		System.out.println(sss);
	}

}
