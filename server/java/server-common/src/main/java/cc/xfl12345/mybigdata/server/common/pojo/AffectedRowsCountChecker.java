package cc.xfl12345.mybigdata.server.common.pojo;

import cc.xfl12345.mybigdata.server.common.appconst.AppConst;
import cc.xfl12345.mybigdata.server.common.appconst.CURD;
import cc.xfl12345.mybigdata.server.common.database.error.TableOperationException;
import lombok.Getter;
import lombok.Setter;

public class AffectedRowsCountChecker {
    @Getter
    @Setter
    protected String messageAffectedRowsCountShouldBeOne = AppConst.MESSAGE_AFFECTED_ROWS_COUNT_SHOULD_BE_ONE;

    @Getter
    @Setter
    protected String messageAffectedRowsCountDoesNotMatch = AppConst.MESSAGE_AFFECTED_ROWS_COUNT_DOES_NOT_MATCH;

    public void checkAffectedRowShouldBeOne(long affectedRowsCount, CURD operation, String tableName) {
        if (affectedRowsCount != 1) {
            throw new TableOperationException(
                messageAffectedRowsCountShouldBeOne,
                affectedRowsCount,
                1L,
                operation,
                tableName
            );
        }
    }

    public void checkAffectedRowsCountDoesNotMatch(
        long affectedRowsCount, long expectAffectedRowsCount, CURD operation, String tableName) {
        if (affectedRowsCount != expectAffectedRowsCount) {
            throw new TableOperationException(
                messageAffectedRowsCountDoesNotMatch,
                affectedRowsCount,
                expectAffectedRowsCount,
                operation,
                tableName
            );
        }
    }
}
