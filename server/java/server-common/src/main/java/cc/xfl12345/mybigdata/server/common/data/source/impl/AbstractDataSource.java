package cc.xfl12345.mybigdata.server.common.data.source.impl;

import cc.xfl12345.mybigdata.server.common.appconst.DefaultSingleton;
import cc.xfl12345.mybigdata.server.common.appconst.TableCurdResult;
import cc.xfl12345.mybigdata.server.common.data.interceptor.InterceptorManager;
import cc.xfl12345.mybigdata.server.common.data.source.DataSource;
import cc.xfl12345.mybigdata.server.common.data.source.pojo.MbdId;
import cc.xfl12345.mybigdata.server.common.database.error.SqlErrorAnalyst;
import cc.xfl12345.mybigdata.server.common.pojo.FieldNotNullChecker;
import lombok.Getter;
import lombok.Setter;

import jakarta.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;

public abstract class AbstractDataSource<Value> implements DataSource<Value> {
    @Getter
    @Setter
    protected FieldNotNullChecker fieldNotNullChecker = DefaultSingleton.FIELD_NOT_NULL_CHECKER;

    @Getter
    @Setter
    protected SqlErrorAnalyst sqlErrorAnalyst;

    @Getter
    protected InterceptorManager interceptorManager;

    @PostConstruct
    public void init() throws Exception {
        fieldNotNullChecker.check(sqlErrorAnalyst, "sqlErrorAnalyst");

        interceptorManager = new InterceptorManager(this);
    }

    @Override
    public MbdId selectIdOrInsert4Id(Value value) {
        MbdId id;
        try {
            id = selectId(value);
        } catch (RuntimeException e) {
            TableCurdResult result = sqlErrorAnalyst.getTableCurdResult(e);
            if (result.equals(TableCurdResult.FAILED_NOT_FOUND)) {
                id = insertAndReturnId(value);
            } else {
                throw e;
            }
        }

        return id;
    }

    @SuppressWarnings("unchecked")
    protected <T> Class<T> getTypeFromRuntime(int typeArgumentIndex) {
        return (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[typeArgumentIndex];
    }
}
