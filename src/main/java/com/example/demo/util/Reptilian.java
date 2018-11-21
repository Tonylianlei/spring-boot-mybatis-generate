package com.example.demo.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
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

    private static String filePath = "E:\\data\\";

    private static String[] URL = {"新闻","网页","微信","知乎","视频","明医","英文","学术","问问","更多»","识图搜索","企业推广","","输入法","浏览器","诚聘英才","免责声明","官方微博","帮助","图说新闻"};

    public static void main(String[] args) throws IOException {
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
        String page = "&start=0&len=3";
        //封装需要的参数
        Map<String , String> valueList = new HashMap<>();
        for (Element el: select) {
            //过滤部分我想要的数据地址
            long count = Arrays.stream(URL).filter(u -> u.equals(el.text())).count();
            if (count <= 0){
                if (!"javascript:void(0)".equals(el.attr("href"))) {
                    if (!"http:".equals(el.attr("href").substring(0, 4))){
                        //解析下一层的数据信息
                        Document href = getDocument(url + el.attr("href"));
                        //将需要的信息写入文件
                        writeFile(filePath+el.text()+".txt" , href);
                        //获取想要的信息
                        List<String> scriptValue = getScriptValue(href);
                        //创建相关的文件夹
                        File files = new File(filePath + el.text());
                        files.mkdir();
                        //处理信息匹配
                        for (String tal : scriptValue ) {
                            if ("全景视觉".equals(el.text())){
                                el.text("全景");
                            }
                            //创建相关信息连接
                            page = "category=" + URLEncoder.encode(el.text(), "GBK") + "&tag=" + URLEncoder.encode(tal.replace("\"", ""), "GBK") + page;
                            //处理文件匹配
                            if ("全景".equals(el.text())){
                                el.text("全景视觉");
                            }
                            //创建相关的文件夹
                            files = new File(filePath + el.text()+"\\" + tal.replace("\"" , ""));
                            files.mkdir();
                            valueList.put(page , files.getPath());
                            page = "&start=0&len=3";
                        }
                    }else {
                        Document href = getDocument(el.attr("href"));
                        writeFile("E:\\data\\"+el.text()+".txt" , href);
                    }
                }
            }
        }
        ThreadAsyncPool threadAsyncPool = new ThreadAsyncPool();
        ThreadPoolExecutor asyncExecutor = threadAsyncPool.getAsyncExecutor();
        HttpRequestExecutePool httpRequestExecutePool = new HttpRequestExecutePool(valueList);
        asyncExecutor.execute(httpRequestExecutePool);
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

    /**
     *开 发 者：连磊
     *开发时间：2018/11/21 16:40
     *方 法 名：writeFile
     *传入参数：[path, document]
     *返 回 值：void
     *描    述：将需要的信息写到文件中，用于分析
     **/
    public static void writeFile(String path , Document document) throws IOException {
        /*File file = new File(path);
        Writer writer = new FileWriter(file);
        String string = document.toString();
        writer.write(string);
        writer.close();*/
    }

    /**
     *开 发 者：连磊
     *开发时间：2018/11/21 16:40
     *方 法 名：getScriptValue
     *传入参数：[document]
     *返 回 值：java.util.List<java.lang.String>
     *描    述：解析script中的变量。获取需要的参数
     **/
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
