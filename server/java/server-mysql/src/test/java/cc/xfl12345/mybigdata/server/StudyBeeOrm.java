package cc.xfl12345.mybigdata.server;

import cc.xfl12345.mybigdata.server.mysql.database.pojo.GlobalDataRecord;
import cc.xfl12345.mybigdata.server.mysql.database.pojo.StringContent;
import cc.xfl12345.mybigdata.server.mysql.spring.helper.JdbcContextFinalizer;
import com.alibaba.druid.DbType;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.clause.MySqlSelectIntoStatement;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import lombok.extern.slf4j.Slf4j;
import org.teasoft.bee.osql.BeeException;
import org.teasoft.bee.osql.api.Condition;
import org.teasoft.bee.osql.Op;
import org.teasoft.bee.osql.api.SuidRich;
import org.teasoft.honey.osql.core.BeeFactory;
import org.teasoft.honey.osql.core.ConditionImpl;
import org.teasoft.honey.osql.core.HoneyFactory;
import org.teasoft.honey.osql.core.ObjectToSQLRich;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class StudyBeeOrm {
    public static void main(String[] args) throws IOException, SQLException {
        DruidDataSource dataSource = TestLoadDataSource.getDataSource();

        BeeFactory beeFactory = BeeFactory.getInstance();
        beeFactory.setDataSource(dataSource);

        StudyBeeOrm studyBeeOrm = new StudyBeeOrm();
        studyBeeOrm.justTest(BeeFactory.getHoneyFactory());

        JdbcContextFinalizer.deregister(null);
    }

    public void justTest(HoneyFactory honeyFactory) {
        TimeBasedGenerator uuidGenerator = Generators.timeBasedGenerator();

        SuidRich suid = honeyFactory.getSuidRich();

        // 插入两行空的 GlobalDataRecord
        printStart();
        ArrayList<GlobalDataRecord> records = new ArrayList<>();
        GlobalDataRecord globalDataRecord = new GlobalDataRecord();
        globalDataRecord.setUuid(uuidGenerator.generate().toString());
        records.add(globalDataRecord);
        globalDataRecord = new GlobalDataRecord();
        globalDataRecord.setUuid(uuidGenerator.generate().toString());
        records.add(globalDataRecord);
        System.out.println(suid.insert(records));
        printEnd();

        // 批量查询测试
        printStart();
        List<GlobalDataRecord> recordList = suid.select(
            new GlobalDataRecord(),
            new ConditionImpl().op(
                GlobalDataRecord.Fields.uuid,
                Op.in,
                records.stream().map(GlobalDataRecord::getUuid).toList()
            )
        );
        System.out.println(recordList);
        printEnd();


        // 超字段长度测试
        printStart();
        StringContent stringContent = new StringContent();
        stringContent.setContent("0123456789".repeat(100));
        stringContent.setGlobalId(recordList.get(0).getId());
        executeInsert(honeyFactory, stringContent);
        printEnd();

        // unique key 冲突测试
        printStart();
        stringContent = new StringContent();
        stringContent.setContent("text");
        stringContent.setGlobalId(recordList.get(0).getId());
        executeInsert(honeyFactory, stringContent);
        printEnd();

        // 主键重复测试
        printStart();
        globalDataRecord = new GlobalDataRecord();
        globalDataRecord.setUuid(uuidGenerator.generate().toString());
        globalDataRecord.setId(recordList.get(0).getId());
        executeInsert(honeyFactory, globalDataRecord);
        printEnd();


        // // 关联查询测试
        // StringContentGlobalRecordAssociation associationQuery = new StringContentGlobalRecordAssociation();
        // associationQuery.setContent("text");
        // MoreTable moreTable = honeyFactory.getMoreTable();
        // System.out.println(JSON.toJSONString(moreTable.select(associationQuery)));


        // 外键约束拒绝删除测试
        printStart();
        stringContent = new StringContent();
        stringContent.setContent("text");
        try {
            suid.delete(stringContent);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Throwable cause = e.getCause();
            if (cause instanceof SQLException sqlException) {
                handleSqlException(sqlException, stringContent);
            }
        }
        printEnd();

        // 条件测试
        printStart();
        Condition condition = new ConditionImpl();
        condition.op(GlobalDataRecord.Fields.tableName, Op.eq, null);
        recordList = suid.select(new GlobalDataRecord(), condition);
        System.out.println(recordList);
        System.out.println(suid.delete(new GlobalDataRecord(), condition));
        printEnd();



        // honeyFactory.getPreparedSql()
        // honeyFactory.getBeeSql().select()
        // List<Long> globalIds = honeyFactory.getCallableSql().select(
        //     "insert_string_content(?, ?, ?, ?)",
        //     Long.MAX_VALUE,
        //     new Object[]{
        //         uuidGenerator.generate(),
        //         "测试描述",
        //         uuidGenerator.generate(),
        //         "xfl666"
        //     }
        // );
        // System.out.println("globalIds:" + globalIds);
    }

    public int executeInsert(HoneyFactory honeyFactory, Object obj) {
        // 尝试插入数据
        int rowCount = 0;
        try {
            rowCount = honeyFactory.getSuidRich().insert(obj);
        } catch (BeeException e) {
            System.out.println(e.getMessage());
            Throwable cause = e.getCause();
            if (cause instanceof SQLException sqlException) {
                handleSqlException(sqlException, obj);
            }
        }

        System.out.println(rowCount);
        return rowCount;
    }

    public void handleSqlException(SQLException sqlException, Object toy) {
        int errorCode = sqlException.getErrorCode();
        BeeFactory beeFactory = BeeFactory.getInstance();
        String dbTypeInString = ((DruidDataSource) beeFactory.getDataSource()).getDbType();
        DbType dbType = DbType.valueOf(dbTypeInString);
        switch (dbType) {
            // 如果是 MySQL 报错
            case mysql -> {
                switch (errorCode) {
                    case 1059 -> { // ER_TOO_LONG_IDENT -- Identifier name '%s' is too long
                        System.out.println("键名太长");
                    }
                    case 1060 -> { // ER_DUP_FIELDNAME -- Duplicate column name '%s'
                        System.out.println("字段名称重复（unique限制）");
                    }
                    case 1061 -> { // ER_DUP_KEYNAME -- Duplicate key name '%s'
                        System.out.println("键名重复");
                    }
                    case 1062 -> { // ER_DUP_ENTRY -- Duplicate entry '%s' for key %d
                        System.out.println("字段内容重复（unique限制）");
                        if (!(toy instanceof StringContent)) {
                            break;
                        }
                        ObjectToSQLRich objectToSQLRich = new ObjectToSQLRich();
                        String sqlStringSelectGDR = objectToSQLRich.toSelectSQL(new GlobalDataRecord());
                        SQLStatement sqlStatement = SQLUtils.parseSingleStatement(sqlStringSelectGDR, DbType.mysql);
                        SQLStatement sqlStatement2 = new MySqlSelectIntoStatement();


                        // MoreTable moreTable = honeyFactory.getMoreTable();
                        // GlobalDataRecord crossQueryEntity = new GlobalDataRecord() {
                        //     @Getter
                        //     @Setter
                        //     @JoinTable(mainField="id", subField="global_id", joinType= JoinType.JOIN)
                        //     private StringContent stringContent = (StringContent) obj;
                        // };
                        // List<GlobalDataRecord> globalDataRecords = moreTable.select(crossQueryEntity);
                    }
                    case 1406 -> { // ER_DATA_TOO_LONG -- Data too long for column '%s' at row %ld
                        System.out.println("数据超出字段长度");
                    }

                    case 1451 -> { // ER_ROW_IS_REFERENCED_2 -- Cannot delete or update a parent row: a foreign key constraint fails (%s)
                        System.out.println("该行已被其他表引用，因外键约束，拒绝删除。");
                    }
                    default -> {
                        System.out.println("未知错误 code=" + errorCode);
                    }
                }
            }
            default -> System.out.println("Not supported database.");
        }
    }


    public void printStart() {
        System.out.print("\n".repeat(10));
        System.out.println("#".repeat(60));
    }
    public void printEnd() {
        System.out.println("#".repeat(60));
        System.out.print("\n".repeat(10));
    }

}
