package cc.xfl12345.mybigdata.server.common.database.error;

import cc.xfl12345.mybigdata.server.common.data.source.pojo.MbdId;

public class IllegalDataException extends RuntimeException {
    protected MbdId[] globalIds;

    public IllegalDataException(String message, MbdId[] globalIds) {
        super(message);
        this.globalIds = globalIds;
    }

    public Object[] getGlobalIds() {
        return globalIds;
    }
}
