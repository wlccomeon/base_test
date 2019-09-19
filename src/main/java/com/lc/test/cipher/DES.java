package com.lc.test.cipher;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;

/**
 * DES算法实现
 */
public class DES {

	/**
	 * iv向量
	 */
	private static final byte[] DESIV = { (byte) 0xCE, (byte) 0x35, (byte) 0x5,
			(byte) 0xD, (byte) 0x98, (byte) 0x91, (byte) 0x8, (byte) 0xA };

	/**
	 * AlgorithmParameterSpec
	 */
	private static AlgorithmParameterSpec IV = null;

	/**
	 * 加密模式
	 */
	private static final int ENCRYPT_MODE = 1;

	/**
	 * 解密模式
	 */
	private static final int DECRYPT_MODE = 2;

	/**
	 * 密钥
	 */
	private Key key;

	public DES(String str) {
		getKey(str);
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	/**
	 * 通过密钥获得key
	 * @param secretKey 密钥
	 */
	public void getKey(String secretKey) {
		try {
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(secretKey.getBytes());
			KeyGenerator generator = null;
			try {
				generator = KeyGenerator.getInstance("DES");
			} catch (NoSuchAlgorithmException e) {
			}
			generator.init(secureRandom);
			IV = new IvParameterSpec(DESIV);
			this.key = generator.generateKey();
			generator = null;

		} catch (Exception e) {
			throw new RuntimeException("Error in getKey(String secretKey), Cause: " + e);
		}
	}

	/**
	 * 字符串des加密
	 * @param data 需要加密的字符串
	 * @return 加密结果
	 * @throws Exception 异常
	 */
	public byte[] encrypt(byte[] data) throws Exception {
		Cipher cipher = getPattern(ENCRYPT_MODE);
		return cipher.doFinal(data);
	}

	/**
	 * 字符串des解密
	 * @param data 需要解密的字符串
	 * @return 解密结果
	 * @throws Exception 异常
	 */
	public byte[] decrypt(byte[] data) throws Exception {
		Cipher cipher = getPattern(DECRYPT_MODE);
		return cipher.doFinal(data);
	}

	/**
	 * 初始化cipher
	 * @param cipherMode cipher工作模式 1：加密； 2：解密
	 * @return cipher
	 * @throws Exception 异常
	 */
	private Cipher getPattern(int cipherMode) throws Exception {
		Cipher cipher;
		cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(cipherMode, key, IV);
		return cipher;
	}

	public static void main(String[] args) throws Exception {
		String testString = "I am YiMi";
		DES des = new DES("blockchain");   // 秘钥
		String stringMi = Base64.getEncoder().encodeToString(des.encrypt(testString.getBytes()));
		System.out.println("密文：" + stringMi);
		byte[] stringMing = des.decrypt(Base64.getDecoder().decode(stringMi));
		System.out.println("明文：" + new String(stringMing));
	}

}
