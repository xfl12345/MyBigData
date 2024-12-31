package cc.xfl12345.mybigdata.server.common.data.requirement;

import lombok.Getter;
import lombok.Setter;

public class DataRequirementPack {
    @Getter
    @Setter
    protected Long defaultDeep = null;

    @Getter
    @Setter
    protected StringTypeRequirement stringTypeRequirement;

    @Getter
    @Setter
    protected NumberTypeRequirement numberTypeRequirement;

    @Getter
    @Setter
    protected GroupTypeRequirement groupTypeRequirement;

    @Getter
    @Setter
    protected ObjectTypeRequirement objectTypeRequirement;
}
