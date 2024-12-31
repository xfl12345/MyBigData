package cc.xfl12345.mybigdata.server.mysql.spring.boot.conf;

import cc.xfl12345.mybigdata.server.common.database.error.SqlErrorAnalyst;
import cc.xfl12345.mybigdata.server.mysql.database.error.SqlErrorAnalystImpl;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.NoArgGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NormalConfig {
    @Bean
    @ConditionalOnMissingBean
    public SqlErrorAnalyst sqlErrorAnalyst() {
        return new SqlErrorAnalystImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public NoArgGenerator uuidGenerator() {
        return Generators.timeBasedGenerator();
    }
}
