package com.augmentum.util;

public class StringUtil {

	public static boolean isNullOrEmpty(String str) {
		
		boolean result = false;
		
		if (str == null || str.equals("")) {
			
			result = true;
		}
		
		return result;
	}
}
