package cc.xfl12345.mybigdata.server.common.pojo;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@SuperBuilder
public class PropertyInfo {
    @Getter
    protected String name;

    @Getter
    protected Field field;

    @Getter
    protected PropertyHelper propertyHelper;

    @Getter
    protected Map<Class<?>, List<Annotation>> annotations;
}
