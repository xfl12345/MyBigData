package cc.xfl12345.mybigdata.server.mysql.spring.boot.conf;

import cc.xfl12345.mybigdata.server.mysql.spring.helper.JdbcContextFinalizer;
import cc.xfl12345.mybigdata.server.mysql.spring.helper.MyDatabaseInitializer;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceWrapper;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.teasoft.spring.boot.config.BeeProperties;

@Configuration
@AutoConfigureBefore({DataSourceAutoConfiguration.class, BeeProperties.class})
public class DatabaseDataSourceConfig {

    protected void initDatabase(DataSourceProperties dataSourceProperties) throws Exception {
        MyDatabaseInitializer initializer = new MyDatabaseInitializer();
        initializer.setUrl(dataSourceProperties.getUrl());
        initializer.setUsername(dataSourceProperties.getUsername());
        initializer.setPassword(dataSourceProperties.getPassword());
        initializer.init();
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean(initMethod = "init")
    @ConditionalOnClass(DruidDataSource.class)
    public DruidDataSource dataSource(DataSourceProperties dataSourceProperties) throws Exception {
        initDatabase(dataSourceProperties);
        return new DruidDataSourceWrapper();
    }

    @Bean
    @ConditionalOnMissingClass("com.alibaba.druid.pool.DruidDataSource")
    public HikariDataSource dataSource2(DataSourceProperties dataSourceProperties) throws Exception {
        initDatabase(dataSourceProperties);

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
        dataSource.setJdbcUrl(dataSourceProperties.getUrl());
        dataSource.setUsername(dataSourceProperties.getUsername());
        dataSource.setPassword(dataSourceProperties.getPassword());
        dataSource.setPoolName(dataSourceProperties.getName());
        dataSource.setDataSourceJNDI(dataSourceProperties.getJndiName());

        return dataSource;
    }


    @Bean
    @ConditionalOnMissingBean
    public JdbcContextFinalizer jdbcContextFinalizer() {
        return new JdbcContextFinalizer();
    }
}
