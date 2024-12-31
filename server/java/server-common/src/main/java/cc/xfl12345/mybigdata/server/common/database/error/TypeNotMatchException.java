package cc.xfl12345.mybigdata.server.common.database.error;

import cc.xfl12345.mybigdata.server.common.appconst.AppDataType;
import com.fasterxml.jackson.databind.JsonNode;

public class TypeNotMatchException extends RuntimeException {

    protected AppDataType expectDataType;

    protected JsonNode data;

    public TypeNotMatchException(String message, AppDataType expectDataType, JsonNode data) {
        super(message);
        this.expectDataType = expectDataType;
        this.data = data;
    }

    public TypeNotMatchException(AppDataType expectDataType, JsonNode data) {
        this("Expect data type: [" + expectDataType + "] but parse JSON failed.", expectDataType, data);
    }

    public AppDataType getExpectDataType() {
        return expectDataType;
    }

    public JsonNode getData() {
        return data;
    }
}

