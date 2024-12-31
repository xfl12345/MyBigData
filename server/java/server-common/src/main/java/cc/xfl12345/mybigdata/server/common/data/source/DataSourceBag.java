package cc.xfl12345.mybigdata.server.common.data.source;

import cc.xfl12345.mybigdata.server.common.appconst.AppDataType;
import cc.xfl12345.mybigdata.server.common.appconst.DefaultSingleton;
import cc.xfl12345.mybigdata.server.common.data.source.pojo.MbdGroup;
import cc.xfl12345.mybigdata.server.common.data.source.pojo.MbdId;
import cc.xfl12345.mybigdata.server.common.data.source.pojo.MbdJsonSchema;
import cc.xfl12345.mybigdata.server.common.data.source.pojo.MbdObject;
import cc.xfl12345.mybigdata.server.common.pojo.FieldNotNullChecker;
import lombok.Getter;
import lombok.Setter;

import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashMap;

public class DataSourceBag {
    @Getter
    @Setter
    protected FieldNotNullChecker fieldNotNullChecker = DefaultSingleton.FIELD_NOT_NULL_CHECKER;

    @Getter
    @Setter
    protected IdDataSource idDataSource;

    @Getter
    @Setter
    protected StringTypeSource stringTypeSource;

    @Getter
    @Setter
    protected NumberTypeSource numberTypeSource;

    @Getter
    @Setter
    protected GroupTypeSource groupTypeSource;

    @Getter
    @Setter
    protected ObjectTypeSource objectTypeSource;

    @Getter
    @Setter
    protected JsonSchemaSource jsonSchemaSource;

    @Getter
    @Setter
    private HashMap<AppDataType, DataSource<?>> dataSourceMap = null;

    @PostConstruct
    public void init() {
        fieldNotNullChecker.check(idDataSource, MbdId.class);
        fieldNotNullChecker.check(stringTypeSource, String.class);
        fieldNotNullChecker.check(numberTypeSource, BigDecimal.class);
        fieldNotNullChecker.check(groupTypeSource, MbdGroup.class);
        fieldNotNullChecker.check(objectTypeSource, MbdObject.class);
        fieldNotNullChecker.check(jsonSchemaSource, MbdJsonSchema.class);

        initDataSourceMap();
    }

    protected void initDataSourceMap() {
        if (dataSourceMap == null) {
            dataSourceMap = new HashMap<>(6);
        }

        dataSourceMap.put(AppDataType.Id, idDataSource);
        dataSourceMap.put(AppDataType.String, stringTypeSource);
        dataSourceMap.put(AppDataType.Number, numberTypeSource);
        dataSourceMap.put(AppDataType.Array, groupTypeSource);
        dataSourceMap.put(AppDataType.Object, objectTypeSource);
        dataSourceMap.put(AppDataType.JsonSchema, jsonSchemaSource);
    }
}
