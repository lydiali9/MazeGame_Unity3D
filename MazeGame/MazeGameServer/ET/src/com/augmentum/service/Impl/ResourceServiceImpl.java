package com.augmentum.service.Impl;

import com.augmentum.bean.Resource;
import com.augmentum.dao.ResourceDao;
import com.augmentum.dao.Impl.ResourceDaoImpl;
import com.augmentum.service.MapService;
import com.augmentum.service.PlayerService;
import com.augmentum.service.PositionService;
import com.augmentum.service.ResourceService;

public class ResourceServiceImpl implements ResourceService{

	private ResourceDao resourceDao = null;
	
	public ResourceServiceImpl(){
		
		resourceDao = new ResourceDaoImpl();
	}
	
	//Gets medal.
	public int getPlayerMedal(int playerId) {
		
		return resourceDao.getPlayerMedal(playerId);
	}
	
	//Gets other state.
	public int GetOtherState(int targetPlayerId, int playerId, float longitude, float latitude) {
		
		int result = -1;
		PlayerService playerService = new PlayerServiceImpl();
  
		if (playerService.isPlayerBusy(targetPlayerId)) {		
	  
			result = 0;
		} else {
	  
			PositionService positionService = new PositionServiceImpl();
			float[] newPosition = positionService.getCurrentPosition(longitude, latitude, playerId);
			float newLongitude = newPosition[0];
			float newLatitude = newPosition[1];
   
			if (playerService.isPlayerInLocal(targetPlayerId, newLongitude, newLatitude)) {
    
				playerService.changePlayerState(targetPlayerId);
				MapService mapService = new MapServiceImpl();
				result = mapService.getMapId(targetPlayerId);
				
				if (result == 0) {
					
					result = -2;
				}
			} else {
    
				result = -1;
			}
		}
  
		return result;
	}
	
	//Gets player`s resource.
	public Resource getPlayerResource(int playerId) {
		
		return resourceDao.getPlayerResource(playerId);
	}
}
