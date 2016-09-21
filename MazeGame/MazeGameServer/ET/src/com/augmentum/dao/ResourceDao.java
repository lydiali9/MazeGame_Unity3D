package com.augmentum.dao;

import com.augmentum.bean.Resource;

public interface ResourceDao {

	//Gets medal.
	public int getPlayerMedal(int playerId);
	
	//Gets player`s resource.
	public Resource getPlayerResource(int playerId);

}
