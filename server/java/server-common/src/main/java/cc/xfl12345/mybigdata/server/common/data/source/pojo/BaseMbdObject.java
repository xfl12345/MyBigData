package cc.xfl12345.mybigdata.server.common.data.source.pojo;

import cc.xfl12345.mybigdata.server.common.appconst.AppDataType;

public interface BaseMbdObject {
    MbdId getGlobalId();

    AppDataType getDataType();
}
