package com.whcard.adapter;

import java.util.List;

import com.whcard.bean.DemandItemBean;
import com.whcard.main.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HouseDemandAdapter extends BaseAdapter {

	List<DemandItemBean> list;
	LayoutInflater mInflater;
		
	public HouseDemandAdapter(List<DemandItemBean> list,Context context) {
		super();
		this.list = list;
		this.mInflater = LayoutInflater.from(context);
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
		HouseDemandHolder hdHolder;
		if (convertView==null) {
			convertView=mInflater.inflate(R.layout.item_house_demand, null);
			hdHolder=new HouseDemandHolder();
			hdHolder.item_house_demand_address=(TextView) convertView.findViewById(R.id.item_house_demand_address);
			hdHolder.item_house_demand_title=(TextView) convertView.findViewById(R.id.item_house_demand_title);
			hdHolder.item_house_demand_rental=(TextView) convertView.findViewById(R.id.item_house_demand_rental);
			hdHolder.item_house_demand_time=(TextView) convertView.findViewById(R.id.item_house_demand_time);
			convertView.setTag(hdHolder);
		}else {
			hdHolder=(HouseDemandHolder) convertView.getTag();
		}
		hdHolder.item_house_demand_address.setText(list.get(position).getItem_house_demand_address());
		hdHolder.item_house_demand_title.setText(list.get(position).getItem_house_demand_title());
		hdHolder.item_house_demand_rental.setText(list.get(position).getItem_house_demand_rental());
		hdHolder.item_house_demand_time.setText(list.get(position).getItem_house_demand_time());
		
		return convertView;
	}

	public void onDateChange(List<DemandItemBean> list) {
		this.list = list;
		this.notifyDataSetChanged();		
	}

}
