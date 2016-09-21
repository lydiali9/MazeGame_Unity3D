package com.augmentum.test;

import java.util.Calendar;
import java.util.Date;

import com.augmentum.bean.MapResource;
import com.augmentum.service.MapService;
import com.augmentum.service.PositionService;
import com.augmentum.service.Impl.MapServiceImpl;
import com.augmentum.service.Impl.PositionServiceImpl;

public class test {
	
	public static void main(String[] args) {
		
		Calendar calendar = Calendar.getInstance();
		
		System.out.println(calendar.get(Calendar.HOUR_OF_DAY));
	}

}
