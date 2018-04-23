package com.founder.utils;

import java.io.IOException;

import sun.misc.BASE64Decoder;

import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BASE64EncoderStream;

public class Base64Util {
	/**
	 * base64-string
	 */
	public static String Base64ToString(String name){
		name = name.trim();
		BASE64Decoder d = new BASE64Decoder();
		try {
			byte[] b = d.decodeBuffer(name);
			String result =  new String(b);
			return result;
		} catch (IOException e) {
			return "";
		}
	}

	/**
	 * string-Base64
	 */
	public static String StringToBase64(String value){
		value = value.trim();
		try {
			String result = new String(BASE64EncoderStream.encode(value.getBytes()));
			return result;
		} catch (Exception e) {
			return "";
		}
	}
}
