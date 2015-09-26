package com.whcard.bean;

import java.io.Serializable;

public class WuhanCard implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int whcard_id;
	private String whcard_name;
	private String whcard_gender;
	private String whcard_identity;
	private String whcard_pic;
	private String whcard_add;
	private String whcard_tel;
	private String whcard_nation;
	private String whcard_naplace;
	private int whcard_page_num;
	private String sfp_id;
	
	public int getWhcard_page_num() {
		return whcard_page_num;
	}

	public String getWhcard_nation() {
		return whcard_nation;
	}
	public void setWhcard_nation(String whcard_nation) {
		this.whcard_nation = whcard_nation;
	}
	public String getWhcard_naplace() {
		return whcard_naplace;
	}
	public void setWhcard_naplace(String whcard_naplace) {
		this.whcard_naplace = whcard_naplace;
	}

	public int getWhcard_id() {
		return whcard_id;
	}
	public void setWhcard_id(int whcard_id) {
		this.whcard_id = whcard_id;
	}
	public String getWhcard_name() {
		return whcard_name;
	}
	public void setWhcard_name(String whcard_name) {
		this.whcard_name = whcard_name;
	}
	
	public String getWhcard_gender() {
		return whcard_gender;
	}
	public void setWhcard_gender(String whcard_gender) {
		this.whcard_gender = whcard_gender;
	}
	public String getWhcard_identity() {
		return whcard_identity;
	}
	public void setWhcard_identity(String whcard_identity) {
		this.whcard_identity = whcard_identity;
	}
	public String getWhcard_pic() {
		return whcard_pic;
	}
	public void setWhcard_pic(String whcard_pic) {
		this.whcard_pic = whcard_pic;
	}
	public String getWhcard_add() {
		return whcard_add;
	}
	public void setWhcard_add(String whcard_add) {
		this.whcard_add = whcard_add;
	}
	public String getWhcard_tel() {
		return whcard_tel;
	}
	public void setWhcard_tel(String whcard_tel) {
		this.whcard_tel = whcard_tel;
	}

	public String getSfp_id() {
		return sfp_id;
	}

	public void setSfp_id(String sfp_id) {
		this.sfp_id = sfp_id;
	}
	
}
