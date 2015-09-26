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
				"所有商品85折",
				"洪山区光谷分店",
				"图片路径",
				"中百仓储",
				"13198459647");
		discountBean.save(this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), 
						"添加数据成功！", Toast.LENGTH_SHORT).show();
			}			
			@Override
			public void onFailure(int arg0, String arg1) {
				Toast.makeText(getApplicationContext(), 
						"创建数据失败"+arg1, Toast.LENGTH_SHORT).show();						
			}
		});
	}
}
*/