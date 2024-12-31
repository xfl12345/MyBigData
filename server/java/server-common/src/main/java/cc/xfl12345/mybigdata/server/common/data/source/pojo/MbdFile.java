package cc.xfl12345.mybigdata.server.common.data.source.pojo;

import cc.xfl12345.mybigdata.server.common.appconst.AppDataType;

public interface MbdFile extends BaseMbdObject {
    String getFormat();

    @Override
    default AppDataType getDataType() {
        return AppDataType.File;
    }
}
