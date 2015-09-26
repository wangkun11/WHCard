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
		// ���ðٶȵ�ͼservice����ȡ��γ��
		mLocationClient = ((LocationApplication) getApplication()).mLocationClient;
		initLocation();
		mLocationClient.start();

		// ���ղ�����װ��data
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
		// 1������ͼƬΪ��ɫ
		// 2���л����������Fragment
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
	 * �л�ͼƬ����ɫ
	 */
	private void resetImgs() {
		imgWhcard.setImageResource(R.drawable.tab_whcard);
		imgRenting.setImageResource(R.drawable.tab_renting);
		imgCooperator.setImageResource(R.drawable.tab_cooperator);
		imgInfo.setImageResource(R.drawable.tab_transferinfo);
	}

	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// ��ѡ��Ĭ�ϸ߾��ȣ����ö�λģʽ���߾��ȣ��͹��ģ����豸
		option.setCoorType("bd09ll");// ��ѡ��Ĭ��gcj02�����÷��صĶ�λ�������ϵ
		int span = 3*60*1000;
		option.setScanSpan(span);// ��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
		//option.setIsNeedAddress(true);// ��ѡ�������Ƿ���Ҫ��ַ��Ϣ��Ĭ�ϲ���Ҫ
		option.setOpenGps(true);// ��ѡ��Ĭ��false,�����Ƿ�ʹ��gps
		option.setLocationNotify(true);// ��ѡ��Ĭ��false�������Ƿ�gps��Чʱ����1S1��Ƶ�����GPS���
		//option.setIsNeedLocationDescribe(true);// ��ѡ��Ĭ��false�������Ƿ���Ҫλ�����廯�����������BDLocation.getLocationDescribe��õ�����������ڡ��ڱ����찲�Ÿ�����
		option.setIsNeedLocationPoiList(true);// ��ѡ��Ĭ��false�������Ƿ���ҪPOI�����������BDLocation.getPoiList��õ�
		option.setIgnoreKillProcess(true);// ��ѡ��Ĭ��false����λSDK�ڲ���һ��SERVICE�����ŵ��˶������̣������Ƿ���stop��ʱ��ɱ��������̣�Ĭ��ɱ��
		option.SetIgnoreCacheException(true);// ��ѡ��Ĭ��false�������Ƿ��ռ�CRASH��Ϣ��Ĭ���ռ�
		option.setEnableSimulateGps(false);// ��ѡ��Ĭ��false�������Ƿ���Ҫ����gps��������Ĭ����Ҫ
		mLocationClient.setLocOption(option);
	}
}
