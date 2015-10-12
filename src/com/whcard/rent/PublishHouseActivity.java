package com.whcard.rent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore.MediaColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.whcard.adapter.GridViewAdapter;
import com.whcard.bean.House;
import com.whcard.bean.PublishHouseBean;
import com.whcard.info.EditInfoActivity;
import com.whcard.main.R;
import com.whcard.net.GetUrl;
import com.whcard.net.HttpUtil;
import com.whcard.util.Util;

public class PublishHouseActivity extends Activity implements OnClickListener,OnItemClickListener{

	private PublishHouseBean houseContentBean;
	
	//���в��ֿؼ��д�����
	private EditText publish_house_address;
	private EditText publish_house_cell;
	private EditText publish_house_type;
	private EditText publish_house_way;
	private EditText publish_house_rental;
	private EditText publish_house_area;
	private EditText publish_house_floor;
	private EditText publish_house_decorate;
	
	//�������ã��Ը�ѡ�����ʽ
	private TableLayout publish_house_config;
	
	private EditText publish_house_title;
	private EditText publish_house_describle;
	private EditText publish_house_contactor;
	private EditText publish_house_tell;
	
	//������ť
	private Button publish_house_submit;
	private Button rent_btn_house;
	//����ͼƬ��ر���
	private GridView publish_house_gridView;
	private List<Object> imageList;
	private GridViewAdapter adapter;
	Bitmap addbmp=null;
	
	private List<File> fileList=new ArrayList<File>();
	private String jsonString;
	private ProgressDialog progressDialog;
	
	private House house=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_publish_house);
		
		initView();
		initAdapter();
		//�Ƚ����޸���Ϣ���͹�����bundle
		house = (House) getIntent().getSerializableExtra("houseContent");
		//�����Ϊ�գ�����䵽�ؼ���
		if (house!=null) {
			initData();
		}			
		publish_house_gridView.setAdapter(adapter);
		publish_house_gridView.setOnItemClickListener(this);
		publish_house_submit.setOnClickListener(this);
	}

	private void initData() {
		//���ؼ�����
		publish_house_address.setText(house.getHou_add());
		publish_house_cell.setText(house.getHou_cell());
		publish_house_type.setText(house.getHou_type());
		publish_house_way.setText(house.getHou_rentway());
		publish_house_rental.setText(house.getHou_rental()+"");
		publish_house_area.setText(house.getHou_area()+"");
		publish_house_floor.setText(house.getHou_floor());
		publish_house_decorate.setText(house.getHou_decorate());
		
		publish_house_title.setText(house.getHou_title());
		publish_house_describle.setText(house.getHou_descrip());
		publish_house_contactor.setText(house.getSfp_name());
		publish_house_tell.setText(house.getSfp_tel());
		
		//���ͼƬ����
		imageList.addAll(house.getHou_img());
		adapter.onDateChange(imageList);
	}

	private void initAdapter() {
		Bitmap  addpicButton = BitmapFactory.decodeResource(getResources(), R.drawable.gridview_addpic);
		imageList=new ArrayList<Object>();
		imageList.add(addpicButton);
		adapter=new GridViewAdapter(imageList, this,0);
	}
	private void getViewData() {
		houseContentBean=new PublishHouseBean();

		houseContentBean.setPublish_house_userid(Util.getUserInfo(this, "userId"));
		
		houseContentBean.setPublish_house_address(publish_house_address.getText().toString());
		//
		houseContentBean.setPublish_house_area(String.valueOf(120));
		houseContentBean.setPublish_house_cell(publish_house_cell.getText().toString());
		
	    houseContentBean.setPublish_house_config("������������ӣ��յ�����ˮ��");
		
		houseContentBean.setPublish_house_contactor(publish_house_contactor.getText().toString());
		houseContentBean.setPublish_house_decorate(publish_house_decorate.getText().toString());
		houseContentBean.setPublish_house_describle(publish_house_describle.getText().toString());
		houseContentBean.setPublish_house_floor(publish_house_floor.getText().toString());
		houseContentBean.setPublish_house_rental(publish_house_rental.getText().toString());
		houseContentBean.setPublish_house_tell(publish_house_tell.getText().toString());
		houseContentBean.setPublish_house_title(publish_house_title.getText().toString());
		houseContentBean.setPublish_house_type(publish_house_type.getText().toString());
		houseContentBean.setPublish_house_way(publish_house_way.getText().toString());
		
		houseContentBean.setPublish_house_houseid(house==null?"false":house.getHou_id()+"");
		
	}
	private void initView() {
		publish_house_address=(EditText) findViewById(R.id.publish_house_address);
		publish_house_cell=(EditText) findViewById(R.id.publish_house_cell);
		publish_house_type=(EditText) findViewById(R.id.publish_house_type);
		publish_house_way=(EditText) findViewById(R.id.publish_house_way);
		publish_house_rental=(EditText) findViewById(R.id.publish_house_rental);
		publish_house_area=(EditText) findViewById(R.id.publish_house_area);
		publish_house_floor=(EditText) findViewById(R.id.publish_house_floor);
		publish_house_decorate=(EditText) findViewById(R.id.publish_house_decorate);
		
		publish_house_title=(EditText) findViewById(R.id.publish_house_title);
		publish_house_describle=(EditText) findViewById(R.id.publish_house_describle);
		publish_house_contactor=(EditText) findViewById(R.id.publish_house_contactor);
		publish_house_tell=(EditText) findViewById(R.id.publish_house_tell);

		publish_house_submit=(Button) findViewById(R.id.publish_house_submit);
		rent_btn_house=(Button) findViewById(R.id.rent_btn_source);
		
		publish_house_gridView=(GridView) findViewById(R.id.publish_house_gridView);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//��ͼƬ  
		if (resultCode == Activity.RESULT_OK && requestCode == 1) {
			Uri uri = data.getData();

			if (!TextUtils.isEmpty(uri.getAuthority())) {
				// ��ѯѡ��ͼƬ
				Cursor cursor = this.getContentResolver().query(uri,
						new String[] { MediaColumns.DATA }, null,
						null, null);
				// ���� û�ҵ�ѡ��ͼƬ
				if (null == cursor) {
					return;
				}
				// ����ƶ�����ͷ ��ȡͼƬ·��
				cursor.moveToFirst();
				String imagePath = cursor.getString(cursor.getColumnIndex(MediaColumns.DATA));
				File image=new File(imagePath);
				fileList.add(image);
				
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inJustDecodeBounds = false;
				options.inSampleSize = 50; // width��hight��Ϊԭ����ʮ��һ
				
				addbmp = BitmapFactory.decodeFile(imagePath,options);
				imageList.add(addbmp);
				adapter.onDateChange(imageList);
			}
		}
	}
	@Override
	public void onClick(View v) {
		//�������磬�ύ����
		//�ύ֮��BitmapҪ�ǵ����
		MyAsyncTask myAsyncTask=new MyAsyncTask();
		myAsyncTask.execute();
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (position == 0) { // ���ͼƬλ��Ϊ+ 0��Ӧ0��ͼƬ
			Toast.makeText(this, "���ͼƬ", Toast.LENGTH_SHORT).show();
			// ѡ��ͼƬ
			Intent intent = new Intent(Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent, 1);
			// ͨ��onResume()ˢ������
		} else {
			dialog(position);
		}
	}
	 /*
     * Dialog�Ի�����ʾ�û�ɾ������
     * positionΪɾ��ͼƬλ��
     */
    protected void dialog(final int position) {
    	AlertDialog.Builder builder = new Builder(this);
    	builder.setMessage("ȷ���Ƴ������ͼƬ��");
    	builder.setTitle("��ʾ");
    	builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
    		@Override
    		public void onClick(DialogInterface dialog, int which) {
    			dialog.dismiss();
    			imageList.remove(position);
    	        adapter.onDateChange(imageList);
    		}
    	});
    	builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
    		@Override
    		public void onClick(DialogInterface dialog, int which) {
    			dialog.dismiss();
    			}
    		});
    	builder.create().show();
    }
	
	
    class MyAsyncTask extends AsyncTask<String, Integer, String>
	{

		@Override
		protected void onPreExecute() {
			//׼��Json�ַ�����������ʾ��
			Gson gson=new Gson();
			//��ȡ�ؼ���������ʼ��houseContentBean
			getViewData();
			jsonString = gson.toJson(houseContentBean);
			Log.d("houseContentBean", houseContentBean.getPublish_house_address());
			progressDialog=ProgressDialog.show(PublishHouseActivity.this, "", "���ڷ��������Եȡ�����");
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String httpReturn;
			if ("false".equals(houseContentBean.getPublish_house_houseid())) {
				httpReturn=HttpUtil.httpClient(GetUrl.PublishHouse, jsonString, fileList);
			} else {
				httpReturn=HttpUtil.httpClient(GetUrl.UpdateHouse, jsonString, fileList);
			} 
			return httpReturn;
		}
		
		@Override
		protected void onPostExecute(String result) {
			progressDialog.dismiss();
			JSONObject jsonObject=null;
			String state="�쳣";
			try {
				jsonObject = new JSONObject(result);
				state=jsonObject.getString("state");				
			} catch (JSONException e) {
				Log.d("json�ַ��������쳣", "json�ַ��������쳣");
			}
			Log.d("State", state);
			//������󷢲��ɹ�������ת����Դ����,������Դ��ť��Ϊ��ɫ����Դ��ť��Ϊ��ɫ
			if (state.equals("Success")) {			
				Toast.makeText(PublishHouseActivity.this, "ס����Դ�����ɹ���", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(PublishHouseActivity.this,HouseSourceActivity.class);
				startActivity(intent);
				PublishHouseActivity.this.finish();
			}//������ʾ���󷢲�ʧ��
			else{
				Toast.makeText(PublishHouseActivity.this, "ס�����󷢲�ʧ��", Toast.LENGTH_SHORT).show();
			}
		}	
	}
}