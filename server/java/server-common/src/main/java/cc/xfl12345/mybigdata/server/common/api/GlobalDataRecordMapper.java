package cc.xfl12345.mybigdata.server.common.api;

import cc.xfl12345.mybigdata.server.common.database.mapper.TableBasicMapper;
import cc.xfl12345.mybigdata.server.common.database.pojo.CommonGlobalDataRecord;
import cc.xfl12345.mybigdata.server.common.data.source.pojo.MbdId;

import java.util.Date;
import java.util.List;

public interface GlobalDataRecordMapper extends TableBasicMapper<CommonGlobalDataRecord> {
    CommonGlobalDataRecord getNewDataInstance(Date createTime, MbdId tableNameId);

    CommonGlobalDataRecord getNewRegisteredDataInstance(Date createTime, MbdId tableNameId);

    List<CommonGlobalDataRecord> getNewDataInstances(Date createTime, MbdId tableNameId, int batchSize);

    List<CommonGlobalDataRecord> getNewRegisteredDataInstances(Date createTime, MbdId tableNameId, int batchSize);

    List<CommonGlobalDataRecord> getRecordsByUUID(List<String> uuids);

    void updateOneRow(MbdId id, Date updateTime);

    void updateOneRow(CommonGlobalDataRecord record, Date updateTime);
}
