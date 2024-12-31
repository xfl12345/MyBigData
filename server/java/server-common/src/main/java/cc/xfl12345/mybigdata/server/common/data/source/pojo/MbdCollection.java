package cc.xfl12345.mybigdata.server.common.data.source.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.function.Function;

public interface MbdCollection extends BaseMbdObject {
    String getName();

    void setName(String name);

    @JsonIgnore
    default <T, R> boolean isPropertyEqual(T a, T b, Function<T, R> getter) {
        return getter.apply(a) == null ?
            getter.apply(b) == null : getter.apply(a).equals(getter.apply(b));
    }

    @JsonIgnore
    boolean isEqualsExceptData(MbdCollection mbdCollection);
}
