package com.lc.test.skills.functionApply;



import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * 摘要类型
 *
 * @author: miaozhizhuang
 * @date: 2020/11/9
 * @version: V1.0
 */
public enum ApiUserDigestTypeEnum {
    //等价于
    // new Function<String, String>() {
    //    @Override
    //    public String apply(String context) {
    //        return DigestLUtils.md5Hex(context).toUpperCase();
    //    }
    //}
    MD5("1", "MD5", (context) -> DigestLUtils.md5Hex(context).toUpperCase()),
    SHA256("2", "SHA256）",(context) -> DigestLUtils.sha256Hex(context).toUpperCase());

    ApiUserDigestTypeEnum(String code, String text, Function<String, String> digester) {
        this.code = code;
        this.text = text;
        this.digester = digester;
    }


    private String code;
    private String text;

    // 接收一个参数String(原始字符串)，返回一个结果String(加密后的字符串)
    private Function<String, String> digester;

    public String getCode() {
        return code;
    }


    public String getText() {
        return text;
    }

    public String digest(String context) {
        return digester.apply(context);
    }

    public static Optional<ApiUserDigestTypeEnum> of(String code) {
        return Stream.of(values()).filter(e -> e.getCode().equals(code)).findAny();
    }

}
