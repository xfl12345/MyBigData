package cc.xfl12345.mybigdata.server.common.data.source.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class PlainMdbGroup implements MbdGroup {
    @Getter
    @Setter
    protected MbdId globalId;

    @Getter
    @Setter
    protected String name;

    @Setter
    protected Boolean uniqueItems;

    @Override
    public Boolean isUniqueItems() {
        return uniqueItems;
    }

    @Getter
    @Setter
    protected List<MbdId> items;

}
