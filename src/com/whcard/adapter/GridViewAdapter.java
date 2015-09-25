package com.whcard.adapter;

import java.util.List;

import com.loopj.android.image.SmartImageView;
import com.whcard.main.R;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class GridViewAdapter extends BaseAdapter {
	private List<Object> list;
	LayoutInflater mInflater;
	int flag;
	
	public GridViewAdapter(List<Object> list, Context context,int flag) {
		this.list = list;
		this.mInflater = LayoutInflater.from(context);
		this.flag=flag;
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
		GridItemHolder giHolder;
		if (convertView == null) {
			if (flag==0) {
				convertView = mInflater.inflate(R.layout.item_grid, null);
				giHolder = new GridItemHolder();
				giHolder.item_grid_imageView = (SmartImageView) convertView
						.findViewById(R.id.item_grid_imageView);
			} else {
				convertView = mInflater.inflate(R.layout.item_grid_imgwall, null);
				giHolder = new GridItemHolder();
				giHolder.item_grid_imageView = (SmartImageView) convertView
						.findViewById(R.id.item_grid_imgwall);
			}
			

			convertView.setTag(giHolder);
		} else {
			giHolder = (GridItemHolder) convertView.getTag();
		}
		
		if (list.get(position) instanceof Bitmap) {
			giHolder.item_grid_imageView.setImageBitmap((Bitmap)list.get(position) );
			return convertView;
		} else {
			giHolder.item_grid_imageView.setImageUrl((String) list.get(position));
			return convertView;
		}
		
	}

	public void onDateChange(List<Object> list) {
		this.list = list;
		this.notifyDataSetChanged();
	}
}
