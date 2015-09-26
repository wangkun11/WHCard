package com.whcard.util;


public class ResultStateCode {
	public static final String LOG_SUCCESS="LOG_SUCCESS";		//登录成功
	public static final String LOG_TELL_ERROR="LOG_TELL_ERROR";		//登录用户名错误
	public static final String LOG_PASS_ERROR="LOG_PASS_ERROR";		//登录密码错误
	public static final String LOG_SERVER_ERROR="LOG_SERVER_ERROR";		//登录服务器错误
	
	public static final String REG_GFP_SUCCESS="REG_GFP_SUCCESS";	//普通用户注册成功
	public static final String REG_ERROR_TEL_EXIST="REG_ERROR_TEL_EXIST";//普通注册号码已存在
	public static final String REG_GFP_FAILED="REG_GFP_FAILED";	//普通用户注册失败
	//public static final String REG_ERROR_PWD_ERR="REG_ERROR_PWD_ERR";	//普通注册密码不合标准
	
	public static final String REG_SFP_SUCCESS="REG_SFP_SUCCESS";	//标准用户注册成功
	public static final String REG_SFP_FAILED="REG_SFP_FAILED";	//标准用户注册失败			
	
	public static final String UDP_SUCCESS="UDP_SUCCESS";		//更新成功
	public static final String UDP_FAIE="UDP_FAIE";	 		//更新失败
	//public static final String QUE_SUCESS="QUE_SUCESS";		//查询成功
	
	public static final String QUE_SFP_ERROR="QUE_SFP_ERROR";	//查询失败

}