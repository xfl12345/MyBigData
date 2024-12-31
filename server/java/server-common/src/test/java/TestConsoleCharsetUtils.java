import cc.xfl12345.mybigdata.server.common.utility.ConsoleCharsetUtils;

import java.nio.charset.Charset;

public class TestConsoleCharsetUtils {
    public static void main(String[] args) throws Exception {
        ConsoleCharsetUtils consoleCharsetUtils = new ConsoleCharsetUtils();
        consoleCharsetUtils.init();
        Charset charset = consoleCharsetUtils.getCharset();
        if (charset != null) {
            System.out.println("Current console charset name is [" + charset.name() + "]");
        } else {
            System.out.println("Current process is not running via a console.");
        }
    }
}
