/**
 * Project Name:trustsql_sdk
 * File Name:Constants.java
 * Package Name:com.tencent.trustsql.sdk
 * Date:Jul 26, 201711:17:18 AM
 * Copyright (c) 2017, Tencent All Rights Reserved.
 *
*/

package com.mindata.blockchain.common;

import java.math.BigInteger;

/**
 * ClassName:Constants <br/>
 * Date:     Jul 26, 2017 11:17:18 AM <br/>
 * @author   Rony
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class Constants {
	
	public static final int PUBKEY_DIGEST_LENGTH = 90; // public key length
	public static final int PRVKEY_DIGEST_LENGTH = 45; //private key length
	public static final int ADDR_DIGEST_LENGTH = 35;   // address length
	public static final int SIGN_DIGEST_LENGTH = 98;   // signature length
	public static final int KEY_DES3_DIGEST_LENGTH = 24;  // max size of key for DES3 encrypt
	public static final int KEY_AES128_DIGEST_LENGTH = 16; // max size of key for AES128 encrypt
	public static final int TRANSSQL_DIGEST_LENGTH = 8192; // max size of trans sql for TrustSQL
	
	public static final String RANDOM_NUMBER_ALGORITHM = "SHA1PRNG";
	public static final String RANDOM_NUMBER_ALGORITHM_PROVIDER = "SUN";
	public static final BigInteger MAXPRIVATEKEY = new BigInteger("00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364140", 16);

	public static final String INFO_SHARE_PUBKEY = "BC8s/4qEAvVl4Sv0LwQOWJcVU6Q5hBd+7LlJeEivVmUbdtwP4RTfN8x/G+muMhN8SrweyyVVMIcIrnMWoFqGfIA=";
}

