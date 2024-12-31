package cc.xfl12345.mybigdata.server.common.data.interceptor;


public class ActionInterceptor {
    /**
     * 在执行操作之前，先处理一些事情。如果返回 false。则表示终止此次操作。
     */
    public boolean beforeAction(String apiName, Object[] param) {
        return true;
    }

    /**
     * 在操作完成之后再做点事情。
     *
     * @param actionInputData  传入参数
     * @param actionOutputData 返回值
     * @return 如果返回 false 则表示拦截此操作的返回值，如果返回 true 则表示正常返回 actionOutputData
     */
    public boolean afterAction(String apiName, Object[] actionInputData, Object actionOutputData) {
        return true;
    }
}
