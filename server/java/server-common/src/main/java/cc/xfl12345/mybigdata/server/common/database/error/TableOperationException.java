package cc.xfl12345.mybigdata.server.common.database.error;


import cc.xfl12345.mybigdata.server.common.appconst.CURD;

public class TableOperationException extends RuntimeException {
    protected CURD operation;

    protected String tableName;

    protected long affectedRowsCount;

    protected long expectAffectedRowsCount;

    public TableOperationException(String message, long affectedRowsCount, long expectAffectedRowsCount, CURD operation, String tableName) {
        super(message);
        this.affectedRowsCount = affectedRowsCount;
        this.expectAffectedRowsCount = expectAffectedRowsCount;
        this.operation = operation;
        this.tableName = tableName;
    }

    public CURD getOperation() {
        return operation;
    }

    public String getTableName() {
        return tableName;
    }

    public long getAffectedRowsCount() {
        return affectedRowsCount;
    }

    public long getExpectAffectedRowsCount() {
        return expectAffectedRowsCount;
    }
}
