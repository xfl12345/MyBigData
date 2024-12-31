package cc.xfl12345.mybigdata.server.common.database.mapper;

import java.util.List;

public interface TableMapper<Pojo, Condition> extends TableBasicMapper<Pojo> {
    /**
     * 按 条件 查询数据。失败则抛出异常。
     */
    List<Pojo> selectByCondition(Condition condition);

    /**
     * 按 条件 更新数据。失败则抛出异常。
     * @return 影响行数
     */
    long updateByCondition(Pojo pojo, Condition condition);

    /**
     * 按 全局ID 删除数据。失败则抛出异常。
     * @return 影响行数
     */
    long deleteByCondition(Condition condition);

    Class<Condition> getConditionType();
}
