import org.junit.jupiter.api.Test;

import java.io.File;
public class TmpTest {

    @Test
    public void test() {
        System.out.println(Thread.currentThread().getContextClassLoader().getResource("cc/xfl12345/mybigdata/server/common/json/microsoft_windows_code_page.json"));
        System.out.println(System.getProperty("user.dir"));
        File localMapFile = new File(System.getProperty("user.dir"), "src/main/resources/" + "cc/xfl12345/mybigdata/server/common/json/microsoft_windows_code_page.json");
        System.out.println(localMapFile.toPath());
    }
}
