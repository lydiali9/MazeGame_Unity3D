package com.augmentum.service;

import com.augmentum.bean.MapResource;

public interface MapService {

	//Gets mapId by playerId.
	public int getMapId(int playerId);

	//Gets map.
	public String getMap(int mapId);

	//Gets map resource.
	public MapResource GetMapResources(float longitude, float latitude, int playId);

	//Uploads map.
	public int UploadMap(int playerId, String map);

	//Updates resource.
	public int UpdateResource(int mapId, int playerId, String map, int resourceType);

	//Gets other map.
	public String GetOtherMap(int mapId);

	//Quits other map.
	public void quitOtherMap(int targetPlayerId);
	
	public void emptyMap();
}
