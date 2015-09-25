package com.whcard.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class RequirementRental implements Serializable {
	private int rd_id;
	private int rd_rental;
	
	private String rd_address;
	private String rd_type;
	private String rd_way;
	private String rd_title;
	private String rd_describle;
	private String rd_contactor;
	private String rd_tel;
	private String rd_name;
	
	
	private String rd_pubtime;
	private int fp_authority;
	private Standard_Floating_Population sfp;
	
	private int rd_page_num;
	
	
	
	public String getRd_address() {
		return rd_address;
	}
	public void setRd_address(String rd_address) {
		this.rd_address = rd_address;
	}
	public String getRd_type() {
		return rd_type;
	}
	public void setRd_type(String rd_type) {
		this.rd_type = rd_type;
	}
	public String getRd_way() {
		return rd_way;
	}
	public void setRd_way(String rd_way) {
		this.rd_way = rd_way;
	}
	public String getRd_title() {
		return rd_title;
	}
	public void setRd_title(String rd_title) {
		this.rd_title = rd_title;
	}
	public String getRd_describle() {
		return rd_describle;
	}
	public void setRd_describle(String rd_describle) {
		this.rd_describle = rd_describle;
	}
	public String getRd_contactor() {
		return rd_contactor;
	}
	public void setRd_contactor(String rd_contactor) {
		this.rd_contactor = rd_contactor;
	}
	public String getRd_tel() {
		return rd_tel;
	}
	public void setRd_tel(String rd_tel) {
		this.rd_tel = rd_tel;
	}
	public String getRd_name() {
		return rd_name;
	}
	public void setRd_name(String rd_name) {
		this.rd_name = rd_name;
	}
	public int getRd_page_num() {
		return rd_page_num;
	}
	public void setRd_page_num(int rd_page_num) {
		this.rd_page_num = rd_page_num * 20;
	}
	public int getRd_id() {
		return rd_id;
	}
	public void setRd_id(int rd_id) {
		this.rd_id = rd_id;
	}
	public int getRd_rental() {
		return rd_rental;
	}
	public void setRd_rental(int rd_rental) {
		this.rd_rental = rd_rental;
	}

	
	public String getRd_pubtime() {
		return rd_pubtime;
	}
	public void setRd_pubtime(String rd_pubtime) {
		this.rd_pubtime = rd_pubtime;
	}
	public int getFp_authority() {
		return fp_authority;
	}
	public void setFp_authority(int fp_authority) {
		this.fp_authority = fp_authority;
	}
	public Standard_Floating_Population getSfp() {
		return sfp;
	}
	public void setSfp(Standard_Floating_Population sfp) {
		this.sfp = sfp;
	}
}
