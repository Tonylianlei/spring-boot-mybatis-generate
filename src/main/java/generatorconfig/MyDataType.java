package generatorconfig;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

/**
 * 创建人:连磊
 * 日期: 2018/11/8. 15:31
 * 描述：
 */
public class MyDataType extends JavaTypeResolverDefaultImpl {
    public MyDataType() {
        super();
        super.typeMap.put(-6, new JdbcTypeInformation("TINYINT", new FullyQualifiedJavaType(Integer.class.getName())));
    }
}
