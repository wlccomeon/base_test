package com.lc.test.baseknowlege.extend;

public class ChildOne extends Parent {

    @Override
    protected boolean setParentStatus() {
        return true;
    }

    public void myUniqueMethod(){
        System.out.println("子类的特有方法");
    }

    @Override
    public boolean isParentStatus() {
        System.out.println("进入到子类了");
        return super.isParentStatus();
    }

    @Override
    public void setParentStatus(boolean parentStatus) {
        System.out.println("进入到子类了");
        super.setParentStatus(parentStatus);
    }
}
