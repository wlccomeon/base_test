//package java.lang;
//
//import lombok.Data;
//
///**
// * 跟jdk路径和类名完全相同的测试
// * 结果：会导致正常的String，在编译的时候都不能通过了。
// */
//@Data
//public class String {
//    private int a = 0;
//    private String b;
//
//
//
//    public static void main(String[] args) {
//        String myStr = new String();
////        myStr.setA(200);
////        myStr.setB("aaaa");
//    }
//
//    public int getA() {
//        return a;
//    }
//
//    public void setA(int a) {
//        this.a = a;
//    }
//
//    public String getB() {
//        return b;
//    }
//
//    public void setB(String b) {
//        this.b = b;
//    }
//}
