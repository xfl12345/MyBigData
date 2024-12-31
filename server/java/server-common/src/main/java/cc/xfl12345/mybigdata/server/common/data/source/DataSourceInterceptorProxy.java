package cc.xfl12345.mybigdata.server.common.data.source;

import cc.xfl12345.mybigdata.server.common.data.interceptor.DataInterceptorChain;
import cc.xfl12345.mybigdata.server.common.data.interceptor.InterceptorManager;
import cc.xfl12345.mybigdata.server.common.data.source.impl.AbstractDataSource;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DataSourceInterceptorProxy implements InvocationHandler {

    private final DataSource<?> dataSource;

    private final InterceptorManager interceptorManager;

    public DataSourceInterceptorProxy(AbstractDataSource<?> dataSource) {
        this.dataSource = dataSource;
        this.interceptorManager = dataSource.getInterceptorManager();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;

        DataInterceptorChain dataInterceptorChain = interceptorManager.getDataInterceptorChain(method.getName());
        if (dataInterceptorChain == null) {
            result = method.invoke(dataSource, args);
        } else {
            result = dataInterceptorChain.execute(args);
        }

        return result;
    }
}
