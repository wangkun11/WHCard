package com.whcard.main;

import com.whcard.util.Util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ResetPsdActivity extends Activity implements OnClickListener{
	
	
	@SuppressWarnings("unused")
	private static final String TAG = "ResetPsdActivity";
	
	private EditText etVerifiedEmail;
	private Button btnSendEmail;
	
	private RelativeLayout rlResetPsdFinished;
	private Button btnBackToLogin;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_reset_psd);
		
		initView();
	}
	
	private void initView()
	{
		etVerifiedEmail = (EditText) findViewById(R.id.reset_email);
		btnSendEmail = (Button) findViewById(R.id.reset_send_email);
		
		rlResetPsdFinished = (RelativeLayout) findViewById(R.id.rl_reset_psd_finished);
		btnBackToLogin = (Button) findViewById(R.id.btn_back_to_login);
		
		//btnSendEmail.setOnClickListener(this);
		btnBackToLogin.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.reset_send_email:
			String email = etVerifiedEmail.getText().toString();
			if(email.equals(""))
			{
				toast("�����������ַ");
			}
			else if(!Util.isEmailValid(email)) {
				toast("��������Ч�������ַ");
			}
			else {
				sendVerifiedEmail(email);
			}
			break;
			
		case R.id.btn_back_to_login:
			Intent toLoginActivity = new Intent(ResetPsdActivity.this, LoginActivity.class);
			startActivity(toLoginActivity);
			break;
		default:
			break;
		}
		
	}
	
	//�һ����룺�����䷢����֤��Ϣ
	private void sendVerifiedEmail(final String emailAddress)
	{
		
	}
	
	private void toast(String toast)
	{
		Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
	}
}
