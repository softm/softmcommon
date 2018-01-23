package net.softm.lib.common;

import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import net.softm.lib.Util;
import android.util.Base64;

public class Aes256 {

	public static String secretKey = "KDCXE6aBfBC1n6L04hNu2Yu3or207Hmy"; // http://randomkeygen.com/
			
	public static byte[] ivBytes = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	//암호화
	public static String encode(String str) {
		byte[] keyData = secretKey.getBytes();

		String string = null;
		try {
			SecretKeySpec secureKey = new SecretKeySpec(keyData, "AES");
			AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
			
			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			c.init(Cipher.ENCRYPT_MODE, secureKey, ivSpec );

			byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
			string = new String(Base64.encode(encrypted, Base64.DEFAULT));
		} catch (Exception e) {
			e.printStackTrace();
			Util.e(e.getMessage());
		}

		return string;
	}

	//복호화
	public static String decode(String str) {
		byte[] keyData = secretKey.getBytes();
		
		String string = null;
		try {
			SecretKeySpec secureKey = new SecretKeySpec(keyData, "AES");
			AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
			
			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			c.init(Cipher.DECRYPT_MODE, secureKey, ivSpec );

			byte[] byteStr = Base64.decode(str, Base64.DEFAULT);

			string = new String(c.doFinal(byteStr),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			Util.e(e.getMessage());
		}
		
		return string;
	}
}
