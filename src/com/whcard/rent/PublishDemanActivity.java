package com.whcard.rent;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.whcard.bean.House;
import com.whcard.bean.PublishDemandBean;
import com.whcard.bean.RequirementRental;
import com.whcard.main.R;
import com.whcard.net.GetUrl;
import com.whcard.net.HttpUtil;
import com.whcard.util.Util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PublishDemanActivity extends Activity implements OnClickListener{
	
	//还有部分控件有待完善
	private EditText publish_demand_address;
	private EditText publish_demand_type;
	private EditText publish_demand_way;
	private EditText publish_demand_rental;
	private EditText publish_demand_title;
	private EditText publish_demand_describle;
	private EditText publish_demand_contactor;
	private EditText publish_demand_tell;
	
	private Button publish_demand_submit;
	
	
	private PublishDemandBean demandContentBean;
	ProgressDialog progressDialog=null;
	String jsonString;
	
	private RequirementRental demand;
	private Boolean flag=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_publish_demand);
		
		initView();
		//先接收修改信息发送过来的demand
		demand = (RequirementRental) getIntent().getSerializableExtra("demandContent");
		// 如果不为空，则填充到控件上
		if (demand != null) {
			flag=true;
			initData();
		}else {
			Log.d("修改需求时，获取到的bundle:", "bundle为空！");
		}	
		publish_demand_submit.setOnClickListener(this);
	}
	private void initData() {
		//填充控件内容		
		publish_demand_address.setText(demand.getRd_address());
		publish_demand_type.setText(demand.getRd_type());
		publish_demand_way.setText(demand.getRd_way());
		publish_demand_rental.setText(demand.getRd_rental()+"");
		publish_demand_title.setText(demand.getRd_title());
		publish_demand_describle.setText(demand.getRd_describle());
		publish_demand_contactor.setText(demand.getRd_contactor());
		publish_demand_tell.setText(demand.getRd_tel());	
		
		Log.d("修改需求时，获取到的bundle:", demand.getRd_title());
	}
	
	private void getViewData() {
		demandContentBean=new PublishDemandBean();
		
		demandContentBean.setPublish_demand_userid(Util.getUserInfo(this, "userId"));
		
		demandContentBean.setPublish_demand_address(publish_demand_address.getText().toString());
		demandContentBean.setPublish_demand_contactor(publish_demand_contactor.getText().toString());
		demandContentBean.setPublish_demand_describle(publish_demand_describle.getText().toString());
		demandContentBean.setPublish_demand_rental(publish_demand_rental.getText().toString());
		demandContentBean.setPublish_demand_tell(publish_demand_tell.getText().toString());
		demandContentBean.setPublish_demand_title(publish_demand_title.getText().toString());
		demandContentBean.setPublish_demand_type(publish_demand_type.getText().toString());
		demandContentBean.setPublish_demand_way(publish_demand_way.getText().toString());
	}

	private void initView() {
		publish_demand_address=(EditText)findViewById(R.id.publish_demand_address);
		publish_demand_type=(EditText) findViewById(R.id.publish_demand_type);
		publish_demand_way=(EditText) findViewById(R.id.publish_demand_way);
		publish_demand_rental=(EditText) findViewById(R.id.publish_demand_rental);
		publish_demand_title=(EditText) findViewById(R.id.publish_demand_title);
		publish_demand_describle=(EditText) findViewById(R.id.publish_demand_describle);
		publish_demand_contactor=(EditText) findViewById(R.id.publish_demand_contactor);
		publish_demand_tell=(EditText) findViewById(R.id.publish_demand_tell);
		
		publish_demand_submit=(Button) findViewById(R.id.publish_demand_submit);
		
	}

	@Override
	public void onClick(View v) {
		//向服务发送数据，如果发送成功则跳转找需求列表界面，否则弹出发送失败提示
		MyAsyncTask myAsyncTask=new MyAsyncTask();
		myAsyncTask.execute();
	}
	class MyAsyncTask extends AsyncTask<String, Integer, String>
	{

		@Override
		protected void onPreExecute() {
			//准备Json字符串，进度提示框
			Gson gson=new Gson();
			getViewData();
			jsonString = gson.toJson(demandContentBean);
			progressDialog=ProgressDialog.show(PublishDemanActivity.this, "", "正在发布，请稍等。。。");
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String httpReturn;
			if (flag) {
				httpReturn=HttpUtil.httpClient(GetUrl.UpdateDemand, jsonString);
			}else {
				httpReturn=HttpUtil.httpClient(GetUrl.PublishDemand, jsonString);
			}			
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
			} catch (JSONException e) {
				Log.d("json字符串解析异常", "json字符串解析异常");
			}
			Log.d("State", state);
			//如果需求发布成功，则跳转到租房需求界面,发布需求按钮变为黑色，租房需求按钮变为绿色
			if (state.equals("Success")) {			
				Toast.makeText(PublishDemanActivity.this, "住房需求发布成功！", Toast.LENGTH_SHORT).show();			
				Intent intent=new Intent(PublishDemanActivity.this,MyDemandActivity.class);
				startActivity(intent);
				PublishDemanActivity.this.finish();
			}//否则提示需求发布失败
			else{
				Toast.makeText(PublishDemanActivity.this, "住房需求发布失败", Toast.LENGTH_SHORT).show();
			}
		}	
	}
}
