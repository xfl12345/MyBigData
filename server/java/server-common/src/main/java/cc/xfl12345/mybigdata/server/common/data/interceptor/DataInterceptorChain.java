package cc.xfl12345.mybigdata.server.common.data.interceptor;


import cc.xfl12345.mybigdata.server.common.data.source.DataSource;
import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.concurrent.CopyOnWriteArrayList;

public class DataInterceptorChain {
    protected final ActionInterceptorChain actionInterceptors;

    protected DataSource<?> dataSource;

    protected Method defaultAction;

    private final String apiName;

    protected Type[] paramType;

    protected Type returnType;

    @Getter
    protected CopyOnWriteArrayList<DataInterceptor> interceptors = new CopyOnWriteArrayList<>();

    public DataInterceptorChain(
        ActionInterceptorChain actionInterceptors,
        DataSource<?> dataSource,
        Method apiMethod) {
        this.actionInterceptors = actionInterceptors;
        this.dataSource = dataSource;
        this.defaultAction = apiMethod;
        this.paramType = apiMethod.getGenericParameterTypes();
        this.returnType = apiMethod.getGenericReturnType();

        this.apiName = apiMethod.getName();
    }

    public ActionInterceptorChain getActionInterceptors() {
        return actionInterceptors;
    }

    public Object execute(Object[] param) throws InvocationTargetException, IllegalAccessException {
        boolean keepGoing = actionInterceptors.beforeAction(apiName, param);
        if (!keepGoing) {
            return null;
        }

        Object actionOutputData = null;
        int lastIndex = 0;

        for (DataInterceptor interceptor : interceptors) {
            keepGoing = interceptor.beforeAction(param);
            if (!keepGoing) {
                if (interceptor.isShouldBranch()) {
                    actionOutputData = interceptor.branchAction(param);
                    for (int i = 0; i <= lastIndex; i++) {
                        interceptors.get(i).afterAction(param, actionOutputData);
                    }
                }

                break;
            }

            lastIndex += 1;
        }

        if (keepGoing) {
            actionOutputData = defaultAction.invoke(dataSource, param);
            for (DataInterceptor interceptor : interceptors) {
                interceptor.afterAction(param, actionOutputData);
            }
        }

        return actionInterceptors.afterAction(apiName, param, actionOutputData) ?
            actionOutputData : null;
    }
}
