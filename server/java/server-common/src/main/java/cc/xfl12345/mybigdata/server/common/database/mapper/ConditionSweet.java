package cc.xfl12345.mybigdata.server.common.database.mapper;

import cc.xfl12345.mybigdata.server.common.data.source.pojo.MbdId;

public interface ConditionSweet<Condition> {
    Condition getConditionWithSelectedFields(String... fields);

    void addFields2Condition(Condition condition, String... fields);

    Condition getConditionWithId(MbdId id);

    void addId2Condition(Condition condition, MbdId id);
}
