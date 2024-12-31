package cc.xfl12345.mybigdata.server.common.pojo;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class IntegerFlagChecker {
    protected int mask;

    protected Supplier<Integer> valueSupplier = null;

    protected Consumer<Integer> valueConsumer = null;

    public IntegerFlagChecker(int mask) {
        this.mask = mask;
    }

    public IntegerFlagChecker(int mask, Supplier<Integer> valueSupplier, Consumer<Integer> valueConsumer) {
        this.mask = mask;
        this.valueSupplier = valueSupplier;
        this.valueConsumer = valueConsumer;
    }

    public int getMask() {
        return mask;
    }

    public Supplier<Integer> getValueSupplier() {
        return valueSupplier;
    }

    public Consumer<Integer> getValueConsumer() {
        return valueConsumer;
    }

    public boolean isEnable(int value) {
        return (mask & value) == mask;
    }

    public boolean isDisable(int value) {
        return !isEnable(value);
    }

    public boolean isEnable() {
        return valueSupplier != null && (mask & valueSupplier.get()) == mask;
    }

    public boolean isDisable() {
        return !isEnable();
    }

    /**
     * 设置标志位为 启用
     *
     * @return 返回是否执行成功
     */
    public boolean setEnable() {
        if (valueConsumer == null || valueSupplier == null) {
            return false;
        }

        valueConsumer.accept(mask | valueSupplier.get());
        return true;
    }

    /**
     * 设置标志位为 禁用
     *
     * @return 返回是否执行成功
     */
    public boolean setDisable() {
        if (valueConsumer == null || valueSupplier == null) {
            return false;
        }

        valueConsumer.accept((~mask) & valueSupplier.get());
        return true;
    }

    /**
     * 切换标志位状态
     *
     * @return 返回是否执行成功
     */
    public boolean switchStatus(boolean enable) {
        return enable ? setEnable() : setDisable();
    }

    public boolean getBoolean(int value) {
        return isEnable(value);
    }

    public boolean getBoolean() {
        return isEnable();
    }
}
