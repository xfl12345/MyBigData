package cc.xfl12345.mybigdata.server.common.data.source;

import cc.xfl12345.mybigdata.server.common.appconst.AppDataType;
import cc.xfl12345.mybigdata.server.common.data.source.pojo.MbdGroup;
import cc.xfl12345.mybigdata.server.common.data.source.pojo.MbdId;
import cc.xfl12345.mybigdata.server.common.pojo.ReactiveMode;

public interface GroupTypeSource extends MbdTypeDataSource<MbdGroup> {
    MbdGroup getReactiveMbdGroup(MbdId globalId, ReactiveMode mode);

    @Override
    default AppDataType getDataEnumType() {
        return AppDataType.Array;
    }
}
