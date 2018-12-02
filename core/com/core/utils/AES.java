/**
 * 
 */
package com.core.utils;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Administrator
 * 
 */
public class AES {
	private static final String key = "jb";
	public static String encrypt(String strIn){
		try {
			return encrypt(key,strIn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return strIn;
		
	}
	
	private static String encrypt(String strKey, String strIn) throws Exception {
		SecretKeySpec skeySpec = getKey(strKey);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(strIn.getBytes());
		return Base64.getEncoder().encodeToString(encrypted);
	}
	

	public static String decrypt(String strIn) throws Exception {
		return decrypt(key,strIn);
	}

	private static String decrypt(String strKey, String strIn) throws Exception {
		SecretKeySpec skeySpec = getKey(strKey);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
		byte[] encrypted1 = Base64.getDecoder().decode(strIn);
		byte[] original = cipher.doFinal(encrypted1);
		String originalString = new String(original);
		return originalString;
	}

	private static SecretKeySpec getKey(String strKey) throws Exception {
		byte[] arrBTmp = strKey.getBytes();
		byte[] arrB = new byte[16]; // 创建一个空的16位字节数组（默认值为0）

		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}

		SecretKeySpec skeySpec = new SecretKeySpec(arrB, "AES");

		return skeySpec;
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String Code = "2217,11,21,08,00,00";
		String Code1 = "2217,11,21,23,59,59";
		String codE;

		codE = AES.encrypt(Code);
		System.out.println("密文：" + codE+"\t\r");
		codE = AES.encrypt(Code1);
		System.out.println("密文：" + codE);
		
		System.out.println("解密：" + AES.decrypt("eJ6bB8JyXuOe8V0O4S7vZzPzP39pcg2R+ZEdVlsL+n0="));
	}

}
