package com.augmentum.util;

public class TransformUtil {

	public static String toResultZero() {
		
		return "{\"result\":0}";
	}
	
	public static String toResultNegative() {
		
		return "{\"result\":-1}";
	}
	
	public static String toResult(int result) {
		
		return "{\"result\":" + result + "}";
	}
	
	public static String toResultAndMapid(int result, int mapId) {
		
		return "{\"result\":" + result + ", \"mapId\":" + mapId + "}";
	}
	
	public static String toMapid( int mapId) {
		
		return "{\"mapId\":" + mapId + "}";
	}
	
	public static String toIsBreak(int isBreak) {
		
		return "{\"isBreak\":" + isBreak + "}";
	}
	
	public static String toTime(int time) {
		
		return "{\"time\":" + time + "}";
	}
	
	public static String toIdAndName(int id, String name) {
		
		return "{\"result\":1, \"id\":" + id + ", \"name\":\"" + name + "\"}";
	}
}
