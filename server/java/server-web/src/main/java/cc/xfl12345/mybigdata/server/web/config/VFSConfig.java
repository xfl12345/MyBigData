package cc.xfl12345.mybigdata.server.web.config;

import cc.xfl12345.mybigdata.server.web.plugin.apache.vfs.SpringBootResourceFileProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.vfs2.*;
import org.apache.commons.vfs2.impl.DefaultFileSystemManager;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.ftps.FtpsFileSystemConfigBuilder;
import org.apache.commons.vfs2.provider.ram.RamFileProvider;
import org.apache.commons.vfs2.provider.ram.RamFileSystem;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;
import org.apache.commons.vfs2.provider.zip.ZipFileSystemConfigBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;

@Configuration
@Slf4j
public class VFSConfig {
    @Bean
    public FileSystemOptions fileSystemOptions() throws FileSystemException {
        FileSystemOptions fileSystemOptions = new FileSystemOptions();

        ZipFileSystemConfigBuilder zipBuilder = ZipFileSystemConfigBuilder.getInstance();
        zipBuilder.setCharset(fileSystemOptions, StandardCharsets.UTF_8);

        FtpsFileSystemConfigBuilder ftpsBuilder = FtpsFileSystemConfigBuilder.getInstance();
        ftpsBuilder.setConnectTimeout(fileSystemOptions, Duration.ofSeconds(5));
        ftpsBuilder.setUserDirIsRoot(fileSystemOptions, false);
        ftpsBuilder.setAutodetectUtf8(fileSystemOptions, true);
        ftpsBuilder.setPassiveMode(fileSystemOptions, true);
        ftpsBuilder.setControlEncoding(fileSystemOptions, StandardCharsets.UTF_8.name());

        SftpFileSystemConfigBuilder sftpBuilder = SftpFileSystemConfigBuilder.getInstance();
        sftpBuilder.setConnectTimeout(fileSystemOptions, Duration.ofSeconds(5));
        sftpBuilder.setUserDirIsRoot(fileSystemOptions, false);
        sftpBuilder.setStrictHostKeyChecking(fileSystemOptions, "no");

        // Webdav4FileSystemConfigBuilder webdavBuilder = Webdav4FileSystemConfigBuilder.getInstance();
        // webdavBuilder.setConnectionTimeout(fileSystemOptions, Duration.ofSeconds(50));
        // webdavBuilder.setSoTimeout(fileSystemOptions, Duration.ofSeconds(50));
        // webdavBuilder.setMaxConnectionsPerHost(fileSystemOptions, 1000);
        // webdavBuilder.setMaxTotalConnections(fileSystemOptions, 1000);
        // webdavBuilder.setHostnameVerificationEnabled(fileSystemOptions, false);
        // webdavBuilder.setPreemptiveAuth(fileSystemOptions, true);
        // webdavBuilder.setUrlCharset(fileSystemOptions, StandardCharsets.UTF_8.name());
        // webdavBuilder.setFollowRedirect(fileSystemOptions, true);
        // webdavBuilder.setKeepAlive(fileSystemOptions, true);

        return fileSystemOptions;
    }

    @Bean(destroyMethod = "close")
    public StandardFileSystemManager apacheVfsFileSystemManager() throws IOException {
        // 修复一些BUG，让SpringBoot APP哪怕以JAR包运行也能正常读取resource资源
        SpringBootResourceFileProvider resourceFileProvider = new SpringBootResourceFileProvider();

        // 获取 "org/apache/commons/vfs2/impl/providers.xml" 资源
        String providersXmlFileRelativePath = "org/apache/commons/vfs2/impl/providers.xml";
        URL confURL = Objects.requireNonNull(Thread.currentThread().getContextClassLoader()
            .getResource(providersXmlFileRelativePath));

        // 因为 VFS 配置只能通过 URL 传参载入，
        // 而且 VFS 不仅不识别 SpringBoot 的 resource URL，
        // 目标资源文件的编码格式还不能被自动校正转成字符串，
        // 所以 只能手动创建个临时 内存文件系统，
        // 把目标资源文件 以 指定编码 加载到 内存里，
        // 创建个 VFS 识别它自己的 兼容URL 拿去配置它自己。
        DefaultFileSystemManager tmpFileSystemManager = new DefaultFileSystemManager();
        tmpFileSystemManager.addProvider("ram", new RamFileProvider());
        tmpFileSystemManager.setCacheStrategy(CacheStrategy.ON_RESOLVE);
        tmpFileSystemManager.init();

        InputStream inputStream = confURL.openStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        FileObject ramFileObject = tmpFileSystemManager.resolveFile("ram:/" + providersXmlFileRelativePath);
        OutputStream outputStream = ramFileObject.getContent().getOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);

        String tmpStr = bufferedReader.readLine();
        // try 语句关闭流的顺序是 从右往左
        try (inputStream; inputStreamReader; bufferedReader; outputStream; outputStreamWriter) {
            while (tmpStr != null) {
                outputStreamWriter.write(tmpStr);
                tmpStr = bufferedReader.readLine();
            }
        }

        // 正式开始实例化主角
        StandardFileSystemManager fileSystemManager = new StandardFileSystemManager();
        fileSystemManager.setConfiguration(Objects.requireNonNull(ramFileObject.getURL()));
        // 每次解析都重新读取，实现实时
        fileSystemManager.setCacheStrategy(CacheStrategy.ON_RESOLVE);
        fileSystemManager.init();
        fileSystemManager.removeProvider("res");
        fileSystemManager.addProvider("res", resourceFileProvider);
        VFS.setManager(fileSystemManager);

        log.info(fileSystemManager.getClass().getCanonicalName()
            + " is created.Support URL schema: "
            + Arrays.toString(fileSystemManager.getSchemes()));
        tmpFileSystemManager.close();
        return fileSystemManager;
    }

    @Bean(destroyMethod = "close")
    public RamFileSystem ramFileSystem(FileSystemManager fileSystemManager) throws IOException {
        FileObject fileObject = fileSystemManager.resolveFile("ram:/");
        return (RamFileSystem) fileObject.getFileSystem();
    }
}
