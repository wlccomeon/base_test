package com.lc.test.baseknowlege.extend;

public class Test {

    public static void main(String[] args) {

//        Parent parent = new Parent();
//        if (parent.isParentStatus()){
//            System.out.println("初始值为true");
//        }else{
//            System.out.println("初始值为false");
//        }
//
//
//        Parent child = new ChildOne();
//        child.getLastStatus();
//        ((ChildOne) child).myUniqueMethod();
//
//        if (!parent.isParentStatus()){
//            System.out.println("更改了父类值");
//        }

        Parent parent = new ChildOne();
        parent.isParentStatus();


    }
}
