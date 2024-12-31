package cc.xfl12345.mybigdata.server.common.database.pojo;

import cc.xfl12345.mybigdata.server.common.appconst.DefaultSingleton;
import cc.xfl12345.mybigdata.server.common.data.source.pojo.MbdId;
import cc.xfl12345.mybigdata.server.common.database.mapper.TableBasicMapper;
import cc.xfl12345.mybigdata.server.common.pojo.FieldNotNullChecker;
import lombok.Getter;
import lombok.Setter;

import jakarta.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class SingleTableBasicMapperWarpper<Pojo, CommonPojo> implements TableBasicMapper<CommonPojo> {
    @Getter
    @Setter
    protected FieldNotNullChecker fieldNotNullChecker = DefaultSingleton.FIELD_NOT_NULL_CHECKER;

    protected abstract TableBasicMapper<Pojo> getTableBasicMapper();

    @PostConstruct
    public void init() throws Exception {
        fieldNotNullChecker.check(getTableBasicMapper(), getDatabasePojoClass());
    }

    protected abstract CommonPojo cast2CommonPojo(Pojo pojo);

    protected abstract Pojo cast2Pojo(CommonPojo commonPojo);

    @Override
    public long insert(CommonPojo commonPojo) {
        return getTableBasicMapper().insert(cast2Pojo(commonPojo));
    }

    @Override
    public long insertBatch(List<CommonPojo> commonPojos) {
        return getTableBasicMapper().insertBatch(commonPojos.parallelStream().map(this::cast2Pojo).toList());
    }

    @Override
    public MbdId insertAndReturnId(CommonPojo commonPojo) {
        return getTableBasicMapper().insertAndReturnId(cast2Pojo(commonPojo));
    }

    @Override
    public CommonPojo selectOne(CommonPojo commonPojo, String... fields) {
        return cast2CommonPojo(getTableBasicMapper().selectOne(cast2Pojo(commonPojo), fields));
    }

    @Override
    public CommonPojo selectById(MbdId globalId, String... fields) {
        return cast2CommonPojo(getTableBasicMapper().selectById(globalId, fields));
    }

    @Override
    public LinkedHashMap<MbdId, CommonPojo> selectBatchById(List<MbdId> globalIdList, String... fields) {
        // Function<Map.Entry<MbdId, Pojo>, MbdId> getIdFunc = Map.Entry::getKey;
        // Function<Map.Entry<MbdId, Pojo>, CommonPojo> getCommonPojoFunc = kv -> cast2CommonPojo(kv.getValue());

        return getTableBasicMapper().selectBatchById(globalIdList, fields).entrySet().parallelStream().collect(Collectors.toMap(
            Map.Entry::getKey,
            kv -> cast2CommonPojo(kv.getValue()),
            (key1, key2) -> key2,
            LinkedHashMap::new
        ));
    }

    @Override
    public MbdId selectId(CommonPojo commonPojo) {
        return getTableBasicMapper().selectId(cast2Pojo(commonPojo));
    }

    @Override
    public void updateById(CommonPojo commonPojo, MbdId globalId) {
        getTableBasicMapper().updateById(cast2Pojo(commonPojo), globalId);
    }

    @Override
    public void deleteById(MbdId globalId) {
        getTableBasicMapper().deleteById(globalId);
    }

    @Override
    public void deleteBatchById(List<MbdId> globalIdList) {
        getTableBasicMapper().deleteBatchById(globalIdList);
    }

    @Override
    public boolean isForUpdate() {
        return getTableBasicMapper().isForUpdate();
    }

    @Override
    public void setForUpdate(boolean forUpdate) {
        getTableBasicMapper().setForUpdate(forUpdate);
    }

    @Override
    public void clearForUpdateFlag() {
        getTableBasicMapper().clearForUpdateFlag();
    }

    protected abstract Class<Pojo> getDatabasePojoClass();
}
