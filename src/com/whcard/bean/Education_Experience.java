package com.whcard.bean;

import java.util.Date;


public class Education_Experience {
	private int edu_id;    //编号
	private String edu_gradschool;   //学校
	private String edu_major;   //专业
	private String edu_education;   //学历
	private Date edu_staTime;    //开始时间
	private Date edu_endTime;    //结束时间
	private String edu_description;   //专业描述
	private String sfp_id;  //流动人员编号
	public int getEdu_id() {
		return edu_id;
	}
	public void setEdu_id(int edu_id) {
		this.edu_id = edu_id;
	}
	public String getEdu_gradschool() {
		return edu_gradschool;
	}
	public void setEdu_gradschool(String edu_gradschool) {
		this.edu_gradschool = edu_gradschool;
	}
	public String getEdu_major() {
		return edu_major;
	}
	public void setEdu_major(String edu_major) {
		this.edu_major = edu_major;
	}
	public String getEdu_education() {
		return edu_education;
	}
	public void setEdu_education(String edu_education) {
		this.edu_education = edu_education;
	}
	public Date getEdu_staTime() {
		return edu_staTime;
	}
	public void setEdu_staTime(Date edu_staTime) {
		this.edu_staTime = edu_staTime;
	}
	public Date getEdu_endTime() {
		return edu_endTime;
	}
	public void setEdu_endTime(Date edu_endTime) {
		this.edu_endTime = edu_endTime;
	}
	public String getEdu_description() {
		return edu_description;
	}
	public void setEdu_description(String edu_description) {
		this.edu_description = edu_description;
	}
	public String getSfp_id() {
		return sfp_id;
	}
	public void setSfp_id(String sfp_id) {
		this.sfp_id = sfp_id;
	}
	
}
