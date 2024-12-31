package cc.xfl12345.mybigdata.server.common.pojo;

import lombok.Getter;

import jakarta.persistence.Id;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;

public class ClassDeclaredInfo {
    @Getter
    protected Class<?> clazz;

    /**
     * fieldName -> Field
     */
    @Getter
    protected Map<String, Field> declaredFields;

    /**
     * propertyName -> PropertyDescriptor
     */
    @Getter
    protected Map<String, PropertyDescriptor> propertiesMap;

    @Getter
    protected BeanInfo beanInfo;

    /**
     * fieldName -> PropertyInfo
     */
    @Getter
    protected Map<String, PropertyInfo> propertyInfoMap;

    @Getter
    protected Map<Class<?>, List<Annotation>> clazzAnnotations;

    @Getter
    protected List<String> jpaIdFields;

    public ClassDeclaredInfo(Object object) throws Exception {
        init(object);
    }

    protected void init(Object object) throws Exception {
        this.clazz = object.getClass();

        declaredFields = new ConcurrentHashMap<>();
        clazzAnnotations = new ConcurrentHashMap<>();
        jpaIdFields = new CopyOnWriteArrayList<>();

        beanInfo = Introspector.getBeanInfo(clazz, clazz.getSuperclass());

        propertiesMap = new HashMap<>();
        for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {
            propertiesMap.put(descriptor.getDisplayName(), descriptor);
        }
        propertyInfoMap = new ConcurrentHashMap<>(propertiesMap.size());

        BiConsumer<Map<Class<?>, List<Annotation>>, Annotation> annotationHandler = (annotationMap, annotation) -> {
            // 获取类型
            Class<?> annotationClass = annotation.annotationType();
            // 初始化 & 获取 & 加入 List
            annotationMap.putIfAbsent(annotationClass, new CopyOnWriteArrayList<>());
            List<Annotation> annotationList = annotationMap.get(annotationClass);
            annotationList.add(annotation);
        };


        // 遍历 Class 上的注解
        Arrays.asList(clazz.getDeclaredAnnotations()).parallelStream().forEach(annotation -> {
            annotationHandler.accept(clazzAnnotations, annotation);
        });

        // 遍历 字段
        Arrays.asList(clazz.getDeclaredFields()).parallelStream().forEach(field -> {
            String fieldName = field.getName();
            declaredFields.put(fieldName, field);
            PropertyDescriptor propertyDescriptor = propertiesMap.get(fieldName);

            Map<Class<?>, List<Annotation>> annotationMap = new ConcurrentHashMap<>();

            // 遍历 字段 上的注解
            Annotation[] annotations = field.getDeclaredAnnotations();
            Arrays.asList(annotations).parallelStream().forEach(annotation -> {
                annotationHandler.accept(clazzAnnotations, annotation);
                annotationHandler.accept(annotationMap, annotation);
                if (annotation instanceof Id) {
                    jpaIdFields.add(fieldName);
                }
            });

            if (propertyDescriptor != null) {
                propertyInfoMap.put(
                    fieldName,
                    PropertyInfo.builder()
                        .name(fieldName)
                        .field(field)
                        .propertyHelper(new PropertyHelper(object, propertyDescriptor))
                        .annotations(annotationMap)
                        .build()
                );
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getAnnotationByType(Class<T> annotationType) {
        return (List<T>) clazzAnnotations.get(annotationType);
    }
}
