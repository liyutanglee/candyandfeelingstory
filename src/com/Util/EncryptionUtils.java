package com.Util;

import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtils {
	private static final String Algorithm = "DES";

	//加密模式
	public static byte[] encryptMode(byte[] keybyte, byte[] src) {
		try {
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {e2.printStackTrace();
		} catch (java.lang.Exception e3) {e3.printStackTrace();}
		return null;
	}

	//解密模式
	public static byte[] decryptMode(byte[] keybyte, byte[] src) {
		try {
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {e2.printStackTrace();
		} catch (java.lang.Exception e3) {e3.printStackTrace();}
		return null;
	}

	//2进制转换成16进制字符串
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1){hs = hs + "0" + stmp;}else{hs = hs + stmp;}
			if (n < b.length - 1){hs = hs + "";}
		}
		return hs.toUpperCase();
	}

	//16进制转成2进制
	public static byte[] hex2byte(String hex) throws IllegalArgumentException {
		if (hex.length() % 2 != 0) {
			throw new IllegalArgumentException();
		}
		char[] arr = hex.toCharArray();
		byte[] b = new byte[hex.length() / 2];
		for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
			String swap = "" + arr[i++] + arr[i];
			int byteint = Integer.parseInt(swap, 16) & 0xFF;
			b[j] = new Integer(byteint).byteValue();
		}
		return b;
	}

	// 加密
	public static String Encrypt(String str, byte[] key) {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		byte[] encrypt = encryptMode(key, str.getBytes());
		return byte2hex(encrypt);
	}

	// 解密
	public static String Decrypt(String str, byte[] key) {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		byte[] decrypt = decryptMode(key, hex2byte(str));
		return new String(decrypt);
	}

}
