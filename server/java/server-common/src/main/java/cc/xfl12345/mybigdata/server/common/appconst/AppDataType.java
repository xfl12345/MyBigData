package cc.xfl12345.mybigdata.server.common.appconst;

public enum AppDataType {
    Null,
    Boolean,
    String,
    Number,
    Array,
    Object,
    JsonSchema,
    Id,
    File;

    public static AppDataType getDataTypeByV202012KeyWords(String type) {
        AppDataType dataType;
        switch (type) {
            case "null" -> dataType = Null;
            case "boolean" -> dataType = Boolean;
            case "object" -> dataType = Object;
            case "array" -> dataType = Array;
            case "number" -> dataType = Number;
            case "string" -> dataType = String;
            default -> dataType = null;
        }

        return dataType;
    }
}
