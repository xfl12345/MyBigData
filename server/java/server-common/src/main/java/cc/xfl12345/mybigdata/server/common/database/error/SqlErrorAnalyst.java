package cc.xfl12345.mybigdata.server.common.database.error;

import cc.xfl12345.mybigdata.server.common.appconst.TableCurdResult;
import lombok.NonNull;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public interface SqlErrorAnalyst extends SqlErrorMapper {
    TableCurdResult getTableCurdResult(DataSource dataSource, int vendorCode) throws SQLException;

    TableCurdResult getTableCurdResult(Connection connection, int vendorCode) throws SQLException;

    TableCurdResult getTableCurdResult(@NonNull Exception exception);

    TableCurdResult getTableCurdResult(@NonNull SQLException exception);
}
