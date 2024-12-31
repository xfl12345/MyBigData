package cc.xfl12345.mybigdata.server.common.data.condition;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class SingleTableCondition {
    @Getter
    @Setter
    protected String tableName;

    @Getter
    @Setter
    protected List<ValueCondition> valueConditions;
}
