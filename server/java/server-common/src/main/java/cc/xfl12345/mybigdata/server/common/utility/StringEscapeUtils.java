package cc.xfl12345.mybigdata.server.common.utility;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class StringEscapeUtils {

    protected static final Map<String, Map<Character, byte[]>> byteMapper = new HashMap<>();
    // charset名称 -> (Java 字符串 -> 转义字符串)
    protected static final Map<String, Map<Character, String>> urlEscapeMapper = new HashMap<>();
    protected static final Map<String, Map<Character, String>> sqlEscape4LikeMapper = new HashMap<>();

    static {
        char[] charList = "()`~!@#$%^&*-_+=|{}[]:;'<>,.? /".toCharArray();
        Charset[] charsets = {
            StandardCharsets.UTF_8,
            StandardCharsets.ISO_8859_1,
            StandardCharsets.US_ASCII,
            StandardCharsets.UTF_16,
            StandardCharsets.UTF_16BE,
            StandardCharsets.UTF_16LE
        };

        for (Charset charset : charsets) {
            String charsetName = charset.name();
            HashMap<Character, byte[]> hashMap = new HashMap<>();
            HashMap<Character, String> hashMap4URL = new HashMap<>();
            for (Character ch : charList) {
                String charInString = "" + ch;
                hashMap.put(ch, charInString.getBytes(charset));
                hashMap4URL.put(ch, URLEncoder.encode(charInString, charset));
            }
            byteMapper.put(charsetName, hashMap);
            urlEscapeMapper.put(charsetName, hashMap4URL);
        }

        HashMap<Character, String> mysqlEscape4LikeMapper = new HashMap<>();
        mysqlEscape4LikeMapper.put('%', "\\%");
        mysqlEscape4LikeMapper.put('_', "\\_");
        sqlEscape4LikeMapper.put("mysql", mysqlEscape4LikeMapper);
    }

    public static Map<String, Map<Character, byte[]>> getByteMapper() {
        return Collections.unmodifiableMap(byteMapper);
    }

    public static Map<String, Map<Character, String>> getUrlEscapeMapper() {
        return Collections.unmodifiableMap(urlEscapeMapper);
    }

    public static Map<String, Map<Character, String>> getSqlEscape4LikeMapper() {
        return Collections.unmodifiableMap(sqlEscape4LikeMapper);
    }

    public static String escapeURL(String content, Set<Character> excludeCharacters) {
        Map<Character, String> charMapper = urlEscapeMapper.get(StandardCharsets.UTF_8.name());
        if (charMapper == null || "".equals(content)) {
            return content;
        }
        int originContentLength = content.length();
        StringBuilder stringBuilder = new StringBuilder(originContentLength << 1);
        for (int i = 0; i < originContentLength; i++) {
            char currChar = content.charAt(i);
            if (excludeCharacters.contains(currChar)) {
                stringBuilder.append(currChar);
            } else {
                stringBuilder.append(URLEncoder.encode(String.valueOf(currChar), StandardCharsets.UTF_8).toLowerCase(Locale.ROOT));
            }
        }
        return stringBuilder.toString();
    }

    public static String escapeSql4Like(String sqlDialect, String content) {
        Map<Character, String> charMapper = sqlEscape4LikeMapper.get(sqlDialect);
        if (charMapper == null || "".equals(content)) {
            return content;
        }
        int originContentLength = content.length();
        StringBuilder stringBuilder = new StringBuilder(originContentLength << 1);
        for (int i = 0; i < originContentLength; i++) {
            char currChar = content.charAt(i);
            String replacement = charMapper.get(currChar);
            if (replacement == null) {
                stringBuilder.append(currChar);
            } else {
                stringBuilder.append(replacement);
            }
        }
        return stringBuilder.toString();
    }
}
