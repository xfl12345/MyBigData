package cc.xfl12345.mybigdata.server.common.data.source;

import cc.xfl12345.mybigdata.server.common.appconst.AppDataType;
import cc.xfl12345.mybigdata.server.common.appconst.DefaultSingleton;
import cc.xfl12345.mybigdata.server.common.data.requirement.DataRequirementPack;
import cc.xfl12345.mybigdata.server.common.data.source.pojo.BaseMbdObject;
import cc.xfl12345.mybigdata.server.common.data.source.pojo.MbdId;
import cc.xfl12345.mybigdata.server.common.pojo.FieldNotNullChecker;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

import jakarta.annotation.PostConstruct;
import java.util.List;

public abstract class DataSourceHome {
    @Getter
    @Setter
    protected FieldNotNullChecker fieldNotNullChecker = DefaultSingleton.FIELD_NOT_NULL_CHECKER;

    @Getter
    @Setter
    protected DataSourceBag dataSourceBag;

    @PostConstruct
    public void init() throws Exception {
        fieldNotNullChecker.check(dataSourceBag, "dataSourceBag");
    }

    public abstract MbdId addData(JsonNode data, AppDataType dataType);

    public abstract AppDataType getDataTypeById(MbdId id);

    public abstract MbdId getIdByData(JsonNode data);

    public abstract BaseMbdObject getMbdDataById(MbdId id, Long recursionDepth);

    public abstract JsonNode getDataById(MbdId id, Long recursionDepth);

    public abstract boolean setData(BaseMbdObject baseMbdObject);

    public abstract List<MbdId> deleteData(JsonNode data, Long recursionDepth);

    public abstract boolean deleteDataById(MbdId id, Long recursionDepth);
}
