package com.whcard.adapter;

import java.util.List;

import com.loopj.android.image.SmartImageView;
import com.whcard.bean.HouseItemBean;
import com.whcard.main.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HouseSourceAdapter extends BaseAdapter {

	List<HouseItemBean> list;
	LayoutInflater mInflater;

	public HouseSourceAdapter(Context context,List<HouseItemBean> list) {
		mInflater=LayoutInflater.from(context);
		this.list = list;		
	}

	public void onDateChange(List<HouseItemBean> list) {
		this.list = list;
		this.notifyDataSetChanged();
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
		HouseSourceHolder hsHolder;
		if (convertView==null) {
			convertView=mInflater.inflate(R.layout.item_house_source, null);
			hsHolder=new HouseSourceHolder();
			hsHolder.house_item_img=(SmartImageView) convertView.findViewById(R.id.house_item_img);
			hsHolder.house_item_title=(TextView) convertView.findViewById(R.id.house_item_title);
			hsHolder.house_item_add=(TextView) convertView.findViewById(R.id.house_item_add);
			hsHolder.house_item_rental=(TextView) convertView.findViewById(R.id.house_item_rental);

			convertView.setTag(hsHolder);
		}else {
			hsHolder = (HouseSourceHolder) convertView.getTag();
		}
		// 设置图片内容:根据url
		String imgPath = list.get(position).getHouse_item_img();
		hsHolder.house_item_img.setImageUrl(imgPath);
	//	Log.d("list的图片path", imgPath);
		// 设置文字内容
		hsHolder.house_item_title.setText(list.get(position).getHouse_item_title());
		hsHolder.house_item_add.setText(list.get(position).getHouse_item_add());
		hsHolder.house_item_rental.setText(list.get(position).getHouse_item_rental());

		return convertView;
	}
}
