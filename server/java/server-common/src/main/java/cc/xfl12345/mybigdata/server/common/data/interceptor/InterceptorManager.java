package cc.xfl12345.mybigdata.server.common.data.interceptor;


import cc.xfl12345.mybigdata.server.common.appconst.CURD;
import cc.xfl12345.mybigdata.server.common.data.DataSourceApi;
import cc.xfl12345.mybigdata.server.common.data.source.DataSource;
import lombok.Getter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

public class InterceptorManager {

    protected HashMap<String, Method> apiMethodMap;

    protected HashMap<String, DataInterceptorChain> dataInterceptorChainMap;

    public InterceptorManager(DataSource<?> dataSource) {
        init(dataSource);
    }

    protected void init(DataSource<?> dataSource) {
        apiMethodMap = new HashMap<>();
        dataInterceptorChainMap = new HashMap<>();

        List<Method> methods = List.of(dataSource.getClass().getMethods());
        for (Method method : methods) {
            DataSourceApi annotation = method.getAnnotation(DataSourceApi.class);
            if (annotation != null) {
                String apiName = method.getName();
                apiMethodMap.put(apiName, method);

                ActionInterceptorChain actionInterceptorChain = null;
                switch (annotation.curdType()) {
                    case CREATE -> actionInterceptorChain = insertActionInterceptorChain;
                    case UPDATE -> actionInterceptorChain = updateActionInterceptorChain;
                    case RETRIEVE -> actionInterceptorChain = selectActionInterceptorChain;
                    case DELETE -> actionInterceptorChain = deleteActionInterceptorChain;
                    case NULL -> actionInterceptorChain = mixActionInterceptorChain;
                }

                DataInterceptorChain dataInterceptorChain = new DataInterceptorChain(
                    actionInterceptorChain,
                    dataSource,
                    method
                );
                dataInterceptorChainMap.put(apiName, dataInterceptorChain);
            }
        }
    }

    @Getter
    protected final ActionInterceptorChain insertActionInterceptorChain =
        new ActionInterceptorChain(CURD.CREATE);

    @Getter
    protected final ActionInterceptorChain updateActionInterceptorChain =
        new ActionInterceptorChain(CURD.UPDATE);

    @Getter
    protected final ActionInterceptorChain selectActionInterceptorChain =
        new ActionInterceptorChain(CURD.RETRIEVE);

    @Getter
    protected final ActionInterceptorChain deleteActionInterceptorChain =
        new ActionInterceptorChain(CURD.DELETE);

    @Getter
    protected final ActionInterceptorChain mixActionInterceptorChain =
        new ActionInterceptorChain(CURD.NULL);


    public DataInterceptorChain getDataInterceptorChain(String apiName) {
        return dataInterceptorChainMap.get(apiName);
    }
}
