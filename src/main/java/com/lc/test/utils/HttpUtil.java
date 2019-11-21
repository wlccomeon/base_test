package com.lc.test.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigDecimal;
import java.net.URI;
import java.util.*;

/**
 * HTTP工具类
 * @author why
 * @version V1.0 2019年06月20日13:18:23
 */
public class HttpUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);
	private static final PoolingHttpClientConnectionManager CONNECTION_MANAGER = new PoolingHttpClientConnectionManager();
	private static final CloseableHttpClient HTTP_CLIENT;
	private static final Integer READ_TIMEOUT = 60_000;
	private static final Integer CONNECT_TIMEOUT = 5_000;
	private static final Integer CONNECTION_REQUEST_TIMEOUT = 5_000;

	static{
		CONNECTION_MANAGER.setMaxTotal(100);
		CONNECTION_MANAGER.setDefaultMaxPerRoute(CONNECTION_MANAGER.getMaxTotal());
		HTTP_CLIENT = HttpClients.custom().setConnectionManager(CONNECTION_MANAGER).build();
	}

	/**
	 * 向指定url发送get请求
	 *
	 * @param url 目标url
	 * @return 响应体字符串
	 */
	public static String httpGet(String url){
		return httpGet(url, null);
	}

	/**
	 * 向指定url发送get请求
	 * get请求的参数是拼接在url之后，有一定长度限制
	 *
	 * @param url 目标url
	 * @param params 请求参数
	 * @return 响应体字符串
	 */
	public static String httpGet(String url, Map<String, String> params){
		return httpGet(url, params, null, null);
	}

	/**
	 * 向指定url发送get请求（包含header，可空，cookie可单独处理，也可空）
	 * get请求的参数是拼接在url之后，有一定长度限制
	 *
	 * @param url 目标url
	 * @param params 请求参数
	 * @param cookies cookie集合
	 * @param headers header集合
	 * @return 响应体字符串
	 */
	public static String httpGet(String url, Map<String, String> params, Map<String, String> cookies, Map<String, String> headers) {

		HttpGet httpGet = null;
		try{
			String queryString = assembleQueryString(params);
			// get请求对象
			httpGet = createHttpGet(Strings.isNullOrEmpty(queryString) ? url : url + "?" + queryString);

			// 设置cookie
			HttpContext httpContext = loadCookies(cookies);

			// 设置header
			loadHeaders(httpGet, headers);
			// 发送get请求获取响应体字符串
			return execute(httpGet, httpContext);
		}finally {
			try {
				if(httpGet != null){
					httpGet.releaseConnection();
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * 向指定url发送post请求，请求体为json串
	 *
	 * @param url 目标url
	 * @param params 参数对象
	 * @return 响应体字符串
	 */
	public static String jsonPost(String url, Object params){
		return jsonPost(url, params, null, null);
	}

	/**
	 * 向指定url发送post请求，请求体为json串（包含header，可空，cookie可单独处理，也可空）
	 *
	 * @param url 目标url
	 * @param params 参数对象
	 * @param cookies cookie集合
	 * @param headers header集合
	 * @return 响应体的字符串表示
	 */
	public static String jsonPost(String url, Object params, Map<String, String> cookies, Map<String, String> headers){
		HttpPost httpPost = null;
		try{
			// 创建post对象
			httpPost = createHttpPost(url);

			// 设置cookie
			HttpContext httpContext = loadCookies(cookies);

			// 设置header
			loadHeaders(httpPost, headers, true);

			// 设置请求体
			loadStringEntity(httpPost, params);

			// 发送请求并获取响应体
			return execute(httpPost, httpContext);
		}finally {
			try{
				if(httpPost != null){
					httpPost.releaseConnection();
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * 向指定url发送post请求，请求体为表单参数
	 *
	 * @param url 请求地址
	 * @param params 请求参数
	 * @return 响应体的字符串表示
	 * 30秒超时，请求发送失败，响应码非200都会抛RuntimeException
	 */
	public static String formPost(String url, Map<String, String> params){
		return formPost(url, params, null, null);
	}

	/**
	 * 向指定url发送post请求，请求体为表单参数（包含cookie和header，可空）
	 *
	 * @param url 请求地址
	 * @param params 请求参数
	 * @param cookies cookie容器
	 * @param headers header容器
	 * @return 响应体的字符串表示
	 * @throws RuntimeException
	 * 30秒超时，请求发送失败，响应码非200都会抛RuntimeException
	 */
	public static String formPost(String url, Map<String, String> params, Map<String, String> cookies, Map<String, String> headers) {
		HttpPost httpPost = null;
		try{
			// 创建post请求对象
			httpPost = createHttpPost(url);

			// 设置cookie
			HttpContext httpContext = loadCookies(cookies);

			// 设置header
			loadHeaders(httpPost, headers, false);

			// 设置请求体
			loadFormEntity(httpPost, params);

			// 获取响应体字符串
			return execute(httpPost, httpContext);
		}finally {
			try {
				if(httpPost != null){
					httpPost.releaseConnection();
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	private static String execute(HttpRequestBase httpRequest, HttpContext httpContext){
		CloseableHttpResponse response = null;
		try {
			response = execute(httpRequest, httpContext, "utf-8");
			return getResponseString(response, "utf-8");
		} catch (IOException ignored) {
		} finally {
			IOUtils.closeQuietly(response);
		}
		return null;
	}


	/**
	 * 发送http请求
	 *
	 * @param httpRequest 请求对象
	 * @param httpContext 请求上下文对象
	 * @return 响应对象,调用完毕response对象必须手动close
	 */
	private static CloseableHttpResponse execute(HttpRequestBase httpRequest, HttpContext httpContext, String charset) throws IOException {
		CloseableHttpResponse response;

		String requestInfo = getRequestInfo(httpRequest ,charset);

		try {
			LOGGER.info("向" + requestInfo + " 发送请求");
			response = HTTP_CLIENT.execute(httpRequest, httpContext);
		}
		catch (IOException e) {
			httpRequest.abort();
			throw new RuntimeException("通信异常：" + e.getMessage());
		}

		LOGGER.info("成功获取来自 " + requestInfo + " 的响应");

		StatusLine statusLine = response.getStatusLine();
		int statusCode = statusLine.getStatusCode();
		LOGGER.info("响应码: " + statusCode);

		LOGGER.info("响应头: " + Arrays.toString(response.getAllHeaders()));

		// TODO 重定向302
		// 响应码小于200或者大于等于300
		if (statusCode < HttpStatus.SC_OK || statusCode >= HttpStatus.SC_MULTIPLE_CHOICES) {
			String reason = statusLine.getReasonPhrase();
			httpRequest.abort();
			throw new RuntimeException("请求发生错误：" + statusCode + " - " + reason);
		}

		return response;
	}

	private static String getRequestInfo(HttpRequestBase httpRequest, String charset) throws IOException {
		String requestInfo = httpRequest.getURI().toString();
		if(httpRequest instanceof HttpPost){
			HttpEntity entity = ((HttpPost) httpRequest).getEntity();
			if(entity != null){
				requestInfo += "\n" + EntityUtils.toString(entity, charset);
			}
		}

		return requestInfo;
	}

	/**
	 * 设置表单参数格式的请求体
	 *
	 * @param httpPost post请求对象
	 * @param params 参数集合
	 */
	private static void loadFormEntity(HttpPost httpPost, Map<String, String> params) {
		if(params != null){
			UrlEncodedFormEntity formEntity = null;
			LOGGER.info("请求参数: " + params.toString());
			try {
				formEntity = new UrlEncodedFormEntity(getValuePairs(params), "utf-8");
			} catch (UnsupportedEncodingException e) {
				LOGGER.error("", e);
			}
			httpPost.setEntity(formEntity);
		}
	}

	/**
	 * 设置header
	 *
	 * @param httpRequest 请求对象
	 * @param headers header集合
	 */
	private static void loadHeaders(HttpRequestBase httpRequest, Map<String, String> headers){
		loadHeaders(httpRequest, headers, null);
	}

	/**
	 * 设置header
	 *
	 * @param httpRequest 请求对象
	 * @param headers header集合
	 */
	private static void loadHeaders(HttpRequestBase httpRequest, Map<String, String> headers, Boolean isJson) {
		Map<String, String> headersMap = new HashMap<>();
		if(isJson != null){
			headersMap.put("Content-Type", "application/json;charset=utf-8");
			if(!isJson){
				headersMap.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			}
		}
		if(!MapUtils.isEmpty(headers)){
			headersMap.putAll(headers);
		}
		httpRequest.setHeaders(getHeadersNew(headersMap));
	}

	/**
	 * 创建post请求对象
	 *
	 * @param url 目标url
	 * @return post请求对象
	 */
	private static HttpPost createHttpPost(String url) {
		if(Strings.isNullOrEmpty(url)){
			throw new RuntimeException("请求地址不能为空！");
		}
		HttpPost httpPost = new HttpPost(URI.create(url));
		// 30秒超时
		setTimeout(httpPost);

		return httpPost;
	}

	/**
	 * 通过cookie容器获取cookie
	 *
	 * @param cookies cookie容器
	 * @return cookie存储对象
	 */
	private static CookieStore getCookieStore(Map<String, String> cookies) {
		CookieStore cookieStore = new BasicCookieStore();
		if(!MapUtils.isEmpty(cookies)){
			LOGGER.info("请求cookies: " + cookies.toString());
			for(Map.Entry<String, String> entry : cookies.entrySet()){
				cookieStore.addCookie(new BasicClientCookie(entry.getKey(), entry.getValue()));
			}
		}
		return cookieStore;
	}

	/**
	 * 通过header容器获取header
	 *
	 * @param headersMap header容器
	 * @return header数组
	 */
	private static Header[] getHeaders(Map<String, String> headersMap) {
		Header[] headers;
		if(!MapUtils.isEmpty(headersMap)){
			headers = new Header[headersMap.size() + 1];
			int i = 1;
			for(Map.Entry<String, String> entry : headersMap.entrySet()){
				headers[i] = new BasicHeader(entry.getKey(), entry.getValue());
				i++;
			}
		}else{
			headers = new Header[1];
		}
		headers[0] = new BasicHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		LOGGER.info("请求头: " + Arrays.toString(headers));
		return headers;
	}

	/**
	 * 通过header容器获取header
	 *
	 * @param headersMap header容器
	 * @return header数组
	 */
	private static Header[] getHeadersNew(Map<String, String> headersMap) {
		Header[] headers;
		if(!MapUtils.isEmpty(headersMap)){
			headers = new Header[headersMap.size()];
			int i = 0;
			for(Map.Entry<String, String> entry : headersMap.entrySet()){
				headers[i] = new BasicHeader(entry.getKey(), entry.getValue());
				i++;
			}
			LOGGER.info("请求头: " + Arrays.toString(headers));
			return headers;
		}

		return null;
	}

	/**
	 * 处理参数容器
	 *
	 * @param params 参数容器
	 * @param actionSubject 动作主体
	 */
	private static void manageParams(Map<String, String> params, Object actionSubject){
		MultiValueMap valueMap;
		String key;
		String value;
		if(!MapUtils.isEmpty(params)) {
			if (params instanceof MultiValueMap) {
				valueMap = (MultiValueMap) params;
				for (Object aKey : valueMap.keySet()) {
					try {
						key = (String) aKey;
						Collection collection = valueMap.getCollection(key);
						for (Object val : collection) {
							value = val == null ? "" : val.toString();
							loadParams(actionSubject, key, value);
						}
					} catch (ClassCastException e) {
						throw new RuntimeException("请求参数获取失败：MultiValueMap的key必须为String类型！");
					}
				}
			} else {
				for (Map.Entry<String, String> entry : params.entrySet()) {
					key = entry.getKey();
					value = entry.getValue() == null ? "" : entry.getValue();
					loadParams(actionSubject, key, value);
				}
			}
		}
	}

	/**
	 * 将参数加载进动作主体
	 *
	 * @param actionSubject 加载目标主体
	 * @param key 参数名
	 * @param value 参数值
	 */
	private static void loadParams(Object actionSubject, String key, String value) {
		StringBuilder sb;
		List<NameValuePair> list;
		if(actionSubject instanceof StringBuilder){
			sb = (StringBuilder) actionSubject;
			sb.append(key).append("=").append(value).append("&");
		}else if(actionSubject instanceof List){
			//noinspection unchecked
			list = (List<NameValuePair>) actionSubject;
			list.add(new BasicNameValuePair(key, value));
		}
	}

	/**
	 * 组装url查询字符串
	 *
	 * @param params 请求参数集合
	 * @return 查询字符串
	 */
	private static String assembleQueryString(Map<String, String> params) {
		StringBuilder queryString = new StringBuilder("");
		manageParams(params, queryString);
		return queryString.length() == 0 ? queryString.toString() : queryString.substring(0, queryString.length() - 1);
	}

	/**
	 * 通过请求参数容器获取参数键值对集合
	 *
	 * @param params 参数容器
	 * @return 参数键值对集合
	 */
	private static List<? extends NameValuePair> getValuePairs(Map<String, String> params) {
		int size = params == null ? 0 : params.size();
		List<NameValuePair> nameValuePairs = new ArrayList<>(size);
		manageParams(params, nameValuePairs);
		return nameValuePairs;
	}

	/**
	 * 设置cookie
	 *
	 * @param cookies cookie集合
	 * @return http上下文对象
	 */
	private static HttpContext loadCookies(Map<String,String> cookies) {
		HttpContext httpContext = new BasicHttpContext();
		httpContext.setAttribute(HttpClientContext.COOKIE_STORE, getCookieStore(cookies));
		return httpContext;
	}

	/**
	 * 设置请求体
	 *
	 * @param httpPost post请求对象
	 * @param params 参数对象
	 */
	private static void loadStringEntity(HttpPost httpPost, Object params) {
		if(params != null){
			String entity;
			if(params instanceof String){
				entity = (String) params;
			}else{
				entity = JSON.toJSONStringWithDateFormat(params, "yyyy-MM-dd HH:mm:ss");
			}
			LOGGER.info("请求体: " + entity);

			httpPost.setEntity(new StringEntity(entity, "utf-8"));
		}
	}

	/**
	 * 创建get请求对象
	 *
	 * @param url 目标url
	 * @return get请求对象
	 */
	private static HttpGet createHttpGet(String url) {
		if(Strings.isNullOrEmpty(url)){
			throw new RuntimeException("请求地址不能为空！");
		}
		HttpGet httpGet = new HttpGet(URI.create(url));

		// 超时设置
		setTimeout(httpGet);

		return httpGet;
	}

	/**
	 * 设置请求超时时间
	 *
	 * @param httpRequest 请求对象
	 */
	private static void setTimeout(HttpRequestBase httpRequest) {
		RequestConfig.Builder builder = RequestConfig.custom();
		builder.setConnectTimeout(CONNECT_TIMEOUT);
		LOGGER.info("连接超时设置: " + CONNECT_TIMEOUT/1000 + " 秒");
		builder.setSocketTimeout(READ_TIMEOUT);
		LOGGER.info("读取响应超时设置: " + READ_TIMEOUT/1000 + " 秒");
		builder.setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT);
		LOGGER.info("连接池获取连接超时设置: " + CONNECTION_REQUEST_TIMEOUT/1000 + " 秒");
		httpRequest.setConfig(builder.build());
	}

	public static String post(String url, String requestBody){
		return post(url, requestBody, "utf-8");
	}

	public static String post(String url, String requestBody, String charset){
		return post(url, requestBody, null, charset);
	}

	public static String post(String url, String requestBody, Map<String, String> headers){
		return post(url, requestBody, headers, "utf-8");
	}

	public static String post(String url, String requestBody, Map<String, String> headers, String charset){
		StringEntity stringEntity = new StringEntity(requestBody, charset);
		return post(url, stringEntity, headers, charset);
	}

	/**
	 * 一定要关闭response对象
	 *
	 * @param url
	 * @return
	 */
	public static CloseableHttpResponse getResponse(String url){
		return getResponse(url, null);
	}

	/**
	 * 一定要关闭response对象
	 *
	 * @param url
	 * @return
	 */
	public static CloseableHttpResponse getResponse(String url, Map<String, String> headers){
		return getResponse(url, headers, "utf-8");
	}

	/**
	 * 一定要关闭response对象
	 *
	 * @param url
	 * @return
	 */
	public static CloseableHttpResponse getResponse(String url, Map<String, String> headers, String charset){
		HttpGet httpGet = new HttpGet(URI.create(url));

		httpGet.setHeaders(getHeadersNew(headers));

		return response(httpGet, charset);
	}

	private static CloseableHttpResponse response(HttpRequestBase httpRequest, String charset) {
		try {
			return execute(httpRequest, null, charset);
		} catch (IOException e) {
			httpRequest.abort();
			LOGGER.error("", e);
			throw new RuntimeException("不支持的字符集：" + charset);
		}
	}

	/**
	 * 一定要关闭response对象
	 *
	 * @param url
	 * @return
	 */
	private static CloseableHttpResponse postResponse(String url, String requestBody){
		return postResponse(url, requestBody, null);
	}

	/**
	 * 一定要关闭response对象
	 *
	 * @param url
	 * @return
	 */
	private static CloseableHttpResponse postResponse(String url, String requestBody, Map<String, String> headers){
		return postResponse(url, requestBody, headers, "utf-8");
	}

	/**
	 * 一定要关闭response对象
	 *
	 * @param url
	 * @return
	 */
	private static CloseableHttpResponse postResponse(String url, String requestBody, Map<String, String> headers, String charset){
		HttpPost httpPost = new HttpPost(URI.create(url));

		httpPost.setHeaders(getHeadersNew(headers));

		httpPost.setEntity(new StringEntity(requestBody, charset));


		return response(httpPost, charset);
	}

	private static String post(String url, StringEntity stringEntity, String charset){
		return post(url, stringEntity, null, charset);
	}

	private static String post(String url, StringEntity stringEntity, Map<String, String> headers, String charset){

		HttpPost httpPost = null;

		CloseableHttpResponse response = null;
		try {
			httpPost = new HttpPost(URI.create(url));

			httpPost.setHeaders(getHeadersNew(headers));

			httpPost.setEntity(stringEntity);

			response = execute(httpPost, null, charset);
			return getResponseString(response, charset);
		} catch (IOException e) {
			LOGGER.error("", e);
			throw new RuntimeException("不支持的字符集：" + charset);
		} finally {
			IOUtils.closeQuietly(response);
			try {
				if(httpPost != null){
					httpPost.releaseConnection();
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	private static String getResponseString(HttpResponse response, String charset) throws IOException {
		String result = EntityUtils.toString(response.getEntity(), charset);
		if(result.length() <= 1000){
			LOGGER.info("响应体: " + result);
		}
		return result;

	}
//    public static String httpPost4File(String url, Map<String, String> params, String filePath, Map<String, String> cookies, Map<String, String> headers) {
//        LOGGER.info("发送文件：开始向 " + url + " 发送请求");
//        CloseableHttpClient httpClient = null;
//        // 响应对象
//        CloseableHttpResponse response = null;
//        try {
//            httpClient = HttpClients.createDefault();
//            HttpPost httpPost = createHttpPost(url);
//
//            // 设置cookie
//            HttpContext httpContext = new BasicHttpContext();
//            httpContext.setAttribute(HttpClientContext.COOKIE_STORE, getCookieStore(cookies));
//
//            // 设置header
//            Header[] header = getHeaders(headers);
//            header[0] = new BasicHeader("Content-Type", "image/jpeg");
//            httpPost.setHeaders(header);
//
//            File file = new File(filePath);
//
//            String imgSuffix = StringUtils.substringAfterLast(file.getName(),".");
//            FileEntity fileEntity = null;
//            Boolean isCompress = new BigDecimal(file.length()).compareTo(new BigDecimal(sizeM)) > 0;
//            if(isCompress && (imgSuffix.equalsIgnoreCase("JPG") || imgSuffix.equalsIgnoreCase("PNG") ||
//                    imgSuffix.equalsIgnoreCase("JPEG") || imgSuffix.equalsIgnoreCase("BMP"))){
//                File compressFile = compressPic(file);
//                fileEntity = new FileEntity(compressFile);
//            }else{
//                fileEntity = new FileEntity(file);
//            }
//
//            httpPost.setEntity(fileEntity);
//
//            // 设置请求体
//            loadFormEntity(httpPost, params);
//            LOGGER.info("开始向 " + url + " 发送请求");
//            response = httpClient.execute(httpPost, httpContext);
//            LOGGER.info("成功获取响应");
//
//            StatusLine statusLine = response.getStatusLine();
//            int statusCode = statusLine.getStatusCode();
//            LOGGER.info("响应码: " + statusCode);
//            LOGGER.info("响应头: " + Arrays.toString(response.getAllHeaders()));
//
//            // TODO 重定向
//            // 响应码小于200或者大于等于300
//            if (statusCode < HttpStatus.SC_OK || statusCode >= HttpStatus.SC_MULTIPLE_CHOICES) {
//                throw new RuntimeException("请求发生错误：" + statusCode + " - " + statusLine.getReasonPhrase());
//            }
//
//            String result = EntityUtils.toString(response.getEntity(), "utf-8");
//            LOGGER.info("响应体: " + result);
//            JSONObject json = JSON.parseObject(result);
//            if(json.get("hash") != null && json.get("hash").equals("-1")){
//                throw new RuntimeException("上传文件失败：请检查文件大小是否超过1M!");
//            }
//            return result;
//        }  catch (ParseException e) {
//            LOGGER.error("解析响应体失败！", e);
//            throw new RuntimeException("解析请求响应失败！", e);
//        } catch (IOException e) {
//            LOGGER.error("请求失败!", e);
//            throw new RuntimeException("请求失败！", e);
//        }finally {
//            IOUtils.closeQuietly(response);
//            IOUtils.closeQuietly(httpClient);
//        }
//    }
//    public static String httpPost4File(String url, Map<String, String> params, String filePath) {
//        return httpPost4File(url, params, filePath, null, null);
//    }

//    /**
//     * 获取远程文件流，将其转发
//     * @param url
//     * @param params
//     * @param remotePath
//     * @return
//     */
//    public static String httpPostFile(String url, Map<String, String> params, String remotePath,Map<String, String> cookies) {
//        LOGGER.info("发送文件：开始向 " + url + " 发送请求");
//        CloseableHttpClient httpClient = null;
//        //远程文件流响应对象
//        CloseableHttpResponse remoteResponse = null;
//        // 响应对象
//        CloseableHttpResponse response = null;
//        try {
//            httpClient = HttpClients.createDefault();
//            HttpPost httpPost = createHttpPost(url);
//
//            // 设置cookie
//            HttpContext httpContext = new BasicHttpContext();
//            httpContext.setAttribute(HttpClientContext.COOKIE_STORE, getCookieStore(cookies));
//
//            //获取文件大小，判断是否需要压缩
//            remoteResponse = getResponse(remotePath);
//            byte[] bytes = EntityUtils.toByteArray(remoteResponse.getEntity());
//            String imgSuffix = StringUtils.substringAfterLast(remotePath,".");
//            FileEntity fileEntity = null;
//            Boolean isCompress = new BigDecimal(bytes.length).compareTo(new BigDecimal(sizeM)) > 0;
//            if(isCompress && (imgSuffix.equalsIgnoreCase("JPG") || imgSuffix.equalsIgnoreCase("PNG") ||
//                    imgSuffix.equalsIgnoreCase("JPEG") || imgSuffix.equalsIgnoreCase("BMP"))){
//                File file = new File(PIC_PATH +File.separator+ UUID.randomUUID()+"."+imgSuffix);
//                if (!file.getParentFile().exists()) {
//                    file.getParentFile().mkdirs();
//                }
//                OutputStream os = null;
//                try{
//                    os = new BufferedOutputStream(new FileOutputStream(file));
//                    os.write(bytes);
//                    File compressFile = compressPic(file);
//                    fileEntity = new FileEntity(compressFile);
//                    httpPost.setEntity(fileEntity);
//                }catch (IOException e){
//                    e.printStackTrace();
//                }finally {
//                    if(os != null){
//                        os.close();
//                    }
//                }
//            }else{
//                //获取远程文件流
//                remoteResponse = getResponse(remotePath);
//                httpPost.setEntity(remoteResponse.getEntity());
//            }
//
//            // 设置请求体
//            loadFormEntity(httpPost, params);
//            LOGGER.info("开始向 " + url + " 发送请求");
//            response = httpClient.execute(httpPost, httpContext);
//            LOGGER.info("成功获取响应");
//
//            StatusLine statusLine = response.getStatusLine();
//            int statusCode = statusLine.getStatusCode();
//            LOGGER.info("响应码: " + statusCode);
//            LOGGER.info("响应头: " + Arrays.toString(response.getAllHeaders()));
//
//            // 响应码小于200或者大于等于300
//            if (statusCode < HttpStatus.SC_OK || statusCode >= HttpStatus.SC_MULTIPLE_CHOICES) {
//                throw new RuntimeException("请求发生错误：" + statusCode + " - " + statusLine.getReasonPhrase());
//            }
//
//            String result = EntityUtils.toString(response.getEntity(), "utf-8");
//            LOGGER.info("响应体: " + result);
//            JSONObject json = JSON.parseObject(result);
//            if(json.get("hash") != null && json.get("hash").equals("-1")){
//                throw new RuntimeException("上传文件失败：请检查文件大小是否超过1M!");
//            }
//            return result;
//        }  catch (ParseException e) {
//            LOGGER.error("解析响应体失败！", e);
//            throw new RuntimeException("解析请求响应失败！", e);
//        } catch (IOException e) {
//            LOGGER.error("请求失败!", e);
//            throw new RuntimeException("请求失败！", e);
//        }finally {
//            IOUtils.closeQuietly(response);
//            IOUtils.closeQuietly(remoteResponse);
//            IOUtils.closeQuietly(httpClient);
//        }
//    }

	private static final long sizeM = 1024 << 10;
	private static final String PIC_PATH = "/pqsoft/chewu";
//    private static File compressPic(File fromFile) throws IOException{
//        String toPath = fromFile.getParent()+File.separator+"compress_"+
//                StringUtils.substringBeforeLast(fromFile.getName(),".");
//        String suffPix = StringUtils.substringAfterLast(fromFile.getName(), ".");
//
//        File toFile = new File(toPath+".jpg");
//        long imageSize = fromFile.length();
//        //获取压缩比例
//        BigDecimal scale = new BigDecimal(sizeM).divide(
//                new BigDecimal(imageSize),2,BigDecimal.ROUND_DOWN);
//        scale = scale.subtract(new BigDecimal(0.05));
//        BigDecimal half = new BigDecimal(0.35);
//        if(scale.compareTo(half) > 0){
//            scale = half;
//        }
//        if("JPEG".equalsIgnoreCase(suffPix) || "JPG".equalsIgnoreCase(suffPix)){
//            scale = scale.divide(new BigDecimal(2));
//        }
//        Thumbnails.of(fromFile).scale(1.0F).outputQuality(scale.doubleValue()).toFile(toFile);
//        return toFile;
//    }




}
