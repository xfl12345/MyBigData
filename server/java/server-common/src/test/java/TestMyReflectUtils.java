import cc.xfl12345.mybigdata.server.common.utility.MyReflectUtils;

import java.io.IOException;

public class TestMyReflectUtils {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        MyReflectUtils.getClasses(
            "cc/xfl12345/mybigdata/server/common/pojo".replace('/', '.'),
            false,
            false,
            true
        ).forEach(
            cls -> System.out.println(cls.getCanonicalName())
        );
    }
}
