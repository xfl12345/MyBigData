package cc.xfl12345.mybigdata.server.common.api;

import cc.xfl12345.mybigdata.server.common.pojo.DatabaseDataSourceInfo;

import java.util.List;

public interface DatabaseViewer {
    /**
     * 获取 项目涉及到的 数据库表 的名称（原生名称，数据库里的命名，非 ORM 代称）
     * @return 所有表名
     */
    List<String> getAllTableNames();

    /**
     * 获取 指定表名 的 所有字段 的 名称（统一驼峰命名法 camelcase）
     * @param tableName 数据库里表的名称
     * @return 字段列表
     */
    List<String> getTableFieldNames(String tableName);

    /**
     * 获取 指定表名 所含有的 所有记录 的 总条数
     * @param tableName 数据库里表的名称
     * @return 总条数
     */
    long getTableRecordCount(String tableName);

    /**
     * 获取 以 指定表名、行数起点 和 限制数量 为约束的范围数据
     * @param tableName 指定表名
     * @param offset 行数起点
     * @param limit 限制数量
     * @return 有限范围之内的数据
     */
    List<Object> getTableContent(String tableName, long offset, long limit);

    /**
     * 获取 APP 所有 数据库 DataSource ，查看底层驱动情况。
     * @return APP 底层使用的 所有数据库 DataSource
     */
    List<DatabaseDataSourceInfo> getAllDataSourceInfos();
}
