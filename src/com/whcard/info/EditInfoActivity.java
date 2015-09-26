package com.whcard.info;

import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.whcard.bean.Standard_Floating_Population;
import com.whcard.main.R;
import com.whcard.net.GetUrl;
import com.whcard.net.HttpUtil;
import com.whcard.util.ResultStateCode;
import com.whcard.util.Util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EditInfoActivity extends Activity implements OnClickListener{

	private TextView btBack;
	
	//������Ϣ 7��
	private EditText et_sfp_name;
	private EditText et_sfp_sex;
	private EditText et_sfp_nation;
	private EditText et_sfp_tel;
	private EditText et_sfp_identity;
	private EditText et_sfp_native;
	private EditText et_sfp_temporary_residence;
	
	//ѡ����Ϣ 12��
	private EditText et_sfp_naplace;
	private EditText et_sfp_birthdate;
	private EditText et_sfp_politic;
	private EditText et_sfp_email;
	private EditText et_sfp_height;
	private EditText et_sfp_blood;
	private EditText et_sfp_marriage;
	private EditText et_sfp_birthplace;
	private EditText et_sfp_gradschool;
	private EditText et_sfp_education;
	private EditText et_sfp_major;
	private EditText et_sfp_graddate;
	
	//��ť
	private ImageView et_sfp_cardimage;
	private Button et_sfp_selectimage;
	private Button et_sfp_submit;
	
	private Standard_Floating_Population sfp;
	
	
	private String jsonString;
	private File imageFile=null;
	private ProgressDialog progressDialog;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_info_edit);
		
		initView();
		initListener();
	}
	private void getData(){
		sfp=new Standard_Floating_Population();
		//��ȡUserId
		sfp.setSfp_id(Util.getUserInfo(this, "userId"));
		
		//String setSfp_birthdate=et_sfp_birthdate.getText().toString();
		sfp.setSfp_birthdate(et_sfp_birthdate.getText().toString());
		//String sfp_graddate=et_sfp_graddate.getText().toString();
		sfp.setSfp_graddate(et_sfp_graddate.getText().toString());
		//int����
		//Integer.valueOf(et_sfp_height.getText().toString())
		sfp.setSfp_height(170);
		sfp.setSfp_birthplace(et_sfp_birthplace.getText().toString());
		sfp.setSfp_blood(et_sfp_blood.getText().toString());
		sfp.setSfp_education(et_sfp_education.getText().toString());
		sfp.setSfp_email(et_sfp_email.getText().toString());		
		sfp.setSfp_gradschool(et_sfp_gradschool.getText().toString());
		
		
		sfp.setSfp_identity(et_sfp_identity.getText().toString());
		//sfp.setSfp_identityimage(et_sfp_identityimage);
		sfp.setSfp_major(et_sfp_major.getText().toString());
		sfp.setSfp_marriage(et_sfp_marriage.getText().toString());
		sfp.setSfp_name(et_sfp_name.getText().toString());
		sfp.setSfp_naplace(et_sfp_naplace.getText().toString());
		sfp.setSfp_nation(et_sfp_nation.getText().toString());
		sfp.setSfp_native(et_sfp_native.getText().toString());
		sfp.setSfp_politic(et_sfp_politic.getText().toString());
		//sfp.setSfp_regtime(et_sfp_regtime);
		sfp.setSfp_sex(et_sfp_sex.getText().toString());
		sfp.setSfp_tel(et_sfp_tel.getText().toString());
		sfp.setSfp_temporary_residence(et_sfp_temporary_residence.getText().toString());
		//sfp.setSfp_verify(et_sfp_verify);
	}
	private void initView() {
		//��ť
		btBack=(TextView) findViewById(R.id.edit_info_back);
		
		//������Ϣ
		et_sfp_name=(EditText) findViewById(R.id.et_sfp_name);
		et_sfp_sex=(EditText) findViewById(R.id.et_sfp_sex);
		et_sfp_nation=(EditText) findViewById(R.id.et_sfp_nation);
		et_sfp_tel=(EditText) findViewById(R.id.et_sfp_tel);
		et_sfp_identity=(EditText) findViewById(R.id.et_sfp_identity);
		et_sfp_native=(EditText) findViewById(R.id.et_sfp_native);
		et_sfp_temporary_residence=(EditText) findViewById(R.id.et_sfp_temporary_residence);
		
		//ѡ����Ϣ
		et_sfp_naplace=(EditText) findViewById(R.id.et_sfp_naplace);
		et_sfp_birthdate=(EditText) findViewById(R.id.et_sfp_birthdate);
		et_sfp_politic=(EditText) findViewById(R.id.et_sfp_politic);
		et_sfp_email=(EditText) findViewById(R.id.et_sfp_email);
		et_sfp_height=(EditText) findViewById(R.id.et_sfp_height);
		et_sfp_blood=(EditText) findViewById(R.id.et_sfp_blood);
		et_sfp_marriage=(EditText) findViewById(R.id.et_sfp_marriage);
		et_sfp_birthplace=(EditText) findViewById(R.id.et_sfp_birthplace);
		et_sfp_gradschool=(EditText) findViewById(R.id.et_sfp_gradschool);
		et_sfp_education=(EditText) findViewById(R.id.et_sfp_education);
		et_sfp_major=(EditText) findViewById(R.id.et_sfp_major);
		et_sfp_graddate=(EditText) findViewById(R.id.et_sfp_graddate);
		
		//֤�������
		et_sfp_cardimage=(ImageView) findViewById(R.id.et_sfp_cardimage);
		et_sfp_selectimage=(Button) findViewById(R.id.et_sfp_selectimage);
		et_sfp_submit=(Button) findViewById(R.id.et_sfp_submit);
	}

	private void initListener() {
		btBack.setOnClickListener(this);
		et_sfp_submit.setOnClickListener(this);
		et_sfp_selectimage.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.edit_info_back:
			finish();
			break;

		case R.id.et_sfp_selectimage:
			// ѡ��ͼƬ
			Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent, 1);
			break;
			
		case R.id.et_sfp_submit:			
			MyAsyncTask myAsyncTask=new MyAsyncTask();
			myAsyncTask.execute();			
			break;
		default:
			break;
		}
	}

	class MyAsyncTask extends AsyncTask<String, Integer, String>
	{
		@Override
		protected void onPreExecute() {
			//�����¼����ʾ��¼������
			progressDialog=null;
			progressDialog=ProgressDialog.show(EditInfoActivity.this, "", "�����ύ�����Եȡ�����");
			//��ʼ��sfp
			getData();
			//ת��Ϊjson�ַ���
			Gson gson=new Gson();
			jsonString=gson.toJson(sfp);
			//���͸�������		
		}
		
		@Override
		protected String doInBackground(String... params) {
			if (imageFile==null) {
				return "NoFile";
			} else {
				String httpReturn=HttpUtil.httpClient(GetUrl.EditInfo, jsonString, imageFile);			
				return httpReturn;
			}		
		}
		
		@Override
		protected void onPostExecute(String result) {
			progressDialog.dismiss();
			if ("NoFile".equals(result)) {
				Toast.makeText(EditInfoActivity.this, "��ѡ��֤���գ�", Toast.LENGTH_SHORT).show();
			} else {
				JSONObject jsonObject=null;
				String state=null;
				try {
					jsonObject = new JSONObject(result);
					state=jsonObject.getString("state");
					//Log.d("state", state);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.d("State", state);
				if (ResultStateCode.REG_SFP_SUCCESS.equals(state)) {
					Toast.makeText(EditInfoActivity.this, "�ϴ��ɹ���", Toast.LENGTH_SHORT).show();
					Util.saveUserInfo(EditInfoActivity.this, null, null, null, null, "true", null);
					Intent intent=new Intent(EditInfoActivity.this,ShowInfoActivity.class);
					startActivity(intent);
				}
				else if (ResultStateCode.REG_SFP_FAILED.equals(state)) {
					Toast.makeText(EditInfoActivity.this, "�ϴ�ʧ�ܣ�", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(EditInfoActivity.this, "δ֪����", Toast.LENGTH_SHORT).show();
				}
				//Log.d("ResultStateCode.REG_SFP_FAILED.equals(state)", ResultStateCode.REG_SFP_FAILED.equals(state)+"");
				finish();
			}
			
		}	
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// ��ͼƬ
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
				imageFile=new File(imagePath);
				
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inJustDecodeBounds = false;
				options.inSampleSize = 20; // width��hight��Ϊԭ����ʮ��һ
				Bitmap addbmp=BitmapFactory.decodeFile(imagePath,options);
				et_sfp_cardimage.setImageBitmap(addbmp);
				et_sfp_cardimage.setVisibility(View.VISIBLE);
			}
		}
	}
}
