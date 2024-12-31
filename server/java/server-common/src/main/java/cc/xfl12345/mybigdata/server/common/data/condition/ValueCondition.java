package cc.xfl12345.mybigdata.server.common.data.condition;

import com.fasterxml.jackson.databind.JsonNode;

public interface ValueCondition {
    public ValueCondition op(String field, Op op, Object value);

    public JsonNode getJson();
}
