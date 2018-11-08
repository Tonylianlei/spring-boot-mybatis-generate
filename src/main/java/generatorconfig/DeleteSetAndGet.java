package generatorconfig;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

/**
 * 创建人:连磊
 * 日期: 2018/11/7. 17:16
 * 描述：
 */
public class DeleteSetAndGet extends PluginAdapter {



    /**
     *开 发 者：连磊
     *开发时间：2018/11/7 17:17
     *方 法 名：modelGetterMethodGenerated
     *传入参数：[method, topLevelClass, introspectedColumn, introspectedTable, modelClassType]
     *返 回 值：boolean
     *描    述：是否生成get方法
     **/
    @Override
    public boolean modelGetterMethodGenerated(Method method,
                                              TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable,
                                              ModelClassType modelClassType) {
        return false;
    }

    /**
     *开 发 者：连磊
     *开发时间：2018/11/7 17:18
     *方 法 名：modelSetterMethodGenerated
     *传入参数：[method, topLevelClass, introspectedColumn, introspectedTable, modelClassType]
     *返 回 值：boolean
     *描    述：是否生成set方法
     **/
    @Override
    public boolean modelSetterMethodGenerated(Method method,
                                              TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable,
                                              ModelClassType modelClassType) {
        return false;
    }

    /**
     *开 发 者：连磊
     *开发时间：2018/11/8 15:17
     *方 法 名：validate
     *传入参数：[list]
     *返 回 值：boolean
     *描    述：是否执行上方的修改方法，true：执行，false：不执行
     **/
    @Override
    public boolean validate(List<String> list) {
        return true;
    }
}
