package com.founder.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Md5Util {
	/**
	 * MD5加密密码
	 * @param pwd
	 * @return 32位编码
	 */
	public static String getMD5PassWord(String pwd){
		MessageDigest msd;
		try {
			msd = MessageDigest.getInstance("MD5");
			msd.reset();
			msd.update(pwd.getBytes());
			byte[] res = msd.digest();
			StringBuffer stb = new StringBuffer(res.length * 2);
			for (byte b : res) {
				stb.append(Character.forDigit((b & 240) >> 4, 16));
				stb.append(Character.forDigit(b & 15, 16));
			}
			return stb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.out.println("----------------MD5加密错误。");
			return "";
		}
	}


	public static void main(String[] args) {
		System.out.println(getMD5PassWord("123456"));
	}
}
