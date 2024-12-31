import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class StudyGenericType {
    public static void main(String[] args) throws Exception {
        SimpleGenericObject<Long> object = new SimpleGenericObject<>(Long.class);

        printType(object.getRuntimeListType());

        Stream.of(object.getClass().getDeclaredFields())
            .forEach(field -> printType(field.getGenericType()));

        ArrayList<Integer> integerArrayList = new ArrayList<>();
        printType(integerArrayList.getClass());
    }

    public static void printType(Type type) {
        String msg = "TypeClass: [" + type.getClass().getCanonicalName() + "]";
        msg += ", Type: [" + type + "]";
        if (type instanceof ParameterizedType parameterizedType) {
            // StringBuilder stringBuilder = new StringBuilder();
            msg += ", ActualTypeArguments: " + Arrays.toString(parameterizedType.getActualTypeArguments());
        }
        System.out.println(msg);
    }

    public static class SimpleGenericObject<Value> {
        public ArrayList<Double> list = new ArrayList<>();

        protected Class<Value> valueClass;

        public SimpleGenericObject(Class<Value> valueClass) {
            this.valueClass = valueClass;
        }


        // @SuppressWarnings("unchecked")
        protected Type getRuntimeListType() throws IOException, NoSuchFieldException {
            Type result;
            {
                TypeDescription.Generic generic = TypeDescription.Generic.Builder
                    .parameterizedType(ArrayList.class, valueClass).build();

                // new ByteBuddy()
                // .subclass(List.class)
                // ArrayFactory.forType(generic)

                // String tempFieldName = "list";

                DynamicType.Unloaded<?> unloadedClass = new ByteBuddy()
                    // .subclass((Class<List<Value>>) (Class<?>) List.class)
                    // .subclass(Object.class)
                    .subclass(generic)
                    // .defineField(tempFieldName, generic, Visibility.PUBLIC)
                    .make();


                Class<?> dynamicType = unloadedClass
                    .load(Thread.currentThread().getContextClassLoader())
                    .getLoaded();
                unloadedClass.close();

                // Field field = dynamicType.getDeclaredField(tempFieldName);
                // result = (Class<List<Value>>) field.getType();

                result = dynamicType.getGenericSuperclass();
            }

            // Allow GC to clean garbage class byte
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // List<Value> list = new ArrayList<>();
            // return (Class<List<Value>>) ((Class<?>)List.class);
            return result;
        }
    }
}
