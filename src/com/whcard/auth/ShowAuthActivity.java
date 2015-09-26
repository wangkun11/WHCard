package com.whcard.auth;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.image.SmartImageView;
import com.whcard.bean.WuhanCard;
import com.whcard.main.LoginActivity;
import com.whcard.main.MainActivity;
import com.whcard.main.R;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ShowAuthActivity extends Activity implements OnClickListener{

	private SmartImageView whcard_pic;
	private TextView whcard_id;
	private TextView whcard_name;
	private TextView whcard_gender;
	private TextView whcard_identity;
	private TextView whcard_add;
	private TextView whcard_tel;
	private TextView whcard_nation;
	private TextView whcard_naplace;
	
	private Button whcard_auth_agree;
	private Button whcard_auth_disagree;
	
	WuhanCard whCard;
	ProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_show_auth);

		initView();
		// 先接收修改信息发送过来的bundle
		Bundle bundle = getIntent().getExtras();
		whCard=(WuhanCard) bundle.getSerializable("wuhanCard");
		initData();
		
		whcard_auth_agree.setOnClickListener(this);
		whcard_auth_disagree.setOnClickListener(this);
	}

	private void initView() {
		whcard_pic=(SmartImageView) findViewById(R.id.whcard_pic);
		whcard_id=(TextView) findViewById(R.id.whcard_id);
		whcard_name=(TextView) findViewById(R.id.whcard_name);
		whcard_gender=(TextView) findViewById(R.id.whcard_gender);
		whcard_identity=(TextView) findViewById(R.id.whcard_identity);
		whcard_add=(TextView) findViewById(R.id.whcard_add);
		whcard_tel=(TextView) findViewById(R.id.whcard_tel);
		whcard_nation=(TextView) findViewById(R.id.whcard_nation);
		whcard_naplace=(TextView) findViewById(R.id.whcard_naplace);
		
		whcard_auth_agree=(Button) findViewById(R.id.whcard_auth_agree);
		whcard_auth_disagree=(Button) findViewById(R.id.whcard_auth_disagree);
	}

	private void initData() {
		whcard_pic.setImageUrl(whCard.getWhcard_pic());;
		whcard_id.setText(whCard.getWhcard_id()+"");
		whcard_name.setText(whCard.getWhcard_name());
		whcard_gender.setText(whCard.getWhcard_gender());
		whcard_identity.setText(whCard.getWhcard_identity());
		whcard_add.setText(whCard.getWhcard_add());
		whcard_tel.setText(whCard.getWhcard_tel());
		whcard_nation.setText(whCard.getWhcard_nation());
		whcard_naplace.setText(whCard.getWhcard_naplace());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.whcard_auth_agree:
			AuthAgreeAsyncTask agreeAsyncTask=new AuthAgreeAsyncTask();
			agreeAsyncTask.execute();
			break;
        case R.id.whcard_auth_disagree:
			Intent intent=new Intent(ShowAuthActivity.this,AuthDisagreeActivity.class);
			intent.putExtra("whcard_id", whCard.getSfp_id());
			intent.putExtra("auther_id", Util.getUserInfo(ShowAuthActivity.this, "userId"));
			
			startActivity(intent);
			break;

		default:
			break;
		}
	}
	
	private class AuthAgreeAsyncTask extends AsyncTask<String, Integer, String>{
		
		private String jsonString;
		@Override
		protected void onPreExecute() {
			progressDialog=ProgressDialog.show(ShowAuthActivity.this, "", "正在认证登录，请稍等。。。");	
			JSONObject authObject=new JSONObject();
			try {
				authObject.put("auther_id", Util.getUserInfo(ShowAuthActivity.this, "userId"));
				authObject.put("whcard_id", whCard.getSfp_id());
				
				authObject.put("result", "agree");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}					
			jsonString = authObject.toString();
		}	
		@Override
		protected String doInBackground(String... params) {
			String httpReturn=HttpUtil.httpClient(GetUrl.AuthAgree, jsonString);
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
					Intent intent = new Intent(ShowAuthActivity.this, AuthActivity.class);
					startActivity(intent);
					finish();		
				} else {
					toast("认证失败！");
				}
			}		
		}	
	}
	public void toast(String toast) {
		Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
	}
}
