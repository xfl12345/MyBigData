package cc.xfl12345.mybigdata.server.common.data.source.pojo;

import com.networknt.schema.JsonSchema;
import lombok.Getter;
import lombok.Setter;

public class PlainMbdJsonSchema implements MbdJsonSchema {
    @Getter
    @Setter
    protected MbdId globalId;

    @Getter
    @Setter
    protected String name;

    @Getter
    @Setter
    protected JsonSchema jsonSchema;

    // @Override
    // public boolean equals(Object obj) {
    //     if (obj instanceof MbdJsonSchema mbdJsonSchema) {
    //         return getGlobalId().equals(mbdJsonSchema.getGlobalId()) &&
    //             getName().equals(mbdJsonSchema.getName()) &&
    //             getJsonSchema().getSchemaNode().equals(mbdJsonSchema.getJsonSchema().getSchemaNode());
    //     }
    //
    //     return false;
    // }
}
