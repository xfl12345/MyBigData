package cc.xfl12345.mybigdata.server.common.data.source.pojo;

import lombok.Getter;
import lombok.Setter;

public class PlainMbdFile implements MbdFile {
    @Getter
    @Setter
    protected MbdId globalId;

    @Getter
    @Setter
    protected String format;
}
