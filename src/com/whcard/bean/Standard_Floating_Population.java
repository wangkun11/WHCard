package com.whcard.bean;

import java.io.Serializable;
import java.util.ArrayList;


public class Standard_Floating_Population implements Serializable{
	//必填
	private String sfp_id;
	private String sfp_name;
	private String sfp_sex;
	private String sfp_nation;//民族
	private String sfp_identity;//身份证
	private String sfp_identityimage;
	private String sfp_temporary_residence;
	private String sfp_tel;
	//
	private String sfp_native;//户口所在地
	
	private String sfp_furtime;
	private String sfp_regtime;	
	private int sfp_verify;
	
	private String sfp_naplace;//籍贯
	private String sfp_birthdate;//
	private String sfp_politic;
	private String sfp_email;
	
	private int sfp_height;//身高
	
	private String sfp_blood;
	private String sfp_marriage;
	private String sfp_birthplace;

	private String sfp_gradschool;
	private String sfp_education;
	private String sfp_major;
	
	private String sfp_graddate;//
	
	private ArrayList<Education_Experience> sfp_edu;
	private ArrayList<Work_Experience> sfp_work;
	
	
	public ArrayList<Education_Experience> getSfp_edu() {
		return sfp_edu;
	}
	public void setSfp_edu(ArrayList<Education_Experience> sfp_edu) {
		this.sfp_edu = sfp_edu;
	}
	public ArrayList<Work_Experience> getSfp_work() {
		return sfp_work;
	}
	public void setSfp_work(ArrayList<Work_Experience> sfp_work) {
		this.sfp_work = sfp_work;
	}
	public String getSfp_id() {
		return sfp_id;
	}
	public void setSfp_id(String sfp_id) {
		this.sfp_id = sfp_id;
	}
	public String getSfp_name() {
		return sfp_name;
	}
	public void setSfp_name(String sfp_name) {
		this.sfp_name = sfp_name;
	}
	public String getSfp_sex() {
		return sfp_sex;
	}
	public void setSfp_sex(String sfp_sex) {
		this.sfp_sex = sfp_sex;
	}
	public String getSfp_nation() {
		return sfp_nation;
	}
	public void setSfp_nation(String sfp_nation) {
		this.sfp_nation = sfp_nation;
	}
	public String getSfp_identity() {
		return sfp_identity;
	}
	public void setSfp_identity(String sfp_identity) {
		this.sfp_identity = sfp_identity;
	}
	public String getSfp_identityimage() {
		return sfp_identityimage;
	}
	public void setSfp_identityimage(String sfp_identityimage) {
		this.sfp_identityimage = sfp_identityimage;
	}
	public String getSfp_temporary_residence() {
		return sfp_temporary_residence;
	}
	public void setSfp_temporary_residence(String sfp_temporary_residence) {
		this.sfp_temporary_residence = sfp_temporary_residence;
	}
	public String getSfp_tel() {
		return sfp_tel;
	}
	public void setSfp_tel(String sfp_tel) {
		this.sfp_tel = sfp_tel;
	}
	public String getSfp_native() {
		return sfp_native;
	}
	public void setSfp_native(String sfp_native) {
		this.sfp_native = sfp_native;
	}
	public String getSfp_furtime() {
		return sfp_furtime;
	}
	public void setSfp_furtime(String sfp_furtime) {
		this.sfp_furtime = sfp_furtime;
	}
	public String getSfp_regtime() {
		return sfp_regtime;
	}
	public void setSfp_regtime(String sfp_regtime) {
		this.sfp_regtime = sfp_regtime;
	}
	public int getSfp_verify() {
		return sfp_verify;
	}
	public void setSfp_verify(int sfp_verify) {
		this.sfp_verify = sfp_verify;
	}
	public String getSfp_naplace() {
		return sfp_naplace;
	}
	public void setSfp_naplace(String sfp_naplace) {
		this.sfp_naplace = sfp_naplace;
	}
	public String getSfp_birthdate() {
		return sfp_birthdate;
	}
	public void setSfp_birthdate(String sfp_birthdate) {
		this.sfp_birthdate = sfp_birthdate;
	}
	public String getSfp_politic() {
		return sfp_politic;
	}
	public void setSfp_politic(String sfp_politic) {
		this.sfp_politic = sfp_politic;
	}
	public String getSfp_email() {
		return sfp_email;
	}
	public void setSfp_email(String sfp_email) {
		this.sfp_email = sfp_email;
	}
	public int getSfp_height() {
		return sfp_height;
	}
	public void setSfp_height(int sfp_height) {
		this.sfp_height = sfp_height;
	}
	public String getSfp_blood() {
		return sfp_blood;
	}
	public void setSfp_blood(String sfp_blood) {
		this.sfp_blood = sfp_blood;
	}
	public String getSfp_marriage() {
		return sfp_marriage;
	}
	public void setSfp_marriage(String sfp_marriage) {
		this.sfp_marriage = sfp_marriage;
	}
	public String getSfp_birthplace() {
		return sfp_birthplace;
	}
	public void setSfp_birthplace(String sfp_birthplace) {
		this.sfp_birthplace = sfp_birthplace;
	}
	public String getSfp_gradschool() {
		return sfp_gradschool;
	}
	public void setSfp_gradschool(String sfp_gradschool) {
		this.sfp_gradschool = sfp_gradschool;
	}
	public String getSfp_education() {
		return sfp_education;
	}
	public void setSfp_education(String sfp_education) {
		this.sfp_education = sfp_education;
	}
	public String getSfp_major() {
		return sfp_major;
	}
	public void setSfp_major(String sfp_major) {
		this.sfp_major = sfp_major;
	}
	public String getSfp_graddate() {
		return sfp_graddate;
	}
	public void setSfp_graddate(String sfp_graddate) {
		this.sfp_graddate = sfp_graddate;
	}	
	
}
