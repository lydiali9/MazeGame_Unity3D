package com.augmentum.service;

import java.util.Date;

import com.augmentum.bean.LoginData;

public interface PlayerService {

	//Gets all information of player by account.
	public LoginData getLoginData(String account,String password);

	//Display player online.
	public void updatePlayerState(int id);

	//Checks whether other player to enter.
	public int CheckState(int id);

	//Gets other player to enter the time.
	public Date getBreakTime(int id);

	//Checks whether player is busy.
	public boolean isPlayerBusy(int targetPlayerId);

	//Checks whether there are player in current place.
	public boolean isPlayerInLocal(int targetPlayerId, float newLongitude, float newLatitude);

	//Changes player`s state.
	public void changePlayerState(int targetPlayerId);

	public void logout(int id);
	
	public void restorePlayerBreakState();
}
