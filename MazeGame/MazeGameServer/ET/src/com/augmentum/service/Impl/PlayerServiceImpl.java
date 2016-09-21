package com.augmentum.service.Impl;

import java.util.Date;

import com.augmentum.bean.LoginData;
import com.augmentum.dao.PlayerDao;
import com.augmentum.dao.Impl.PlayerDaoImpl;
import com.augmentum.service.PlayerService;

public class PlayerServiceImpl implements PlayerService{

	private PlayerDao playerDao = null;
	
	public PlayerServiceImpl(){
		
		playerDao = new PlayerDaoImpl();
	}
	
	//Gets all information of player by account.
	public LoginData getLoginData(String account,String password) {
		
		return playerDao.getLoginData(account,password);
	}

	//Display player online.
	public void updatePlayerState(int id) {
		
		playerDao.updatePlayerState(id);
	}

	//Checks whether other player to enter.
	public int CheckState(int id) {
		
		return playerDao.CheckState(id);
	}

	//Gets other player to enter the time.
	public Date getBreakTime(int id) {
		
		return playerDao.getBreakTime(id);
	}

	//Checks whether player is busy.
	public boolean isPlayerBusy(int targetPlayerId) {
		
		return playerDao.isPlayerBusy(targetPlayerId);
	}

	//Checks whether there are player in current place.
	public boolean isPlayerInLocal(int targetPlayerId, float newLongitude, float newLatitude) {
		
		return playerDao.isPlayerInLocal(targetPlayerId, newLongitude,newLatitude);
	}

	//Changes player`s state.
	public void changePlayerState(int targetPlayerId) {
		
		playerDao.changePlayerState(targetPlayerId);		
	}

	public void logout(int id) {
		
		playerDao.logout(id);
	}
	
	public void restorePlayerBreakState() {
		
		playerDao.restorePlayerBreakState();
	}
}
