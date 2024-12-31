import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.util.List;

/**
 * <a href="https://juejin.cn/post/6844903597977632776">source code URL</a>
 */
public class TestGenericArrayTypeBean<T> {

    //泛型数组类型
    private T[] value;
    private List<String>[] list;

    //不是泛型数组类型
    private List<String> singleList;
    private T singleValue;

    public static void main(String[] args){
        Field[] fields = TestGenericArrayTypeBean.class.getDeclaredFields();
        for (Field field: fields){
            field.setAccessible(true);
            //输出当前变量是否为GenericArrayType类型
            System.out.println("Field: "
                + field.getName()
                + "; instanceof GenericArrayType"
                + ": "
                + (field.getGenericType() instanceof GenericArrayType));
            if (field.getGenericType() instanceof GenericArrayType){
                //如果是GenericArrayType，则输出当前泛型类型
                System.out.println("Field: "
                    + field.getName()
                    + "; getGenericComponentType()"
                    + ": "
                    + (((GenericArrayType) field.getGenericType()).getGenericComponentType()));
            }
        }
    }
}
