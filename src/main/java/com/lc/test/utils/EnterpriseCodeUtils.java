package com.lc.test.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @desc: 社会组织代码和统一社会信用代码校验工具
 * @author wlc
 * @datetime: 2023/11/7 0007 15:31
 */
public class EnterpriseCodeUtils {
    protected static final Logger logger = LoggerFactory.getLogger(EnterpriseCodeUtils.class);
    private static final String BASE_CODE_STRING = "0123456789ABCDEFGHJKLMNPQRTUWXY";

    private static final char[] BASE_CODE_ARRAY = BASE_CODE_STRING.toCharArray();
    private static final List<Character> BASE_CODES = new ArrayList<>();
    //验证社会信用代码字符长度及格式
    private static final String BASE_CODE_REGEX = "[" + BASE_CODE_STRING + "]{18}";
    //验证组织机构代码字符长度及格式
    private static final String BASE_CODE_REGEX2 = "[" + BASE_CODE_STRING + "]{9}";
    //18位社会信用代码加权因子
    private static final int[] WEIGHT = {1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28};
    //9位组织机构加权因子
    private static final int[] WEIGHT2 = {3,7,9,10,5,8,4,2};


    static {
        for (char c : BASE_CODE_ARRAY) {
            BASE_CODES.add(c);
        }
    }
    /**
     * isValidEntpCode:验证企业社会信用代码是否正确
     * @param socialCreditCode
     * @return
     * @return :boolean
     * @Date:2020年5月18日 上午11:36:52
     */
    public static final boolean isValidEntpCode(String socialCreditCode) {
        if (StringUtils.isBlank(socialCreditCode) || !Pattern.matches(BASE_CODE_REGEX, socialCreditCode)) {
            return false;
        }
        char[] businessCodeArray = socialCreditCode.toCharArray();
        char check = businessCodeArray[17];
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            char key = businessCodeArray[i];
            sum += (BASE_CODES.indexOf(key) * WEIGHT[i]);
        }
        int value = 31 - sum % 31;
        return check == BASE_CODE_ARRAY[value % 31];
    }
    /**
     * isSearchMechanism:验证企业组织机构代码是否正确
     * @param mechanismCode
     * @return
     * @return :boolean
     * @Date:2020年5月18日 上午11:36:44
     */
    public static boolean isSearchMechanism(String mechanismCode) {
        Pattern.matches(BASE_CODE_REGEX2, mechanismCode);
        if (!Pattern.matches(BASE_CODE_REGEX2, mechanismCode)) {
            return false;
        }
        char[] socialCreditCodeArray = mechanismCode.toCharArray();
        char check = socialCreditCodeArray[8];
        int sum = 0;
        for (int i = 0; i < 8; i++) {
            sum += (BASE_CODES.indexOf(socialCreditCodeArray[i]) * WEIGHT2[i]);
        }
        int mods = 11 - sum % 11;
        String c = "";
        switch (mods) {
            case 10:
                c = "X";
                break;
            case 11:
                c = "0";
                break;
            default:
                c = mods + "";
        }
        return  String.valueOf(check).equals(c);
    }
    /**
     * isValidUnitCode:验证是否是组织机构代码或社会信用代码
     * @param unitCode
     * @return
     * @return :boolean
     * @Date:2020年5月18日 下午2:11:53
     */
    public static final boolean isValidUnitCode(String unitCode) {
        if (StringUtils.isBlank(unitCode)) {
            return false;
        }
        boolean res=isValidEntpCode(unitCode);  //
        if(!res){
            res=isSearchMechanism(unitCode);
        }
        return res;
    }


    public static void main(String[] args) throws UnsupportedEncodingException {
        List<String> dataList = Arrays.asList("91310115MA1K49",
                "91441322MA4UJCMM80" ,
                "91610137673289953K" ,
                "913209810552234205" ,
                "91330203MA2GWEQ46F" ,
                "91610304MA7J8N2654" ,
                "91360823MA35YEFE0P" ,
                "91320583MA25NK6N1W" ,
                "91320583MA1NMT7W5M" ,
                "91320583MA20P2BL4G" ,
                "9144070359585651XN" ,
                "913205057527227780");
        for (String s : dataList) {
            System.out.println("isValidEntpCode(s) = " + isValidEntpCode(s));
        }
    }
}

