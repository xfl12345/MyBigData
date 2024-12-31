package cc.xfl12345.mybigdata.server.common.data.source.pojo;

import cc.xfl12345.mybigdata.server.common.appconst.AppDataType;

import java.util.Map;

public interface MbdObject extends MbdCollection {
    MbdJsonSchema getSchema();

    void setSchema(MbdJsonSchema reactiveJsonSchema);

    String getSchemaPath();

    void setSchemaPath(String schemaPath);

    Map<String, MbdId> getMap();

    void setMap(Map<String, MbdId> map);

    @Override
    default boolean isEqualsExceptData(MbdCollection mbdCollection) {
        if (mbdCollection instanceof MbdObject mbdObject) {
            return isPropertyEqual(this, mbdObject, MbdObject::getGlobalId) &&
                isPropertyEqual(this, mbdObject, MbdObject::getName) &&
                isPropertyEqual(this, mbdObject, MbdObject::getSchema) &&
                isPropertyEqual(this, mbdObject, MbdObject::getSchemaPath);
        }

        return false;
    }

    @Override
    default AppDataType getDataType() {
        return AppDataType.Object;
    }
}
