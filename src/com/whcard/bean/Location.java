package com.whcard.bean;

import java.sql.Timestamp;

public class Location {
	private int l_id;
	private String l_longitude;
	private String l_latitude;
	private String sfp_id;
	private Timestamp l_time;
	public Timestamp getL_time() {
		return l_time;
	}
	public void setL_time(Timestamp l_time) {
		this.l_time = l_time;
	}
	public int getL_id() {
		return l_id;
	}
	public void setL_id(int l_id) {
		this.l_id = l_id;
	}
	public String getL_longitude() {
		return l_longitude;
	}
	public void setL_longitude(String l_longitude) {
		this.l_longitude = l_longitude;
	}
	public String getL_latitude() {
		return l_latitude;
	}
	public void setL_latitude(String l_latitude) {
		this.l_latitude = l_latitude;
	}
	public String getSfp_id() {
		return sfp_id;
	}
	public void setSfp_id(String sfp_id) {
		this.sfp_id = sfp_id;
	}
}
