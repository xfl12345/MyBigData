package cc.xfl12345.mybigdata.server.common.data.source.pojo;

import cc.xfl12345.mybigdata.server.common.appconst.AppDataType;
import com.networknt.schema.JsonSchema;

public interface MbdJsonSchema extends BaseMbdObject {
    String getName();

    void setName(String name);

    JsonSchema getJsonSchema();

    void setJsonSchema(JsonSchema jsonSchema);

    @Override
    default AppDataType getDataType() {
        return AppDataType.JsonSchema;
    }
}
