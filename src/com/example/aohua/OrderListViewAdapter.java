package com.example.aohua;

import java.util.ArrayList;

import com.aohua.model.Se_Order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderListViewAdapter extends BaseAdapter{
	private ArrayList<Se_Order> orderList=null;
	private Context context=null;
	//构造函数
	public OrderListViewAdapter(ArrayList<Se_Order> orderList,Context context) {
		this.context=context;
		this.orderList=orderList;
	}
	@Override
	public int getCount() {
		return orderList==null?0:orderList.size();
	}

	@Override
	public Object getItem(int position) {
		return orderList==null?null:orderList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater layoutInflater=LayoutInflater.from(this.context);
		View view=layoutInflater.inflate(R.layout.order_list, null);
		
		TextView orderCodeView=(TextView) view.findViewById(R.id.orderCode);
		TextView customerView=(TextView) view.findViewById(R.id.customer);
		TextView writetimeView=(TextView) view.findViewById(R.id.Writetime);
		
		Se_Order se_order=(Se_Order) getItem(position);
		if(se_order!=null){
			//将订单变成列表形式
			orderCodeView.setText("单号："+se_order.getOrderCode());
			customerView.setText("客户："+se_order.getCustName());
			writetimeView.setText(se_order.getWriteDate());
		}
		return view;
	}

}
