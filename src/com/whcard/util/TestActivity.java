/*package com.whcard.util;

import cn.bmob.v3.listener.SaveListener;

import com.whcard.bean.DiscountBean;
import com.whcard.main.R;
import com.whcard.main.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class TestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		DiscountBean discountBean=new DiscountBean(
				"������Ʒ85��",
				"��ɽ����ȷֵ�",
				"ͼƬ·��",
				"�аٲִ�",
				"13198459647");
		discountBean.save(this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), 
						"������ݳɹ���", Toast.LENGTH_SHORT).show();
			}			
			@Override
			public void onFailure(int arg0, String arg1) {
				Toast.makeText(getApplicationContext(), 
						"��������ʧ��"+arg1, Toast.LENGTH_SHORT).show();						
			}
		});
	}
}
*/