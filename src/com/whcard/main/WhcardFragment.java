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
        mLocationClient.start();//��λSDK start֮���Ĭ�Ϸ���һ�ζ�λ���󣬿����������ж�isstart����������request  
*/        
        //mLocationClient.stop(); //�ر���������Ҫʱ����      
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
		Log.d("ͼƬurl", GetUrl.PreUrl+data.getString("whCardPath"));
		fra_whcard_cardimg.setImageUrl(GetUrl.PreUrl+data.getString("whCardPath"));
		fra_whcard_twodimcode.setImageUrl(GetUrl.PreUrl+data.getString("twoDimCodePath"));
		fra_whcard_authstate.setText(
				Util.getUserInfo(getActivity(), "isAuthorize").equals("false")?"δ��֤":"����֤");
	}
	private void initListener() {
		// TODO Auto-generated method stub
		
	}
	private void initLocation(){	
		//�������ã�
		//����ģʽ��LocationMode.Hight_Accuracy;LocationMode.Battery_Saving;LocationMode.Device_Sensors;
		LocationMode tempMode = LocationMode.Hight_Accuracy;
		//���־�γ�ȱ�׼�����Ҳ��ֱ�׼:"gcj02";�ٶȾ�γ�ȱ�׼:"bd09ll";�ٶ�ī���б�׼:"bd09";
		String tempcoor="gcj02";
		//����Ƶ�ʣ�
		int span=3*60*1000;
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(tempMode);//��ѡ��Ĭ�ϸ߾��ȣ����ö�λģʽ���߾��ȣ��͹��ģ����豸
        option.setCoorType(tempcoor);//��ѡ��Ĭ��gcj02�����÷��صĶ�λ�������ϵ��
        
        option.setScanSpan(span);//��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
        option.setIsNeedAddress(true);//��ѡ�������Ƿ���Ҫ��ַ��Ϣ��Ĭ�ϲ���Ҫ
        option.setOpenGps(true);//��ѡ��Ĭ��false,�����Ƿ�ʹ��gps
        option.setLocationNotify(true);//��ѡ��Ĭ��false�������Ƿ�gps��Чʱ����1S1��Ƶ�����GPS���
        option.setIgnoreKillProcess(true);//��ѡ��Ĭ��true����λSDK�ڲ���һ��SERVICE�����ŵ��˶������̣������Ƿ���stop��ʱ��ɱ��������̣�Ĭ�ϲ�ɱ��
        option.setEnableSimulateGps(false);//��ѡ��Ĭ��false�������Ƿ���Ҫ����gps��������Ĭ����Ҫ
        option.setIsNeedLocationDescribe(true);//��ѡ��Ĭ��false�������Ƿ���Ҫλ�����廯�����������BDLocation.getLocationDescribe��õ�����������ڡ��ڱ����찲�Ÿ�����
        option.setIsNeedLocationPoiList(true);//��ѡ��Ĭ��false�������Ƿ���ҪPOI�����������BDLocation.getPoiList��õ�
        mLocationClient.setLocOption(option);
    }
}
