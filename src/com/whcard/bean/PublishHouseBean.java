package com.whcard.bean;

import java.io.Serializable;

//发布房源时封装成json
public class PublishHouseBean implements Serializable {
	
	//用户id
	private String publish_house_userid;
	private String publish_house_houseid;
	//还有部分控件有待完善
	private String publish_house_address;
	private String publish_house_cell;
	private String publish_house_type;
	private String publish_house_way;
	private String publish_house_rental;
	private String publish_house_area;
	private String publish_house_floor;
	private String publish_house_decorate;
	
	//房屋配置：以复选框的形式
	private String publish_house_config;
	
	private String publish_house_title;
	private String publish_house_describle;
	private String publish_house_contactor;
	private String publish_house_tell;
	
	public String getPublish_house_houseid() {
		return publish_house_houseid;
	}
	public void setPublish_house_houseid(String publish_house_houseid) {
		this.publish_house_houseid = publish_house_houseid;
	}
	public String getPublish_house_userid() {
		return publish_house_userid;
	}
	public void setPublish_house_userid(String publish_house_userid) {
		this.publish_house_userid = publish_house_userid;
	}
	public String getPublish_house_address() {
		return publish_house_address;
	}
	public void setPublish_house_address(String publish_house_address) {
		this.publish_house_address = publish_house_address;
	}
	public String getPublish_house_cell() {
		return publish_house_cell;
	}
	public void setPublish_house_cell(String publish_house_cell) {
		this.publish_house_cell = publish_house_cell;
	}
	public String getPublish_house_type() {
		return publish_house_type;
	}
	public void setPublish_house_type(String publish_house_type) {
		this.publish_house_type = publish_house_type;
	}
	public String getPublish_house_way() {
		return publish_house_way;
	}
	public void setPublish_house_way(String publish_house_way) {
		this.publish_house_way = publish_house_way;
	}
	public String getPublish_house_rental() {
		return publish_house_rental;
	}
	public void setPublish_house_rental(String publish_house_rental) {
		this.publish_house_rental = publish_house_rental;
	}
	public String getPublish_house_area() {
		return publish_house_area;
	}
	public void setPublish_house_area(String publish_house_area) {
		this.publish_house_area = publish_house_area;
	}
	public String getPublish_house_floor() {
		return publish_house_floor;
	}
	public void setPublish_house_floor(String publish_house_floor) {
		this.publish_house_floor = publish_house_floor;
	}
	public String getPublish_house_decorate() {
		return publish_house_decorate;
	}
	public void setPublish_house_decorate(String publish_house_decorate) {
		this.publish_house_decorate = publish_house_decorate;
	}
	public String getPublish_house_config() {
		return publish_house_config;
	}
	public void setPublish_house_config(String publish_house_config) {
		this.publish_house_config = publish_house_config;
	}
	public String getPublish_house_title() {
		return publish_house_title;
	}
	public void setPublish_house_title(String publish_house_title) {
		this.publish_house_title = publish_house_title;
	}
	public String getPublish_house_describle() {
		return publish_house_describle;
	}
	public void setPublish_house_describle(String publish_house_describle) {
		this.publish_house_describle = publish_house_describle;
	}
	public String getPublish_house_contactor() {
		return publish_house_contactor;
	}
	public void setPublish_house_contactor(String publish_house_contactor) {
		this.publish_house_contactor = publish_house_contactor;
	}
	public String getPublish_house_tell() {
		return publish_house_tell;
	}
	public void setPublish_house_tell(String publish_house_tell) {
		this.publish_house_tell = publish_house_tell;
	}
	
	
	
}
