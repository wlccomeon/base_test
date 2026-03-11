package com.lc.test.skills.functionApply;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author wlc
 * @desc
 * @date 2026/3/11 星期三
 */
public class DigestLUtils {

    private static final String MD5 = "MD5";
    private static final String SHA_1 = "SHA-1";
    private static final String SHA_256 = "SHA-256";
    private static final String SHA_512 = "SHA-512";

    public static byte[] digest(byte[] bytesContent, String algorithm) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            digest.reset();
            digest.update(bytesContent);
            return digest.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("获取摘要信息失败", e);
        }
    }

    public static byte[] digest(String content, String algorithm) {
        return digest(content.getBytes(StandardCharsets.UTF_8), algorithm);
    }

    public static byte[] md5(byte[] bytesContent) {
        return digest(bytesContent, "MD5");
    }

    public static byte[] md5(String content) {
        return digest(content, "MD5");
    }

    public static String md5Hex(byte[] bytesContent) {
        byte[] digest = digest(bytesContent, "MD5");
        return HexLUtils.toHex(digest);
    }

    public static String md5Hex(String content) {
        byte[] digest = digest(content, "MD5");
        return HexLUtils.toHex(digest);
    }

    public static byte[] sha1(byte[] bytesContent) {
        return digest(bytesContent, "SHA-1");
    }

    public static byte[] sha1(String content) {
        return digest(content, "SHA-1");
    }

    public static String sha1Hex(byte[] bytesContent) {
        byte[] digest = digest(bytesContent, "SHA-1");
        return HexLUtils.toHex(digest);
    }

    public static String sha1Hex(String content) {
        byte[] digest = digest(content, "SHA-1");
        return HexLUtils.toHex(digest);
    }

    public static byte[] sha256(byte[] bytesContent) {
        return digest(bytesContent, "SHA-256");
    }

    public static byte[] sha256(String content) {
        return digest(content, "SHA-256");
    }

    public static String sha256Hex(byte[] bytesContent) {
        byte[] digest = digest(bytesContent, "SHA-256");
        return HexLUtils.toHex(digest);
    }

    public static String sha256Hex(String content) {
        byte[] digest = digest(content, "SHA-256");
        return HexLUtils.toHex(digest);
    }

    public static byte[] sha512(byte[] bytesContent) {
        return digest(bytesContent, "SHA-512");
    }

    public static byte[] sha512(String content) {
        return digest(content, "SHA-512");
    }

    public static String sha512Hex(byte[] bytesContent) {
        byte[] digest = digest(bytesContent, "SHA-512");
        return HexLUtils.toHex(digest);
    }

    public static String sha512Hex(String content) {
        byte[] digest = digest(content, "SHA-512");
        return HexLUtils.toHex(digest);
    }

}
