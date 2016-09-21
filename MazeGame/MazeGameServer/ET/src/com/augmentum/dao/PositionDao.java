package com.augmentum.dao;

public interface PositionDao {
	
		//Checks whether there are player by playerId.
	public boolean hasPlayer(int playerId);

		//Checks whether there are player by playerId,longitude,latitude.
	public boolean hasPlayerPosition(float longitude, float latitude, int playerId);

		//Gets current position by  playerId,longitude,latitude.
	public float[] getCurrentPosition(float longitude, float latitude, int playerId);

		//Checks whether player enter to current position.
	public boolean isCurrentPosition(float longitude, float latitude, int playerId);

		//Changes player position.
	public void changePlayerPosition(int playerId);

		//Changes player current position.
	public void changePlayerCurrentPosition(float longitude, float latitude, int playerId);

		//Adds the position of player.
	public void addPlayerPosition(float longitude, float latitude, int playerId);

	public void emptyPosition();
}
