package com.gl.utils.httpClient;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author green
 * @date 2018/12/4/004
 */
public class HttpUtil {
    private static Logger logger = Logger.getLogger(HttpUtil.class);

    /**
     * 方法功能说明：    http的doGet请求
     * @参数： @param url
     * @参数： @param map
     * @参数： @return
     * @return String
     * @throws
     */
    public static String doGet(String url,Map map) throws Exception{
        String params = HttpUtil.formatUrlMap(map, false, false);
        //拼接成的带参数的url
        url = url+"?"+params;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity());
                return strResult;
            }
        }
        catch (IOException e) {
            logger.error("HttpUtil.doGet():出错了",e);
        }
        return null;
    }

    /**
     * post请求(用于key-value格式的参数)
     * @param url
     * @param params
     * @return
     */
    public static String doPost(String url, Map params){
        BufferedReader in = null;
        try {
            // 定义HttpClient
            HttpClient client = HttpClientBuilder.create().build();
            // 实例化HTTP方法
            HttpPost request = new HttpPost();
            request.setURI(new URI(url));
            //设置参数
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String value = String.valueOf(params.get(name));
                nvps.add(new BasicNameValuePair(name, value));
                //System.out.println(name +"-"+value);
            }
            request.setEntity(new UrlEncodedFormEntity(nvps,StandardCharsets.UTF_8));

            HttpResponse response = client.execute(request);
            int code = response.getStatusLine().getStatusCode();
            if(code == 200){	//请求成功
                in = new BufferedReader(new InputStreamReader(response.getEntity()
                        .getContent(),"utf-8"));
                StringBuffer sb = new StringBuffer("");
                String line = "";
                String NL = System.getProperty("line.separator");
                while ((line = in.readLine()) != null) {
                    sb.append(line + NL);
                }
                in.close();
                return sb.toString();
            }
            else{
                logger.error("HttpUtil.doPost():状态码："+ code);
                return null;
            }
        }
        catch(Exception e){
            logger.error("HttpUtil.doPost():出错了",e);
            return null;
        }
    }

    /**
     *
     * 方法用途: 对所有传入参数按照字段名的Unicode码从小到大排序（字典序），并且生成url参数串<br>
     * 实现步骤: <br>
     *
     * @param paraMap   要排序的Map对象
     * @param urlEncode   是否需要URLENCODE
     * @param keyToLower    是否需要将Key转换为全小写
     *            true:key转化成小写，false:不转化
     * @return
     */
    public static String formatUrlMap(Map<String, String> paraMap, boolean urlEncode, boolean keyToLower)
    {
        String buff = "";
        Map<String, String> tmpMap = paraMap;
        try
        {
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(tmpMap.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>()
         {
                @Override
                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2)
                {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });
            // 构造URL 键值对的格式
            StringBuilder buf = new StringBuilder();
            for (Map.Entry<String, String> item : infoIds)
            {
                //StringUtils类需要依赖commons-lang3.jar包
                if (StringUtils.isNotBlank(item.getKey()))
                {
                    String key = item.getKey();
                    String val = item.getValue();
                    if (urlEncode)
                    {
                        val = URLEncoder.encode(val, "utf-8");
                    }
                    if (keyToLower)
                    {
                        buf.append(key.toLowerCase() + "=" + val);
                    } else
                    {
                        buf.append(key + "=" + val);
                    }
                    buf.append("&");
                }
            }
            buff = buf.toString();
            if (buff.isEmpty() == false)
            {
                buff = buff.substring(0, buff.length() - 1);
            }
        } catch (Exception e) {
            logger.error("HttpUtil.formatUrlMap():出错了",e);
            return null;
        }
        return buff;
    }

    public static void main(String[] args) throws Exception {
//        Map convert = new HashMap();
//        String weixin = "{\"userid\": \"" + "GeLin" + "\"}";
////        String body = URLEncoder.encode(weixin,"UTF-8");
//        JSONObject jsonObject = new JSONObject();
//        String body = "%7b%22userid%22%3a+%22GeLin%22%7d";
//        String access_token = "sM7SivX8nay2u94JDNlnD-ghgQ5EbpyavGA5XhyLUn8DOvW7hcF8v3QLLctJ7vgIxkW6urUw6Fsyh3L7SEewwGFs8BDuyCFq_4cnkhbhaBPxccsGKyY20xYeHTywEF4cUyrcndO1EsyD_K5t02IkJ48SxJvyAEPsf6MJWyzkngJky8UiyptIJKyi5v9teuZz5YVJBikDkk45izyfTgpGCw";
////            String body  = "{\"userid\":\""+userid+"\"+}";
////        convert.put("tid",73);
////        convert.put("access_token",access_token);
////        convert.put("body",jsonObject.toJSONString());
////        convert.put("f","json");
////        convert.put("userid",jsonObject.toJSONString());
//        convert.put("userid","GeLin");
////            String openIdData = HttpUtil.doPost("https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_openid?access_token="+access_token,convert);
//            String openIdData = HttpUtil.doPost("https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_openid?access_token=sM7SivX8nay2u94JDNlnD-ghgQ5EbpyavGA5XhyLUn8DOvW7hcF8v3QLLctJ7vgIxkW6urUw6Fsyh3L7SEewwGFs8BDuyCFq_4cnkhbhaBPxccsGKyY20xYeHTywEF4cUyrcndO1EsyD_K5t02IkJ48SxJvyAEPsf6MJWyzkngJky8UiyptIJKyi5v9teuZz5YVJBikDkk45izyfTgpGCw",convert);
////        String openIdData = HttpUtil.doPost("https://work.weixin.qq.com/api/devtools/devhandler.php",convert);
//
//
////        convert.put("body",jsonObject.toJSONString());
////        String openIdData = HttpUtil.doPost("https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_openid?access_token="+access_token,convert);
//        System.out.println(openIdData);
//
//        convert.put("appid","ww7c85cc7dbb47afde");
//        convert.put("redirect_uri","http%3a%2f%2ftemp.foway.com%3a8088%2fapi%2flogin");
//        convert.put("response_type","code");
//        convert.put("scope","snsapi_base");
//        convert.put("state","STATE#wechat_redirect");
//        String s = doGet("https://open.weixin.qq.com/connect/oauth2/authorize",convert);

//        convert.put("touser","GeLin");
//        convert.put("toparty","");
//        convert.put("totag","");
//        convert.put("msgtype","text");
//        convert.put("agentid",1000007);
//        convert.put("text",jsonObject.toJSONString());
//        convert.put("safe",0);

//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("touser","GeLin");
//        jsonObject.put("toparty","");
//        jsonObject.put("totag","");
//        jsonObject.put("msgtype","text");
//        jsonObject.put("agentid",1000007);
//        Content content = new Content();
//        content.content = "你有一个会议马上开始了";
//        jsonObject.put("text",content);
//        jsonObject.put("safe",0);
//        String s = HttpUtil3.doPostJson(" https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=sM7SivX8nay2u94JDNlnD-ghgQ5EbpyavGA5XhyLUn8DOvW7hcF8v3QLLctJ7vgIxkW6urUw6Fsyh3L7SEewwGFs8BDuyCFq_4cnkhbhaBPxccsGKyY20xYeHTywEF4cUyrcndO1EsyD_K5t02IkJ48SxJvyAEPsf6MJWyzkngJky8UiyptIJKyi5v9teuZz5YVJBikDkk45izyfTgpGCw",jsonObject.toJSONString());
//        System.out.println(s);
        Map map = new HashMap();
        map.put("appid","wx23a0e870ec746ff5");
        map.put("secret","c37702f7fa62147060575cab92ff5a88");
        map.put("grant_type","client_credential");

//        String access_token_json = HttpUtil.doGet("https://qyapi.weixin.qq.com/cgi-bin/gettoken",map);
//        JSONObject jsonObject = JSONObject.parseObject(access_token_json);
//        String access_token = (String) jsonObject.get("access_token");
        String access_token_json = HttpUtil.doGet("https://api.weixin.qq.com/cgi-bin/token",map);
        System.out.println(access_token_json);
    }


    public static class Content{
        private String content;
        public Content(){
            content = "";
        }
    }

}
