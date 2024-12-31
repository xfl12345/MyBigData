package cc.xfl12345.mybigdata.server.common.data.source.pojo;

import cc.xfl12345.mybigdata.server.common.appconst.AppDataType;

public interface MbdString extends MbdSingleData<String> {
    @Override
    default AppDataType getDataType() {
        return AppDataType.String;
    }
}
