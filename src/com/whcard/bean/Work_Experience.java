package com.whcard.bean;

import java.util.Date;

public class Work_Experience {
	private int work_id;  //���
	private String work_company;  //������λ
	private String work_position;  //ְλ
	private Date work_staTime;   //��ʼʱ��
	private Date work_endTime;   //����ʱ��
	private String work_description;  //ְ������
	private String sfp_id;   //������Ա���
	public int getWork_id() {
		return work_id;
	}
	public void setWork_id(int work_id) {
		this.work_id = work_id;
	}
	public String getWork_company() {
		return work_company;
	}
	public void setWork_company(String work_company) {
		this.work_company = work_company;
	}
	public String getWork_position() {
		return work_position;
	}
	public void setWork_position(String work_position) {
		this.work_position = work_position;
	}
	public Date getWork_staTime() {
		return work_staTime;
	}
	public void setWork_staTime(Date work_staTime) {
		this.work_staTime = work_staTime;
	}
	public Date getWork_endTime() {
		return work_endTime;
	}
	public void setWork_endTime(Date work_endTime) {
		this.work_endTime = work_endTime;
	}
	public String getWork_description() {
		return work_description;
	}
	public void setWork_description(String work_description) {
		this.work_description = work_description;
	}
	public String getSfp_id() {
		return sfp_id;
	}
	public void setSfp_id(String sfp_id) {
		this.sfp_id = sfp_id;
	}
	
  
}
