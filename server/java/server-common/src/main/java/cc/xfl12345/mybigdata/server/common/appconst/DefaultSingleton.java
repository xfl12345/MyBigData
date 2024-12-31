package cc.xfl12345.mybigdata.server.common.appconst;

import cc.xfl12345.mybigdata.server.common.pojo.AffectedRowsCountChecker;
import cc.xfl12345.mybigdata.server.common.pojo.FieldNotNullChecker;

public class DefaultSingleton {
    public static final FieldNotNullChecker FIELD_NOT_NULL_CHECKER = new FieldNotNullChecker();

    public static final AffectedRowsCountChecker AFFECTED_ROWS_COUNT_CHECKER = new AffectedRowsCountChecker();
}
