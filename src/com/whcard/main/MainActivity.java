package com.whcard.main;

import java.util.ArrayList;
import java.util.List;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.whcard.info.MineFragment;
import com.whcard.rent.RentingFragment;
import com.whcard.location.LocationApplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends FragmentActivity implements OnClickListener {

	private LinearLayout tabWhcard;
	private LinearLayout tabRenting;
	private LinearLayout tabCooperator;
	private LinearLayout tabInfo;

	private ImageButton imgWhcard;
	private ImageButton imgRenting;
	private ImageButton imgCooperator;
	private ImageButton imgInfo;

	private ViewPager viewPager;
	private FragmentPagerAdapter fpAdapter;
	private List<Fragment> fList;

	private String whCardPath = "123";
	private String twoDimCodePath = "123";
	Bundle data = null;

	private LocationClient mLocationClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_transfer_main);
		// 启用百度地图service，获取经纬度
		mLocationClient = ((LocationApplication) getApplication()).mLocationClient;
		initLocation();
		mLocationClient.start();

		// 接收参数封装到data
		Intent intent = getIntent();
		whCardPath = intent.getStringExtra("whCardPath");
		twoDimCodePath = intent.getStringExtra("twoDimCodePath");
		// Log.d("whCardPath", whCardPath);
		data = new Bundle();
		data.putString("whCardPath", whCardPath);
		data.putString("twoDimCodePath", twoDimCodePath);

		initView();
		initEvent();
	}

	private void initView() {

		tabWhcard = (LinearLayout) findViewById(R.id.id_tab_whcard);
		tabRenting = (LinearLayout) findViewById(R.id.id_tab_renting);
		tabCooperator = (LinearLayout) findViewById(R.id.id_tab_cooperator);
		tabInfo = (LinearLayout) findViewById(R.id.id_tab_transferinfo);

		imgWhcard = (ImageButton) findViewById(R.id.id_tab_whcard_img);
		imgRenting = (ImageButton) findViewById(R.id.id_tab_renting_img);
		imgCooperator = (ImageButton) findViewById(R.id.id_tab_cooperator_img);
		imgInfo = (ImageButton) findViewById(R.id.id_tab_transferinfo_img);

		viewPager = (ViewPager) findViewById(R.id.id_viewpager);
		fList = new ArrayList<Fragment>();
		Fragment tab1 = new WhcardFragment();
		tab1.setArguments(data);
		Fragment tab2 = new RentingFragment();
		Fragment tab3 = new CooperatorFragment();
		Fragment tab4 = new MineFragment();
		fList.add(tab1);
		fList.add(tab2);
		fList.add(tab3);
		fList.add(tab4);

		fpAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return fList.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				return fList.get(arg0);
			}
		};
		viewPager.setAdapter(fpAdapter);

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				int currentItem = viewPager.getCurrentItem();
				setTab(currentItem);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	private void initEvent() {
		tabWhcard.setOnClickListener(this);
		tabRenting.setOnClickListener(this);
		tabCooperator.setOnClickListener(this);
		tabInfo.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.id_tab_whcard:
			setSelect(0);
			break;
		case R.id.id_tab_renting:
			setSelect(1);
			break;
		case R.id.id_tab_cooperator:
			setSelect(2);
			break;
		case R.id.id_tab_transferinfo:
			setSelect(3);
			break;

		default:
			break;
		}
	}

	private void setSelect(int i) {
		// 1、设置图片为亮色
		// 2、切换内容区域的Fragment
		setTab(i);
		viewPager.setCurrentItem(i);
	}

	private void setTab(int i) {
		resetImgs();
		switch (i) {
		case 0:
			imgWhcard.setImageResource(R.drawable.tab_whcard_pressed);
			break;
		case 1:
			imgRenting.setImageResource(R.drawable.tab_renting_pressed);
			break;
		case 2:
			imgCooperator.setImageResource(R.drawable.tab_cooperator_pressed);
			break;
		case 3:
			imgInfo.setImageResource(R.drawable.tab_transferinfo_pressed);
			break;
		}
	}

	/**
	 * 切换图片至暗色
	 */
	private void resetImgs() {
		imgWhcard.setImageResource(R.drawable.tab_whcard);
		imgRenting.setImageResource(R.drawable.tab_renting);
		imgCooperator.setImageResource(R.drawable.tab_cooperator);
		imgInfo.setImageResource(R.drawable.tab_transferinfo);
	}

	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
		int span = 3*60*1000;
		option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		//option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		//option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		option.setIgnoreKillProcess(true);// 可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
		option.SetIgnoreCacheException(true);// 可选，默认false，设置是否收集CRASH信息，默认收集
		option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
		mLocationClient.setLocOption(option);
	}
}
