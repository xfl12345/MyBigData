package cc.xfl12345.mybigdata.server.mysql.database.pojo;

import cc.xfl12345.mybigdata.server.common.pojo.OpenCloneable;

import java.io.Serializable;

/**
 * 表名：object_content
 * 表注释：专门记录 "JSON Object" 的表。
*/
@lombok.Data
@lombok.experimental.SuperBuilder
@lombok.NoArgsConstructor
@lombok.experimental.FieldNameConstants
@io.swagger.annotations.ApiModel("专门记录 \"JSON Object\" 的表。")
@jakarta.persistence.Table(name = "object_content")
@jakarta.persistence.Entity
public class ObjectContent implements OpenCloneable, Serializable {
    /**
     * 对象id
     */
    @jakarta.persistence.Column(name = "global_id", nullable = false)
    @jakarta.persistence.GeneratedValue(generator = "JDBC")
    @io.swagger.annotations.ApiModelProperty("对象id")
    @jakarta.persistence.Id
    private Long globalId;

    /**
     * 属性名称
     */
    @jakarta.persistence.Column(name = "the_key", nullable = false)
    @io.swagger.annotations.ApiModelProperty("属性名称")
    private Long theKey;

    /**
     * 属性值
     */
    @jakarta.persistence.Column(name = "the_value", nullable = true)
    @io.swagger.annotations.ApiModelProperty("属性值")
    private Long theValue;

    private static final long serialVersionUID = 1L;

    @Override
    public ObjectContent clone() throws CloneNotSupportedException {
        return (ObjectContent) super.clone();
    }
}
