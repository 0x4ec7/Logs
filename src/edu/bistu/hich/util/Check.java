package edu.bistu.hich.util;

import java.util.zip.CRC32;

import edu.bistu.hich.entity.MyCallLog;

/** 
 * @ClassName: Check 
 * @Description: check, encode, ... 
 * @author 仇之东   hich.cn@gmail.com 
 * @date May 17, 2014 5:21:01 PM 
 *  
 */ 
public class Check {

	public static String getCRC32(String value) {
		CRC32 crc32 = new CRC32();
		crc32.update(value.getBytes());
		return replace(Long.toHexString(crc32.getValue()));
	}
	
	public static String getCRC32(MyCallLog callLog){
		return getCRC32(callLog.getNumber() + callLog.getDate());
	}

	public static char[] getCodes() {
		StringBuilder sb = new StringBuilder();
		char[] chars = Constants.ZHIDONG.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if ('A' <= chars[i] && chars[i] <= 'Z') {
				if (sb.indexOf("" + chars[i]) == -1) {
					sb.append(chars[i]);
				}
			}
		}
		return sb.toString().toCharArray();
	}

	private static String replace(String value) {
		StringBuilder sb = new StringBuilder();
		char[] chars = value.toUpperCase().toCharArray();
		char[] codes = getCodes();
		for (int i = 0; i < chars.length; i++) {
			sb.append(codes[Integer.parseInt("" + chars[i], 0x10)]);
		}
		return sb.toString();
	}

}
