package generatorconfig;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.StringUtility;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Properties;

/**
 * 创建人:连磊
 * 日期: 2018/11/7. 11:02
 * 描述：
 */
public class MyGeneratorConfig implements CommentGenerator {


    @Override
    public void addConfigurationProperties(Properties properties) {
        
    }

    /**
     *开 发 者：连磊
     *开发时间：2018/11/7 15:19
     *方 法 名：addFieldComment
     *传入参数：[field, introspectedTable, introspectedColumn]
     *返 回 值：void
     *描    述：生成字段注释
     **/
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())){
            field.addJavaDocLine("@ApiModelProperty(value = \"" + introspectedColumn.getRemarks().trim() + "\")");
        }
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        
    }

    /**
     *开 发 者：连磊
     *开发时间：2018/11/7 15:20
     *方 法 名：addModelClassComment
     *传入参数：[topLevelClass, introspectedTable]
     *返 回 值：void
     *描    述：生成引入类和说明
     **/
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addJavaDocLine("import lombok.Data;");
        topLevelClass.addJavaDocLine("import io.swagger.annotations.ApiModelProperty;");
        topLevelClass.addJavaDocLine("import io.swagger.annotations.ApiModel;");


        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * 创建时间：" + LocalDate.now() + "\t" + LocalTime.now());
        topLevelClass.addJavaDocLine(" * 创 建 者：" + System.getProperty("user.name"));
        topLevelClass.addJavaDocLine(" * 实体说明："+introspectedTable.getRemarks().trim().replace("\n" , ""));
        topLevelClass.addJavaDocLine(" */");
        topLevelClass.addJavaDocLine("@Data");
        topLevelClass.addJavaDocLine("@ApiModel(description = \""+introspectedTable.getRemarks()+"\")");
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean b) {
        
    }

    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
        
    }

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        
    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        
    }

    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        
    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        
    }

    @Override
    public void addComment(XmlElement xmlElement) {
        
    }

    @Override
    public void addRootComment(XmlElement xmlElement) {
        
    }
}