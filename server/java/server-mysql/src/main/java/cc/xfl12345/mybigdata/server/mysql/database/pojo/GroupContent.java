package cc.xfl12345.mybigdata.server.mysql.database.pojo;

import cc.xfl12345.mybigdata.server.common.pojo.OpenCloneable;

import java.io.Serializable;

/**
 * 表名：group_content
 * 表注释：专门记录 "JSON Array" 的表
*/
@lombok.Data
@lombok.experimental.SuperBuilder
@lombok.NoArgsConstructor
@lombok.experimental.FieldNameConstants
@io.swagger.annotations.ApiModel("专门记录 \"JSON Array\" 的表")
@jakarta.persistence.Table(name = "group_content")
@jakarta.persistence.Entity
public class GroupContent implements OpenCloneable, Serializable {
    /**
     * 组id
     */
    @jakarta.persistence.Column(name = "global_id", nullable = false)
    @jakarta.persistence.GeneratedValue(generator = "JDBC")
    @io.swagger.annotations.ApiModelProperty("组id")
    @jakarta.persistence.Id
    private Long globalId;

    /**
     * 组内对象的下标
     */
    @jakarta.persistence.Column(name = "item_index", nullable = false)
    @io.swagger.annotations.ApiModelProperty("组内对象的下标")
    private Long itemIndex;

    /**
     * 组内对象
     */
    @jakarta.persistence.Column(name = "item", nullable = false)
    @io.swagger.annotations.ApiModelProperty("组内对象")
    private Long item;

    private static final long serialVersionUID = 1L;

    @Override
    public GroupContent clone() throws CloneNotSupportedException {
        return (GroupContent) super.clone();
    }
}
