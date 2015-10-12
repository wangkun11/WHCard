package com.whcard.rent;


import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.whcard.adapter.HouseDemandAdapter;
import com.whcard.bean.DemandItemBean;
import com.whcard.bean.HouseItemBean;
import com.whcard.main.R;
import com.whcard.myview.LoadListView;
import com.whcard.myview.LoadListView.ILoadListener;
import com.whcard.net.GetUrl;
import com.whcard.net.HttpUtil;
import com.whcard.util.Util;

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

public class MyDemandActivity extends Activity implements OnItemClickListener,ILoadListener{

	private List<DemandItemBean> list;
	private LoadListView listView;
	HouseDemandAdapter hdAdapter;
	
	private int refreshcount=-1;
	public ProgressDialog progressDialog;
	public static MyDemandActivity instance;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_my_demand);
		instance=this;
		initDate();
		
	}
	private void showListView() {
		if (hdAdapter==null) {
			listView=(LoadListView) findViewById(R.id.rent_mydemand_listview);
			
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
			demandItemBean.setItem_house_demand_address("���-³��");
			demandItemBean.setItem_house_demand_rental("1500");
			demandItemBean.setItem_house_demand_title("��������һ�����ҵ���ȫ");
			demandItemBean.setItem_house_demand_time("2015��8��15��");
			list.add(demandItemBean);
		}*/
	}
	
	private void getMoreDate() {
		MyAsyncTask myAsyncTask=new MyAsyncTask();
		myAsyncTask.execute();
		/*for (int i = 0; i < 5; i++) {
			DemandItemBean demandItemBean=new DemandItemBean();
			demandItemBean.setItem_house_demand_address("���ظ���");
			demandItemBean.setItem_house_demand_rental("1500");
			demandItemBean.setItem_house_demand_title("��������һ�����ҵ���ȫ");
			demandItemBean.setItem_house_demand_time("2015��8��15��");
			list.add(demandItemBean);
		}*/
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		int n=(int) listView.getItemIdAtPosition(position);
		String demandId=list.get(n).getItem_house_demand_id();
		Intent intent = new Intent(MyDemandActivity.this, DemandContenActivity.class);
		intent.putExtra("demandId", demandId);
		intent.putExtra("isAlter", "true");
		startActivity(intent);
	}
	@Override
	public void onLoad() {
		getMoreDate();
		//����listview��ʾ��
		showListView();
		//֪ͨlistview�������
		listView.loadComplete();
	}
	private class MyAsyncTask extends AsyncTask<String, Integer, String>{		
		private String jsonString;
		@Override
		protected void onPreExecute() {
			JSONObject myDemandRequest = new JSONObject();
			refreshcount++;
			try {
				myDemandRequest.put("Id",
						Util.getUserInfo(MyDemandActivity.this, "userId"));
				myDemandRequest.put("Count", String.valueOf(refreshcount));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jsonString = myDemandRequest.toString();
			progressDialog = ProgressDialog.show(MyDemandActivity.this, "","���ڻ�ȡ��Ϣ�����Եȡ�����");
		}

		@Override
		protected String doInBackground(String... params) {
			String httpReturn = HttpUtil.httpClient(GetUrl.GetMyDemand,jsonString);
			return httpReturn;
		}

		@Override
		protected void onPostExecute(String result) {

			// ȡ�����ڵ�¼��ʾ��
			progressDialog.dismiss();
			if ("serverweb is error".equals(result)) {
				Toast.makeText(MyDemandActivity.this, "�������쳣�����Ժ����ԡ�����",Toast.LENGTH_SHORT).show();
			} else if ("webserver is stop".equals(result)) {
				Toast.makeText(MyDemandActivity.this, "������ֹͣ���У����Ժ����ԡ�����",Toast.LENGTH_SHORT).show();
			} else {
				String resultState = new String();
				JSONArray demandList = null;
				JSONObject jsonObject = null;
				try {
					jsonObject = new JSONObject(result);
					resultState = jsonObject.getString("state");
				} catch (JSONException e) {
					Log.d("����ס���б���Ϣ", "���ݽ����쳣");
				}
				// ���resultState��������ʾ�û���������
				Log.d("state", resultState);
				if ("Error".equals(resultState)) {
					Toast.makeText(MyDemandActivity.this, "�������������Ժ����ԣ�",
							Toast.LENGTH_SHORT).show();
					finish();
				} else {
					// ���򣬽����������ݲ���ʾ�ڽ�����
					try {
						demandList = jsonObject.getJSONArray("content");
						if (demandList != null) {
							for (int i = 0; i < demandList.length(); i++) {
								DemandItemBean demandBean=new DemandItemBean();
								demandBean.setItem_house_demand_address(demandList.getJSONObject(i).getString("item_house_demand_address"));
								demandBean.setItem_house_demand_id(demandList.getJSONObject(i).getString("item_house_demand_id"));
								demandBean.setItem_house_demand_rental(demandList.getJSONObject(i).getString("item_house_demand_rental"));
								demandBean.setItem_house_demand_time(demandList.getJSONObject(i).getString("item_house_demand_time"));
								demandBean.setItem_house_demand_title(demandList.getJSONObject(i).getString("item_house_demand_title"));
								list.add(demandBean);
							}
							//������ص����ݣ���֪ͨ���������½���
							Log.d("�ҵ�����", list.size()+"��");
							showListView();
							demandList=null;
						} else {
							// ������ʾ�û����ݼ������
							Toast.makeText(MyDemandActivity.this,"������ȫ�����أ�", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						Log.d("��ʾ�����б�", "���ݽ����쳣");
					}
				}
			}
		}
	}
	
}
