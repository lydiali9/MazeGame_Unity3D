package com.augmentum.thread;

import com.augmentum.service.MapService;
import com.augmentum.service.Impl.MapServiceImpl;

public class AutomaticChangePlayerState extends Thread {

	private MapService mapService;
	private int targetPlayerId;
	
	public AutomaticChangePlayerState(int targetPlayerId) {
		
		mapService = new MapServiceImpl();
		this.targetPlayerId = targetPlayerId;
	}
	
	public void run() {
		
		try {
			
			Thread.sleep(180000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
		mapService.quitOtherMap(targetPlayerId);
	}
}
