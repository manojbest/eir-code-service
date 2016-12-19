package com.eircode.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.eircode.exception.HashGenerationException;

public final class HashGeneratorUtil {
	
	private HashGeneratorUtil() {

	}

	public static String hashString(final String message) throws HashGenerationException {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] hashedBytes = digest.digest(message.getBytes("UTF-8"));
			
			return convertByteArrayToHexString(hashedBytes);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
			throw new HashGenerationException("Could not generate hash from String", ex);
		}
	}
	
	private static String convertByteArrayToHexString(final byte[] arrayBytes) {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < arrayBytes.length; i++) {
			stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
					.substring(1));
		}		
		return stringBuffer.toString();
	}
}
