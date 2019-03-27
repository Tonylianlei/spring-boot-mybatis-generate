package com.example.demo.util.picutil;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebClientOptions;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 创建人:连磊
 * 日期: 2018/11/23. 10:24
 * 描述：
 */
public class PicCrawl {

    private static Logger logger = LoggerFactory.getLogger(PicCrawl.class);

    private static String filePath = "E:\\data";

    public static void main(String[] args) {
        getInfo("https://www.mzitu.com");
    }

    public static void getInfo(String url){
        Document document = htmlUtil(url);
        List<PicBean> picBeans = new ArrayList<>();
        if (null != document){
            Map<String, String> typeUrlMap = getTypeUrl(document);
            List<String> typeList = new ArrayList<>(typeUrlMap.keySet());
            System.out.println(typeList);
            for (String type : typeList){
                String typeUrl = typeUrlMap.get(type);
                document = htmlUtil(typeUrl);
                String page = getPage(document);
                if (null != page){
                    Integer pageNumer = getPageNumber(page);
                    for (int i = 1 ; i < 10 ; i ++){
                        document = htmlUtil(typeUrl+"/page/"+i+"/");
                        List<PicBean> picInfo = getPicInfo(document, type);
                        picBeans.addAll(picInfo);
                    }
                }else {

                }
            }
        }
        picBeans.stream().forEach(picBean -> {
            downloadPic(picBean.getTitle() , picBean.getUrl() , picBean.getPath());
        });
    }

    public static Document analysisUrl(String url){
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        Document document = null;
        try {

            HttpResponse httpResponse = httpClient.execute(httpGet);
            StatusLine statusLine = httpResponse.getStatusLine();
            if (HttpServletResponse.SC_OK == statusLine.getStatusCode()){
                String htmlEntity = EntityUtils.toString(httpResponse.getEntity());
                document = Jsoup.parse(htmlEntity);
            }else {
                logger.error("请求链接打开失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    public static String getPage(Document document){
        Elements elements = document.select("div.nav-links");
        Elements select = elements.select("a");
        if (select.size() > 0) {
            if (select.get(select.size() - 1).text().contains("下一页") ) {
                return select.get(select.size() - 2).attr("href");
            }else {
                return select.get(select.size() - 1).attr("href");
            }
        }
        return null;
    }

    public static Map<String , String> getTypeUrl(Document document){
        Map<String , String> typeMap = new HashMap<>();
        Elements select = document.select("ul#menu-nav");
        Elements a = select.select("a");
        for (Element el : a) {
            typeMap.put(el.text() , el.attr("href"));
        }
        return typeMap;
    }

    public static Document htmlUtil(String url){
        //新建一个模拟谷歌Chrome浏览器的浏览器客户端对象
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        WebClientOptions options = webClient.getOptions();
        //当JS执行出错的时候是否抛出异常, 这里选择不需要
        options.setThrowExceptionOnScriptError(false);
        //当HTTP的状态非200时是否抛出异常, 这里选择不需要
        options.setThrowExceptionOnFailingStatusCode(false);
        options.setActiveXNative(false);
        //是否启用CSS, 因为不需要展现页面, 所以不需要启用
        options.setCssEnabled(false);
        //很重要，启用JS
        options.setJavaScriptEnabled(true);
        //很重要，设置支持AJAX
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());

        HtmlPage page = null;
        try {
            //尝试加载网页
            page = webClient.getPage(url);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            webClient.close();
        }
        //异步JS执行需要耗时,所以这里线程要阻塞30秒,等待异步JS执行结束
        webClient.waitForBackgroundJavaScript(30000);
        //直接将加载完成的页面转换成xml格式的字符串
        String pageXml = page.asXml();
        Document document = Jsoup.parse(pageXml);
        return document;
    }

    public static Integer getPageNumber(String pageUrl){
        String[] split = pageUrl.split("/");
        return Integer.valueOf(split[split.length - 1]);
    }

    public static List<PicBean> getPicInfo(Document document , String type){
        List<PicBean> picBeans = new ArrayList<>();
        Elements select = document.select("ul#pins");
        Elements picSrc = select.select("a").select("img");
        for (Element el : picSrc) {
            PicBean alt = getPicInfo(el.attr("alt"), el.attr("data-original"), type);
            picBeans.add(alt);
        }
        return picBeans;
    }

    public static PicBean getPicInfo(String title , String url , String type){
        File file = new File(filePath + "\\" + type);
        if (!file.exists()){
            file.mkdir();
        }
        return new PicBean(title , url , file.getPath());
        //downloadPic(title , url , file.getPath());
    }


    public static void downloadPic(String title , String url , String filePath){

        try {
            URL url1 = new URL(url);
            File file = new File("E:\\data\\info.txt");
            try {
                FileWriter fileOutputStream = new FileWriter(file , true);
                PrintWriter printWriter = new PrintWriter(fileOutputStream);
                printWriter.print(title + ":" + url + "\n");
                printWriter.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(title + ":\t" + url);
            try {
                URLConnection urlConnection = url1.openConnection();
                urlConnection.setConnectTimeout(5 * 1000);
                //防止简单反扒
                String referer = url1.getProtocol()+"://"+url1.getHost();
                urlConnection.setRequestProperty("Referer", referer);
                urlConnection.setRequestProperty("User-Agent", " Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36");
                urlConnection.setRequestProperty("Upgrade-Insecure-Requests", "1");
                InputStream inputStream = urlConnection.getInputStream();
                String[] split = url.split("/");
                String picUrl = split[split.length - 1];

                byte[] bytes = new byte[1024];
                int len;
                String regEx="[`!@#$%^&*()+=|{}':;',//[//].<>/?！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(regEx);
                OutputStream os = new FileOutputStream(filePath.trim() + "\\"+ pattern.matcher(title).replaceAll("") + picUrl);
                // 开始读取
                while ((len = inputStream.read(bytes)) != -1) {
                    os.write(bytes, 0, len);
                }
                // 完毕，关闭所有链接
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
