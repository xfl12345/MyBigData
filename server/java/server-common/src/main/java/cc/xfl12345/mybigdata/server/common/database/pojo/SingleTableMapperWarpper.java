package cc.xfl12345.mybigdata.server.common.database.pojo;

import cc.xfl12345.mybigdata.server.common.database.mapper.TableMapper;

import java.util.List;

public abstract class SingleTableMapperWarpper<Pojo, CommonPojo, Condition>
    extends SingleTableBasicMapperWarpper<Pojo, CommonPojo>
    implements TableMapper<CommonPojo, Condition> {

    protected abstract TableMapper<Pojo, Condition> getTableMapper();

    @Override
    public List<CommonPojo> selectByCondition(Condition condition) {
        return getTableMapper().selectByCondition(condition).parallelStream().map(this::cast2CommonPojo).toList();
    }

    @Override
    public long updateByCondition(CommonPojo commonPojo, Condition condition) {
        return getTableMapper().updateByCondition(cast2Pojo(commonPojo), condition);
    }

    @Override
    public long deleteByCondition(Condition condition) {
        return getTableMapper().deleteByCondition(condition);
    }

    @Override
    public Class<Condition> getConditionType() {
        return getTableMapper().getConditionType();
    }

}
