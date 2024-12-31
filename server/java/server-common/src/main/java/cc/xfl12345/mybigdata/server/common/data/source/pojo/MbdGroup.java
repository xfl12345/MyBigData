package cc.xfl12345.mybigdata.server.common.data.source.pojo;

import cc.xfl12345.mybigdata.server.common.appconst.AppDataType;

import java.util.List;

public interface MbdGroup extends MbdCollection {
    Boolean isUniqueItems();

    void setUniqueItems(Boolean unique);

    List<MbdId> getItems();

    void setItems(List<MbdId> items);

    @Override
    default boolean isEqualsExceptData(MbdCollection mbdCollection) {
        if (mbdCollection instanceof MbdGroup mbdGroup) {
            return isPropertyEqual(this, mbdGroup, MbdGroup::getGlobalId) &&
                isPropertyEqual(this, mbdGroup, MbdGroup::getName) &&
                isPropertyEqual(this, mbdGroup, MbdGroup::isUniqueItems);
        }

        return false;
    }

    @Override
    default AppDataType getDataType() {
        return AppDataType.Array;
    }
}
