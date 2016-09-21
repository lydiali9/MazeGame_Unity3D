package com.augmentum.bean;

public class Resource {
	private int medal;
	private int bdiamondsNumber;
	private int mdiamondsNumber;
	private int sdiamondsNumber;

	public int getMedal() {
		return medal;
	}

	public void setMedal(int medal) {
		this.medal = medal;
	}

	public int getBdiamondsNumber() {
		return bdiamondsNumber;
	}

	public void setBdiamondsNumber(int bdiamondsNumber) {
		this.bdiamondsNumber = bdiamondsNumber;
	}

	public int getMdiamondsNumber() {
		return mdiamondsNumber;
	}

	public void setMdiamondsNumber(int mdiamondsNumber) {
		this.mdiamondsNumber = mdiamondsNumber;
	}

	public int getSdiamondsNumber() {
		return sdiamondsNumber;
	}

	public void setSdiamondsNumber(int sdiamondsNumber) {
		this.sdiamondsNumber = sdiamondsNumber;
	}

	public String resourceToString() {

		return "{\"medal\":" + medal + ", \"bdiamonds\":" + bdiamondsNumber 
				+ ", \"mdiamonds\":" + mdiamondsNumber + ", \"sdiamonds\":" + sdiamondsNumber + "}";
	}
}
