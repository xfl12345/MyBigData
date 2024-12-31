package cc.xfl12345.mybigdata.server.common.database;

import cc.xfl12345.mybigdata.server.common.appconst.DefaultSingleton;
import cc.xfl12345.mybigdata.server.common.data.source.pojo.MbdId;
import cc.xfl12345.mybigdata.server.common.database.error.IllegalDataException;
import cc.xfl12345.mybigdata.server.common.pojo.FieldNotNullChecker;
import cc.xfl12345.mybigdata.server.common.pojo.TwoWayMap;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import jakarta.annotation.PostConstruct;

@Slf4j
public abstract class AbstractCoreTableCache<ID, Value> {
    @Getter
    @Setter
    protected FieldNotNullChecker fieldNotNullChecker = DefaultSingleton.FIELD_NOT_NULL_CHECKER;

    @Getter
    protected TwoWayMap<Value, MbdId> tableNameCache;

    @Getter
    protected MbdId idOfTrue;

    @Getter
    protected MbdId idOfFalse;

    @Getter
    protected MbdId idOfNull;

    @PostConstruct
    public void init() throws Exception {
        refreshBooleanCache();
        refreshCoreTableNameCache();
    }

    public abstract Class<ID> getIdType();

    public abstract void refreshBooleanCache() throws Exception;

    public abstract void refreshNullCache() throws Exception;

    public abstract void refreshCoreTableNameCache() throws Exception;

    public boolean getBooleanById(MbdId globalId) {
        if (idOfTrue.equals(globalId)) {
            return true;
        }
        if (idOfFalse.equals(globalId)) {
            return false;
        }

        throw new IllegalDataException(
            "The reference of id '" + globalId + "' is not a boolean value.",
            new MbdId[]{globalId}
        );
    }

    public abstract MbdId getTableNameId(Class<?> pojoClass);

    public abstract Class<?> getPojoClassByTableNameId(MbdId id);

    public abstract <T> T getEmptyPoEntity(Class<T> pojoClass);
}
