package com.augmentum.service.Impl;

import com.augmentum.dao.PositionDao;
import com.augmentum.dao.Impl.PositionDaoImpl;
import com.augmentum.service.MapService;
import com.augmentum.service.PositionService;

public class PositionServiceImpl implements PositionService {

	private PositionDao positionDao = null;

	public PositionServiceImpl() {
		
		positionDao = new PositionDaoImpl();
	}

	// Checks whether player has position.
	public int checkPosition(float longitude, float latitude, int playerId) {

		int mapId = 0;

		if (hasPlayer(playerId)) { // with this player

			if (hasPlayerPosition(longitude, latitude, playerId)) { // with this position

				float[] newPosition = getCurrentPosition(longitude, latitude, playerId);
				float newLongitude = newPosition[0];
				float newLatitude = newPosition[1];

				if (!isCurrentPosition(newLongitude, newLatitude, playerId)) { // is not current

					changePlayerPosition(playerId);
					changePlayerCurrentPosition(newLongitude, newLatitude, playerId);
				}
				
				MapService mapService = new MapServiceImpl();
				mapId = mapService.getMapId(playerId);
			} else { // without this position

				changePlayerPosition(playerId);
				addPlayerPosition(longitude, latitude, playerId);
			}
		} else { // without this player

			addPlayerPosition(longitude, latitude, playerId);
		}

		return mapId;
	}

	// Adds the position of player.
	public void addPlayerPosition(float longitude, float latitude, int playerId) {
		
		positionDao.addPlayerPosition(longitude, latitude, playerId);
	}

	// Changes player current position.
	public void changePlayerCurrentPosition(float longitude, float latitude, int playerId) {
		
		positionDao.changePlayerCurrentPosition(longitude, latitude, playerId);
	}

	// Changes player position.
	public void changePlayerPosition(int playerId) {
		
		positionDao.changePlayerPosition(playerId);
	}

	// Checks whether player enter to current position.
	public boolean isCurrentPosition(float longitude, float latitude, int playerId) {
		
		return positionDao.isCurrentPosition(longitude, latitude, playerId);
	}

	// Gets current position by playerId,longitude,latitude.
	public float[] getCurrentPosition(float longitude, float latitude, int playerId) {
		
		return positionDao.getCurrentPosition(longitude, latitude, playerId);
	}

	// Checks whether there are player by playerId,longitude,latitude.
	public boolean hasPlayerPosition(float longitude, float latitude, int playerId) {
		
		return positionDao.hasPlayerPosition(longitude, latitude, playerId);
	}

	// Checks whether there are player by playerId.
	public boolean hasPlayer(int playerId) {
		
		return positionDao.hasPlayer(playerId);
	}
	
	public void emptyPosition() {
		
		positionDao.emptyPosition();
	}
}
