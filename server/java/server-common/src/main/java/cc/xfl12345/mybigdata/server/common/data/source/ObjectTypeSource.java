package cc.xfl12345.mybigdata.server.common.data.source;

import cc.xfl12345.mybigdata.server.common.appconst.AppDataType;
import cc.xfl12345.mybigdata.server.common.data.source.pojo.MbdId;
import cc.xfl12345.mybigdata.server.common.data.source.pojo.MbdObject;
import cc.xfl12345.mybigdata.server.common.pojo.ReactiveMode;

public interface ObjectTypeSource extends MbdTypeDataSource<MbdObject> {
    MbdObject getReactiveMbdObject(MbdId globalId, ReactiveMode mode);

    @Override
    default AppDataType getDataEnumType() {
        return AppDataType.Object;
    }
}
