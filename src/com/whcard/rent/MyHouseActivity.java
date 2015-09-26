package com.whcard.rent;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.whcard.adapter.HouseSourceAdapter;
import com.whcard.bean.HouseItemBean;
import com.whcard.main.R;
import com.whcard.main.R.layout;
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
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MyHouseActivity extends Activity implements OnItemClickListener,
		ILoadListener {

	private List<HouseItemBean> list = new ArrayList<HouseItemBean>();
	private LoadListView listView;
	HouseSourceAdapter hsAdapter;

	private int refreshcount = -1;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_house);

		initDate();
	}

	private void showListView() {
		if (hsAdapter == null) {
			listView = (LoadListView) findViewById(R.id.rent_myhouse_listview);
			hsAdapter = new HouseSourceAdapter(this, list);

			listView.setInterface(this);
			listView.setAdapter(hsAdapter);
			listView.setOnItemClickListener(this);
		} else {
			hsAdapter.onDateChange(list);
		}

	}

	private void initDate() {
		MyAsyncTask myAsyncTask = new MyAsyncTask();
		myAsyncTask.execute();
		/*
		 * for (int i = 0; i < 10; i++) { HouseItemBean houseBean=new
		 * HouseItemBean(); houseBean.setHouse_item_title("[����] [����] [�ҵ���ȫ]");
		 * houseBean.setHouse_item_add("³��-���������ⷿ");
		 * houseBean.setHouse_item_rental("1200"); list.add(houseBean); }
		 */
	}

	private void getMoreDate() {
		MyAsyncTask myAsyncTask = new MyAsyncTask();
		myAsyncTask.execute();
		/*
		 * for (int i = 0; i < 5; i++) { HouseItemBean houseBean=new
		 * HouseItemBean(); houseBean.setHouse_item_title("�������");
		 * houseBean.setHouse_item_add("³��-���������ⷿ");
		 * houseBean.setHouse_item_rental("1200"); list.add(houseBean); }
		 */
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		int n = (int) listView.getItemIdAtPosition(position);
		String houseId = list.get(n).getHouse_item_id();
		Intent intent = new Intent(this, HouseContentActivity.class);
		intent.putExtra("houseId", houseId);
		intent.putExtra("isAlter", "true");
		startActivity(intent);
	}

	@Override
	public void onLoad() {
		// ��ȡ��������
		getMoreDate();
		// ֪ͨlistview�������
		listView.loadComplete();
	}

	private class MyAsyncTask extends AsyncTask<String, Integer, String> {
		private String jsonString;

		@Override
		protected void onPreExecute() {
			JSONObject myHouseRequest = new JSONObject();
			refreshcount++;
			try {
				myHouseRequest.put("Id",
						Util.getUserInfo(MyHouseActivity.this, "userId"));
				myHouseRequest.put("Count", String.valueOf(refreshcount));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jsonString = myHouseRequest.toString();
			progressDialog = ProgressDialog.show(MyHouseActivity.this, "","���ڻ�ȡ��Ϣ�����Եȡ�����");
		}

		@Override
		protected String doInBackground(String... params) {
			String httpReturn = HttpUtil.httpClient(GetUrl.GetMyHouse,jsonString);
			return httpReturn;
		}

		@Override
		protected void onPostExecute(String result) {

			// ȡ�����ڵ�¼��ʾ��
			progressDialog.dismiss();
			if ("serverweb is error".equals(result)) {
				Toast.makeText(MyHouseActivity.this, "�������쳣�����Ժ����ԡ�����",Toast.LENGTH_SHORT).show();
			} else if ("webserver is stop".equals(result)) {
				Toast.makeText(MyHouseActivity.this, "������ֹͣ���У����Ժ����ԡ�����",Toast.LENGTH_SHORT).show();
			} else {
				String resultState = new String();
				JSONArray houseList = null;
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
					Toast.makeText(MyHouseActivity.this, "�������������Ժ����ԣ�",
							Toast.LENGTH_SHORT).show();
					finish();
				} else {
					// ���򣬽����������ݲ���ʾ�ڽ�����
					try {
						houseList = jsonObject.getJSONArray("content");
						if (houseList != null) {
							for (int i = 0; i < houseList.length(); i++) {
								HouseItemBean houseBean=new HouseItemBean();
								houseBean.setHouse_item_id(houseList.getJSONObject(i).getString("house_item_id"));
								houseBean.setHouse_item_img(houseList.getJSONObject(i).getString("house_item_img"));
								houseBean.setHouse_item_title(houseList.getJSONObject(i).getString("house_item_title"));
								houseBean.setHouse_item_add(houseList.getJSONObject(i).getString("house_item_add"));
								houseBean.setHouse_item_rental(houseList.getJSONObject(i).getString("house_item_rental"));
								list.add(houseBean);
							Log.d("��Դ�б�����������ݣ�", houseList.getJSONObject(i).getString("house_item_img"));
							}
							//������ص����ݣ���֪ͨ���������½���
							showListView();
							houseList=null;
						} else {
							// ������ʾ�û����ݼ������
							Toast.makeText(MyHouseActivity.this,"������ȫ�����أ�", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						Log.d("��ʾ��Դ�б�", "���ݽ����쳣");
					}
				}
			}
		}
	}
}
