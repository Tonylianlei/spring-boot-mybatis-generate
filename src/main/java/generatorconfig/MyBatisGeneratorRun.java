package generatorconfig;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MyBatisGeneratorRun {

    public static void main(String[] args) throws Exception{
        generator();
    }

    public static void generator() throws Exception{

        String generatorPath = "generatorConfig.xml";
        deleteMapperXml(generatorPath);

       List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        InputStream resourceAsStream = new MyBatisGeneratorRun().getClass().getClassLoader().getResourceAsStream(generatorPath);

        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(resourceAsStream);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }

    /**
     *开 发 者：连磊
     *开发时间：2018/11/7 16:24
     *方 法 名：deleteMapperXml
     *传入参数：[]
     *返 回 值：void
     *描    述：解析generator删除对应xml文件
     **/
    public static void deleteMapperXml(String generatorPath){
        InputStream resourceAsStream = new MyBatisGeneratorRun().getClass().getClassLoader().getResourceAsStream(generatorPath);
        SAXBuilder saxBuilder = new SAXBuilder();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(resourceAsStream, "UTF-8");
            try {
                Document build = saxBuilder.build(inputStreamReader);
                Element rootElement = build.getRootElement();
                Element sqlMapGenerator = rootElement.getChild("context");
                Element javaClientGenerator = sqlMapGenerator.getChild("sqlMapGenerator");
                List<Element> children = sqlMapGenerator.getChildren();
                List<Element> table = children.stream().filter(ch -> "table".equals(ch.getName())).collect(Collectors.toList());
                table.forEach(element -> {
                    File file = null;
                    if ("%".equals(element.getAttributeValue("tableName"))){
                        file = new File("./" + javaClientGenerator.getAttributeValue("targetProject")+"/"+javaClientGenerator.getAttributeValue("targetPackage"));
                        File[] files = file.listFiles();
                        Arrays.stream(files).forEach(file1 -> {
                            file1.delete();
                        });
                    }else {
                        file = new File("./" + javaClientGenerator.getAttributeValue("targetProject")+"/"+javaClientGenerator.getAttributeValue("targetPackage")+ "/" + element.getAttributeValue("domainObjectName")+"Mapper.xml");
                        if (file.exists()){
                            file.delete();
                        }
                    }
                });
                resourceAsStream.close();
            } catch (JDOMException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}