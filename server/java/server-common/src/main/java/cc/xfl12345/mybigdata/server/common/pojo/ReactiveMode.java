package cc.xfl12345.mybigdata.server.common.pojo;

public class ReactiveMode {
    public static final int MASK_LOCK = 1;

    public static final int MASK_CACHE = 1 << 1;

    public static final int MASK_ON_DEMAND = 1 << 2;

    protected Integer flag = 0;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag == null ? 0 : flag;
    }

    protected IntegerFlagChecker lockFlag = new IntegerFlagChecker(MASK_LOCK, this::getFlag, this::setFlag);

    protected IntegerFlagChecker cacheFlag = new IntegerFlagChecker(MASK_CACHE, this::getFlag, this::setFlag);
    protected IntegerFlagChecker onDemandFlag = new IntegerFlagChecker(MASK_ON_DEMAND, this::getFlag, this::setFlag);

    /**
     * 用于初始化实例时，锁定当前行
     */
    public IntegerFlagChecker getLockFlag() {
        return lockFlag;
    }

    public IntegerFlagChecker getCacheFlag() {
        return cacheFlag;
    }


    /**
     * 当且仅当 cache 标志为 true 时， onDemand 标志位才生效。
     */
    public IntegerFlagChecker getOnDemandFlag() {
        return onDemandFlag;
    }

    public static class Builder {
        private ReactiveMode mode;

        public static Builder getInstance() {
            return new Builder();
        }

        public Builder setLockFlag(boolean enable) {
            mode.getLockFlag().switchStatus(enable);
            return this;
        }

        public Builder setCacheFlag(boolean enable) {
            mode.getCacheFlag().switchStatus(enable);
            return this;
        }

        public Builder setOnDemandFlag(boolean enable) {
            mode.getOnDemandFlag().switchStatus(enable);
            return this;
        }

        public ReactiveMode build() {
            return mode;
        }
    }
}
