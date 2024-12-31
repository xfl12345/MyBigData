package cc.xfl12345.mybigdata.server.common.data.source.pojo;

import lombok.Getter;
import lombok.Setter;

public abstract class PlainMbdSingleData<Value> implements MbdSingleData<Value> {
    @Getter
    @Setter
    protected MbdId globalId;

    @Setter
    protected Value value;

    @Override
    public Value getValue() {
        return value;
    }
}
