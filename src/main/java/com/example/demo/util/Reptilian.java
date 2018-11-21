package com.example.demo.util;

import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;

/**
 * 创建人:连磊
 * 日期: 2018/11/20. 15:31
 * 描述：
 */
public class Reptilian {

    @Resource(name = "defaultThreadPool")
    private ThreadPoolTaskExecutor executor;

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
        String page = "&start=0&len=50";
        for (Element el: select) {
            long count = Arrays.stream(URL).filter(u -> u.equals(el.text())).count();
            if (count <= 0){
                if (!"javascript:void(0)".equals(el.attr("href"))) {
                    if (!"http:".equals(el.attr("href").substring(0, 4))){
                        /*System.out.println(el.text() + el.attr("href"));*/
                        Document href = getDocument(url + el.attr("href"));
                        writeFile("E:\\data\\"+el.text()+".txt" , href);
                        List<String> scriptValue = getScriptValue(href);
                        File files = new File("E:\\data\\" + el.text());
                        files.mkdir();
                        for (String tal : scriptValue ) {
                            if ("全景视觉".equals(el.text())){
                                el.text("全景");
                            }
                            page = "category=" + URLEncoder.encode(el.text() , "GBK") + "&tag=" +  URLEncoder.encode(tal.replace("\"" , "") , "GBK") + page;
                            JSONObject value = HttpRequest.getJSONObject(HttpRequest.sendGet("https://pic.sogou.com/pics/channel/getAllRecomPicByTag.jsp", page, "GBK"));
                            System.out.println(value);
                            if ("全景".equals(el.text())){
                                el.text("全景视觉");
                            }
                            String fileFolder = "E:\\data\\" + el.text()+"\\" + tal.replace("\"" , "");
                            files = new File(fileFolder);
                            files.mkdir();
                            HttpRequest.getInfo(value , fileFolder);

                            page = "&start=0&len=50";
                            System.out.println("****************--------------------------------------------********************");
                        }
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

    public static List<String> getScriptValue(Document document ){
        List<String> valueList = new ArrayList<>();
        Elements script = document.select("script");
        for (Element element : script){
            String[] vars = element.data().toString().split("var");
            for (String value : vars) {
                if (value.contains("=")){
                    if (value.contains("jsonTag")){
                        value = value.split("=")[1].trim();
                        value = value.substring( 1 , value.length() - 2);
                        valueList = Arrays.stream(value.split(",")).collect(Collectors.toList());
                    }
                }
            }
        }
        return valueList;
    }
}
