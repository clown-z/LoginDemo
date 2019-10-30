package com.clown.logindemo.util;

import com.clown.logindemo.entity.User;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpClient {

    public static void main(String[] args) throws Exception {
//        testPost2();
//        testGetMethod();
//        testPostMethod();
        String url = "http://api.apiopen.top/getJoke";
        Map<String, String> map = new HashMap<>();
//        map.put("username", "doPost");
//        map.put("password", "12345");
//        map.put("count", "2");
//        map.put("page", "1");
//        map.put("type", "video");
        String s = requestUtil.httpsRequest(url, "POST", "count=2&page=1&type=video");
        System.out.println("s: " + s);

    }

    public static void testGetMethod() throws IOException {
        // 创建httpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建httpget实例
        HttpGet get = new HttpGet("http://localhost:8088/user/all");
        // 执行http get请求
        CloseableHttpResponse res = httpClient.execute(get);
        // 获取返回实体
        HttpEntity entity =  res.getEntity();
        // 获取网页内容
        System.out.println("返回结果： " + EntityUtils.toString(entity, "UTF-8"));
        // response关闭
        res.close();
        // httpClient关闭
        httpClient.close();
    }
    /**
     * 发送对应的值到指定的URL，不用key-value值传过去，可以直接put一个json字符串过去，也可以是xml字符串
     * @throws IOException
     */
    public static void testPost2() throws IOException {
        // 创建httpClient实例
        CloseableHttpClient client = HttpClients.createDefault();
        //创建httpPost实例
        HttpPost post = new HttpPost("http://localhost:8088/user/add");
        post.setEntity(new StringEntity("通过xml工具类转成xml字符串", "UTF-8"));
        CloseableHttpResponse res = client.execute(post);
        System.out.println("StatusLine" + res.getStatusLine());
        HttpEntity entity = res.getEntity();
        System.out.println("返回结果： " + EntityUtils.toString(entity, "UTF-8"));
        res.close(); // response关闭
        client.close(); // httpClient关闭
    }

    public static void testPostMethod() throws IOException {
        // 创建httpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8088/user/add");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicHeader("username", "testPostMethod"));
        params.add(new BasicHeader("password", "123456"));
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        CloseableHttpResponse res = httpClient.execute(httpPost);
        System.out.println(res.getStatusLine());
        HttpEntity entity = res.getEntity();
        System.out.println("返回结果： " + EntityUtils.toString(entity, "UTF-8"));
        res.close();
        httpClient.close();
    }



}
