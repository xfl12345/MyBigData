package cc.xfl12345.mybigdata.server.common.database.error;

import cc.xfl12345.mybigdata.server.common.data.source.pojo.MbdObject;
import com.networknt.schema.ValidationMessage;

import java.util.Set;

public class DataValidationException extends RuntimeException {
    protected MbdObject mbdObject;

    protected Set<ValidationMessage> jsonSchemaValidationMessage;

    public DataValidationException(String message, MbdObject mbdObject, Set<ValidationMessage> jsonSchemaValidationMessage) {
        super(message);
        this.mbdObject = mbdObject;
        this.jsonSchemaValidationMessage = jsonSchemaValidationMessage;
    }

    public MbdObject getMbdObject() {
        return mbdObject;
    }

    public Set<ValidationMessage> getJsonSchemaValidationMessage() {
        return jsonSchemaValidationMessage;
    }
}

