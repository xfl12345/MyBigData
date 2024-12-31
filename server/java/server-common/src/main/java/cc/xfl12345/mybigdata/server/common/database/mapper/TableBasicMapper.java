package cc.xfl12345.mybigdata.server.common.database.mapper;

import cc.xfl12345.mybigdata.server.common.data.source.pojo.MbdId;

import java.util.LinkedHashMap;
import java.util.List;

public interface TableBasicMapper<Pojo> {
    /**
     * 插入数据。失败则抛出异常。
     * @return 影响行数
     */
    long insert(Pojo pojo);

    /**
     * 插入数据。失败则抛出异常。如果影响行数与插入数组长度不一致，也应该抛出异常。
     * @return 影响行数
     */
    long insertBatch(List<Pojo> pojos);

    /**
     * 插入数据，返回 全局数据记录表 的 ID
     * @return 全局数据记录表 的 ID
     */
    MbdId insertAndReturnId(Pojo pojo);

    /**
     * 唯一检索，定值查询。给定 POJO ，使用 POJO 内部的值作为筛选条件， 使用 fields 作为获取内容的约束范围。
     * 如果 fields 的值是 null 或者是一个 空数组，则将查询该 POJO 所有字段的值。
     * 假定有 POJO 结构 {a: int, b: int, c: int, d: int}
     * 在数据库中有唯一行 {a: 1, b: 2, c: 3, d: 4} 且满足 a = 1 条件的只有该行，
     * 传入 POJO={a: 1} ， fields=["d"] ，
     * 则该函数应该返回 POJO {d: 4} 。
     * 假定有 POJO 结构 {a: int, b: int, c: int, d: int, e: string}
     * 在数据库中有唯一行 {a: 1, b: 2, c: 3, d: 4, e: "Hello,world!"} 且满足 a = 1 条件的只有该行，
     * 传入 POJO={a: 1} ， fields=[] （或者传入 POJO={a: 1} , fields=null），
     * 则该函数应该返回 POJO {a: 1, b: 2, c: 3, d: 4, e: "Hello,world!"} 。
     * 如果不满足唯一匹配（数据库返回多条匹配结果），则应当抛出运行时异常 {@link cc.xfl12345.mybigdata.server.common.database.error.TableOperationException}
     */
    Pojo selectOne(Pojo pojo, String... fields);

    /**
     * 唯一检索，定值查询。给定 ID ，使用 ID 的值作为筛选条件， 使用 fields 作为获取内容的约束范围。
     * fields 的约定同 {@link TableBasicMapper#selectOne(Object, String...)}
     * @param globalId 全局数据记录表 的 ID
     * @param fields 指定返回的 POJO 哪些字段该有内容
     * @return POJO
     */
    Pojo selectById(MbdId globalId, String... fields);

    /**
     * {@link TableBasicMapper#selectById(MbdId, String...)} 的 批量版
     * @param globalIdList 一些 全局数据记录表 的 ID
     * @param fields 指定返回的 POJO 哪些字段该有内容
     * @return POJO
     */
    LinkedHashMap<MbdId, Pojo> selectBatchById(List<MbdId> globalIdList, String... fields);

    /**
     * 给定数据，返回 全局数据记录表 的 ID
     * @return 全局数据记录表 的 ID
     */
    MbdId selectId(Pojo pojo);

    /**
     * 按 全局ID 更新数据。失败则抛出异常。
     */
    void updateById(Pojo pojo, MbdId globalId);

    /**
     * 按 全局ID 删除数据。失败则抛出异常。
     */
    void deleteById(MbdId globalId);

    /**
     * {@link TableBasicMapper#deleteById(MbdId)} 的 批量版
     * @param globalIdList 一些 全局数据记录表 的 ID
     */
    void deleteBatchById(List<MbdId> globalIdList);

    boolean isForUpdate();

    void setForUpdate(boolean forUpdate);

    void clearForUpdateFlag();

    /**
     * 受限于 Java 的半伪泛型，为了编程快乐，特别强制实现获取 泛型类型 的接口。
     * @return 实现该接口时使用的泛型类型
     */
    Class<Pojo> getPojoType();
}
