package com.whcard.rent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.loopj.android.image.SmartImageView;
import com.whcard.bean.House;
import com.whcard.bean.HouseItemBean;
import com.whcard.bean.PublishHouseBean;
import com.whcard.main.R;
import com.whcard.net.GetUrl;
import com.whcard.net.HttpUtil;

import android.R.string;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HouseContentActivity extends Activity implements OnClickListener{

	private SmartImageView house_content_img;
	
	private TextView house_content_title;
	private TextView house_content_rental;
	private TextView house_content_pubtime;
	private TextView house_content_type;
	private TextView house_content_area;
	private TextView house_content_floor;
	private TextView house_content_rentWay;
	private TextView house_content_decorate;
	
	//����:TableLayout����
	private TableLayout house_content_config;
	
	
	private TextView house_content_descrip;
	private TextView house_content_cell;
	private TextView house_content_add;
	private TextView house_content_sfp_name;
	private TextView house_content_sfp_tell;
	
	private Button house_content_alter;
	
	private String houseId;
	private String isAlter;
	
	private House house=null;
	private ArrayList<String> imgPathList;
	
	@Override
 	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_house_content);
		
		initView();
		//1����ȡhouseId
		houseId=getIntent().getStringExtra("houseId");
		isAlter=getIntent().getStringExtra("isAlter");
		
		//2�������б��д��ݹ�����id�������磬��ȡ�������ݣ������
		MyAsyncTask myAsyncTask=new MyAsyncTask();
		myAsyncTask.execute();
		
		
	}
	private void alterHouse() {
		if ("true".equals(isAlter)) {
			house_content_alter.setVisibility(View.VISIBLE);
			house_content_alter.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					//��ת���������棬��ʹ��bundle��housecontent���͹�ȥ
					Intent intent=new Intent(HouseContentActivity.this,PublishHouseActivity.class);
					Bundle bundle=new Bundle();
					bundle.putSerializable("houseContent",house);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			});
		}
	}
	@Override
	public void onClick(View v) {
		Intent intent=new Intent(HouseContentActivity.this,HouseImageWallActivity.class);
		Bundle bundle=new Bundle();
		bundle.putStringArrayList("imgPathList", imgPathList);
		intent.putExtras(bundle);
		startActivity(intent);
	}	
	private void initData() {
		
		house_content_title.setText(house.getHou_title());
		house_content_rental.setText(house.getHou_rental()+"");
		house_content_pubtime.setText(String.valueOf(house.getHou_pubtime()));
		house_content_type.setText(house.getHou_type());
		house_content_area.setText(house.getHou_area()+"");
		house_content_floor.setText(house.getHou_floor());
		house_content_rentWay.setText(house.getHou_rentway());
		house_content_decorate.setText(house.getHou_decorate());
		
		house_content_descrip.setText(house.getHou_descrip());
		house_content_cell.setText(house.getHou_cell());
		house_content_add.setText(house.getHou_add());
		house_content_sfp_name.setText(house.getSfp_name());
		house_content_sfp_tell.setText(house.getSfp_tel());
		
		//
	    imgPathList=house.getHou_img();
	    if (imgPathList.size()>0) {
	    	house_content_img.setImageUrl(imgPathList.get(0));
		}
	}
	private void initView() {
		house_content_img=(SmartImageView) findViewById(R.id.house_content_img);
		
		house_content_title=(TextView) findViewById(R.id.house_content_title);
		house_content_rental=(TextView) findViewById(R.id.house_content_rental);
		house_content_pubtime=(TextView) findViewById(R.id.house_content_pubtime);
		house_content_type=(TextView) findViewById(R.id.house_content_type);
		house_content_area=(TextView) findViewById(R.id.house_content_area);
		house_content_floor=(TextView) findViewById(R.id.house_content_floor);
		house_content_rentWay=(TextView) findViewById(R.id.house_content_rentWay);
		house_content_decorate=(TextView) findViewById(R.id.house_content_decorate);
		
		house_content_descrip=(TextView) findViewById(R.id.house_content_descrip);
		house_content_cell=(TextView) findViewById(R.id.house_content_cell);
		house_content_add=(TextView) findViewById(R.id.house_content_add);
		house_content_sfp_name=(TextView) findViewById(R.id.house_content_sfp_name);
		house_content_sfp_tell=(TextView) findViewById(R.id.house_content_sfp_tell);
		
		house_content_alter=(Button) findViewById(R.id.house_content_alter);
	}
	private class MyAsyncTask extends AsyncTask<String, Integer, String>{		
		private ProgressDialog progressDialog;
		@Override
		protected void onPreExecute() {
			progressDialog=ProgressDialog.show(HouseContentActivity.this, "", "���ڻ�ȡ��׼��Ϣ�����Եȡ�����");
		}
		@Override
		protected String doInBackground(String... params) {
			String httpReturn=HttpUtil.httpClient(GetUrl.GetHouseContent, houseId);
			return httpReturn;
		}
		@Override
		protected void onPostExecute(String result) {			
			//ȡ�����ڵ�¼��ʾ��		
			progressDialog.dismiss();
			if ("webserver is error".equals(result)) {
				Toast.makeText(HouseContentActivity.this, "�������쳣�����Ժ����ԡ�����", Toast.LENGTH_SHORT).show();
			} else if ("webserver is stop".equals(result)) {
				Toast.makeText(HouseContentActivity.this, "������ֹͣ���У����Ժ����ԡ�����", Toast.LENGTH_SHORT).show();
			}else{							
				String resultState=new String();
				//JSONObject houseContent=null;
				
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
					Toast.makeText(HouseContentActivity.this, "�������������Ժ����ԣ�", Toast.LENGTH_SHORT).show();
					finish();
				}
				else{
					//���򣬽����������ݲ���ʾ�ڽ�����
					try {					
						Gson gson=new Gson();		
						String jsoncontent=jsonObject.getString("content");
						//Log.d("jsoncontent", jsoncontent);
						house=gson.fromJson(jsoncontent, House.class);
						
						initData();		
						alterHouse();
						house_content_img.setOnClickListener(HouseContentActivity.this);
					} catch (JSONException e) {
						Log.d("��ʾ��Դ�б�", "���ݽ����쳣");
					}				
				}
			}
		}	
	}
}
