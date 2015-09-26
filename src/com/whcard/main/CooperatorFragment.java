package com.whcard.main;

import java.util.ArrayList;
import java.util.List;

import com.whcard.adapter.CooperarorAdapter;
import com.whcard.bean.DiscountBean;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class CooperatorFragment extends Fragment {
	private View view;
	private List<DiscountBean> list;
	private ListView listView;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_cooperator, container, false);
		initData();
		CooperarorAdapter adapter=new CooperarorAdapter(getActivity(), list);
		listView.setAdapter(adapter);
		return view;
	}
	
	
	private void initData() {
		listView=(ListView) view.findViewById(R.id.id_co_listview);
		list=new ArrayList<DiscountBean>();
		for (int i = 0; i < 10; i++) {
			DiscountBean discountBean=new DiscountBean();
			discountBean.setDiscount_img("discount_img");
			discountBean.setCo_name("中百仓储"+i);
			discountBean.setCo_tel("13"+i+"9836425"+i);
			discountBean.setDiscount_info("暑期促销，全场五折！");
			discountBean.setDiscount_address("光谷分店");
			list.add(discountBean);
		}
	}

}
