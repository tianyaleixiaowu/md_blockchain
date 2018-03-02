/**
 * Project Name:trustsql_sdk
 * File Name:EncryptUtil.java
 * Package Name:com.tencent.trustsql.sdk.util
 * Date:Jul 26, 20172:48:58 PM
 * Copyright (c) 2017, Tencent All Rights Reserved.
 *
 */

package com.mindata.blockchain.common.algorithm;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * ClassName:EncryptUtil <br/>
 * Date: Jul 26, 2017 2:48:58 PM <br/>
 * 
 * @author Rony
 * @version
 * @since JDK 1.7
 * @see
 */
public class AESAlgorithm {

	/**
	 * aesEncode:aes 加密. <br/>
	 *
	 * @author Rony
	 * @param key
	 *            秘钥
	 * @param plainText
	 *            明文
	 * @return
	 * @throws Exception
	 * @since JDK 1.7
	 */
	public static byte[] aesEncode(byte[] key, byte[] data) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] cipherText = cipher.doFinal(data);
		return cipherText;
	}

	/**
	 * aesDecode: aes 解密. <br/>
	 *
	 * @author Rony
	 * @param key
	 * @param encryptedText
	 * @return
	 * @throws Exception
	 * @since JDK 1.7
	 */
	public static byte[] aesDecode(byte[] key, byte[] data) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
		SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] result = cipher.doFinal(data);  
		return result;
	}

}
