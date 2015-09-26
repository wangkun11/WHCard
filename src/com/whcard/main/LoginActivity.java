package com.whcard.main;


import org.json.JSONException;
import org.json.JSONObject;

import com.whcard.auth.AuthActivity;
import com.whcard.net.GetUrl;
import com.whcard.net.HttpUtil;
import com.whcard.util.ResultStateCode;
import com.whcard.util.UserType;
import com.whcard.util.Util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

/**
 * 登陆界面
 */
public class LoginActivity extends Activity implements OnClickListener{

	@SuppressWarnings("unused")
	private static final String TAG = "LoginActicity";

	private Button btnLogin;
	private Button btnResetPsd;
	private Button btnRegist;
	
	private EditText etUsername;
	private EditText etPassword;
	
	private RadioGroup radioGroup;
	
	//文件中存储的用户名和密码，用来初始化登录框
	private String userNameOnfile=null;   
	private String passwordOnfile=null;
	
	//控件里面的用户名和密码，用来临时保存控件里输入的信息
	private String userName;
	private String password;
	private Integer userType=UserType.Transfer;
	
	private String jsonString;
	private ProgressDialog progressDialog;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		
		initView();
		getUserPreferences();
		
		
		if (!("".equals(userNameOnfile)||"".equals(passwordOnfile))) {
			etUsername.setText(userNameOnfile);
			etPassword.setText(passwordOnfile);
		}		
		initEvent();					
	}	
	private void getUserPreferences() {				
		userNameOnfile=Util.getUserInfo(this, "userName");
		passwordOnfile=Util.getUserInfo(this, "password");
	}

	private void initEvent() {
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.login_radio0:
					userType=UserType.Transfer;
					break;
				case R.id.login_radio1:
					userType=UserType.HouseOwner;
					break;
				case R.id.login_radio2:
					userType=UserType.Cooperator;
					break;
				case R.id.login_radio3:
					userType=UserType.Authorizer;
					break;
				default:
					break;
				}
			}
		});
		btnLogin.setOnClickListener(this);
		btnResetPsd.setOnClickListener(this);
		btnRegist.setOnClickListener(this);
	}

	private void initView() {
		btnLogin = (Button) findViewById(R.id.login_btn);
		btnResetPsd = (Button) findViewById(R.id.login__reset_psd);
		btnRegist = (Button) findViewById(R.id.login__regist);

		etUsername = (EditText) findViewById(R.id.login_username);
		etPassword = (EditText) findViewById(R.id.login_password);
		
		radioGroup = (RadioGroup) findViewById(R.id.login_radioGroup);	
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 登陆
		case R.id.login_btn:
			userName = etUsername.getText().toString();
			password = etPassword.getText().toString();

			if( !Util.isNetworkConnected(this) ){
				toast("网络连接错误 !");
			} else if (userName.equals("") || password.equals("")) {
				toast("请输入武汉名片用户名和密码");
				break;
			} else {
				/*Util.saveUserInfo(LoginActivity.this,userName,password,userType,null,null);				
				Intent toResetPsdActivity = new Intent(LoginActivity.this, MainActivity.class);
				startActivity(toResetPsdActivity);
				finish();*/
				LoginAsyncTask loginAsyncTask=new LoginAsyncTask();
				loginAsyncTask.execute();
			}
			break;
			
		case R.id.login__reset_psd:
			Intent toResetPsdActivity = new Intent(LoginActivity.this, ResetPsdActivity.class);
			startActivity(toResetPsdActivity);
			break;

		case R.id.login__regist:
			Intent toRegistActivity = new Intent(LoginActivity.this, RegistActivity.class);
			startActivity(toRegistActivity);
			break;

		default:
			break;

		}
	}
	private class LoginAsyncTask extends AsyncTask<String, Integer, String>{

		/**
		 * 登录：
		 * 1、将登录信息封装成jsonString 
		 * 2、请求交互
		 * 3、获取返回值并处理
		 */	
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			//点击登录是显示登录进度条
			progressDialog=null;
			progressDialog=ProgressDialog.show(LoginActivity.this, "", "正在登录，请稍等。。。");
			JSONObject userObject=new JSONObject();
			try {
				userObject.put("userName", userName);
				userObject.put("password", password);
				userObject.put("userType", userType);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}					
			jsonString = userObject.toString();
		}
		
		@Override
		protected String doInBackground(String... params) {
			String httpReturn=HttpUtil.httpClient(GetUrl.Login, jsonString);
			return httpReturn;
		}
		@Override
		protected void onPostExecute(String result) {
			//取消正在登录显示框		
			progressDialog.dismiss();
			
			if ("webserver is stop".equals(result)) {
				toast("服务器停止运行！请稍后登录。。。");
			}else if ("webserver is error".equals(result)) {
				toast("服务器内部异常！请稍后登录。。。");
			}else{
				String state=new String();
				String userId=new String();
				String isFullRegister=new String();
				String whCardPath=new String();
				String twoDimCodePath=new String();
				String isAuthorize=new String();
				
				try {
					JSONObject loginJson  = new JSONObject(result);									
		            isFullRegister=loginJson.getString("isFullRegister");
					state=loginJson.getString("state");			
					userId=loginJson.getString("userId");
					whCardPath=loginJson.getString("whCardPath");
					twoDimCodePath=loginJson.getString("twoDimCodePath");
					isAuthorize=loginJson.getString("isAuthorize");
				} catch (JSONException e) {
					toast("数据解析异常！");
					return;
				}
			    Log.d("isFullRegister", isFullRegister+"");
				Log.d("state", state);
				Log.d("userId", userId);
				Log.d("whCardPath", whCardPath);
				Log.d("twoDimCodePath", twoDimCodePath);
				Log.d("存储登录信息", "用户名："+userName+"-密码："+password+"-用户类型："+userType+"-用户Id"+userId+"-是否完全注册："+isFullRegister);
				
				//首先判断是否登录正确，
				if (ResultStateCode.LOG_SUCCESS.equals(state)) {
					//若是授权人，则登录到授权人界面，否则到主界面
					if (userType==UserType.Authorizer) {
						Util.saveUserInfo(LoginActivity.this,userName,password,userType,userId,isFullRegister,null);
						Intent intent = new Intent(LoginActivity.this, AuthActivity.class);
						startActivity(intent);
						finish();
					} else {
						Util.saveUserInfo(LoginActivity.this,userName,password,userType,userId,isFullRegister,null);
						
						//如果是授权人启动到主界面，并传递参数
						Intent toMainActivity = new Intent(LoginActivity.this, MainActivity.class);
						toMainActivity.putExtra("whCardPath", whCardPath);
						toMainActivity.putExtra("twoDimCodePath", twoDimCodePath);
						startActivity(toMainActivity);
						finish();
					}			
				} else if (ResultStateCode.LOG_TELL_ERROR.equals(state)) {
					//清空输入框
					etUsername.setText("");
					etPassword.setText("");
					toast("该手机号未注册，请重新输入！");
				}else if (ResultStateCode.LOG_PASS_ERROR.equals(state)){
					etUsername.setText("");
					etPassword.setText("");
					toast("用户密码输入错误，请重新输入！");
				}else {
					toast("服务器异常，请稍后登录！");
				}
			}
			
		}	
	}
	public void toast(String toast) {
		Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
	}
}
