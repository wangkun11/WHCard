package com.whcard.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * ������
 * 
 * @date 2014-5-9
 * @author Stone
 */
public class Util {

	/**
	 * �жϵ绰�����Ƿ���Ч
	 * 
	 * @param phoneNumber
	 * @return true ��Ч / false ��Ч
	 */
	public static boolean isPhoneNumberValid(String phoneNumber) {

		boolean isValid = false;

		String expression = "((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
		CharSequence inputStr = phoneNumber;

		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);

		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}
	
	/**
	 * �ж������ַ�Ƿ���Ч
	 * 
	 * @param email
	 * @return true ��Ч / false ��Ч
	 */
	public static boolean isEmailValid(String email)
	{
		String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        return email.matches(regex);
	}

	// �ж������Ƿ�����
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	//�����û��ĵ�½��¼
	public static void saveUserInfo(Context context,
			String userName, String password, Integer userType, 
			String userId, String isRegist,String isAuthorize) {
		
			SharedPreferences sp = context.getSharedPreferences("UserInfo", 0);
			Editor editor = sp.edit();
			if (userName==null) {
				
			} else {
				editor.putString("userName", ""+userName);
			}
			if (password==null) {
				
			} else {
				editor.putString("password", ""+password);
			}		
			if (userType==null) {
				
			}else {
				editor.putInt("userType", userType);
			}
			if (userId==null) {
				
			} else {
				editor.putString("userId", ""+userId);
			}
			if (isRegist==null) {

			}else {
				editor.putString("isRegist", isRegist);
			}
			if (isAuthorize==null) {

			} else {
				editor.putString("isAuthorize", isAuthorize);
			}
			editor.commit();
		}
	public static String getUserInfo(Context context,String key){
		SharedPreferences userInfo=context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
		if ("userType".equals(key)) {
			return userInfo.getInt("userType", 0)+"";
		}else {
			return userInfo.getString(key, "error");
		}
	}
}
