package com.whcard.rent;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.whcard.adapter.HouseSourceAdapter;
import com.whcard.bean.HouseItemBean;
import com.whcard.info.ShowInfoActivity;
import com.whcard.main.R;
import com.whcard.myview.LoadListView;
import com.whcard.myview.LoadListView.ILoadListener;
import com.whcard.net.GetUrl;
import com.whcard.net.HttpUtil;
import com.whcard.util.MyJsonObject;
import com.whcard.util.ResultStateCode;

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

public class HouseSourceActivity extends Activity implements OnItemClickListener,ILoadListener{
	private List<HouseItemBean> list=new ArrayList<HouseItemBean>();
	private LoadListView listView;
	HouseSourceAdapter hsAdapter;
	
	private int refreshcount=-1;
	private ProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_rent_house);
		initDate();
	}
	private void showListView() {
		if (hsAdapter==null) {
			listView=(LoadListView) findViewById(R.id.rent_source_listview);
			hsAdapter=new HouseSourceAdapter(this, list);
			
			listView.setInterface(this);
			listView.setAdapter(hsAdapter);
			listView.setOnItemClickListener(this);
		} else {
			hsAdapter.onDateChange(list);
		}
		
	}

	private void initDate() {		
		MyAsyncTask myAsyncTask=new MyAsyncTask();
		myAsyncTask.execute();
		/*for (int i = 0; i < 10; i++) {
			HouseItemBean houseBean=new HouseItemBean();
			houseBean.setHouse_item_title("[单间] [整租] [家电齐全]");
			houseBean.setHouse_item_add("鲁巷-吴家湾大厦租房");
			houseBean.setHouse_item_rental("1200");
			list.add(houseBean);
		}*/
	}

	private void getMoreDate() {
		MyAsyncTask myAsyncTask=new MyAsyncTask();
		myAsyncTask.execute();
		/*for (int i = 0; i < 5; i++) {
			HouseItemBean houseBean=new HouseItemBean();
			houseBean.setHouse_item_title("更多加载");
			houseBean.setHouse_item_add("鲁巷-吴家湾大厦租房");
			houseBean.setHouse_item_rental("1200");
			list.add(houseBean);
		}*/
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		int n=(int) listView.getItemIdAtPosition(position);
		String houseId=list.get(n).getHouse_item_id();
		Intent intent = new Intent(this, HouseContentActivity.class);
		Log.d("i的值", n+"");
		Log.d("houseId", houseId);
		intent.putExtra("houseId", houseId);
		intent.putExtra("isAlter", "false");
		startActivity(intent);		
	}
	@Override
	public void onLoad() {
		//获取更多数据
		getMoreDate();
		//通知listview加载完毕
		listView.loadComplete();
	}
	private class MyAsyncTask extends AsyncTask<String, Integer, String>{		
		private String jsonString;
		@Override
		protected void onPreExecute() {
			refreshcount++;
			jsonString = String.valueOf(refreshcount);
			progressDialog=ProgressDialog.show(HouseSourceActivity.this, "", "正在获取房源列表，请稍等。。。");
		}
		@Override
		protected String doInBackground(String... params) {
			String httpReturn=HttpUtil.httpClient(GetUrl.GetHouseList, jsonString);
			return httpReturn;
		}
		@Override
		protected void onPostExecute(String result) {
			
			//取消正在登录显示框		
			progressDialog.dismiss();
			if ("serverweb is error".equals(result)) {
				Toast.makeText(HouseSourceActivity.this, "服务器异常！请稍后重试。。。", Toast.LENGTH_SHORT).show();
			} else if ("webserver is stop".equals(result)) {
				Toast.makeText(HouseSourceActivity.this, "服务器停止运行！请稍后重试。。。", Toast.LENGTH_SHORT).show();
			}else{							
				String resultState=new String();
				JSONArray houseList=null;
				JSONObject jsonObject=null;
				try {
					jsonObject=new JSONObject(result);
					resultState=jsonObject.getString("state");			
				} catch (JSONException e) {
					Log.d("解析住房列表信息", "数据解析异常");
				}
				//如果resultState错误则提示用户，并返回
				Log.d("state", resultState);
				if ("Error".equals(resultState)) {
					Toast.makeText(HouseSourceActivity.this, "服务器错误，请稍后再试！", Toast.LENGTH_SHORT).show();
					finish();
				}
				else{	
					//否则，解析具体内容并显示在界面上
					try {
						houseList=jsonObject.getJSONArray("content");
						if (houseList!=null) {
							for (int i = 0; i < houseList.length(); i++) {
								HouseItemBean houseBean=new HouseItemBean();
								houseBean.setHouse_item_id(houseList.getJSONObject(i).getString("house_item_id"));
								houseBean.setHouse_item_img(houseList.getJSONObject(i).getString("house_item_img"));
								houseBean.setHouse_item_title(houseList.getJSONObject(i).getString("house_item_title"));
								houseBean.setHouse_item_add(houseList.getJSONObject(i).getString("house_item_add"));
								houseBean.setHouse_item_rental(houseList.getJSONObject(i).getString("house_item_rental"));
								list.add(houseBean);
							Log.d("房源列表解析到的图片路径：", houseList.getJSONObject(i).getString("house_item_img"));
							}
							//如果加载到数据，则通知适配器更新界面
							showListView();
							houseList=null;
						}else {
							//否则提示用户数据加载完毕
							Toast.makeText(HouseSourceActivity.this, "数据已全部加载！", Toast.LENGTH_SHORT).show();
						}					
					} catch (JSONException e) {
						Log.d("显示房源列表", "数据解析异常");
					}				
				}
			}
		}	
	}	
}
