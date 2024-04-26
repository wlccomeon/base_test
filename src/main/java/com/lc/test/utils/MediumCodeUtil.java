package com.lc.test.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 中证码校验规则
 */
public class MediumCodeUtil {

    public static void main(String[] args) {

        //System.out.println("checkPCCBit(\"330281UFL58QFB86\") = " + checkPCCBit("330281UFL58QFB86"));
        //System.out.println("checkPCCBit(\"3212830004763843\") = " + checkPCCBit("3212830004763843"));
        //System.out.println("checkPCCBit(\"CN3418MA8R1PXT12\") = " + checkPCCBit("CN3418MA8R1PXT12"));
        //System.out.println("checkPCCBit(\"4419010051978656\") = " + checkPCCBit("4419010051978656"));
        System.out.println("checkPCCBit(\"330283U16PHKF639\") = " + checkPCCBit("330283U16PHKF639"));
        System.out.println("checkPCCBit(\"CN3708MAC2256X85\") = " + checkPCCBit("CN3708MAC2256X85"));
        System.out.println("checkPCCBit(\"3415210000412395\") = " + checkPCCBit("3415210000412395"));

        //String a1 = "440101728231485R";
        //System.out.println("a1.substring(14, 16) = " + a1.substring(14, 16));
        //
        //int aa = (200%97)+1;
        //System.out.println("aa = " + aa);
        //int b = (3%97)+1;
        //System.out.println("b = " + b);
        //List<String> dataList = Arrays.asList("CN4405MA56QYE839","5111234567890123","330281UFL58QFB86", "610304U0ULUGQ753", "6101040002372645","4419010024394305", "91441322MA4W96QJXG", "91440300785268452W");
        //
        //String join = StringUtils.join(dataList, "，");
        //System.out.println("join = " + join);
        //
        //for (String rightZZM : dataList) {
        //    boolean a = checkZZM(rightZZM);
        //    System.out.println(rightZZM+"    a = " + a);
        //    //boolean b = checkDkkbm(rightZZM);
        //    //System.out.println(rightZZM+"   b = " + b);
        //    boolean c = checkPCCBit(rightZZM);
        //    System.out.println(rightZZM+"    c = " + c);
        //}
    }

    /**
     * 中征码校验方法1(对公提供)
     * @param code
     * @return
     */
    public static boolean checkDkkbm(String code) {
        if (code == null || code.length() != 16) {
            return false;
        }
        int[] weight = {1, 3, 5, 7, 11, 2, 13, 1, 1, 17, 19, 97, 23, 29};
        char[] arr = code.toCharArray();
        char[] cknum = Arrays.copyOfRange(arr, arr.length - 2, arr.length);
        String cknumStr = new String(cknum);
        if (!cknumStr.matches("[0-9]+")) {
            return false;
        }
        int sumt = 0;
        for (int i = 0; i < arr.length; i++) {
            if (!Character.isLetterOrDigit(arr[i])) {
                return false;
            } else if (Character.isLetter(arr[i])) {
                sumt += (arr[i] - 'A' + 10) * weight[i];
            } else {
                sumt += (arr[i] - '0') * weight[i];
            }
        }
        if (cknumStr.equals(String.valueOf(sumt % 97 + 1))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 中征码校验位校验（原贷款卡编码）(大数据提供)
     *
     * @param code
     * @return
     */
    public static boolean checkPCCBit(String code) {
        if (code.length() != 16){
            return false;
        }
        int[] PCC_wi = { 1, 3, 5, 7, 11, 2, 13, 1, 1, 17, 19, 97, 23, 29 };
        String PCC_ci_position = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int sum = 0;
        for (int i = 0; i < 14; i++) {
            int tmp = PCC_ci_position.indexOf(code.charAt(i));
            if (tmp == -1)
                return false;
            sum += tmp * PCC_wi[i];
        }
        int cb2 = (sum % 97) + 1;
        int sl2 = Integer.parseInt(code.substring(14, 16));
        boolean rst = cb2 == sl2;
        return rst;
    }

    //校验中征码（不能用）
    @Deprecated
    public static boolean checkZZM(String value){
        if (StringUtils.isEmpty(value)){
            return false;
        }
        //先进行正则匹配
        String reg = "^[A-Z0-9]{3}[0-9]{4,16}$";
        if(!value.matches(reg)){
            return false;
        }
        //取出校验位
        String code = value.substring(14,16);
        //前14位转化为char数组
        char[] idCode = value.substring(0,14).toCharArray();
        //加权因子
        int[] weight_factor = new int[]{1,3,5,7,11,2,13,1,1,17,19,97,23,29};
        int len = idCode.length;
        int num = 0;
        int temp = 0;
        //循环取和
        for(int i = 0; i<len; i++){
            //字母转数字
            if(idCode[i]>='A' && idCode[i]<='Z'){
                temp = (int) idCode[i]-55;
            }else {
                temp = (int)idCode[i]-48;
            }
            //求和
            num = num + temp * weight_factor[i];
        }
        //取余+1
        int resisue = num % 97 + 1;
        if(Integer.parseInt(code)-resisue == 0){
            return true;
        }
        return false;
    }
}
