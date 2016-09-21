package com.augmentum.test;

import java.util.Calendar;

public class DataBaseOperation extends Thread {

	public void run() {
		
		while (true) {
			
			Calendar calendar = Calendar.getInstance();
			
			if (calendar.get(Calendar.HOUR_OF_DAY) == 17 && calendar.get(Calendar.MINUTE) == 20) {
				
				System.out.println("1234132");
			}
			
			try {
				
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
	}
}
