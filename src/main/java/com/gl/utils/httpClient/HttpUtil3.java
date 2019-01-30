package com.gl.utils.httpClient;


import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author green
 * @date 2018/12/6/006
 */
public class HttpUtil3 {



        public static String doGet(String url, Map<String, String> param) {
            // 创建Httpclient对象
            CloseableHttpClient httpclient = HttpClients.createDefault();
            String resultString = "";
            CloseableHttpResponse response = null;
            try {
                // 创建uri
                URIBuilder builder = new URIBuilder(url);
                if (param != null) {
                    for (String key : param.keySet()) {
                        builder.addParameter(key, param.get(key));
                    }
                }
                URI uri = builder.build();

                // 创建http GET请求
                HttpGet httpGet = new HttpGet(uri);

                // 执行请求
                response = httpclient.execute(httpGet);
                // 判断返回状态是否为200
                if (response.getStatusLine().getStatusCode() == 200) {
                    resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (response != null) {
                        response.close();
                    }
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return resultString;
        }

        public static String doGet(String url) {
            return doGet(url, null);
        }

        public static String doPost(String url, Map<String, String> param) {
            // 创建Httpclient对象
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = null;
            String resultString = "";
            try {
                // 创建Http Post请求
                HttpPost httpPost = new HttpPost(url);
                // 创建参数列表
                if (param != null) {
                    List<NameValuePair> paramList = new ArrayList<>();
                    for (String key : param.keySet()) {
                        paramList.add(new BasicNameValuePair(key, param.get(key)));
                    }
                    // 模拟表单
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "utf-8");
                    httpPost.setEntity(entity);
                }
                // 执行http请求
                response = httpClient.execute(httpPost);
                resultString = EntityUtils.toString(response.getEntity(), "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return resultString;
        }

        public static String doPost(String url) {
            return doPost(url, null);
        }

        /**
         * 请求的参数类型为json
         * @param url
         * @param json
         * @return
         * {username:"",pass:""}
         */
        public static String doPostJson(String url, String json) {
            // 创建Httpclient对象
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = null;
            String resultString = "";
            try {
                // 创建Http Post请求
                HttpPost httpPost = new HttpPost(url);
                // 创建请求内容
                StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
                httpPost.setEntity(entity);
                // 执行http请求
                response = httpClient.execute(httpPost);
                resultString = EntityUtils.toString(response.getEntity(), "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return resultString;
        }

    public static void main(String[] args) {
//        JSONObject jsonObject = new JSONObject();
////        jsonObject.put("userid","GeLin");
////        String s = doPostJson("https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_openid?access_token=sM7SivX8nay2u94JDNlnD-ghgQ5EbpyavGA5XhyLUn8DOvW7hcF8v3QLLctJ7vgIxkW6urUw6Fsyh3L7SEewwGFs8BDuyCFq_4cnkhbhaBPxccsGKyY20xYeHTywEF4cUyrcndO1EsyD_K5t02IkJ48SxJvyAEPsf6MJWyzkngJky8UiyptIJKyi5v9teuZz5YVJBikDkk45izyfTgpGCw",jsonObject.toJSONString());
////        System.out.println(s);
//        jsonObject.put("touser","GeLin");
//        jsonObject.put("toparty","");
//        jsonObject.put("totag","");
//        jsonObject.put("msgtype","text");
//        jsonObject.put("agentid",1000007);
//        Content content = new Content();
//        content.content = "你有一个会议马上开始了哟";
//        jsonObject.put("text", content);
//        System.out.println(jsonObject.toJSONString());
//        String s = doPostJson("https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ab9_G9_-kOE-uV1ZGr67zaVOMjp9rKfSH6SrI-ahqgpHdlNXu8n9eU7YtbDy3SrB67BNlVMi9vaUszgoU863nVGbvlIvXd1w2Ozo_FMClFqiGqIuz39ZYXGicvYarOiYbx11CyX_6rpLccFSW9iC7HpJASAaXphFBQh8__4xLXivNmU7uRUkhD9PVxkZxJFcZEemRzwLsQOOhI2xgCSijw",jsonObject.toJSONString());
//        System.out.println(s);
    }

    public static class Content{
        public String content;
        public Content(){
            content = "";
        }
    }
}
