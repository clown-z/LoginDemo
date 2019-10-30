package com.clown.logindemo.util;


import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.util.IOUtils;
import com.clown.logindemo.entity.User;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.Header;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import javax.net.ssl.SSLContext;

public class HttpUtil {
        private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * http post请求，json格式传输参数
     *
     * @param map 参数对
     * @param url url地址
     * @return
     * @throws IOException
     */
    public static String Post(Map<String, Object> map,String url) throws Exception {
        CloseableHttpClient client = buildSSLCloseableHttpClient();
        HttpPost post = new HttpPost(url);
        //设置超时时间
        RequestConfig config = RequestConfig.custom().setConnectTimeout(10000)
                .setConnectionRequestTimeout(10000)
                .setSocketTimeout(10000).build();
        post.setConfig(config);
        StringEntity stringEntity = new StringEntity(JSON.toJSONString(map), "UTF-8");
        System.out.println(stringEntity.toString());
        stringEntity.setContentEncoding("UTF-8");
        stringEntity.setContentType("application/json");
        post.setEntity(stringEntity);
        CloseableHttpResponse response = null;
        try{
            response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity reEntity = response.getEntity();
                return EntityUtils.toString(reEntity);
            }
        }catch (IOException e){
            logger.error("", e);
        }
        return "";
    }

    private static CloseableHttpClient buildSSLCloseableHttpClient()
            throws Exception {
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null,
                new TrustStrategy() {
                    // 信任所有
                    public boolean isTrusted(X509Certificate[] chain,
                                             String authType) throws CertificateException {
                        return true;
                    }
                }).build();
        // ALLOW_ALL_HOSTNAME_VERIFIER:这个主机名验证器基本上是关闭主机名验证的,实现的是一个空操作，并且不会抛出javax.net.ssl.SSLException异常。
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslContext, new String[] { "TLSv1" }, null,
                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        return HttpClients.custom().setSSLSocketFactory(sslsf).build();
    }

    /**
     *
     * @param params  请求参数
     * @param url 请求地址
     * @return
     */
    public static String getWithHttp(Map<String, Object> params, String url) throws Exception {
        // 获取http客户端
        CloseableHttpClient client = buildSSLCloseableHttpClient();
        System.setProperty("jsse.enableSNIExtension", "false");
//        CloseableHttpClient client = HttpClients.createDefault();
        String apiUrl = "";
        //请求参数拼接
        StringBuffer  param = new StringBuffer();
        int i = 0;
        for (String key:params.keySet()) {
            if (i == 0)
                param.append("?");
            else
                param.append("&");
            param.append(key).append("=").append(params.get(key));
            i++;
        }

        apiUrl = url + param.toString();
        logger.info("apiUrl: " + apiUrl);
        System.out.println("apiUrl: " + apiUrl);
        // 通过httpget方式来实现我们的get请求
        HttpGet httpGet = new HttpGet(apiUrl);
        // 设置超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .setSocketTimeout(5000).build();
        httpGet.setConfig(requestConfig);
        // 通过client调用execute方法，得到我们的执行结果就是一个response，所有的数据都封装在response里面了
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpGet);
            logger.info("StatusCode: " + response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity resEntity = response.getEntity();
                String str = EntityUtils.toString(resEntity, "UTF-8");
                System.out.println("str: " + str);
                return str;
            }
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            response.close();
        }
        return "";
    }

    public static void main(String[] args) throws Exception {
        Map<String, Object> map = new HashMap();
//        User user = new User();
//        user.setUsername("testPost");
//        user.setPassword("123456");
        map.put("count", "2");
        map.put("page", "1");
        map.put("type", "video");
        //https://api.apiopen.top/getJoke?page=1&count=2&type=video
//        String get = getWithHttp(map, "https://api.apiopen.top/videoRecommend");
//        String get = getWithHttp(map, "http://localhost:8088/user/add");
        String pos = Post(map, "https://api.apiopen.top/getJoke");
//        String list = Post(map, "http://localhost:8088/login/list");
        System.out.println(pos);
//        System.out.println(get);
//        System.out.println(list);
    }
}
