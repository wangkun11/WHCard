package com.whcard.main;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

import com.whcard.util.DepthPageTransformer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

public class StartActivity extends Activity implements OnClickListener{
	
	private ViewPager starViewPager;
	private List<View> list;
	private PagerAdapter pagerAdapter;
	private Button startButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_start);
				
		//初始化推送模块
		/*JPushInterface.setDebugMode(true);
		JPushInterface.init(this);*/
		initView();
		
		startButton.setOnClickListener(this);
	}

	private void initView() {
		// TODO Auto-generated method stub
		starViewPager=(ViewPager) findViewById(R.id.id_start_viewpager);
		
		LayoutInflater layoutInflater=LayoutInflater.from(this);
/*		View startView1=layoutInflater.inflate(R.layout.start_1, null);
		View startView2=layoutInflater.inflate(R.layout.start_2, null);*/
		View startView3=layoutInflater.inflate(R.layout.start_3, null);
		
		//其它layout中的组件不能直接通过findViewById获取
		startButton=(Button) startView3.findViewById(R.id.id_start_button);
		
		list=new ArrayList<View>();
	    //list.add(startView1);
		//list.add(startView2);
		list.add(startView3);
		
		//为startViewPager添加动画效果
		starViewPager.setPageTransformer(true, new DepthPageTransformer());
		
		pagerAdapter =new PagerAdapter() {
					
			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView(list.get(position));
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {

				View view=list.get(position);
				container.addView(view);
				return view;
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0==arg1;
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return list.size();
			}
		};
	    starViewPager.setAdapter(pagerAdapter);	
	}
	@Override
	public void onClick(View v) {
		Intent intent=new Intent(StartActivity.this, LoginActivity.class);
		startActivity(intent);
		finish();
	}
}
