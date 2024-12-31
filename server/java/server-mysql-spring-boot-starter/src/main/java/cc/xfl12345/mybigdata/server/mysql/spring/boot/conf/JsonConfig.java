package cc.xfl12345.mybigdata.server.mysql.spring.boot.conf;

import cc.xfl12345.mybigdata.server.common.data.source.pojo.MbdId;
import cc.xfl12345.mybigdata.server.common.json.JsonResourceMapper;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.networknt.schema.JsonMetaSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class JsonConfig {

    @Bean
    @ConditionalOnMissingBean(name = {"json2MbdIdMapper"})
    public Module json2MbdIdMapper() {
        SimpleModule simpleModule = new SimpleModule("json2MbdIdMapper");

        simpleModule.addSerializer(MbdId.class, ToStringSerializer.instance);
        simpleModule.addDeserializer(MbdId.class, new JsonDeserializer<>() {
            @Override
            public MbdId deserialize(JsonParser parser, DeserializationContext context) throws IOException {
                return new MbdId(parser.getLongValue());
            }
        });

        return simpleModule;
    }

    @Bean
    @ConditionalOnMissingBean(name = {"json2MongodbIdMapper"})
    public Module json2MongodbIdMapper() {
        SimpleModule simpleModule = new SimpleModule("json2MongodbIdMapper");

        simpleModule.addSerializer(ObjectId.class, ToStringSerializer.instance);
        simpleModule.addDeserializer(ObjectId.class, new JsonDeserializer<>() {
            @Override
            public ObjectId deserialize(JsonParser parser, DeserializationContext context) throws IOException {
                return new ObjectId(parser.getText());
            }
        });

        return simpleModule;
    }


    @Bean
    @ConditionalOnMissingBean(name = {"jsonSchemaDraftV202012"})
    public JsonMetaSchema jsonSchemaDraftV202012() {
        return JsonSchemaFactory.checkVersion(SpecVersion.VersionFlag.V202012).getInstance();
    }

    @Bean
    @ConditionalOnMissingBean
    public JsonResourceMapper jsonResourceMapper(ObjectMapper objectMapper) {
        JsonResourceMapper jsonResourceMapper = new JsonResourceMapper();
        jsonResourceMapper.setObjectMapper(objectMapper);

        return jsonResourceMapper;
    }

    @Bean
    @ConditionalOnMissingBean
    public JsonSchemaFactory jsonSchemaFactory(
        JsonResourceMapper jsonResourceMapper,
        @Qualifier("jsonSchemaDraftV202012") JsonMetaSchema jsonSchemaDraftV202012) {
        return new JsonSchemaFactory.Builder()
            .addUriMappings(jsonResourceMapper.getUriMappings())
            .addMetaSchema(JsonSchemaFactory.checkVersion(SpecVersion.VersionFlag.V201909).getInstance())
            .addMetaSchema(jsonSchemaDraftV202012)
            .defaultMetaSchemaURI(jsonSchemaDraftV202012.getUri())
            .build();
    }

}
