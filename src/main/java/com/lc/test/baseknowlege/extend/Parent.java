package com.lc.test.baseknowlege.extend;

public class Parent {

    private boolean parentStatus = false;

    public boolean isParentStatus() {
        return parentStatus;
    }

    public void setParentStatus(boolean parentStatus) {
        this.parentStatus = parentStatus;
    }

    public final  void getLastStatus(){
        parentStatus = setParentStatus();
        if (parentStatus){
            System.out.println("子类更改了父类成员变量的值...");
        }
    }

    protected boolean setParentStatus(){
       return false;
    }
}
