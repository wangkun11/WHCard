package com.whcard.main;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.loopj.android.image.SmartImageView;
import com.whcard.location.LocationApplication;
import com.whcard.net.GetUrl;
import com.whcard.util.Util;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WhcardFragment extends Fragment {
	
	private View view;
	private SmartImageView fra_whcard_cardimg;
	private SmartImageView fra_whcard_twodimcode;
	private TextView fra_whcard_authstate;
	Bundle data=null;
	
	private LocationClient mLocationClient;
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view= inflater.inflate(R.layout.fragment_whcard, container, false);		
		initView();
		initData();
		initListener();
		
	/*	mLocationClient = ((LocationApplication)(getActivity().getApplication())).mLocationClient;
        initLocation();
        mLocationClient.start();//定位SDK start之后会默认发起一次定位请求，开发者无须判断isstart并主动调用request  
*/        
        //mLocationClient.stop(); //关闭请求，在需要时调用      
		return view;
	}
	private void initView() {
		fra_whcard_cardimg=(SmartImageView) view.findViewById(R.id.fra_whcard_cardimg);
		fra_whcard_twodimcode=(SmartImageView) view.findViewById(R.id.fra_whcard_twodimcode);
		fra_whcard_authstate=(TextView) view.findViewById(R.id.fra_whcard_authstate);
	}
	private void initData() {
		data=getArguments();
		/*Log.d("whCardPath", data.getString("whCardPath"));
		Log.d("twoDimCodePath", data.getString("twoDimCodePath"));*/
		Log.d("图片url", GetUrl.PreUrl+data.getString("whCardPath"));
		fra_whcard_cardimg.setImageUrl(GetUrl.PreUrl+data.getString("whCardPath"));
		fra_whcard_twodimcode.setImageUrl(GetUrl.PreUrl+data.getString("twoDimCodePath"));
		fra_whcard_authstate.setText(
				Util.getUserInfo(getActivity(), "isAuthorize").equals("false")?"未认证":"已认证");
	}
	private void initListener() {
		// TODO Auto-generated method stub
		
	}
	private void initLocation(){	
		//参数设置：
		//三种模式：LocationMode.Hight_Accuracy;LocationMode.Battery_Saving;LocationMode.Device_Sensors;
		LocationMode tempMode = LocationMode.Hight_Accuracy;
		//三种经纬度标准：国家测绘局标准:"gcj02";百度经纬度标准:"bd09ll";百度墨卡托标准:"bd09";
		String tempcoor="gcj02";
		//发送频率：
		int span=3*60*1000;
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(tempMode);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType(tempcoor);//可选，默认gcj02，设置返回的定位结果坐标系，
        
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mLocationClient.setLocOption(option);
    }
}
