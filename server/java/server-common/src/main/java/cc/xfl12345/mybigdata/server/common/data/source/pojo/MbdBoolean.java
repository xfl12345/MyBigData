package cc.xfl12345.mybigdata.server.common.data.source.pojo;

import cc.xfl12345.mybigdata.server.common.appconst.AppDataType;

public interface MbdBoolean extends MbdSingleData<Boolean> {
    @Override
    default AppDataType getDataType() {
        return AppDataType.Boolean;
    }
}
