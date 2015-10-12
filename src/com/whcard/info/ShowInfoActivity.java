package com.whcard.info;

import org.json.JSONException;
import org.json.JSONObject;

import com.whcard.main.R;
import com.whcard.net.GetUrl;
import com.whcard.net.HttpUtil;
import com.whcard.util.MyJsonObject;
import com.whcard.util.ResultStateCode;
import com.whcard.util.Util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowInfoActivity extends Activity implements OnClickListener{
	
	//��ť
	private ImageView btBack;
	private TextView btEdit;
	
	//��֤���
	private TextView sfp_verify;
	private TextView sfp_regtime;
	private TextView sfp_furTime;
	
	//������Ϣ
	private TextView sfp_name;
	private TextView sfp_sex;
	private TextView sfp_nation;
	private TextView sfp_tel;
	private TextView sfp_identity;
	private TextView sfp_native;
	private TextView sfp_temporary_residence;
	
	//������Ϣ
	private TextView sfp_naplace;
	private TextView sfp_birthdate;
	private TextView sfp_politic;
	private TextView sfp_email;
	private TextView sfp_height;
	private TextView sfp_blood;
	private TextView sfp_marriage;
	private TextView sfp_birthplace;
	private TextView sfp_gradschool;
	private TextView sfp_education;
	private TextView sfp_major;
	private TextView sfp_graddate;
	
	//
	private ProgressDialog progressDialog;
	private String jsonString;

	String httpReturn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_info_show);
		
		initView();
		//initData();
		initListener();
		GetStandardInfo getStandardInfo=new GetStandardInfo();
		getStandardInfo.execute();
	}
	private void initView() {
		//��ť
		btBack=(ImageView) findViewById(R.id.standard_info_back);
		btEdit=(TextView) findViewById(R.id.standard_info_edit);
		
		//��֤���
		sfp_verify=(TextView) findViewById(R.id.sfp_verify);
		sfp_regtime=(TextView) findViewById(R.id.sfp_regtime);
		sfp_furTime=(TextView) findViewById(R.id.sfp_furTime);
		
		//�������
		sfp_name=(TextView) findViewById(R.id.sfp_name);
		sfp_sex=(TextView) findViewById(R.id.sfp_sex);
		sfp_nation=(TextView) findViewById(R.id.sfp_nation);
		sfp_tel=(TextView) findViewById(R.id.sfp_tel);
		sfp_identity=(TextView) findViewById(R.id.sfp_identity);
		sfp_native=(TextView) findViewById(R.id.sfp_native);
		sfp_temporary_residence=(TextView) findViewById(R.id.sfp_temporary_residence);
		
		//�������
		sfp_naplace=(TextView) findViewById(R.id.sfp_naplace);
		sfp_birthdate=(TextView) findViewById(R.id.sfp_birthdate);
		sfp_politic=(TextView) findViewById(R.id.sfp_politic);
		sfp_email=(TextView) findViewById(R.id.sfp_email);
		sfp_height=(TextView) findViewById(R.id.sfp_height);
		sfp_blood=(TextView) findViewById(R.id.sfp_blood);
		sfp_marriage=(TextView) findViewById(R.id.sfp_marriage);
		sfp_birthplace=(TextView) findViewById(R.id.sfp_birthplace);
		sfp_gradschool=(TextView) findViewById(R.id.sfp_gradschool);
		sfp_education=(TextView) findViewById(R.id.sfp_education);
		sfp_major=(TextView) findViewById(R.id.sfp_major);
		sfp_graddate=(TextView) findViewById(R.id.sfp_graddate);

	}

	private void initData() {
		// ��֤���
		sfp_verify.setText("");;
		sfp_regtime.setText("");
		sfp_furTime.setText("");

		// �������
		sfp_name.setText("");
		sfp_sex.setText("");
		sfp_nation.setText("");
		sfp_tel.setText("");
		sfp_identity.setText("");
		sfp_native.setText("");
		sfp_temporary_residence.setText("");

		// �������
		sfp_naplace.setText("");
		sfp_birthdate.setText("");
		sfp_politic.setText("");
		sfp_email.setText("");
		sfp_height.setText("");
		sfp_blood.setText("");
		sfp_marriage.setText("");
		sfp_birthplace.setText("");
		sfp_gradschool.setText("");
		sfp_education.setText("");
		sfp_major.setText("");
		sfp_graddate.setText("");
	}

	
	private void initListener() {
		btBack.setOnClickListener(this);
		btEdit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.standard_info_back:
			finish();
			break;

        case R.id.standard_info_edit:
			Intent intent=new Intent(this,EditInfoActivity.class);
			intent.putExtra("contentJson",httpReturn);
			startActivity(intent);
			break;
		default:
			break;
		}		
	}

    private class GetStandardInfo extends AsyncTask<String, Integer, String>{
    	@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			//����ǰ����װ��ǰ����id
			String userId=Util.getUserInfo(ShowInfoActivity.this, "userId");
			JSONObject userObject=new JSONObject();
			try {
				userObject.put("sfp_id", userId);						
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}					
			jsonString = userObject.toString();
			progressDialog=null;
			progressDialog=ProgressDialog.show(ShowInfoActivity.this, "", "���ڻ�ȡ��׼��Ϣ�����Եȡ�����");
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			httpReturn=HttpUtil.httpClient(GetUrl.GetStandardInfo, jsonString);
			return httpReturn;
		}
		
		@Override
		protected void onPostExecute(String result) {
			progressDialog.dismiss();
			String resultState=new String();
			JSONObject resultContent=null;
			JSONObject jsonObject=null;
			try {
				jsonObject=new JSONObject(result);
				resultState=jsonObject.getString("state");			
			} catch (JSONException e) {
				Log.d("��������������Ϣ��ѯ���", "���ݽ����쳣");
			}
			//���resultState��������ʾ�û���������
			Log.d("state", resultState);
			if (ResultStateCode.QUE_SFP_ERROR.equals(resultState)) {
				Toast.makeText(ShowInfoActivity.this, "��������æ�����Ժ����ԣ�", Toast.LENGTH_SHORT).show();
				finish();
			}
			else{	
				//���򣬽����������ݲ���ʾ�ڽ�����
				try {
					resultContent=jsonObject.getJSONObject("content");
					Log.d("content", "get content success!");
					MyJsonObject contentJson=new MyJsonObject(resultContent);
					// ��֤���
					String isVerify=resultContent.getInt("sfp_verify")==0?"��":"��";
					sfp_verify.setText(isVerify);
					
					sfp_regtime.setText(contentJson.getString("sfp_regtime"));
					sfp_furTime.setText(contentJson.getString("sfp_furTime"));

					// �������
					sfp_name.setText(contentJson.getString("sfp_name"));
					sfp_sex.setText(contentJson.getString("sfp_sex"));
					sfp_nation.setText(contentJson.getString("sfp_nation"));
					sfp_tel.setText(contentJson.getString("sfp_tel"));
					sfp_identity.setText(contentJson.getString("sfp_identity"));
					sfp_native.setText(contentJson.getString("sfp_native"));
					sfp_temporary_residence.setText(contentJson.getString("sfp_temporary_residence"));

					// �������
					sfp_naplace.setText(contentJson.getString("sfp_naplace"));
					sfp_birthdate.setText(contentJson.getString("sfp_birthdate"));
					sfp_politic.setText(contentJson.getString("sfp_politic"));
					sfp_email.setText(contentJson.getString("sfp_email"));
					
					sfp_height.setText(resultContent.getInt("sfp_height")+"cm");
					
					sfp_blood.setText(contentJson.getString("sfp_blood"));
					sfp_marriage.setText(contentJson.getString("sfp_marriage"));
					sfp_birthplace.setText(contentJson.getString("sfp_birthplace"));
					sfp_gradschool.setText(contentJson.getString("sfp_gradschool"));
					sfp_education.setText(contentJson.getString("sfp_education"));
					sfp_major.setText(contentJson.getString("sfp_major"));
					sfp_graddate.setText(contentJson.getString("sfp_graddate"));
				} catch (JSONException e) {
					Log.d("��ʾ����������Ϣ", "���ݽ����쳣");
				}				
			}
		}	
    } 
}
