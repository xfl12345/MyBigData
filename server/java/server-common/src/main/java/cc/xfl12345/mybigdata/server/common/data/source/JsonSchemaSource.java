package cc.xfl12345.mybigdata.server.common.data.source;

import cc.xfl12345.mybigdata.server.common.appconst.AppDataType;
import cc.xfl12345.mybigdata.server.common.data.source.pojo.MbdJsonSchema;

public interface JsonSchemaSource extends MbdTypeDataSource<MbdJsonSchema> {
    @Override
    default AppDataType getDataEnumType() {
        return AppDataType.JsonSchema;
    }
}
