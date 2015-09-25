package com.whcard.rent;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.whcard.adapter.HouseDemandAdapter;
import com.whcard.bean.DemandItemBean;
import com.whcard.bean.HouseItemBean;
import com.whcard.main.LoginActivity;
import com.whcard.main.R;
import com.whcard.myview.LoadListView;
import com.whcard.myview.LoadListView.ILoadListener;
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
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class HouseDemandActivity extends Activity implements OnItemClickListener,ILoadListener{

	private List<DemandItemBean> list;
	private LoadListView listView;
	HouseDemandAdapter hdAdapter;
	
	private int refreshcount=-1;
	private ProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_rent_demand);
		
		initDate();
		showListView();
	}
	private void showListView() {
		if (hdAdapter==null) {
			listView=(LoadListView) findViewById(R.id.rent_demand_listview);
			
			hdAdapter=new HouseDemandAdapter(list,this);
			listView.setInterface(this);
			listView.setAdapter(hdAdapter);
			listView.setOnItemClickListener(this);
		} else {
			hdAdapter.onDateChange(list);
		}
	}
	private void initDate() {
		list=new ArrayList<DemandItemBean>();
		
		MyAsyncTask myAsyncTask=new MyAsyncTask();
		myAsyncTask.execute();
		/*for (int i = 0; i < 20; i++) {
			DemandItemBean demandItemBean=new DemandItemBean();
			demandItemBean.setItem_house_demand_address("光谷-鲁巷");
			demandItemBean.setItem_house_demand_rental("1500");
			demandItemBean.setItem_house_demand_title("求租两室一厅，家电齐全");
			demandItemBean.setItem_house_demand_time("2015年8月15日");
			list.add(demandItemBean);
		}*/
	}
	
	private void getMoreDate() {
		MyAsyncTask myAsyncTask=new MyAsyncTask();
		myAsyncTask.execute();
		/*for (int i = 0; i < 5; i++) {
			DemandItemBean demandItemBean=new DemandItemBean();
			demandItemBean.setItem_house_demand_address("加载更多");
			demandItemBean.setItem_house_demand_rental("1500");
			demandItemBean.setItem_house_demand_title("求租两室一厅，家电齐全");
			demandItemBean.setItem_house_demand_time("2015年8月15日");
			list.add(demandItemBean);
		}*/
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		int n=(int) listView.getItemIdAtPosition(position);
		String demandId=list.get(n).getItem_house_demand_id();
		Intent intent = new Intent(HouseDemandActivity.this, DemandContenActivity.class);
		intent.putExtra("demandId", demandId);
		Log.d("demandId", demandId);
		startActivity(intent);
	}
	@Override
	public void onLoad() {
		getMoreDate();
		//更新listview显示；
		showListView();
		//通知listview加载完毕
		listView.loadComplete();
	}
	private class MyAsyncTask extends AsyncTask<String, Integer, String>{		
		private String jsonString;
		@Override
		protected void onPreExecute() {
			progressDialog=ProgressDialog.show(HouseDemandActivity.this, "", "正在请求，请稍等。。。");
			refreshcount++;
			jsonString = String.valueOf(refreshcount);
		}
		@Override
		protected String doInBackground(String... params) {
			String httpReturn=HttpUtil.httpClient(GetUrl.GetDemandList, jsonString);
			return httpReturn;
		}
		@Override
		protected void onPostExecute(String result) {
			//取消正在登录显示框		
			progressDialog.dismiss();
			if ("serverweb is error".equals(result)) {
				Toast.makeText(HouseDemandActivity.this, "服务器异常！请稍后重试。。。", Toast.LENGTH_SHORT).show();
			} else if ("webserver is stop".equals(result)) {
				Toast.makeText(HouseDemandActivity.this, "服务器停止运行！请稍后重试。。。", Toast.LENGTH_SHORT).show();
			}else{							
				String resultState=new String();
				JSONArray demandList=null;
				JSONObject jsonObject=null;
				try {
					jsonObject=new JSONObject(result);
					resultState=jsonObject.getString("state");			
				} catch (JSONException e) {
					Log.d("解析需求列表信息", "数据解析异常");
				}
				//如果resultState错误则提示用户，并返回
				Log.d("state", resultState);
				if ("Error".equals(resultState)) {
					Toast.makeText(HouseDemandActivity.this, "服务器错误，请稍后再试！", Toast.LENGTH_SHORT).show();
					finish();
				}
				else{	
					//否则，解析具体内容并显示在界面上
					try {
						demandList=jsonObject.getJSONArray("content");
						for (int i = 0; i < demandList.length(); i++) {
							DemandItemBean demandBean=new DemandItemBean();
							demandBean.setItem_house_demand_address(
									demandList.getJSONObject(i).getString("item_house_demand_address"));
							demandBean.setItem_house_demand_id(
									demandList.getJSONObject(i).getString("item_house_demand_id"));
							demandBean.setItem_house_demand_rental(
									demandList.getJSONObject(i).getString("item_house_demand_rental"));
							demandBean.setItem_house_demand_time(
									demandList.getJSONObject(i).getString("item_house_demand_time"));
							demandBean.setItem_house_demand_title(
									demandList.getJSONObject(i).getString("item_house_demand_title"));
							list.add(demandBean);
						Log.d("房源列表解析到的数据：", demandList.getJSONObject(i).getString("item_house_demand_title"));
						}
					} catch (JSONException e) {
						Log.d("显示需求列表", "数据解析异常");
					}				
				}
			}
		showListView();		
		}	
	}
	
}
