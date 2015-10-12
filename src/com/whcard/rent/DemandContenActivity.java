package com.whcard.rent;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.whcard.bean.House;
import com.whcard.bean.RequirementRental;
import com.whcard.main.R;
import com.whcard.net.GetUrl;
import com.whcard.net.HttpUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DemandContenActivity extends Activity {

	private TextView content_house_demand_title;
	private TextView content_house_demand_rental;
	private TextView content_house_demand_time;
	private TextView content_house_demand_type;
	private TextView content_house_demand_way;
	private TextView content_house_demand_address;
	private TextView content_house_demand_describle;
	private TextView content_house_demand__contactor;
	private TextView content_house_demand_tell;
	
	private Button demand_content_alter;
	
	private String demandId;
	private String isAlter;
	
	private ProgressDialog progressDialog;
	RequirementRental demand;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_demand_content);
		
		initView();
		demandId=getIntent().getStringExtra("demandId");	
		isAlter=getIntent().getStringExtra("isAlter");
		
		MyAsyncTask myAsyncTask =new MyAsyncTask();
		myAsyncTask.execute();
		//initData();
	}
	private void alterDemand() {
		if ("true".equals(isAlter)) {
			demand_content_alter.setVisibility(View.VISIBLE);
			demand_content_alter.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					//跳转到发布界面，并使用bundle将housecontent发送过去
					Intent intent=new Intent(DemandContenActivity.this,PublishDemanActivity.class);
					Bundle bundle=new Bundle();
					bundle.putSerializable("demandContent", demand);
					intent.putExtras(bundle);
					startActivity(intent);
					DemandContenActivity.this.finish();
					MyDemandActivity.instance.finish();
				}
			});
		}
	}
	
	/*private void backOnClick(View view){
		onBackPressed();
	}*/
	
	private void initView() {
		content_house_demand_title=(TextView) findViewById(R.id.content_house_demand_title);
		content_house_demand_rental=(TextView) findViewById(R.id.content_house_demand_rental);
		content_house_demand_time=(TextView) findViewById(R.id.content_house_demand_time);
		content_house_demand_type=(TextView) findViewById(R.id.content_house_demand_type);
		content_house_demand_way=(TextView) findViewById(R.id.content_house_demand_way);
		content_house_demand_address=(TextView) findViewById(R.id.content_house_demand_address);
		content_house_demand_describle=(TextView) findViewById(R.id.content_house_demand_describle);
		content_house_demand__contactor=(TextView) findViewById(R.id.content_house_demand__contactor);
		content_house_demand_tell=(TextView) findViewById(R.id.content_house_demand_tell);
		
		demand_content_alter=(Button) findViewById(R.id.demand_content_alter);
	}

	private void initData() {
		content_house_demand_title.setText(demand.getRd_title());
		content_house_demand_rental.setText(demand.getRd_rental()+"");
		content_house_demand_time.setText(demand.getRd_pubtime());
		content_house_demand_type.setText(demand.getRd_type());
		content_house_demand_way.setText(demand.getRd_way());
		content_house_demand_address.setText(demand.getRd_address());
		content_house_demand_describle.setText(demand.getRd_describle());
		content_house_demand__contactor.setText(demand.getRd_contactor());
		content_house_demand_tell.setText(demand.getRd_tel());
	}
	private class MyAsyncTask extends AsyncTask<String, Integer, String>{		
		@Override
		protected void onPreExecute() {
			//获取需求id
			progressDialog=ProgressDialog.show(DemandContenActivity.this, "", "正在请求，请稍等。。。");
		}
		@Override
		protected String doInBackground(String... params) {
			String httpReturn=HttpUtil.httpClient(GetUrl.GetDenandContent, demandId);
			return httpReturn;
		}
		@Override
		protected void onPostExecute(String result) {			
			//取消正在登录显示框		
			progressDialog.dismiss();
			if ("serverweb is error".equals(result)) {
				Toast.makeText(DemandContenActivity.this, "服务器异常！请稍后重试。。。", Toast.LENGTH_SHORT).show();
			} else if ("webserver is stop".equals(result)) {
				Toast.makeText(DemandContenActivity.this, "服务器停止运行！请稍后重试。。。", Toast.LENGTH_SHORT).show();
			}else{							
				String resultState=new String();
				//JSONObject houseContent=null;
				
				JSONObject jsonObject=null;
				try {
					jsonObject=new JSONObject(result);
					resultState=jsonObject.getString("state");			
				} catch (JSONException e) {
					Log.d("解析需求内容信息", "数据解析异常");
				}
				//如果resultState错误则提示用户，并返回
				Log.d("state", resultState);
				if ("Error".equals(resultState)) {
					Toast.makeText(DemandContenActivity.this, "服务器错误，请稍后再试！", Toast.LENGTH_SHORT).show();
					finish();
				}
				else{
					//否则，解析具体内容并显示在界面上
					try {					
						Gson gson=new Gson();		
						String jsoncontent=jsonObject.getString("content");
						Log.d("jsoncontent", jsoncontent);
						demand=gson.fromJson(jsoncontent, RequirementRental.class);
						initData();	
						alterDemand();
					} catch (JSONException e) {
						Log.d("显示需求内容", "数据解析异常");
					}				
				}
			}
		}	
	}
}
