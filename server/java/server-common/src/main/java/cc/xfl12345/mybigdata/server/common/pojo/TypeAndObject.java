package cc.xfl12345.mybigdata.server.common.pojo;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

public class TypeAndObject {
    protected Type type;

    protected Object object;

    public TypeAndObject(Type type, Object object) {
        this(type, object, false);
    }

    public TypeAndObject(Type type, Object object, boolean forceMatch) {
        this.type = type;
        this.object = object;
        if (forceMatch && !isTypeMatchObject(type, object)) {
            throw new IllegalArgumentException("Type [" + type + "] is not match object [" + object.getClass() + "]");
        }
    }

    public static boolean isTypeMatchObject(final Type type, Object object) {
        if (object == null) {
            return type == Void.class;
        }

        Class<?> objectClass = object.getClass();
        if (Objects.equals(type, objectClass)) {
            return true;
        }

        try {
            Class<?> typeAsClass = (Class<?>) type;
            if (typeAsClass.isAssignableFrom(objectClass)) {
                return true;
            }
        } catch (ClassCastException e) {
            if (type instanceof ParameterizedType parameterizedType) {
                return Objects.equals(parameterizedType.getRawType(), objectClass);
            }
            if (type instanceof GenericArrayType genericArrayType) {
                if (genericArrayType.getGenericComponentType() instanceof ParameterizedType parameterizedType) {
                    return Objects.equals(parameterizedType.getRawType(), objectClass);
                }
                return false;
            }
        }

        return false;
    }

    public Type getType() {
        return type;
    }

    public Object getObject() {
        return object;
    }
}
