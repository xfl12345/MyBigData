package cc.xfl12345.mybigdata.server.common.pojo;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class PropertyHelper {
    protected PropertyDescriptor propertyDescriptor;

    protected Function<Object, Object> getter;

    protected BiConsumer<Object, Object> setter;

    protected boolean readable;

    protected boolean writable;

    public PropertyHelper(Object object, PropertyDescriptor descriptor) {
        this.propertyDescriptor = descriptor;

        Method readMethod = descriptor.getReadMethod();
        Method writeMethod = descriptor.getWriteMethod();

        readable = readMethod.canAccess(object);
        if (readable) {
            getter = (input) -> {
                try {
                    return readMethod.invoke(input);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            };
        } else {
            getter = (input) -> null;
        }

        writable = writeMethod.canAccess(object);
        if (writable) {
            setter = (input, val) -> {
                try {
                    writeMethod.invoke(input, val);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            };
        } else {
            setter = (input, val) -> {
            };
        }
    }

    public PropertyDescriptor getPropertyDescriptor() {
        return propertyDescriptor;
    }

    public boolean isReadable() {
        return readable;
    }

    public boolean isWritable() {
        return writable;
    }

    public Object justGet(Object object) {
        return getter.apply(object);
    }

    public void justSet(Object object, Object val) {
        setter.accept(object, val);
    }
}
