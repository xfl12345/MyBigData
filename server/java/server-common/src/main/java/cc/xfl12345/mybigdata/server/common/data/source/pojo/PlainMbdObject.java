package cc.xfl12345.mybigdata.server.common.data.source.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

public class PlainMbdObject implements MbdObject {
    @Getter
    @Setter
    protected MbdId globalId;

    @Getter
    @Setter
    protected String name;

    @Getter
    @Setter
    protected MbdJsonSchema schema;

    @Getter
    @Setter
    protected String schemaPath;

    @Getter
    @Setter
    protected Map<String, MbdId> map;
}
