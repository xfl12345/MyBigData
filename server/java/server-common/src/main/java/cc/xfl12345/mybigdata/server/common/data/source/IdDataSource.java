package cc.xfl12345.mybigdata.server.common.data.source;

import cc.xfl12345.mybigdata.server.common.appconst.AppDataType;
import cc.xfl12345.mybigdata.server.common.data.source.pojo.MbdId;

import java.util.Date;
import java.util.List;

public interface IdDataSource extends DataSource<MbdId> {
    MbdId getNewRegisteredId(Date createTime, MbdId tableNameId);

    List<MbdId> getNewRegisteredIds(Date createTime, MbdId tableNameId, int batchSize);

    void updateOneRow(MbdId id, Date updateTime);

    AppDataType getDataEnumType(MbdId id);

    @Override
    default AppDataType getDataEnumType() {
        return AppDataType.Id;
    }
}
