package com.whcard.util;

public class PopulationResult {
	
	private ResultStateCode state;
	private int userId;
	private boolean isFullRegister;
	private String whCardPath;
	private String twoDimCodePath;
	
	public ResultStateCode getState() {
		return state;
	}
	public void setState(ResultStateCode state) {
		this.state = state;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public boolean isFullRegister() {
		return isFullRegister;
	}
	public void setFullRegister(boolean isFullRegister) {
		this.isFullRegister = isFullRegister;
	}
	public String getWhCardPath() {
		return whCardPath;
	}
	public void setWhCardPath(String whCardPath) {
		this.whCardPath = whCardPath;
	}
	public String getTwoDimCodePath() {
		return twoDimCodePath;
	}
	public void setTwoDimCodePath(String twoDimCodePath) {
		this.twoDimCodePath = twoDimCodePath;
	}

}
