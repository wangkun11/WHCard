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
 * ע�����
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
					toast("�������Ӳ���ȷ������!");
				} else if (password.equals("")|| comfirmPsd.equals("") || userTel.equals("")) {
					Log.d("ע����Ϣ", password+"||"+comfirmPsd+"||"+userTel);
					toast("ע����Ϣ����������������д!");
				} else if (!comfirmPsd.equals(password)) {
					toast("�������������벻һ�£���ȷ��");
				} else if(!Util.isPhoneNumberValid(userTel)) {
					toast("��������ȷ���ֻ�����");
				}else {
					Util.saveUserInfo(RegistActivity.this, userTel, password, null, null,null,null);
					
					/*Intent intent = new Intent(RegistActivity.this,LoginActivity.class);
					startActivity(intent);
					RegistActivity.this.finish();*/
					// ��ʼ�ύע����Ϣ				
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
			progressDialog=ProgressDialog.show(RegistActivity.this, "", "����ע�ᣬ���Եȡ�����");
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
				Log.d("ע��Json����","���ݽ����쳣");
				Toast.makeText(RegistActivity.this, "�������������Ժ����ԣ�", Toast.LENGTH_SHORT).show();
				return;
			}
			if (ResultStateCode.REG_GFP_SUCCESS.equals(state)) {
				//�洢�û�ע����Ϣ������ת����¼����			
				Util.saveUserInfo(RegistActivity.this, userTel, password, null, null,null,null);
				
				Toast.makeText(RegistActivity.this, "ע��ɹ�", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(RegistActivity.this,LoginActivity.class);
				startActivity(intent);
				RegistActivity.this.finish();
			}
			else if (ResultStateCode.REG_GFP_FAILED.equals(state)) {
				Toast.makeText(RegistActivity.this, "ϵͳ�쳣��ע��ʧ�ܣ����Ժ����ԣ�", Toast.LENGTH_SHORT).show();
			}else{
				etTel.setText("");
				etPassword.setText("");
				etComfirmPsd.setText("");
				Toast.makeText(RegistActivity.this, "���ֻ�����ע�ᣬ��������һ�ֻ��ţ�", Toast.LENGTH_SHORT).show();
			}
		}	
	}
}
