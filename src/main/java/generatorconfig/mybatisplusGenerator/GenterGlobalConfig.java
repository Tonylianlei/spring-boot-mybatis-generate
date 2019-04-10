package generatorconfig.mybatisplusGenerator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人:连磊
 * 日期: 2019/4/9. 15:21
 * 描述：
 */
public class GenterGlobalConfig {

    private final static Boolean coverFile = true;

    private final static String DATA_DRIVE = "com.mysql.jdbc.Driver";

    private final static String DATA_NAME = "root";

    private final static String DATA_PASSWORD = "123456";

    private final static String DATA_URL="jdbc:mysql://192.168.2.234:3306/capsys_fof_test?useUnicode=true&characterEncoding=utf8&autoReconnect=true&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&useSSL=true";

    /**
     *  xml模板地址
     */
    private final static String XML_TEMPLATE_PATH = "/templates/mapper.xml.vm";
    /**
     service生成地址
     */
    private final static String SERVICE_PATH="service";
    /**
     * serviceImpl生成地址
     */
    private final static String SERVICE_IMPL_PATH="service.impl";
    /**
     * controller生成地址
     */
    private final static String CONTROLLER_PATH="controller";
    /**
     * bean(实体)生成地址
     */
    private final static String BENA_PATH="bean";
    /**
     * mapper（dao）生成地址
     */
    private final static String MAPPER_PATH="mapper";


    /**
     *开 发 者：连磊
     *开发时间：2019/4/10 14:24
     *方 法 名：getUserName
     *传入参数：[]
     *返 回 值：java.lang.String
     *描    述：获取当前文件创建者
     **/
    private static String getUserName(){
        return System.getProperty("user.name");
    }

    /**
     *开 发 者：连磊
     *开发时间：2019/4/10 14:23
     *方 法 名：getObjectPath
     *传入参数：[]
     *返 回 值：java.lang.String
     *描    述：获取当前项目地址
     **/
    private static String getObjectPath(){
        return System.getProperty("user.dir");
    }


    private static void createMyBatisEntity(){

        AutoGenerator autoGenerator = new AutoGenerator();

        //配置数据库
        DataSourceConfig dataSourceConfig = new DataSourceConfig()
                .setDbType(DbType.MYSQL)
                .setUrl(DATA_URL)
                .setDriverName(DATA_DRIVE)
                .setUsername(DATA_NAME)
                .setPassword(DATA_PASSWORD);


        //全局配置
        GlobalConfig globalConfig = new GlobalConfig()
                .setOutputDir(getObjectPath()+"/src/main/java")
                .setControllerName("%sController")
                .setServiceName("%sService")
                .setServiceImplName("%sServiceImpl")
                .setMapperName("%sMapper")
                .setXmlName("%sMapper")
                .setDateType(DateType.ONLY_DATE)
                .setFileOverride(true)
                .setIdType(IdType.UUID)
                .setAuthor(getUserName())
                .setBaseResultMap(true)
                .setBaseColumnList(true)
                .setEnableCache(false)
                .setOpen(false)
                .setSwagger2(true)
                .setActiveRecord(true);


        //配置策略
        StrategyConfig strategyConfig = new StrategyConfig()
                .setEntityLombokModel(true)
                .setCapitalMode(true)
                .setTablePrefix(new String[]{"tbl_"})
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                //要生成的表名
                .setInclude(new String[]{"tbl_direct_invest_income"})
                .setRestControllerStyle(true);

        //包配置
        PackageConfig packageConfig = new PackageConfig()
                .setParent("com.example.demo")
                .setController("controller")
                .setService("service")
                .setServiceImpl("service.impl")
                .setEntity("bean.entity")
                .setMapper("bean.mapper");

        //调整xml生成的目录
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {

            }
        };

        List<FileOutConfig> fileOutConfigs = new ArrayList<>();
        fileOutConfigs.add(new FileOutConfig(XML_TEMPLATE_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return getObjectPath()+ "/src/main/resources/mapper/"
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        injectionConfig.setFileOutConfigList(fileOutConfigs);

        //关闭xml默认生成目录，调整xml目录到根目录下
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);


        autoGenerator.setDataSource(dataSourceConfig);
        autoGenerator.setGlobalConfig(globalConfig);
        autoGenerator.setStrategy(strategyConfig);
        autoGenerator.setPackageInfo(packageConfig);
        autoGenerator.setTemplate(templateConfig);
        autoGenerator.setCfg(injectionConfig);
        autoGenerator.execute();
    }


    public static void main(String[] args) {
        createMyBatisEntity();
    }

    /*public static void main(String[] args) {
        AutoGenerator autoGenerator = new AutoGenerator();

        //配置数据库
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
        dataSourceConfig.setPassword("123456");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setUrl("jdbc:mysql://192.168.2.234:3306/capsys_fof_test?useUnicode=true&characterEncoding=utf8&autoReconnect=true&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&useSSL=true");

        String filePath = System.getProperty("user.dir");

        //配置GlobalConfig
        GlobalConfig globalConfig = new GlobalConfig();
        //是否开启xml二级缓存
        globalConfig.setEnableCache(false);
        //生成目录
        globalConfig.setOutputDir(filePath+"/src/main/java");
        //生成当前创建者名称
        globalConfig.setAuthor(System.getProperty("user.name"));
        //生成xml中信息
        globalConfig.setBaseColumnList(true);
        globalConfig.setBaseResultMap(true);
        //是否开启swagger
        globalConfig.setSwagger2(true);
        //是否打开生成文件的目录
        globalConfig.setOpen(false);
        //自定义文件名称
        globalConfig.setControllerName("%sController");
        globalConfig.setServiceName("%sService");
        globalConfig.setServiceImplName("%sServiceImpl");
        globalConfig.setMapperName("%sMapper");
        globalConfig.setXmlName("%sMapper");

        globalConfig.setIdType(IdType.UUID);
        globalConfig.setDateType(ONLY_DATE);
        globalConfig.setFileOverride(true);

        globalConfig.setActiveRecord(true);

        //包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setController("controller");
        packageConfig.setEntity("bean.model");
        packageConfig.setMapper("bean.mapper");
        packageConfig.setService("service");
        packageConfig.setServiceImpl("service.impl");
        packageConfig.setParent("com.example.demo");

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        //自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                System.out.println(tableInfo.getEntityName());
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return filePath + "/src/main/resources/mapper/"
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);

        //策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //strategy.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        //strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
        strategy.setInclude("tbl_attention,tbl_department,tbl_direct_invest_cost".split(","));
        //strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(packageConfig.getModuleName() + "tbl_");

        TemplateConfig templateConfig = new TemplateConfig().setXml(null);
        if (coverFile){
            templateConfig.setController(null);
            templateConfig.setService(null);
            templateConfig.setServiceImpl(null);
        }

        autoGenerator.setGlobalConfig(globalConfig);
        autoGenerator.setDataSource(dataSourceConfig);
        autoGenerator.setStrategy(strategy);
        autoGenerator.setPackageInfo(packageConfig);
        autoGenerator.setCfg(cfg);
        autoGenerator.setTemplate(templateConfig);
        autoGenerator.execute();
    }*/
}
