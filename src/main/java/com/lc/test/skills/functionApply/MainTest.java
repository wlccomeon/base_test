package com.lc.test.skills.functionApply;

/**
 * @author wlc
 * @desc
 * @date 2026/3/11 星期三
 */
public class MainTest {


    public static void main(String[] args) {
//        ApiUserDigestTypeEnum digestType = ApiUserDigestTypeEnum.of("md5").get();
//        System.out.println(digestType.digest("123456"));
        //指定调用
        String md5Result = ApiUserDigestTypeEnum.MD5.digest("123456");
        String sha256Result = ApiUserDigestTypeEnum.SHA256.digest("123456");
        System.out.println("md5Result = " + md5Result);
        System.out.println("sha256Result = " + sha256Result);
        //设计优势
        // ✅ 策略模式：将不同的摘要算法封装到枚举中
        // ✅ 可扩展：添加新算法只需增加枚举常量
        // ✅ 类型安全：编译时检查函数签名
        // ✅ 简洁：使用 Lambda 表达式，代码清晰
        //动态选择（可以将code作为参数动态传入，自动选择使用哪种加密算法）
        ApiUserDigestTypeEnum.of("1").ifPresent(digestType ->
                System.out.println(digestType.digest("123456"))
        );
    }

}
