package com.augmentum.thread;

import java.util.Calendar;

import com.augmentum.service.MapService;
import com.augmentum.service.PlayerService;
import com.augmentum.service.PositionService;
import com.augmentum.service.Impl.MapServiceImpl;
import com.augmentum.service.Impl.PlayerServiceImpl;
import com.augmentum.service.Impl.PositionServiceImpl;

public class EmptyData extends Thread {
	
	private PositionService positionService;
	private PlayerService playerService;
	private MapService mapService;
	
	public EmptyData() {
		
		positionService = new PositionServiceImpl();
		playerService = new PlayerServiceImpl();
		mapService = new MapServiceImpl();
	}

	public void run() {
		
		while (true) {
			
			Calendar calendar = Calendar.getInstance();
			
			if (calendar.get(Calendar.HOUR_OF_DAY) == 0 && calendar.get(Calendar.MINUTE) == 0) {
				
				positionService.emptyPosition();
				playerService.restorePlayerBreakState();
				mapService.emptyMap();
			}
			
			try {
				
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
	}
}
