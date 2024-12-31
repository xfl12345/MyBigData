package cc.xfl12345.mybigdata.server.common.database.pojo;


import cc.xfl12345.mybigdata.server.common.data.source.pojo.MbdId;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@lombok.Data
@lombok.experimental.FieldNameConstants
public class CommonGlobalDataRecord implements Cloneable, Serializable {
    private MbdId id;

    private String uuid;

    private Date createTime;

    private Date updateTime;

    private Long modifiedCount;

    private MbdId tableName;

    private MbdId description;

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public CommonGlobalDataRecord clone() throws CloneNotSupportedException {
        return (CommonGlobalDataRecord) super.clone();
    }
}
