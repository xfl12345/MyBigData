package cc.xfl12345.mybigdata.server.mysql.spring.boot.conf;

import cc.xfl12345.mybigdata.server.mysql.spring.web.interceptor.DruidStatInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringMvcInterceptorConfig {
    @Bean
    @ConditionalOnMissingBean
    public DruidStatInterceptor druidStatInterceptor() {
        return new DruidStatInterceptor();
    }
}
