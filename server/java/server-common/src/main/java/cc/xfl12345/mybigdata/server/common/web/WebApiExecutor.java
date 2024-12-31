package cc.xfl12345.mybigdata.server.common.web;

import cc.xfl12345.mybigdata.server.common.pojo.InstanceGenerator;
import cc.xfl12345.mybigdata.server.common.appconst.DefaultSingleton;
import cc.xfl12345.mybigdata.server.common.appconst.TableCurdResult;
import cc.xfl12345.mybigdata.server.common.appconst.api.result.JsonApiResult;
import cc.xfl12345.mybigdata.server.common.database.error.SqlErrorAnalyst;
import cc.xfl12345.mybigdata.server.common.database.error.IllegalDataException;
import cc.xfl12345.mybigdata.server.common.database.error.TableOperationException;
import cc.xfl12345.mybigdata.server.common.pojo.FieldNotNullChecker;
import cc.xfl12345.mybigdata.server.common.web.pojo.response.JsonApiResponseData;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
public class WebApiExecutor {
    @Getter
    @Setter
    protected FieldNotNullChecker fieldNotNullChecker = DefaultSingleton.FIELD_NOT_NULL_CHECKER;

    @Getter
    @Setter
    protected SqlErrorAnalyst sqlErrorAnalyst;

    @Getter
    @Setter
    protected InstanceGenerator<JsonApiResponseData> responseDataInstanceGenerator;

    @PostConstruct
    public void init() throws Exception {
        fieldNotNullChecker.check(responseDataInstanceGenerator, "responseDataInstanceGenerator");
    }

    public  <Param> JsonApiResponseData handle(
        HttpServletRequest httpServletRequest,
        Param param,
        Function<Param, Object> action) {
        return handleImpl(httpServletRequest, param, action);
    }

    public  <Param> JsonApiResponseData handle(
        HttpServletRequest httpServletRequest,
        Param param,
        Consumer<Param> action) {
        return handle(httpServletRequest, param, (theParam) -> {
            action.accept(theParam);
            return null;
        });
    }

    protected <Param> JsonApiResponseData handleImpl(
        HttpServletRequest httpServletRequest,
        Param param,
        Function<Param, Object> action) {
        JsonApiResponseData responseData = responseDataInstanceGenerator.getNewInstance();
        try {
            responseData.setData(action.apply(param));
            responseData.setApiResult(JsonApiResult.SUCCEED);
        } catch (Exception e) {
            onError(httpServletRequest, param, responseData, e);
        }

        return responseData;
    }

    protected <Param> void onError(
        HttpServletRequest httpServletRequest,
        Param param,
        JsonApiResponseData responseData,
        Exception exception) {
        if (exception instanceof TableOperationException e) {
            if (e.getAffectedRowsCount() == 0) {
                switch (e.getOperation()) {
                    case UPDATE, DELETE, RETRIEVE -> {
                        responseData.setApiResult(JsonApiResult.FAILED_NOT_FOUND);
                    }
                    case CREATE -> {
                        responseData.setApiResult(JsonApiResult.FAILED);
                    }
                }
            } else {
                responseData.setApiResult(JsonApiResult.FAILED_FORBIDDEN);
                responseData.setMessage(e.getMessage());
            }
        } else if (exception instanceof IllegalDataException e) {
            responseData.setApiResult(JsonApiResult.FAILED_FORBIDDEN);
            responseData.setMessage(e.getMessage());
        } else {
            TableCurdResult curdResult = sqlErrorAnalyst == null ? null : sqlErrorAnalyst.getTableCurdResult(exception);
            if (curdResult != null && !curdResult.equals(TableCurdResult.UNKNOWN_FAILED)) {
                responseData.setApiResult(JsonApiResult.FAILED);
                responseData.setMessage(curdResult.name());
            } else {
                log.warn(exception.getMessage(), exception);
                responseData.setApiResult(JsonApiResult.OTHER_FAILED);
            }
        }
    }

}
