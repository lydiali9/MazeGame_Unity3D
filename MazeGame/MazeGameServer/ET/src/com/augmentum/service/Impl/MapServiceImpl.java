package com.augmentum.service.Impl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.augmentum.bean.MapResource;
import com.augmentum.dao.MapDao;
import com.augmentum.dao.Impl.MapDaoImpl;
import com.augmentum.service.MapService;
import com.augmentum.service.ResourceService;
import com.augmentum.util.StringUtil;

public class MapServiceImpl implements MapService {

	private MapDao mapDao = null;

	public MapServiceImpl() {
		
		mapDao = new MapDaoImpl();
	}

	// Gets mapId by player.
	public int getMapId(int playerId) {
		
		return mapDao.getMapId(playerId);
	}

	// Gets map.
	public String getMap(int mapId) {
		
		return mapDao.getMap(mapId);
	}

	// Gets map resource.
	public MapResource GetMapResources(float longitude, float latitude, int playerId) {

		int playerCount = getAroundPlayerCount(longitude, latitude, playerId);
		int []playerList = getAroundPlayerList(longitude, latitude, playerId, playerCount);
		
		ResourceService resourceService = new ResourceServiceImpl();
		int medal = resourceService.getPlayerMedal(playerId);
		MapResource mapResource = concludeMapResource(medal, playerList);
		
		return mapResource;
	}

	// Gets map resource.
	public MapResource concludeMapResource(int medal, int[] playerList) {

		int rank = 1;
		if (medal < 50) {

			rank = 1;
		} else if (medal < 150) {

			rank = 2;
		} else if (medal < 300) {

			rank = 3;
		} else if (medal < 500) {

			rank = 4;
		} else if (medal < 800) {

			rank = 5;
		} else if (medal < 1200) {

			rank = 6;
		} else if (medal < 1700) {

			rank = 7;
		} else if (medal < 2300) {

			rank = 8;
		} else if (medal < 3000) {

			rank = 9;
		} else {

			rank = 10;
		}

		MapResource mapResource = new MapResource();
		mapResource.setSizeX(8 + 2 * rank);
		mapResource.setSizeY(8 + 2 * rank);
		mapResource.setMedal(14 + rank - playerList.length);
		mapResource.setBdiamonds((int) (Math.random() + 0.1 + 0.02 * rank));
		mapResource.setMdiamonds((int) (Math.random() + 0.3 + 0.02 * rank));
		mapResource.setSdiamonds((int) (Math.random() + 0.6 + 0.02 * rank));
		mapResource.setTrapNumb(2 * rank);
		mapResource.setPlayerNumb(playerList.length);
		mapResource.setPlayer(playerList);
		
		return mapResource;
	}

	// Gets around player list.
	public int[] getAroundPlayerList(float longitude, float latitude, int playerId, int playerCount) {
		
		return mapDao.getAroundPlayerList(longitude, latitude, playerId, playerCount);
	}

	// Gets aound player count.
	public int getAroundPlayerCount(float longitude, float latitude, int playerId) {
		
		return mapDao.getAroundPlayerCount(longitude, latitude, playerId);
	}

	// Uploads map
	public int UploadMap(int playerId, String map) {
		
		int mapId = insertMap(map);
		int result = changePlayerMapId(playerId, mapId);
		
		if (result == 0) {
			
			mapId = 0;
		}
		
		return mapId;
	}

	// Changes the mapId of player.
	public int changePlayerMapId(int playerId, int mapId) {
		
		return mapDao.changePlayerMapId(playerId, mapId);
	}

	// Inserts map.
	public int insertMap(String map) {
		
		return mapDao.insertMap(map);
	}

	// Updates resource.
	public int UpdateResource(int mapId, int playerId, String map, int resourceType) {
		
		int result = updateMap(mapId, map);
		
		if (result != 0) {

			if (resourceType == 2 || resourceType == 3 || resourceType == 6
					|| resourceType == 7) {

				updatePlayerResource(playerId, resourceType);
			}
		}
		
		return result;
	}

	// Updates player`s resource,
	public void updatePlayerResource(int playerId, int resourceType) {
		
		mapDao.updatePlayerResource(playerId, resourceType);
	}

	// Updates map.
	public int updateMap(int mapId, String map) {
		
		return mapDao.updateMap(mapId, map);
	}

	// Gets other map.
	public String GetOtherMap(int mapId) {

		String mapResource = getMap(mapId);
		
		if (StringUtil.isNullOrEmpty(mapResource)) {
			
			return null;
		} else {

			JSONObject mapResourceJson = JSONObject.fromObject(mapResource);
			JSONArray map = mapResourceJson.getJSONArray("map");

			for (int i = 0; i < map.size(); i++) {

				JSONArray row = map.getJSONObject(i).getJSONArray("row");

				for (int j = 0; j < row.size(); j++) {

					JSONObject columns = row.getJSONObject(j);

					if ((Integer) columns.get("column") > 9999) {

						columns.element("column", 0);
					}
				}
			}
			
			return mapResourceJson.toString();
		}
	}

	//Quits other map.
	public void quitOtherMap(int targetPlayerId) {
		
		mapDao.quitOtherMap(targetPlayerId);
	}
	
	public void emptyMap() {
		
		mapDao.emptyMap();
	}
}
