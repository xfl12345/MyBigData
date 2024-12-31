package cc.xfl12345.mybigdata.server.common.data.source.pojo;

import cc.xfl12345.mybigdata.server.common.utility.MyReflectUtils;
import org.bson.types.ObjectId;

public class MbdId {
    private Class<?> originType;

    private Integer hashcode;

    protected Object idRawValue;

    protected Long theLongValue;

    protected String theStringValue;

    protected ObjectId theBsonObjectId;

    public MbdId(Long id) {
        idRawValue = id;
        initConstruct();
    }

    public MbdId(String id) {
        idRawValue = id;
        initConstruct();
    }

    public MbdId(ObjectId id) {
        idRawValue = id;
        initConstruct();
    }

    public MbdId(MbdId id) {
        idRawValue = id.getValue();
        initConstruct();
    }

    public MbdId(Object theNullValue) {
        idRawValue = theNullValue;
        if (theNullValue != null) {
            onTypeError(theNullValue);
        }
    }

    protected void initConstruct() {
        boolean inited = false;
        if (idRawValue instanceof Long id) {
            theLongValue = id;
            originType = Long.class;
            inited = true;
        } else if (idRawValue instanceof ObjectId id) {
            theBsonObjectId = id;
            originType = ObjectId.class;
            inited = true;
        } else if (idRawValue instanceof String id) {
            theStringValue = id;
            originType = String.class;
            inited = true;
        }

        if (!inited) {
            onTypeError(idRawValue);
        }
    }

    protected void onTypeError(Object id) {
        throw new IllegalArgumentException("The id type of [" + id.getClass().getCanonicalName() + "] is unsupported.");
    }

    public Class<?> getIdType() {
        if (originType == null) {
            originType = MyReflectUtils.getGenericTypeFromRuntime(getClass(), 0);
        }

        return originType;
    }

    public Object getValue() {
        return idRawValue;
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue(Class<T> castType) {
        if (getIdType().equals(castType)) {
            return (T) idRawValue;
        }

        if (String.class.equals(castType)) {
            return (T) idRawValue.toString();
        }

        return castType.cast(idRawValue);
    }

    public Long getLongValue() {
        if (theLongValue == null) {
            if (idRawValue instanceof String str) {
                theLongValue = Long.parseLong(str);
            } else {
                theLongValue = getValue(Long.class);
            }
        }

        return theLongValue;
    }

    public String getStringValue() {
        if (theStringValue == null) {
            theStringValue = getValue(String.class);
        }

        return theStringValue;
    }

    public ObjectId getBsonObjectId() {
        if (theBsonObjectId == null) {
            theBsonObjectId = new ObjectId(getStringValue());
        }

        return theBsonObjectId;
    }

    @Override
    public String toString() {
        return idRawValue == null ? "" : idRawValue.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (idRawValue == null) {
            return obj == null;
        }

        if (obj instanceof MbdId mbdId) {
            if (mbdId.getIdType().equals(getIdType())) {
                // 同类型，直接比原生值
                return idRawValue.equals(mbdId.getValue());
            } else {
                // 不同类型，统一转成字符串再比较
                return getStringValue().equals(mbdId.getStringValue());
            }
        }

        return false;
    }

    public boolean equals(MbdId obj) {
        if (idRawValue == null) {
            return obj == null;
        }

        return idRawValue.equals(obj.getValue());
    }

    @Override
    public int hashCode() {
        if (hashcode == null) {
            hashcode = getStringValue().hashCode();
        }

        return hashcode;
    }
}
