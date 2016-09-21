package com.augmentum.util;
 
import java.math.BigDecimal;
 
public class FloatUtil {
 
 public static float toTwoDecimalPlaces (float value) {
  
  BigDecimal b = new BigDecimal(value);
  return b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();  
 }
}