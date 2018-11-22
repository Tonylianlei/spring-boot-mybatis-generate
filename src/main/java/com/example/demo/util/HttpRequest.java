package com.example.demo.util;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 创建人:连磊
 * 日期: 2018/11/21. 9:38
 * 描述：
 */
public class HttpRequest  {

    public static String sendGet(String url, String param , String encoded) {
        String result = "";
        BufferedReader in = null;
        if (null == encoded){
            encoded = "utf-8";
        }
        try {
            String urlNameString = url + "?" + param;

            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("contentType", encoded);
            connection.setRequestProperty("Accept-Charset", encoded);
            // 建立实际的连接
            connection.connect();

            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(),encoded));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static JSONObject getJSONObject(String value){
        JSONObject jsonObject = JSONObject.fromObject(value);
        return jsonObject;
    }

    public static void getInfo(JSONObject jsonObject , String path){
        JSONArray allItems = jsonObject.getJSONArray("all_items");
        for (Object o: allItems) {
            jsonObject = JSONObject.fromObject(o);

            CloseableHttpClient httpclient = HttpClients.createDefault();
            try {
                HttpGet httpGet = new HttpGet(jsonObject.getString("pic_url"));
                JSONArray tags = jsonObject.getJSONArray("tags");
                long count = tags.stream().filter(object -> object.equals("动态图")).count();
                CloseableHttpResponse response = null;
                try {
                    response = httpclient.execute(httpGet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    HttpEntity entity = response.getEntity();
                    InputStream inStream = null;
                    try {
                        inStream = entity.getContent();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    FileOutputStream fw = null;
                    String regEx="[`!@#$%^&*()+=|{}':;',//[//].<>/?！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                    Pattern pattern = Pattern.compile(regEx);
                    String title = pattern.matcher(jsonObject.getString("title")).replaceAll("").trim();

                    try {
                        fw = new FileOutputStream(path.trim() + "/" + title +( count <=0 ?".jpg" : ".gif" ), false);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    int b = 0;
                    try {
                        b = inStream.read();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    while (b != -1) {
                        try {
                            fw.write(b);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            b = inStream.read();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        EntityUtils.consume(entity);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } finally {
                    try {
                        response.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }finally {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
