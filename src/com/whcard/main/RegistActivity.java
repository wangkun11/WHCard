package com.whcard.main;

import org.json.JSONException;
import org.json.JSONObject;

import com.whcard.net.GetUrl;
import com.whcard.net.HttpUtil;
import com.whcard.util.ResultStateCode;
import com.whcard.util.Util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 注册界面
 * 
 * @date 2014-4-24
 * @author Stone
 */
public class RegistActivity extends Activity{

	@SuppressWarnings("unused")
	private static final String TAG = "RegisterActivity";

	private Button btnReg;
	private EditText etPassword;
	private EditText etComfirmPsd;
	private EditText etTel;

	private String password;
	private String comfirmPsd;
	private String userTel;
	
	String jsonString;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_regist);
		
		initView();
		initEvent();
		
	}
	private void initView() {
		etPassword = (EditText) findViewById(R.id.reg_password);
		etComfirmPsd = (EditText) findViewById(R.id.reg_comfirm_psd);
		etTel = (EditText) findViewById(R.id.reg_usertel);

		btnReg = (Button) findViewById(R.id.reg_btn);
	}
	
	private void initData() {
		password = etPassword.getText().toString();
		comfirmPsd = etComfirmPsd.getText().toString();
		userTel = etTel.getText().toString();
		
	}

	
	private void initEvent() {	
		btnReg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				initData();
				
				if(!Util.isNetworkConnected(RegistActivity.this)) {
					toast("网络连接不正确，请检查!");
				} else if (password.equals("")|| comfirmPsd.equals("") || userTel.equals("")) {
					Log.d("注册信息", password+"||"+comfirmPsd+"||"+userTel);
					toast("注册信息不完整，请重新填写!");
				} else if (!comfirmPsd.equals(password)) {
					toast("您两次密码输入不一致，请确认");
				} else if(!Util.isPhoneNumberValid(userTel)) {
					toast("请输入正确的手机号码");
				}else {
					Util.saveUserInfo(RegistActivity.this, userTel, password, null, null,null,null);
					
					/*Intent intent = new Intent(RegistActivity.this,LoginActivity.class);
					startActivity(intent);
					RegistActivity.this.finish();*/
					// 开始提交注册信息				
					MyAsyncTask myAsyncTask=new MyAsyncTask();
					myAsyncTask.execute();
					
				}
			}
		});		
	}
	
	public void toast(String toast) {
		Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
	};
	
	class MyAsyncTask extends AsyncTask<String, Integer, String>
	{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			JSONObject userObject=new JSONObject();
			try {
				userObject.put("gfp_tel", userTel);
				userObject.put("gfp_password", password);					
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jsonString = userObject.toString();
			progressDialog=null;
			progressDialog=ProgressDialog.show(RegistActivity.this, "", "正在注册，请稍等。。。");
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String httpReturn=HttpUtil.httpClient(GetUrl.Regist, jsonString);
			return httpReturn;
		}
		
		@Override
		protected void onPostExecute(String result) {
			progressDialog.dismiss();
			JSONObject jsonObject=null;
			String state=null;
			try {
				jsonObject = new JSONObject(result);
				state=jsonObject.getString("state");
				Log.d("state", state);
			} catch (JSONException e) {
				Log.d("注册Json解析","数据解析异常");
				Toast.makeText(RegistActivity.this, "服务器错误，请稍后再试！", Toast.LENGTH_SHORT).show();
				return;
			}
			if (ResultStateCode.REG_GFP_SUCCESS.equals(state)) {
				//存储用户注册信息，并跳转到登录界面			
				Util.saveUserInfo(RegistActivity.this, userTel, password, null, null,null,null);
				
				Toast.makeText(RegistActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(RegistActivity.this,LoginActivity.class);
				startActivity(intent);
				RegistActivity.this.finish();
			}
			else if (ResultStateCode.REG_GFP_FAILED.equals(state)) {
				Toast.makeText(RegistActivity.this, "系统异常，注册失败，请稍后再试！", Toast.LENGTH_SHORT).show();
			}else{
				etTel.setText("");
				etPassword.setText("");
				etComfirmPsd.setText("");
				Toast.makeText(RegistActivity.this, "该手机号已注册，请输入另一手机号！", Toast.LENGTH_SHORT).show();
			}
		}	
	}
}
