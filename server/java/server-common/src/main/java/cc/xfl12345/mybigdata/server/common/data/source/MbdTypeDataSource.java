package cc.xfl12345.mybigdata.server.common.data.source;

import cc.xfl12345.mybigdata.server.common.appconst.CURD;
import cc.xfl12345.mybigdata.server.common.data.DataSourceApi;
import cc.xfl12345.mybigdata.server.common.data.source.pojo.BaseMbdObject;

public interface MbdTypeDataSource<Value extends BaseMbdObject> extends DataSource<Value> {
    @DataSourceApi(curdType = CURD.UPDATE)
    default void updateById(Value value) {
        updateById(value, value.getGlobalId());
    }
}
