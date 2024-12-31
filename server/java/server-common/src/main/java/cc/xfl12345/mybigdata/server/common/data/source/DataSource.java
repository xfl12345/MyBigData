package cc.xfl12345.mybigdata.server.common.data.source;

import cc.xfl12345.mybigdata.server.common.appconst.AppDataType;
import cc.xfl12345.mybigdata.server.common.appconst.CURD;
import cc.xfl12345.mybigdata.server.common.data.DataSourceApi;
import cc.xfl12345.mybigdata.server.common.data.interceptor.InterceptorManager;
import cc.xfl12345.mybigdata.server.common.data.source.pojo.MbdId;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.LinkedHashMap;
import java.util.List;

public interface DataSource<Value> {
    /**
     * 获取 数据对象 的 全局数据记录表 的 ID。优先查询。如果值不存在，再插入。
     * 如果数据不存在，则插入并生成 全局ID ，如果数据存在，则返回其 全局ID 。
     *
     * @return 全局数据记录表 的 ID
     */
    @DataSourceApi
    MbdId selectIdOrInsert4Id(Value value);


    /**
     * 插入数据，返回 全局数据记录表 的 ID 。该 API 只对有 自增主键 的表有用。
     *
     * @return 全局数据记录表 的 ID
     */
    @DataSourceApi(curdType = CURD.CREATE)
    MbdId insertAndReturnId(Value value);

    /**
     * 插入数据。失败则抛出异常。
     *
     * @return 影响行数
     */
    @DataSourceApi(curdType = CURD.CREATE)
    long insert(Value value);

    /**
     * 插入数据。失败则抛出异常。
     *
     * @return 影响行数
     */
    @DataSourceApi(curdType = CURD.CREATE)
    long insertBatch(List<Value> values);


    /**
     * 给定数据，返回 全局数据记录表 的 ID
     *
     * @return 全局数据记录表 的 ID
     */
    @DataSourceApi(curdType = CURD.RETRIEVE)
    MbdId selectId(Value value);

    @DataSourceApi(curdType = CURD.RETRIEVE)
    Value selectById(MbdId globalId);

    @DataSourceApi(curdType = CURD.RETRIEVE)
    LinkedHashMap<Value, MbdId> selectBatchId(List<Value> values);

    @DataSourceApi(curdType = CURD.RETRIEVE)
    LinkedHashMap<MbdId, Value> selectBatchById(List<MbdId> globalIdList);


    @DataSourceApi(curdType = CURD.UPDATE)
    default void update(Value theOld, Value theNew) {
        updateById(theNew, selectId(theOld));
    }

    /**
     * 按 全局ID 更新数据。失败则抛出异常。
     */
    @DataSourceApi(curdType = CURD.UPDATE)
    void updateById(Value value, MbdId globalId);


    @DataSourceApi(curdType = CURD.DELETE)
    default void delete(Value value) {
        deleteById(selectId(value));
    }

    /**
     * 按 全局ID 删除数据。失败则抛出异常。
     */
    @DataSourceApi(curdType = CURD.DELETE)
    void deleteById(MbdId globalId);

    @DataSourceApi(curdType = CURD.DELETE)
    void deleteBatchById(List<MbdId> globalIdList);

    AppDataType getDataEnumType();

    Class<Value> getValueType();

    InterceptorManager getInterceptorManager();
}
