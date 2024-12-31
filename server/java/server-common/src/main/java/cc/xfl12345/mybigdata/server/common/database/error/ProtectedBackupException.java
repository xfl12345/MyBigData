package cc.xfl12345.mybigdata.server.common.database.error;

public class ProtectedBackupException extends IllegalStateException {
    public ProtectedBackupException() {
        super("It is forbidden to write data when backing up data.");
    }
}
