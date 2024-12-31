package cc.xfl12345.mybigdata.server.common.data.source.pojo;

import cc.xfl12345.mybigdata.server.common.appconst.AppDataType;

import java.math.BigDecimal;

public interface MbdNumber extends MbdSingleData<BigDecimal> {
    @Override
    default AppDataType getDataType() {
        return AppDataType.Number;
    }
}
