package com.example.demo.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 创建人:连磊
 * 日期: 2018/11/20. 15:31
 * 描述：
 */
public class Reptilian {

    private static String[] URL = {"新闻","网页","微信","知乎","视频","明医","英文","学术","问问","更多»","识图搜索","企业推广","","输入法","浏览器","诚聘英才","免责声明","官方微博","帮助","图说新闻"};

    public static void main(String[] args) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();


        String url = "http://pic.sogou.com";
        getUrl(url);
    }

    /**
     *开 发 者：连磊
     *开发时间：2018/11/20 16:43
     *方 法 名：getUrl
     *传入参数：[url]
     *返 回 值：void
     *描    述：解析首页想要的数据
     **/
    public static void getUrl(String url) throws IOException {
        Document document = getDocument(url);

        Elements select = document.select("a").attr("target","_blank");
        for (Element el: select) {
            long count = Arrays.stream(URL).filter(u -> u.equals(el.text())).count();
            if (count <= 0){
                if (!"javascript:void(0)".equals(el.attr("href"))) {
                    if (!"http:".equals(el.attr("href").substring(0, 4))){

                        System.out.println(el.text() + el.attr("href"));
                        System.out.println("--------------------------------------");
                        Document href = getDocument(url + el.attr("href"));
                        writeFile("E:\\data\\"+el.text()+".txt" , href);
                        getScriptValue(href);
                        //Elements script = href.select("script");

                        //System.out.println(script);

                    }else {
                        Document href = getDocument(el.attr("href"));
                        writeFile("E:\\data\\"+el.text()+".txt" , href);
                    }
                }
            }
        }
    }

    /**
     *开 发 者：连磊
     *开发时间：2018/11/20 16:37
     *方 法 名：getDocument
     *传入参数：[url]
     *返 回 值：org.jsoup.nodes.Document
     *描    述：获取网页数据
     **/
    public static Document getDocument(String url) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet hget = new HttpGet(url);
        // 爬虫URL大部分都是get请求，创建get请求对象
        // 向传智播客官方网站发送请求，获取网页源码
        HttpResponse response = httpClient.execute(hget);
        // EntityUtils工具类把网页实体转换成字符串
        String content = EntityUtils.toString(response.getEntity(), "utf-8");
        Document document = Jsoup.parse(content);
        return document;
    }

    public static void writeFile(String path , Document document) throws IOException {
        /*File file = new File(path);
        Writer writer = new FileWriter(file);
        String string = document.toString();
        writer.write(string);
        writer.close();*/
    }

    public static void getScriptValue(Document document ){
        List<String> valueList = new ArrayList<>();
        Elements script = document.select("script");
        for (Element element : script){
            String[] vars = element.data().toString().split("var");
            for (String value : vars) {
                if (value.contains("=")){
                    if (value.contains("jsonTag")){
                        value = value.split("=")[1].trim();
                        value = value.substring( 1 , value.length() - 1);
                        valueList = Arrays.stream(value.split(",")).collect(Collectors.toList());
                        System.out.println(valueList);
                    }
                }
            }
        }
    }
}
