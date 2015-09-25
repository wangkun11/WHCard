package com.whcard.adapter;

import java.util.List;

import com.whcard.bean.DiscountBean;
import com.whcard.main.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CooperarorAdapter extends BaseAdapter {

	LayoutInflater mInflater;
	List<DiscountBean> list;
	
	public CooperarorAdapter(Context context, List<DiscountBean> list) {
		mInflater=LayoutInflater.from(context);
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		CooperarorHolder cooperarorHolder;
		if (convertView==null) {
			convertView=mInflater.inflate(R.layout.item_cooperator, null);
			cooperarorHolder=new CooperarorHolder();
			cooperarorHolder.co_image=(ImageView) convertView.findViewById(R.id.item_co_image);
			cooperarorHolder.co_name=(TextView) convertView.findViewById(R.id.item_rent_house_title);
			cooperarorHolder.co_info=(TextView) convertView.findViewById(R.id.item_co_info);
			cooperarorHolder.co_address=(TextView) convertView.findViewById(R.id.item_co_address);
			cooperarorHolder.co_tel=(TextView) convertView.findViewById(R.id.item_co_tel);

			convertView.setTag(cooperarorHolder);
		}else {
			cooperarorHolder=(CooperarorHolder) convertView.getTag();
		}
		//设置图片内容
		String imgPath = list.get(position).getDiscount_img();
		//设置文字内容
		cooperarorHolder.co_name.setText(list.get(position).getCo_name());
		cooperarorHolder.co_info.setText(list.get(position).getDiscount_info());
		cooperarorHolder.co_address.setText(list.get(position).getDiscount_address());
		cooperarorHolder.co_tel.setText(list.get(position).getCo_tel());
		return convertView;
	}
	

}
