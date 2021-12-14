package com.lc.test.baseknowlege;

import com.lc.test.entity.User;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StringTest {

    public static void main(String[] args) {
//        testStr();
//        testSubString();
//        testRemoveSpace();
//        User user = new User();
//        user.setName("   吴利昌  哈哈   ");
//        user.setId(1);
//        testDeliveryParam(user);
//        System.out.println("最终的user结果为-->>"+ JSON.toJSONString(user));
//        testStrParam();
//        getNewStringBuider();
//        analysisTime();
//        testStringCompare();
        testStringAddInteger();
    }

    /**
     * 连续六位数字（阿拉伯数字、汉语数字）全部用（*）代替
     */
    @Test
    public void regexTest(){
        String strCN = "一百八十万九千九百我们就八七六五四是如5231265432此的奖励";
        //连续的6个阿拉伯数字或者中文数字替换为*
        String str5 = strCN.replaceAll("[\\d一二三四五六七八九十壹贰叁肆伍陆柒捌玖拾百佰千仟万亿]{6}", "******");
        System.out.println("str5 = " + str5);
    }

    /**
     * 删除空格、制表符等
     */
    @Test
    public void testDelBlank(){
        String str = "   Hello Word!    ";

        //方法1：String.trim();trim()是去掉首尾空格
        System.out.println("1:"+str.trim());
        //方法2：str.replaceAll(" ", ""); 去掉所有空格，包括首尾、中间
        String str2 = str.replaceAll(" ", "");
        System.out.println("2:"+str2);
        //方法3：或者replaceAll(" +","");  去掉所有空格，包括首尾、中间
        String str3 = str.replaceAll(" +", "");
        System.out.println("3:"+str3);
        //方法4：、str = .replaceAll("\\s*", "");可以替换大部分空白字符， 不限于空格 ；           \s 可以匹配空格、制表符、换页符等空白字符的其中任意一个。
        String str4 = str.replaceAll("\\s*", "");
        System.out.println("4:"+str4);
    }

    /**
     * 测试字符串与数字相加的结果
     */
    public static void testStringAddInteger(){
        String a = "abc";
        String b = "Had";
        System.out.println(a+5);
        System.out.println(b+32);
    }

    public static void testSubString(){
        String aa = ",haha,lc";
        String bb = aa.substring(aa.indexOf(",")+1,aa.length());
        String cc = aa.substring(0,aa.indexOf(","));
        int dd = aa.indexOf(",");
        int ee = aa.indexOf("-");
        System.out.println("bb:"+bb);
        System.out.println("cc:"+cc);
        System.out.println("dd:"+dd);
        System.out.println("ee:"+ee);

    }

    @Test
    public void testSubStr(){
        String str = "https://test-img.justmi.cn/cover-template-tool/zip1621932981819/rightbg.png";
        String substring = str.substring(0, str.lastIndexOf("/"));
        System.out.println("substring = " + substring+"/");
    }

    /**
     * 移除空格的方法
     */
    public static void testRemoveSpace(){
        String aa = "    hahaha lc  ";
        String bb = aa.trim();
        System.out.println("aa-->>"+aa+"去除空格之后的结果bb-->>"+bb);
    }

    /**
     * 除了基本类型的包装类，其他对象在作为方法的参数的时候，是可以直接更改原始值的
     * @param user
     */
    public static void testDeliveryParam(User user){
        String originName = user.getName();
        System.out.println("原始name-->>"+originName);
        //这个方法只能去除前后的空格
//        String removeSpaceName = originName.trim();
        //这个方法可以去掉中间和前后的空格
        String removeSpaceName = originName.replace(" ","");
        System.out.println("去除空格之后的name-->>"+removeSpaceName);
    }

    public static void testStr(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("a","11111");
        Integer b = Integer.parseInt(map.get("a").toString());
        System.out.println("b=="+b);
    }

    /**
     * 基本类型的包装类作为参数传递，效果和基本类型作为参数传递时一样的
     * 而其他引用类型如Map、StringBuilder、List等，形参的改变会直接影响实际参数
     */
    public static void testStrParam(){
        String s1 = "hello";
        String s2 = "world";
        System.out.println(s1+"--------"+s2);
        change(s1,s2);
        System.out.println(s1+"--------"+s2);
        StringBuilder sb1 = new StringBuilder("hello");
        StringBuilder sb2 = new StringBuilder("world");
        System.out.println(sb1+"--------"+sb2);
        change(sb1,sb2);
        System.out.println(sb1+"--------"+sb2);
        Integer i1 = 111;
        Integer i2 = 222;
        System.out.println(i1+"--------"+i2);
        change(i1,i2);
        System.out.println(i1+"--------"+i2);
    }

    public static void change(String s1,String s2){
        s1 = s2;
        s2 = s1+s2;
    }
    public static void change(StringBuilder sb1,StringBuilder sb2){
        //sb2为sb1赋值的时候改变的是缓冲区的值，不是真正的改变了sb1的值
        //而使用append方法之后，sb2就真正的改变了
        sb1 = sb2;
        sb2 = sb2.append(sb1);
    }
    public static void change(Integer i1,Integer i2){
        i1 = i2;
        i2=i1+i2;
    }

    public static void getNewStringBuider(){
        StringBuilder result = new StringBuilder();
        StringBuilder firstJoin = new StringBuilder();
        for (int i=0;i<5;i++){
            firstJoin.append("iYa"+i+" or ");
        }
        System.out.println("firstJoin-->>"+firstJoin.toString());
        System.out.println("result-->>"+result.append(firstJoin.substring(0,firstJoin.lastIndexOf("or")-1)));
    }

    /**
     * 根据一个指定的字符串得到分钟数据
     */
    public static void analysisTime(){
        String originStr = "2天20小时32分";
        String time2 = originStr.replace(" ","");
        //        String time1 = "2天20小时32分";
        String time1 = time2;
        int dayLocation = time1.indexOf("天");
        int hourLocation = time1.indexOf("小时");
        int minuteLocation = time1.indexOf("分");

        Long dayMinutes = 0L;
        Long hourMinutes = 0L;
        Long minutes = 0L;

        if (dayLocation>=0){
            String dayStr = time1.substring(0,dayLocation);
            dayMinutes = Integer.parseInt(dayStr)*24*60L;
        }
        if (hourLocation>=0){
            String hourStr = "";
            if (dayLocation>=0){
                hourStr = time1.substring(dayLocation+1,hourLocation);
            }else{
                hourStr = time1.substring(0,hourLocation);
            }
            hourMinutes = Integer.parseInt(hourStr)*60L;
        }

        if (minuteLocation>=0){
            String minuteStr = "";
            if (hourLocation>=0){
                minuteStr = time1.substring(hourLocation+2,minuteLocation);
            }else{
                minuteStr = time1.substring(0,minuteLocation);
            }
            minutes = Long.parseLong(minuteStr);
        }

        Long totalMinutes = dayMinutes + hourMinutes + minutes;
        System.out.println("totalMinutes result -->>"+totalMinutes);
    }

    /**
     * 测试string的比较结果
     */
    public static void testStringCompare(){
        String a = "abc";
        String b = "abc";
        String c = "ab"+"c";
        System.out.println(a==b);  //true ，先看常量池中有没有字符串，如果没有则创建，并指向内存中的引用？
        System.out.println(c==b); //true，最终是相等的

        String s1 = new String("aaa");
        String s2 = "aaa";
        System.out.println(s1 == s2);    // false

        s1 = new String("bbb").intern();
        s2 = "bbb";
        System.out.println(s1 == s2);    // true

        s1 = "ccc";
        s2 = "ccc";
        System.out.println(s1 == s2);    // true

        s1 = new String("ddd").intern();
        s2 = new String("ddd").intern();
        System.out.println(s1 == s2);    // true

        s1 = "ab" + "cd";
        s2 = "abcd";
        System.out.println(s1 == s2);    // true

        String temp = "hh";
        s1 = "a" + temp;
// 如果调用s1.intern 则最终返回true
        s2 = "ahh";
        System.out.println(s1 == s2);    // false

        temp = "hh".intern();
        s1 = "a" + temp;
        s2 = "ahh";
        System.out.println(s1 == s2);    // false

        temp = "hh".intern();
        s1 = ("a" + temp).intern();
        s2 = "ahh";
        System.out.println(s1 == s2);    // true

        s1 = new String("1");    // 同时会生成堆中的对象 以及常量池中1的对象，但是此时s1是指向堆中的对象的
        s1.intern();            // 常量池中的已经存在
        s2 = "1";
        System.out.println(s1 == s2);    // false

        String s3 = new String("1") + new String("1");    // 此时生成了四个对象 常量池中的"1" + 2个堆中的"1" + s3指向的堆中的对象（注此时常量池不会生成"11"）
        s3.intern();    // jdk1.7之后，常量池不仅仅可以存储对象，还可以存储对象的引用，会直接将s3的地址存储在常量池
        String s4 = "11";    // jdk1.7之后，常量池中的地址其实就是s3的地址
        System.out.println(s3 == s4); // jdk1.7之前false， jdk1.7之后true

        s3 = new String("2") + new String("2");
        s4 = "22";        // 常量池中不存在22，所以会新开辟一个存储22对象的常量池地址
        s3.intern();    // 常量池22的地址和s3的地址不同
        System.out.println(s3 == s4); // false

// 对于什么时候会在常量池存储字符串对象，我想我们可以基本得出结论: 1. 显示调用String的intern方法的时候; 2. 直接声明字符串字面常量的时候，例如: String a = "aaa";
// 3. 字符串直接常量相加的时候，例如: String c = "aa" + "bb";  其中的aa/bb只要有任何一个不是字符串字面常量形式，都不会在常量池生成"aabb". 且此时jvm做了优化，不//   会同时生成"aa"和"bb"在字符串常量池中
    }

    /**
     * subString的用法，注意endIndex
     * 2、public String substring(int beginIndex, int endIndex)
     * 返回一个新字符串，它是此字符串的一个子字符串。该子字符串从指定的 beginIndex 处开始， endIndex:到指定的 endIndex-1处结束。
     * 参数：beginIndex - 开始处的索引（包括）
     *     　　endindex 结尾处索引（不包括）。
     */
    @Test
    public void getUUID() {
        String s = UUID.randomUUID().toString();
        System.out.println("s-->>>"+s);
        //感觉这一种写法挺二的
        String result = new StringBuilder().append(s.substring(0, 8)).append(s.substring(9, 13))
                .append(s.substring(14, 18)).append(s.substring(19, 23)).append(s.substring(24)).toString();
        System.out.println("result-->>>"+result);
        //这种写法不是挺好的吗？
        String replace = s.replace("-","");
        System.out.println("replace-->>>"+replace);

        String str = "2020-04-10";
        String str1 = str.replace("-", "");
        System.out.println("str1 = " + str1);
    }

    @Test
    public void subStringTest(){
        String file_auth_path = "nplmnewweb:loanContractFile:";
        String url = "nplmnewweb:loanContractFile:2";
        //获得2
        System.out.println(url.substring(file_auth_path.length(),url.length()));
        //获得2
        System.out.println(url.substring(file_auth_path.length()));
        //根据loanContractFile:获得2
        System.out.println(url.substring(url.indexOf("loanContractFile:")+"loanContractFile:".length()));
        //根据loanContractFile:获得nplmnewweb:
        System.out.println(url.substring(0,url.indexOf("loanContractFile:")));
        int a = 10086;
        int b = 200;
    }

}
