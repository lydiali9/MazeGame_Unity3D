package com.augmentum.dao;

public interface MapDao {

	//Gets mapId by playerId.
	public int getMapId(int playerId);

	//Gets map.
	public String getMap(int mapId);

	//Gets around player count.
	public int getAroundPlayerCount(float longitude, float latitude, int playerId);

	//Gets around player list.
	public int[] getAroundPlayerList(float longitude, float latitude, int playerId, int playerCount);

	//Inserts map.
	public int insertMap(String map);

	//Changes the mapId of player.
	public int changePlayerMapId(int playerId, int mapId);

	//Updates map.
	public int updateMap(int mapId, String map);

	//Updates player`s resource.
	public void updatePlayerResource(int playerId, int resourceType);

	public void quitOtherMap(int targetPlayerId);

	public void emptyMap();
}
