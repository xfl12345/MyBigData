package cc.xfl12345.mybigdata.server.common.data.source;

import cc.xfl12345.mybigdata.server.common.appconst.AppDataType;

public interface StringTypeSource extends DataSource<String> {
    @Override
    default AppDataType getDataEnumType() {
        return AppDataType.String;
    }
}
