package cc.xfl12345.mybigdata.server.common.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class JsonResourceMapper {
    @Getter
    @Setter
    protected String jsonResourceRoot = null;

    @Getter
    @Setter
    protected ObjectMapper objectMapper = null;

    private HashMap<String, String> map = new HashMap<>();

    @PostConstruct
    public void init() throws IOException {
        if (jsonResourceRoot == null) {
            jsonResourceRoot = PackageLandmark.class.getPackageName().replace('.', '/');
        }

        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        refreshCache();
    }

    public URI createJsonSchemFileURI(String resourcePath) {
        return URI.create("resource:/" + jsonResourceRoot + '/' + resourcePath);
    }

    public void refreshCache() throws IOException {
        String resourceClassPath = jsonResourceRoot + '/' + "json_schema_validator_uri_mapping.json";

        URL mappingsURL = Thread.currentThread().getContextClassLoader().getResource(resourceClassPath);
        if (mappingsURL == null) {
            mappingsURL = ClassLoader.getSystemClassLoader().getResource(resourceClassPath);
        }

        if (mappingsURL == null) {
            throw new IllegalArgumentException("Class path of [" + resourceClassPath + "] is not exist.");
        }

        JsonNode jsonNode = objectMapper.readTree(mappingsURL);
        map = new HashMap<>(jsonNode.size());
        for (JsonNode mapping : jsonNode) {
            map.put(
                mapping.get("publicURL").asText(),
                createJsonSchemFileURI(mapping.get("localPath").asText()).toString()
            );
        }
    }

    public Map<String, String> getUriMappings() {
        return map;
    }
}
