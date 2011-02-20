package com.canarylogic.focalpoint.utils

import java.security.MessageDigest;


class EncryptionUtils {

	public static String encrypToMd5(String plainPassword) {
		byte[] md5hash = MessageDigest.getInstance("MD5").digest(plainPassword.getBytes("UTF-8"));
		StringBuilder hexString = new StringBuilder(md5hash.length * 2);
		for (byte b : md5hash) {
			if ((b & 0xff) < 0x10) hexString.append("0");
			hexString.append(Long.toString(b & 0xff, 16));
		}
		def signature = hexString.toString();
		return signature
	}
}
