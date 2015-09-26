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
 * ��½����
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
	
	//�ļ��д洢���û��������룬������ʼ����¼��
	private String userNameOnfile=null;   
	private String passwordOnfile=null;
	
	//�ؼ�������û��������룬������ʱ����ؼ����������Ϣ
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
		// ��½
		case R.id.login_btn:
			userName = etUsername.getText().toString();
			password = etPassword.getText().toString();

			if( !Util.isNetworkConnected(this) ){
				toast("�������Ӵ��� !");
			} else if (userName.equals("") || password.equals("")) {
				toast("�������人��Ƭ�û���������");
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
		 * ��¼��
		 * 1������¼��Ϣ��װ��jsonString 
		 * 2�����󽻻�
		 * 3����ȡ����ֵ������
		 */	
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			//�����¼����ʾ��¼������
			progressDialog=null;
			progressDialog=ProgressDialog.show(LoginActivity.this, "", "���ڵ�¼�����Եȡ�����");
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
			//ȡ�����ڵ�¼��ʾ��		
			progressDialog.dismiss();
			
			if ("webserver is stop".equals(result)) {
				toast("������ֹͣ���У����Ժ��¼������");
			}else if ("webserver is error".equals(result)) {
				toast("�������ڲ��쳣�����Ժ��¼������");
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
					toast("���ݽ����쳣��");
					return;
				}
			    Log.d("isFullRegister", isFullRegister+"");
				Log.d("state", state);
				Log.d("userId", userId);
				Log.d("whCardPath", whCardPath);
				Log.d("twoDimCodePath", twoDimCodePath);
				Log.d("�洢��¼��Ϣ", "�û�����"+userName+"-���룺"+password+"-�û����ͣ�"+userType+"-�û�Id"+userId+"-�Ƿ���ȫע�᣺"+isFullRegister);
				
				//�����ж��Ƿ��¼��ȷ��
				if (ResultStateCode.LOG_SUCCESS.equals(state)) {
					//������Ȩ�ˣ����¼����Ȩ�˽��棬����������
					if (userType==UserType.Authorizer) {
						Util.saveUserInfo(LoginActivity.this,userName,password,userType,userId,isFullRegister,null);
						Intent intent = new Intent(LoginActivity.this, AuthActivity.class);
						startActivity(intent);
						finish();
					} else {
						Util.saveUserInfo(LoginActivity.this,userName,password,userType,userId,isFullRegister,null);
						
						//�������Ȩ�������������棬�����ݲ���
						Intent toMainActivity = new Intent(LoginActivity.this, MainActivity.class);
						toMainActivity.putExtra("whCardPath", whCardPath);
						toMainActivity.putExtra("twoDimCodePath", twoDimCodePath);
						startActivity(toMainActivity);
						finish();
					}			
				} else if (ResultStateCode.LOG_TELL_ERROR.equals(state)) {
					//��������
					etUsername.setText("");
					etPassword.setText("");
					toast("���ֻ���δע�ᣬ���������룡");
				}else if (ResultStateCode.LOG_PASS_ERROR.equals(state)){
					etUsername.setText("");
					etPassword.setText("");
					toast("�û���������������������룡");
				}else {
					toast("�������쳣�����Ժ��¼��");
				}
			}
			
		}	
	}
	public void toast(String toast) {
		Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
	}
}
