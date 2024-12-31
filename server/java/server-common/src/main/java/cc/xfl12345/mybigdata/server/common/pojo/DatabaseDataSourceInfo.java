package cc.xfl12345.mybigdata.server.common.pojo;

import lombok.Getter;
import lombok.Setter;

public class DatabaseDataSourceInfo {
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String dbType;

    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private String driverName;
}
