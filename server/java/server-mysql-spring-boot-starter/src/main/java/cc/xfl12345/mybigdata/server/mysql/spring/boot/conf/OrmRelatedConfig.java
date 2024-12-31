package cc.xfl12345.mybigdata.server.mysql.spring.boot.conf;

import cc.xfl12345.mybigdata.server.common.pojo.InstanceGenerator;
import cc.xfl12345.mybigdata.server.common.web.pojo.response.JsonApiResponseData;
import cc.xfl12345.mybigdata.server.mysql.database.mapper.base.CoreTableCache;
import cc.xfl12345.mybigdata.server.mysql.spring.web.BeeWebApiExecutor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.teasoft.bee.mvc.service.ObjSQLRichService;
import org.teasoft.beex.config.ManageConfig;
import org.teasoft.honey.osql.core.SessionFactory;
import org.teasoft.spring.boot.config.BeeAutoConfiguration;

@Configuration
@AutoConfigureAfter(value = {BeeAutoConfiguration.class})
public class OrmRelatedConfig {
    // protected ManageConfig manageConfig;
    //
    // @Autowired
    // public void setManageConfig(ManageConfig manageConfig) {
    //     this.manageConfig = manageConfig;
    // }

    @Bean
    @ConditionalOnMissingBean
    // @ConditionalOnBean(value = {BeeFactory.class, SessionFactory.class, ManageConfig.class})
    public CoreTableCache coreTableCache(ObjectMapper objectMapper, SessionFactory sessionFactory, ObjSQLRichService objSQLRichService) {
        CoreTableCache coreTableCache = new CoreTableCache();
        coreTableCache.setObjSQLRichService(objSQLRichService);
        coreTableCache.setJacksonObjectMapper(objectMapper);
        return coreTableCache;
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(value = {CoreTableCache.class})
    public BeeWebApiExecutor beeWebApiExecutor(InstanceGenerator<JsonApiResponseData> responseDataInstanceGenerator) {
        BeeWebApiExecutor webApiExecutor = new BeeWebApiExecutor();
        webApiExecutor.setResponseDataInstanceGenerator(responseDataInstanceGenerator);
        return webApiExecutor;
    }
}
