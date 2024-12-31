package cc.xfl12345.mybigdata.server.common.pojo;

import cc.xfl12345.mybigdata.server.common.appconst.AppConst;
import cc.xfl12345.mybigdata.server.common.data.source.DataSource;
import cc.xfl12345.mybigdata.server.common.database.mapper.TableBasicMapper;
import lombok.Getter;
import lombok.Setter;

public class FieldNotNullChecker {
    @Getter
    @Setter
    protected String messageTemplateFieldCanNotBeNull = AppConst.MESSAGE_TEMPLATE_FIELD_CAN_NOT_BE_NULL;

    @Getter
    @Setter
    protected String messageTemplateMapperNotFound = AppConst.MESSAGE_TEMPLATE_MAPPER_NOT_FOUND;

    @Getter
    @Setter
    protected String messageTemplateDatasourceNotFound = AppConst.MESSAGE_TEMPLATE_DATASOURCE_NOT_FOUND;

    public void check(Object fieldValue, String fieldName) {
        if (fieldValue == null) {
            throw new IllegalArgumentException(messageTemplateFieldCanNotBeNull.formatted(fieldName));
        }
    }

    public <T> void check(TableBasicMapper<T> mapper, Class<T> pojoClass) {
        if (mapper == null) {
            throw new IllegalArgumentException(messageTemplateMapperNotFound.formatted(pojoClass.getCanonicalName()));
        }
    }

    public <T> void check(DataSource<T> mapper, Class<T> dataType) {
        if (mapper == null) {
            throw new IllegalArgumentException(messageTemplateDatasourceNotFound.formatted(dataType.getCanonicalName()));
        }
    }
}
