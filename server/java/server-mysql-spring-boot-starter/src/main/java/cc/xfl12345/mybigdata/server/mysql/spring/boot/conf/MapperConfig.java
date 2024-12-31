package cc.xfl12345.mybigdata.server.mysql.spring.boot.conf;

import cc.xfl12345.mybigdata.server.common.database.error.SqlErrorAnalyst;
import cc.xfl12345.mybigdata.server.mysql.database.mapper.base.CoreTableCache;
import cc.xfl12345.mybigdata.server.mysql.database.mapper.base.MapperProperties;
import cc.xfl12345.mybigdata.server.mysql.database.mapper.impl.DaoPack;
import com.fasterxml.uuid.NoArgGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    @ConditionalOnMissingBean
    public MapperProperties tableMapperProperties(
        CoreTableCache coreTableCache,
        NoArgGenerator uuidGenerator,
        SqlErrorAnalyst sqlErrorAnalyst) {
        MapperProperties tableMapperProperties = new MapperProperties();
        tableMapperProperties.setCoreTableCache(coreTableCache);
        tableMapperProperties.setUuidGenerator(uuidGenerator);
        tableMapperProperties.setSqlErrorAnalyst(sqlErrorAnalyst);

        return tableMapperProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public DaoPack daoPack(MapperProperties tableMapperProperties) {
        DaoPack daoPack = new DaoPack();
        daoPack.setMapperProperties(tableMapperProperties);

        return daoPack;
    }
}
