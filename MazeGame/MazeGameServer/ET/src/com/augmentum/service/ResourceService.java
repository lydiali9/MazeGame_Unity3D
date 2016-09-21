package com.augmentum.service;

import com.augmentum.bean.Resource;

public interface ResourceService {

	//Gets medal.
	public int getPlayerMedal(int playerId);

	//gets other state.
	public int GetOtherState(int targetPlayerId, int playerId, float longitude, float latitude);

	//Gets player`s resource.
	public Resource getPlayerResource(int playerId);
}
