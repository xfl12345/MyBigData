package cc.xfl12345.mybigdata.server.mysql.spring.web.controller;

import com.alibaba.druid.stat.DruidStatService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@ConditionalOnClass(DruidStatService.class)
@RestController
@Slf4j
@RequestMapping(DruidStatController.servletName)
public class DruidStatController {

    protected DruidStatService statService = DruidStatService.getInstance();
    public static final String servletName = "druid";
    public static final String servletPathCache1 = "/" + servletName;

    // 为安全考虑，强制必须设置 reset-enable 的值
    @Value("${spring.datasource.druid.stat-view-servlet.reset-enable}")
    public void setResetEnable(boolean resetEnable) {
        statService.setResetEnable(resetEnable);
    }

    public boolean isResetEnable() {
        return statService.isResetEnable();
    }

    protected String resourceRootPath = "support/http/resources/";
    protected URL rootFileURL;
    protected String rootFileUrlString;
    protected ConcurrentHashMap<String, ResourceDetail> druidFrontendFiles = new ConcurrentHashMap<>();

    public static class ResourceDetail {
        public Resource resource;
        public String mimeType;
        public String path;
    }

    @PostConstruct
    public void init() throws Exception {
        Tika tika = new Tika();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        rootFileURL = Objects.requireNonNull(classLoader.getResource(resourceRootPath));
        rootFileUrlString = rootFileURL.toString();
        // String rootFileClasspathBase = rootFileUrlString.substring(0, rootFileUrlString.lastIndexOf(resourceRootPath));

        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(resourceRootPath + "**");
        for (Resource resource : resources) {
            URL currentFileURL = resource.getURL();
            String relativePath;
            if (resource instanceof ClassPathResource classPathResource) {
                relativePath = '/' + classPathResource.getPath();
            } else {
                relativePath = '/' + currentFileURL.toString().substring(rootFileUrlString.length());
            }
            relativePath = relativePath.substring(resourceRootPath.length());

            int lastIndexOfSplitChar = relativePath.lastIndexOf('/');
            // 如果是文件，而不是文件夹
            if (relativePath.length() - 1 > lastIndexOfSplitChar) {
                String filename = relativePath.substring(lastIndexOfSplitChar + 1);

                ResourceDetail detail = new ResourceDetail();
                detail.resource = resource;
                detail.path = relativePath;
                try (InputStream inputStream = resource.getInputStream()) {
                    detail.mimeType = tika.detect(inputStream, filename);
                }

                log.debug("Mapping request: [" + relativePath + "] <---> [" + currentFileURL.toString() + "]");
                druidFrontendFiles.put(relativePath, detail);
            }

        }

    }

    @GetMapping(path = {"", "index"})
    public void redirectIndexPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String relativeURL = request.getServletPath().substring(servletPathCache1.length());
        if ("".equals(relativeURL)) {
            response.sendRedirect("./" + servletName + "/index.html");
        } else {
            response.sendRedirect("./index.html");
        }
    }

    @RequestMapping("/**")
    public void forward(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String relativeURL = request.getServletPath().substring(servletPathCache1.length());

        ResourceDetail resourceDetail = druidFrontendFiles.get(relativeURL);
        // 如果命中了静态资源，则直接返回文件。
        if (resourceDetail != null) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType(resourceDetail.mimeType);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());

            InputStream is = resourceDetail.resource.getInputStream();
            OutputStream os = response.getOutputStream();
            try (is; os) {
                // 8 KiB buffer
                byte[] buffer = new byte[((1 << 10) << 3)];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) > 0) {
                    os.write(buffer, 0, bytesRead);
                }
            }
        } else {
            if (request.getMethod().equalsIgnoreCase(HttpMethod.GET.name())) {
                String httpGetQueryString = request.getQueryString();
                if (httpGetQueryString != null && !httpGetQueryString.isEmpty()) {
                    relativeURL += '?' + httpGetQueryString;
                }
            }

            response.setContentType("application/json;charset=UTF-8");
            try (Writer writer = response.getWriter()) {
                writer.write(statService.service(relativeURL));
            }
        }
    }
}
