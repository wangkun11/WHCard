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
			houseBean.setHouse_item_title("[����] [����] [�ҵ���ȫ]");
			houseBean.setHouse_item_add("³��-���������ⷿ");
			houseBean.setHouse_item_rental("1200");
			list.add(houseBean);
		}*/
	}

	private void getMoreDate() {
		MyAsyncTask myAsyncTask=new MyAsyncTask();
		myAsyncTask.execute();
		/*for (int i = 0; i < 5; i++) {
			HouseItemBean houseBean=new HouseItemBean();
			houseBean.setHouse_item_title("�������");
			houseBean.setHouse_item_add("³��-���������ⷿ");
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
		Log.d("i��ֵ", n+"");
		Log.d("houseId", houseId);
		intent.putExtra("houseId", houseId);
		intent.putExtra("isAlter", "false");
		startActivity(intent);		
	}
	@Override
	public void onLoad() {
		//��ȡ��������
		getMoreDate();
		//֪ͨlistview�������
		listView.loadComplete();
	}
	private class MyAsyncTask extends AsyncTask<String, Integer, String>{		
		private String jsonString;
		@Override
		protected void onPreExecute() {
			refreshcount++;
			jsonString = String.valueOf(refreshcount);
			progressDialog=ProgressDialog.show(HouseSourceActivity.this, "", "���ڻ�ȡ��Դ�б����Եȡ�����");
		}
		@Override
		protected String doInBackground(String... params) {
			String httpReturn=HttpUtil.httpClient(GetUrl.GetHouseList, jsonString);
			return httpReturn;
		}
		@Override
		protected void onPostExecute(String result) {
			
			//ȡ�����ڵ�¼��ʾ��		
			progressDialog.dismiss();
			if ("serverweb is error".equals(result)) {
				Toast.makeText(HouseSourceActivity.this, "�������쳣�����Ժ����ԡ�����", Toast.LENGTH_SHORT).show();
			} else if ("webserver is stop".equals(result)) {
				Toast.makeText(HouseSourceActivity.this, "������ֹͣ���У����Ժ����ԡ�����", Toast.LENGTH_SHORT).show();
			}else{							
				String resultState=new String();
				JSONArray houseList=null;
				JSONObject jsonObject=null;
				try {
					jsonObject=new JSONObject(result);
					resultState=jsonObject.getString("state");			
				} catch (JSONException e) {
					Log.d("����ס���б���Ϣ", "���ݽ����쳣");
				}
				//���resultState��������ʾ�û���������
				Log.d("state", resultState);
				if ("Error".equals(resultState)) {
					Toast.makeText(HouseSourceActivity.this, "�������������Ժ����ԣ�", Toast.LENGTH_SHORT).show();
					finish();
				}
				else{	
					//���򣬽����������ݲ���ʾ�ڽ�����
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
							Log.d("��Դ�б��������ͼƬ·����", houseList.getJSONObject(i).getString("house_item_img"));
							}
							//������ص����ݣ���֪ͨ���������½���
							showListView();
							houseList=null;
						}else {
							//������ʾ�û����ݼ������
							Toast.makeText(HouseSourceActivity.this, "������ȫ�����أ�", Toast.LENGTH_SHORT).show();
						}					
					} catch (JSONException e) {
						Log.d("��ʾ��Դ�б�", "���ݽ����쳣");
					}				
				}
			}
		}	
	}	
}
