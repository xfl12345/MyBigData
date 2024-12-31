package cc.xfl12345.mybigdata.server.common.api;

import cc.xfl12345.mybigdata.server.common.database.mapper.TableBasicMapper;
import cc.xfl12345.mybigdata.server.common.database.pojo.CommonAccount;

public interface AccountMapper extends TableBasicMapper<CommonAccount> {
    default Class<CommonAccount> getPojoType() {
        return CommonAccount.class;
    }
}
