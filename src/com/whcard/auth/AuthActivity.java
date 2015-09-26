package com.whcard.auth;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.whcard.bean.Standard_Floating_Population;
import com.whcard.bean.WuhanCard;
import com.whcard.main.R;
import com.whcard.net.GetUrl;
import com.whcard.net.HttpUtil;
import com.whcard.util.ResultStateCode;
import com.whcard.util.UserType;
import com.whcard.util.Util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AuthActivity extends Activity implements OnItemSelectedListener,OnClickListener {

	private Spinner spinner;
	private EditText auth_value;
	private Button auth_query;

	private String condition;
	private String value;
	public ProgressDialog progressDialog;
	private static final String[] auth_condition = { "���֤��", "�人��Ƭ���" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_auth);

		auth_value = (EditText) findViewById(R.id.auth_value);
		spinner = (Spinner) findViewById(R.id.auth_spinner);
		auth_query=(Button) findViewById(R.id.auth_query);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, auth_condition);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
		
		auth_query.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		MyAsyncTask myAsyncTask=new MyAsyncTask();
		myAsyncTask.execute();
	}
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		switch (position) {
		case 0:
			condition = "whcard_identity";
			break;
		case 1:
			condition = "sfp_id";
			break;
		default:
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}

	private class MyAsyncTask extends AsyncTask<String, Integer, String> {
		private String jsonString;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			// �����¼����ʾ��¼������
			progressDialog = null;
			progressDialog = ProgressDialog.show(AuthActivity.this, "",
					"���ڲ�ѯ�����Եȡ�����");
			value = auth_value.getText().toString();
			JSONObject queryObject = new JSONObject();
			try {
				queryObject.put("condition", condition);
				queryObject.put("value", value);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jsonString = queryObject.toString();
		}

		@Override
		protected String doInBackground(String... params) {
			String httpReturn = HttpUtil.httpClient(GetUrl.QueWHCard,jsonString);
			return httpReturn;
		}

		@Override
		protected void onPostExecute(String result) {
			// ȡ�����ڵ�¼��ʾ��
			progressDialog.dismiss();

			if ("webserver is stop".equals(result)) {
				toast("������ֹͣ���У����Ժ��¼������");
			} else if ("webserver is error".equals(result)) {
				toast("�������ڲ��쳣�����Ժ��¼������");
			} else {
				String state = new String();
				String content = new String();
				JSONObject resultJson=null;
				try {
					resultJson = new JSONObject(result);
					state = resultJson.getString("state");
				} catch (JSONException e) {
					toast("���ݽ����쳣��");
					return;
				}
				// �����ж��Ƿ��¼��ȷ��
				if ("Success".equals(state)) {
					try {
						content=resultJson.getString("content");
						Gson gson=new Gson();
						WuhanCard wuhanCard=gson.fromJson(content, WuhanCard.class);
						
						Intent intent = new Intent(AuthActivity.this,ShowAuthActivity.class);
						Bundle bundle=new Bundle();
						bundle.putSerializable("wuhanCard", wuhanCard);
						intent.putExtras(bundle);
						startActivity(intent);
						finish();
					} catch (JSONException e) {
						Log.d("�人��Ƭ����", "���ݽ����쳣");
					}				
				}else {
					toast("δ��ѯ����¼��");
				}
			}

		}
	}

	public void toast(String toast) {
		Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
	}
}
