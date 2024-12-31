import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.*;
import java.util.List;

/**
 * <a href="https://juejin.cn/post/6844903597977632776">source code URL</a>
 */
public class TestWildcardType {

    public static void main(String[] args){
        //获取TestWildcardType类的所有方法(本例中即 testWildcardType 方法)
        Method[] methods = TestWildcardType.class.getDeclaredMethods();
        for (Method method: methods){
            //获取方法的所有参数类型
            Type[] types = method.getGenericParameterTypes();
            for (Type paramsType: types){
                System.out.println("type: " + paramsType.toString());
                //如果不是参数化类型则直接continue，执行下一个循环条件
                if (!(paramsType instanceof ParameterizedType)){
                    continue;
                }
                //将当前类型强转为参数化类型并获取其实际参数类型(即含有通配符的泛型类型)
                Type type = ((ParameterizedType) paramsType).getActualTypeArguments()[0];
                //输出其是否为通配符类型
                System.out.println("type instanceof WildcardType : " +
                    ( type instanceof WildcardType));
                if (type instanceof WildcardType){
                    int lowIndex = ((WildcardType) type).getLowerBounds().length - 1;
                    int upperIndex = ((WildcardType) type).getUpperBounds().length - 1;
                    //输出上边界与下边界
                    System.out.println("getLowerBounds(): "
                        +
                        (lowIndex >= 0 ? ((WildcardType) type).getLowerBounds()[lowIndex] : "String ")
                        + "; getUpperBounds(): "
                        +
                        (upperIndex >=0 ? ((WildcardType) type).getUpperBounds()[upperIndex]:"Object"));
                }
            }
        }
    }
    public void testWildcardType(List<? extends OutputStream> numberList,
                                 List<? super InputStream> upperList, List<Integer> list, InputStream inputStream){}
}
