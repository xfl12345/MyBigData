package cc.xfl12345.mybigdata.server.common.data.condition;

import cc.xfl12345.mybigdata.server.common.data.condition.meta.FieldOperationValue;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

public class ValueConditionImpl implements ValueCondition {
    protected ArrayList<FieldOperationValue> arrayList = new ArrayList<>(6);

    protected ObjectMapper objectMapper;

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ValueConditionImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public ValueCondition op(String field, Op op, Object value) {
        FieldOperationValue fieldOperationValue = new FieldOperationValue();
        fieldOperationValue.setField(field);
        fieldOperationValue.setOperation(op);
        fieldOperationValue.setValue(value);
        arrayList.add(fieldOperationValue);
        return this;
    }

    @Override
    public JsonNode getJson() {
        return objectMapper.valueToTree(arrayList);
    }
}
