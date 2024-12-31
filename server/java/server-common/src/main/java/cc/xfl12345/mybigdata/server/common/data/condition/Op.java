package cc.xfl12345.mybigdata.server.common.data.condition;

/**
 * Operation Type.
 */
public enum Op {
    /**
     * 等于。"equalTo" is "=".
     */
    equalTo,

    /**
     * 大于。"greaterThan" is ">".
     */
    greaterThan,

    /**
     * 小于。"lessThan" is "<".
     */
    lessThan,

    /**
     * 不等于。"notEqualTo" is "!=".
     */
    notEqualTo,

    /**
     * 大于或等于。"greaterThanOrEqualTo" is ">=".
     */
    greaterThanOrEqualTo,

    /**
     * 小于或等于。"lessThanOrEqualTo" is "<=".
     */
    lessThanOrEqualTo,


    /**
     * 自定义模糊匹配（百分号 和 下划线 将不会被转义，可能导致SQL注入攻击）
     */
    like,


    /**
     * 前缀匹配
     */
    likePrefix,

    /**
     * 后缀匹配
     */
    likeSuffix,

    /**
     * 包含匹配
     */
    likeIncluded,


    /**
     * 在某集合之内
     */
    in,

    /**
     * 在某集合之外
     */
    notIn,


    /**
     * 布尔代数“非”运算
     */
	not;


    Op(){
    }

}
