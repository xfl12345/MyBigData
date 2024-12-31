package cc.xfl12345.mybigdata.server.common.data.source;

import cc.xfl12345.mybigdata.server.common.appconst.AppDataType;

import java.math.BigDecimal;

public interface NumberTypeSource extends DataSource<BigDecimal> {
    @Override
    default AppDataType getDataEnumType() {
        return AppDataType.Number;
    }
}
