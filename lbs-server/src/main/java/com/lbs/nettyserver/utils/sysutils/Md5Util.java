package com.lbs.nettyserver.utils.sysutils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

public class Md5Util {
	/**
	 * 默认的加密方式
	 * 
	 * @param password
	 * @return
	 */
	public static String getEncryptedPwd(String encryStr) {
		return md5Decode(encryStr, 32).toUpperCase();
	}

	/**
	 * 可逆加密密码
	 * 
	 * @param password
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String reversibleEncryptPassword(String password) throws UnsupportedEncodingException {
		return new String(Base64.encodeBase64(password.getBytes("UTF-8")));
	}

	/**
	 * md5加密
	 * 
	 * @param src
	 *            了源字符串
	 * @param decodeByte
	 *            加密位数
	 * @return
	 */
	public static String md5Decode(String src, int decodeByte) {
		if (src == null) {
			return null;
		}
		String dest = null;
		try {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(src.getBytes("UTF-8"));
				byte b[] = md.digest();
				int i;
				StringBuffer buf = new StringBuffer("");
				for (int offset = 0; offset < b.length; offset++) {
					i = b[offset];
					if (i < 0) {
						i += 256;
					}
					if (i < 16) {
						buf.append("0");
					}
					buf.append(Integer.toHexString(i));
				}
				if (decodeByte == 16) {
					dest = buf.toString().substring(8, 24);
				} else {
					dest = buf.toString();
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				dest = src;
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			dest = src;
		}
		return dest;
	}
}
