package cc.xfl12345.mybigdata.server.common.data.condition.meta;

import cc.xfl12345.mybigdata.server.common.data.condition.Op;
import lombok.Data;

@Data
public class FieldOperationValue {
    protected String field;

    protected Op operation;

    protected Object value;
}
