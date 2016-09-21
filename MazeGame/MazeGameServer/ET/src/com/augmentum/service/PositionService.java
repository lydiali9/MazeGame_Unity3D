package com.augmentum.service;

public interface PositionService {

	// Checks whether player has position.
	public int checkPosition(float longitude, float latitude, int id);

	//Gets current position.
	public float[] getCurrentPosition(float longitude, float latitude, int playerId);

	public void emptyPosition();
}
