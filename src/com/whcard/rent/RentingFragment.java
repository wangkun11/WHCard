package com.whcard.rent;

import com.whcard.info.EditInfoActivity;
import com.whcard.main.R;
import com.whcard.rent.PublishHouseActivity.MyAsyncTask;
import com.whcard.util.UserType;
import com.whcard.util.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class RentingFragment extends Fragment implements OnClickListener{
	
	private View view;
	private ImageButton btnSource;
	private ImageButton btnDemand;
	private ImageButton btnPublishSource;
	private ImageButton btnPublishDemand;
	private ImageButton btnMyHouse;
	private ImageButton btnMyDemand;

	private Boolean isTransfer=false;	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_renting, container, false);

		initView();	
		isTransfer=setUserType();
		initEvent();
		return view;
	}
	private void initEvent() {
		btnSource.setOnClickListener(this);
		btnDemand.setOnClickListener(this);
		btnPublishSource.setOnClickListener(this);
		btnPublishDemand.setOnClickListener(this);
		btnMyHouse.setOnClickListener(this);
		btnMyDemand.setOnClickListener(this);
	}

	private void initView() {
		btnSource=(ImageButton) view.findViewById(R.id.rent_btn_source);
		btnDemand=(ImageButton) view.findViewById(R.id.rent_btn_demand);
		btnPublishDemand=(ImageButton) view.findViewById(R.id.rent_btn_publishdemand);
		btnPublishSource=(ImageButton) view.findViewById(R.id.rent_btn_publishsource);
		btnMyHouse=(ImageButton) view.findViewById(R.id.rent_btn_myhouse);
		btnMyDemand=(ImageButton) view.findViewById(R.id.rent_btn_mydemand);

	}
	
	@Override
	public void onClick(View v) {
		Intent intent;
		String isRegist=Util.getUserInfo(getActivity(), "isRegist");
		int userType=Integer.valueOf(Util.getUserInfo(getActivity(), "userType"));
		switch (v.getId()) {
		case R.id.rent_btn_source:
			intent=new Intent(getActivity(),HouseSourceActivity.class);
			startActivity(intent);
			break;
		case R.id.rent_btn_demand:
			intent=new Intent(getActivity(),HouseDemandActivity.class);
			startActivity(intent);
			break;
		case R.id.rent_btn_publishdemand:
			if (userType==UserType.HouseOwner) {
				Toast.makeText(getActivity(), "您以房主身份登录，无法发布需求", Toast.LENGTH_SHORT).show();
				break;
			}
			if ("true".equals(isRegist)) {
				intent=new Intent(getActivity(),PublishDemanActivity.class);
				Bundle bundle=new Bundle();
				bundle.putSerializable("demandContent", null);
				intent.putExtras(bundle);
				startActivity(intent);
			}else {			
				AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(getActivity());
				dialogBuilder.setMessage("没有完整注册过不能发布房源，现在完善？");
				dialogBuilder.setPositiveButton("是的", new DialogInterface.OnClickListener() {			
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent1=new Intent(getActivity(), EditInfoActivity.class);
						startActivity(intent1);
					}
				});
				dialogBuilder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				});
				AlertDialog dialog=dialogBuilder.create();
				dialog.show();			
			}				
			break;		
		case R.id.rent_btn_publishsource:
			if (userType==UserType.Transfer) {
				Toast.makeText(getActivity(), "您以流动人员身份登录，无法发布房源", Toast.LENGTH_SHORT).show();
				break;
			}
			if ("true".equals(isRegist)) {
				intent=new Intent(getActivity(),PublishHouseActivity.class);
				Bundle bundle=new Bundle();
				bundle.putSerializable("houseContent", null);
				intent.putExtras(bundle);
				startActivity(intent);
			}else {			
				AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(getActivity());
				dialogBuilder.setMessage("没有完整注册过不能发布房源，现在完善？");
				dialogBuilder.setPositiveButton("是的", new DialogInterface.OnClickListener() {			
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent1=new Intent(getActivity(), EditInfoActivity.class);
						startActivity(intent1);
					}
				});
				dialogBuilder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				});
				AlertDialog dialog=dialogBuilder.create();
				dialog.show();
				
			}		
			break;
		case R.id.rent_btn_mydemand:
			if (userType==UserType.HouseOwner) {
				Toast.makeText(getActivity(), "您以房主身份登录，无法查看我的需求", Toast.LENGTH_SHORT).show();
				break;
			}
			if ("true".equals(isRegist)) {
				intent=new Intent(getActivity(),MyDemandActivity.class);
				startActivity(intent);
			}else {			
				AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(getActivity());
				dialogBuilder.setMessage("没有完整注册过,您没有发布过房源，现在完善？");
				dialogBuilder.setPositiveButton("是的", new DialogInterface.OnClickListener() {			
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent1=new Intent(getActivity(), EditInfoActivity.class);
						startActivity(intent1);
					}
				});
				dialogBuilder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				});
				AlertDialog dialog=dialogBuilder.create();
				dialog.show();
				
			}
			break;
		case R.id.rent_btn_myhouse:
			if (userType==UserType.Transfer) {
				Toast.makeText(getActivity(), "您以流动人员身份登录，无法查看我的房源", Toast.LENGTH_SHORT).show();
				break;
			}
			if ("true".equals(isRegist)) {
				intent=new Intent(getActivity(),MyHouseActivity.class);
				startActivity(intent);
			}else {			
				AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(getActivity());
				dialogBuilder.setMessage("没有完整注册过,您没有发布过房源，现在完善？");
				dialogBuilder.setPositiveButton("是的", new DialogInterface.OnClickListener() {			
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent1=new Intent(getActivity(), EditInfoActivity.class);
						startActivity(intent1);
					}
				});
				dialogBuilder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				});
				AlertDialog dialog=dialogBuilder.create();
				dialog.show();
				
			}
			break;
		default:
			break;
		}		
	}
	private Boolean setUserType() {
		SharedPreferences userInfo=getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
		int userType=userInfo.getInt("userType", 4);
		Log.d("UserType", userType+"");
		if (userType==0) {
			return true;
		}else {
			return false;
		}
	}
}
