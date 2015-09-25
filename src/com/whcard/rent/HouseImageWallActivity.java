package com.whcard.rent;

import java.util.ArrayList;
import java.util.List;

import com.whcard.adapter.GridViewAdapter;
import com.whcard.main.R;
import com.whcard.main.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class HouseImageWallActivity extends Activity {

	private List<String> imgPathList;
	private GridView house_imagewall_grid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_house_image_wall);
		house_imagewall_grid=(GridView) findViewById(R.id.house_imagewall_grid);
		
		imgPathList=getIntent().getStringArrayListExtra("imgPathList");
		Log.d("imgpathµÄ³¤¶È", imgPathList.size()+"");
		List<Object> tempimgPath=new ArrayList<Object>();
		for (int i = 0; i < imgPathList.size(); i++) {
			tempimgPath.add(imgPathList.get(i));
		}
		
		house_imagewall_grid=(GridView) findViewById(R.id.house_imagewall_grid);
		
		GridViewAdapter adapter=new GridViewAdapter(tempimgPath, this,1);
		house_imagewall_grid.setAdapter(adapter);
		house_imagewall_grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				String path=imgPathList.get(position);
				Intent intent=new Intent(HouseImageWallActivity.this,HouseImageActivity.class);
				intent.putExtra("path", path);
				startActivity(intent);
			}
		});
		
	}
}
