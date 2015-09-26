package com.whcard.auth;

import org.json.JSONException;
import org.json.JSONObject;

import com.whcard.main.R;
import com.whcard.main.R.id;
import com.whcard.main.R.layout;
import com.whcard.net.GetUrl;
import com.whcard.net.HttpUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AuthDisagreeActivity extends Activity {

	private EditText auth_disagree_content;
	private Button auth_disagree_submit;
	
	private ProgressDialog progressDialog;
	private String auther_id;
	private String whcard_id;
	private String content;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_auth_disagree);
		
		auth_disagree_content=(EditText) findViewById(R.id.auth_disagree_content);
		auth_disagree_submit=(Button) findViewById(R.id.auth_disagree_submit);
		
		auther_id=getIntent().getStringExtra("auther_id");
		whcard_id=getIntent().getStringExtra("whcard_id");
		
		auth_disagree_submit.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				AuthDisagreeAsyncTask disagreeAsyncTask=new AuthDisagreeAsyncTask();
				disagreeAsyncTask.execute();				
			}
		});
	}
	
private class AuthDisagreeAsyncTask extends AsyncTask<String, Integer, String>{
		String jsonString;
		@Override
		protected void onPreExecute() {
			progressDialog=ProgressDialog.show(AuthDisagreeActivity.this, "", "正在认证登录，请稍等。。。");	
			content=auth_disagree_content.getText().toString();
			JSONObject authObject=new JSONObject();
			try {
				authObject.put("auther_id", auther_id);
				authObject.put("whcard_id", whcard_id);
				authObject.put("result", "disagree");
				authObject.put("content", content);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}					
			jsonString = authObject.toString();
		}	
		@Override
		protected String doInBackground(String... params) {
			String httpReturn=HttpUtil.httpClient(GetUrl.AuthDisagree, jsonString);
			return httpReturn;
		}
		@Override
		protected void onPostExecute(String result) {	
			progressDialog.dismiss();
			
			if ("webserver is stop".equals(result)) {
				toast("服务器停止运行！请稍后登录。。。");
			}else if ("webserver is error".equals(result)) {
				toast("服务器内部异常！请稍后登录。。。");
			}else{
				JSONObject jsonObject = null;
				String state=null;
				try {
					jsonObject = new JSONObject(result);
					state = jsonObject.getString("state");
				} catch (JSONException e) {
					Log.d("解析认证结果信息", "数据解析异常");
				}
				Log.d("state", state);
				if ("Success".equals(state)) {			
					Intent intent = new Intent(AuthDisagreeActivity.this, AuthActivity.class);
					startActivity(intent);
					finish();		
				} else {
					toast("提交失败！");
				}
			}		
		}	
	}
	public void toast(String toast) {
		Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
	}
}
