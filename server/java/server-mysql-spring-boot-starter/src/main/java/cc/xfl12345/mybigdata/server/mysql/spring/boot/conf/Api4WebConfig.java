package cc.xfl12345.mybigdata.server.mysql.spring.boot.conf;

import cc.xfl12345.mybigdata.server.common.api.*;
import cc.xfl12345.mybigdata.server.common.data.source.DataSourceHome;
import cc.xfl12345.mybigdata.server.mysql.api.*;
import cc.xfl12345.mybigdata.server.mysql.database.mapper.base.CoreTableCache;
import cc.xfl12345.mybigdata.server.mysql.database.mapper.base.MapperProperties;
import cc.xfl12345.mybigdata.server.mysql.database.mapper.impl.DaoPack;
import cc.xfl12345.mybigdata.server.mysql.database.mapper.impl.bee.GlobalDataRecordBeeTableMapper;
import cc.xfl12345.mybigdata.server.mysql.database.pojo.AuthAccount;
import cc.xfl12345.mybigdata.server.mysql.database.pojo.GlobalDataRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Api4WebConfig {

    @Bean
    @ConditionalOnMissingBean(value = {GlobalDataRecordMapper.class})
    public GlobalDataRecordBeeTableMapper globalDataRecordMapper(DaoPack daoPack) {
        MapperProperties mapperProperties = daoPack.getMapperProperties();

        GlobalDataRecordMapperImpl mapper = new GlobalDataRecordMapperImpl();
        mapper.setCoreTableCache(mapperProperties.getCoreTableCache());
        mapper.setUuidGenerator(mapperProperties.getUuidGenerator());

        mapper.setTableMapper(daoPack.getBeeTableMapper(GlobalDataRecord.class));

        return mapper;
    }

    @Bean
    @ConditionalOnMissingBean
    public AccountMapper accountMapper(DaoPack daoPack) {
        AccountMapperImpl accountMapper = new AccountMapperImpl();
        accountMapper.setTableBasicMapper(daoPack.getTableBasicMapper(AuthAccount.class));

        return accountMapper;
    }

    @Bean
    @ConditionalOnMissingBean
    public AdvanceSearchMapper advanceSearchMapper(CoreTableCache coreTableCache) {
        return new AdvanceSearchMapperImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public DatabaseViewer databaseViewer(CoreTableCache coreTableCache) {
        DatabaseViewerImpl databaseViewer = new DatabaseViewerImpl();
        databaseViewer.setCoreTableCache(coreTableCache);

        return databaseViewer;
    }

    @Bean
    @ConditionalOnMissingBean
    public IdViewer idViewer(DataSourceHome dataSourceHome) {
        IdViewerImpl idViewer = new IdViewerImpl();
        idViewer.setDataSourceHome(dataSourceHome);

        return idViewer;
    }
}
