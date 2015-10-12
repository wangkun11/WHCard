package com.whcard.info;

import com.whcard.main.LoginActivity;
import com.whcard.main.R;
import com.whcard.util.Util;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MineFragment extends Fragment implements OnClickListener{
	
	private View view;
	private Button mine_show_info;
	private Button mine_feedback;
	private Button mine_aboutsoft;
	private Button mine_instruction;
	private Button mine_update;
	private Button mine_share;
	private Button mine_exit;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.fragment_transferinfo, container, false);
		
		initView();
		initListener();
		return view;
	}

	private void initListener() {
		mine_show_info.setOnClickListener(this);
		
		mine_feedback.setOnClickListener(this);
		mine_aboutsoft.setOnClickListener(this);
		mine_instruction.setOnClickListener(this);
		mine_update.setOnClickListener(this);
		mine_share.setOnClickListener(this);
		mine_exit.setOnClickListener(this);

	}

	private void initView() {
		mine_show_info=(Button) view.findViewById(R.id.mine_show_info);
		
		mine_feedback=(Button) view.findViewById(R.id.mine_feedback);
		mine_aboutsoft=(Button) view.findViewById(R.id.mine_aboutsoft);
		mine_instruction=(Button) view.findViewById(R.id.mine_instruction);
		mine_update=(Button) view.findViewById(R.id.mine_update);
		mine_share=(Button) view.findViewById(R.id.mine_share);
		mine_exit=(Button) view.findViewById(R.id.mine_exit);
	}

	@Override
	public void onClick(View v) {
		Intent intent=null;
		switch (v.getId()) {		
		case R.id.mine_show_info:
			//如果没有完整注册，提示用户先进行完整注册
			//先拿到sharePreference中的值
			String isRegist=Util.getUserInfo(getActivity(), "isRegist");
			Log.d("Transferinfo.isRegist", isRegist);
			if ("true".equals(isRegist)) {
				intent=new Intent(getActivity(), ShowInfoActivity.class);
				startActivity(intent);
			}else {
				
				AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(getActivity());
				dialogBuilder.setMessage("没有完整注册过，现在完善？");
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
		case R.id.mine_feedback:
			intent =new Intent(getActivity(),FeedbackActivity.class);
			startActivity(intent);
			break;
		case R.id.mine_aboutsoft:
			intent =new Intent(getActivity(),SoftInfoActivity.class);
			startActivity(intent);
			break;
		case R.id.mine_update:
			Toast.makeText(getActivity(), "软件目前已经是最新版本！", Toast.LENGTH_SHORT).show();
			break;
		case R.id.mine_instruction:
			intent =new Intent(getActivity(),InstructionActivity.class);
			startActivity(intent);
			break;
		case R.id.mine_share:
			Toast.makeText(getActivity(), "软件已分享给您的好友！", Toast.LENGTH_SHORT).show();
			break;
		case R.id.mine_exit:
			Toast.makeText(getActivity(), "软件已分享给您的好友！", Toast.LENGTH_SHORT).show();
			intent = new Intent(getActivity(),LoginActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

}
