package cc.xfl12345.mybigdata.server.common.pojo;


public class DoubleItem<FirstItem, SecondItem> {
    public DoubleItem() {
    }

    public DoubleItem(FirstItem firstItem, SecondItem secondItem) {
        this.firstItem = firstItem;
        this.secondItem = secondItem;
    }

    protected FirstItem firstItem;

    protected SecondItem secondItem;

    public FirstItem getFirstItem() {
        return firstItem;
    }

    public void setFirstItem(FirstItem firstItem) {
        this.firstItem = firstItem;
    }

    public SecondItem getSecondItem() {
        return secondItem;
    }

    public void setSecondItem(SecondItem secondItem) {
        this.secondItem = secondItem;
    }
}
