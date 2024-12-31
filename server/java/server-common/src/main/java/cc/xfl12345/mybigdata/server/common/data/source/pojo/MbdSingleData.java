package cc.xfl12345.mybigdata.server.common.data.source.pojo;

public interface MbdSingleData<Value> extends BaseMbdObject {
    Value getValue();

    void setValue(Value value);
}
