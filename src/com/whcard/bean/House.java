package com.whcard.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;



public class House implements Serializable {
	private int hou_id;
	private String hou_rentway;
	private String hou_cell;
	private String hou_add;
	private String hou_type;
	private String hou_floor;
	
	private String hou_config;
	private int hou_rental;
	private String hou_title;
	private String hou_descrip;
	private String hou_pubtime;
	private int hou_rentstate;
	private Standard_Floating_Population sfp;
	private int hou_page_num;
	
	private int hou_area;
	private String hou_decorate;
	private String sfp_tel;
	private String sfp_name;
	private ArrayList<String> hou_img;
	public ArrayList<String> getHou_img() {
		return hou_img;
	}
	public void setHou_img(ArrayList<String> hou_img) {
		this.hou_img = hou_img;
	}
	private int num;
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	


	public int getHou_area() {
		return hou_area;
	}
	public void setHou_area(int hou_area) {
		this.hou_area = hou_area;
	}
	public String getHou_decorate() {
		return hou_decorate;
	}
	public void setHou_decorate(String hou_decorate) {
		this.hou_decorate = hou_decorate;
	}
	public String getSfp_tel() {
		return sfp_tel;
	}
	public void setSfp_tel(String sfp_tel) {
		this.sfp_tel = sfp_tel;
	}
	public String getSfp_name() {
		return sfp_name;
	}
	public void setSfp_name(String sfp_name) {
		this.sfp_name = sfp_name;
	}
	public void setSfp(Standard_Floating_Population sfp) {
		this.sfp = sfp;
	}
	public int getHou_id() {
		return hou_id;
	}
	public void setHou_id(int hou_id) {
		this.hou_id = hou_id;
	}
	public String getHou_rentway() {
		return hou_rentway;
	}
	public void setHou_rentway(String hou_rentway) {
		this.hou_rentway = hou_rentway;
	}
	public String getHou_cell() {
		return hou_cell;
	}
	public void setHou_cell(String hou_cell) {
		this.hou_cell = hou_cell;
	}
	public String getHou_add() {
		return hou_add;
	}
	public void setHou_add(String hou_add) {
		this.hou_add = hou_add;
	}
	public String getHou_type() {
		return hou_type;
	}
	public void setHou_type(String hou_type) {
		this.hou_type = hou_type;
	}
	public String getHou_floor() {
		return hou_floor;
	}
	public void setHou_floor(String hou_floor) {
		this.hou_floor = hou_floor;
	}
	public String getHou_config() {
		return hou_config;
	}
	public void setHou_config(String hou_config) {
		this.hou_config = hou_config;
	}
	public int getHou_rental() {
		return hou_rental;
	}
	public void setHou_rental(int hou_rental) {
		this.hou_rental = hou_rental;
	}
	public String getHou_title() {
		return hou_title;
	}
	public void setHou_title(String hou_title) {
		this.hou_title = hou_title;
	}
	public String getHou_descrip() {
		return hou_descrip;
	}
	public void setHou_descrip(String hou_descrip) {
		this.hou_descrip = hou_descrip;
	}
	public String getHou_pubtime() {
		return hou_pubtime;
	}
	public void setHou_pubtime(String hou_pubtime) {
		this.hou_pubtime = hou_pubtime;
	}
	public int getHou_rentstate() {
		return hou_rentstate;
	}
	public void setHou_rentstate(int hou_rentstate) {
		this.hou_rentstate = hou_rentstate;
	}
	public Standard_Floating_Population getSfp() {
		return sfp;
	}
	public void setSfp_id(Standard_Floating_Population sfp) {
		this.sfp = sfp;
	}
	public int getHou_page_num() {
		return hou_page_num;
	}
	public void setHou_page_num(int hou_page_num) {
		this.hou_page_num = hou_page_num;
	}
    

}
