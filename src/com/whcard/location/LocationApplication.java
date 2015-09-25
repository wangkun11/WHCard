package com.whcard.location;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.Poi;
import com.google.gson.Gson;
import com.whcard.bean.Location;
import com.whcard.main.LoginActivity;
import com.whcard.main.RegistActivity;
import com.whcard.net.GetUrl;
import com.whcard.net.HttpUtil;
import com.whcard.util.ResultStateCode;
import com.whcard.util.Util;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 主Application，所有百度定位SDK的接口说明请参迃线上文档：http://developer.baidu.com/map/loc_refer/index.html
 *
 * 百度定位SDK官方网站：http://developer.baidu.com/map/index.php?title=android-locsdk
 */
public class LocationApplication extends Application {
    public LocationClient mLocationClient;
    public MyLocationListener mMyLocationListener;
    
	private String l_longitude;
	private String l_latitude;
	
    @Override
    public void onCreate() {
        super.onCreate();
        mLocationClient = new LocationClient(this.getApplicationContext());
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
    }
    /**
     * 实现实时位置回调监听
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
   
            //sb.append("\nerror code : ");
            //获取定位类型: 参考 定位结果描述 相关的字段
            //sb.append(location.getLocType());
            
            sb.append("\nlatitude : ");
            l_latitude=String.valueOf(location.getLatitude());
            sb.append(l_latitude);
            
            sb.append("\nlontitude : ");
            l_longitude=String.valueOf(location.getLongitude());
            sb.append(l_longitude);
            
            //sb.append("\nradius : ");
            //获取定位精度,默认值0.0f
            //sb.append(location.getRadius());
           /* if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信恿
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请棿查网络是否鿚畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试睿重启手机");
            }
            sb.append("\nlocationdescribe : ");// 位置语义化信恿
            sb.append(location.getLocationDescribe());
            List<Poi> list = location.getPoiList();// POI信息
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }*/
            Log.i("百度地图返回数据：", sb.toString());
            MyAsyncTask myAsyncTask=new MyAsyncTask();
            myAsyncTask.execute();
        }


    }
    class MyAsyncTask extends AsyncTask<String, Integer, String>
	{
    	String jsonString;
		@Override
		protected void onPreExecute() {			
			Location location=new Location();
			location.setL_latitude(l_latitude);
			location.setL_longitude(l_longitude);
			location.setSfp_id(Util.getUserInfo(getApplicationContext(), "userId"));
			Gson gson=new Gson();
			jsonString=gson.toJson(location);
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String httpReturn=HttpUtil.httpClient(GetUrl.AddLocation, jsonString);
			return httpReturn;
		}
		
		@Override
		protected void onPostExecute(String result) {
			
		}	
	}
}
