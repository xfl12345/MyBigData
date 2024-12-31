package cc.xfl12345.mybigdata.server.common.api;

import cc.xfl12345.mybigdata.server.common.appconst.AppDataType;
import cc.xfl12345.mybigdata.server.common.data.source.pojo.BaseMbdObject;
import cc.xfl12345.mybigdata.server.common.data.source.pojo.MbdId;

public interface IdViewer {
    AppDataType getDataTypeById(MbdId id);

    BaseMbdObject getDataById(MbdId id, long recursionDepth);
}
