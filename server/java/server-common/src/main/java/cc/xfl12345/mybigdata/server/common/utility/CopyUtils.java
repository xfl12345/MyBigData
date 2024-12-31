package cc.xfl12345.mybigdata.server.common.utility;

import cc.xfl12345.mybigdata.server.common.appconst.api.result.*;
import cc.xfl12345.mybigdata.server.common.web.pojo.response.JsonApiResponseData;

public class CopyUtils {
    public static void copy(EmailVerificationApiResult src, JsonApiResponseData dest) {
        dest.setCode(src.getNum());
        dest.appendMessage(src.getName());
    }

    public static void copy(FileApiResult src, JsonApiResponseData dest) {
        dest.setCode(src.getNum());
        dest.appendMessage(src.getName());
    }

    public static void copy(JsonApiResult src, JsonApiResponseData dest) {
        dest.setCode(src.getNum());
        dest.appendMessage(src.getName());
    }

    public static void copy(LoginApiResult src, JsonApiResponseData dest) {
        dest.setCode(src.getNum());
        dest.appendMessage(src.getName());
    }

    public static void copy(LogoutApiResult src, JsonApiResponseData dest) {
        dest.setCode(src.getNum());
        dest.appendMessage(src.getName());
    }

    public static void copy(RegisterApiResult src, JsonApiResponseData dest) {
        dest.setCode(src.getNum());
        dest.appendMessage(src.getName());
    }

    public static void copy(SentEmailApiResult src, JsonApiResponseData dest) {
        dest.setCode(src.getNum());
        dest.appendMessage(src.getName());
    }
}
