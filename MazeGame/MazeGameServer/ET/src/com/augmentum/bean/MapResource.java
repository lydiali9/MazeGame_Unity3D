package com.augmentum.bean;

public class MapResource {

	private int sizeX;
	private int sizeY;
	private int medal;
	private int bdiamonds;
	private int mdiamonds;
	private int sdiamonds;
	private int trapNumb;
	private int playerNumb;
	private int[] player;

	public int getSizeX() {
		return sizeX;
	}

	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}

	public int getMedal() {
		return medal;
	}

	public void setMedal(int medal) {
		this.medal = medal;
	}

	public int getBdiamonds() {
		return bdiamonds;
	}

	public void setBdiamonds(int bdiamonds) {
		this.bdiamonds = bdiamonds;
	}

	public int getMdiamonds() {
		return mdiamonds;
	}

	public void setMdiamonds(int mdiamonds) {
		this.mdiamonds = mdiamonds;
	}

	public int getSdiamonds() {
		return sdiamonds;
	}

	public void setSdiamonds(int sdiamonds) {
		this.sdiamonds = sdiamonds;
	}

	public int getTrapNumb() {
		return trapNumb;
	}

	public void setTrapNumb(int trapNumb) {
		this.trapNumb = trapNumb;
	}

	public int getPlayerNumb() {
		return playerNumb;
	}

	public void setPlayerNumb(int playerNumb) {
		this.playerNumb = playerNumb;
	}

	public int[] getPlayer() {
		return player;
	}

	public void setPlayer(int[] player) {
		this.player = player;
	}

	public String mapResourceToJson() {

		String mapJson = "{\"sizeX\":" + sizeX + ", \"sizeY\":" + sizeY + ", \"medal\":" + medal + ", \"bdiamonds\":"
				+ bdiamonds + ", \"mdiamonds\":" + mdiamonds + ", \"sdiamonds\":" + sdiamonds 
				+ ", \"trapNumb\":" + trapNumb + ", \"playerNumb\":" + playerNumb + ", \"player\":[";
        
		for (int i = 0; i < playerNumb; i++) {

			mapJson += "{\"playerId\":" + player[i] + "}";
         
			if (i != (playerNumb - 1)) {
          
				mapJson += ",";
			}
		}
		
		mapJson += "]}";       
		return mapJson;
	}
}