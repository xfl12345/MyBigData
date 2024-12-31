import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class TestJavaCharsetSupport {
    public static void main(String[] args) throws IOException {
        URL localMapFileURL = Thread.currentThread().getContextClassLoader().getResource("cc/xfl12345/mybigdata/server/common/json/microsoft_windows_code_page.json");
        if (localMapFileURL != null) {
            InputStream inputStream = localMapFileURL.openConnection().getInputStream();
            String codePageMapInJson = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            @SuppressWarnings("unchecked")
            LinkedHashMap<String, String> codePageMap = (LinkedHashMap<String, String>) new Gson().fromJson(codePageMapInJson, LinkedHashMap.class);

            ConcurrentHashMap<String, String> unsupportedCharset = new ConcurrentHashMap<>();
            ConcurrentHashMap<String, String> illegalCharset = new ConcurrentHashMap<>();

            codePageMap.entrySet().parallelStream().forEach(entry -> {
                try {
                    Charset.forName(entry.getValue());
                } catch (IllegalCharsetNameException illegalCharsetNameException) {
                    // System.err.println("IllegalCharsetNameException: " + illegalCharsetNameException.getMessage());
                    illegalCharset.put(entry.getKey(), entry.getValue());
                } catch (UnsupportedCharsetException unsupportedCharsetException) {
                    // System.err.println("UnsupportedCharsetException: " + unsupportedCharsetException.getMessage());
                    unsupportedCharset.put(entry.getKey(), entry.getValue());
                }
            });


            NumberFormat numberFormat = NumberFormat.getPercentInstance();
            numberFormat.setMaximumFractionDigits(2);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            System.out.println("*".repeat(20));
            System.out.println("unsupportedCharset: " + gson.toJson(unsupportedCharset));
            System.out.printf("unsupportedCharset: count(%s), percent(%s)%n",
                unsupportedCharset.size(),
                numberFormat.format(unsupportedCharset.size() * 1.0 / codePageMap.size())
            );
            System.out.println("*".repeat(20));
            System.out.println("illegalCharset: " + gson.toJson(illegalCharset));
            System.out.printf("illegalCharset: count(%s), percent(%s)%n",
                illegalCharset.size(),
                numberFormat.format(illegalCharset.size() * 1.0 / codePageMap.size())
            );
            System.out.println("*".repeat(20));
        }


    }
}
