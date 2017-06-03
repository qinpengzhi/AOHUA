package com.example.aohua;

import java.util.ArrayList;

import com.aohua.model.Order;
import com.aohua.model.Se_Order;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderListViewAdapter extends BaseAdapter{
	private ArrayList<Order> orderList=null;
	private Context context=null;
	//构造函数
	public OrderListViewAdapter(ArrayList<Order> orderList,Context context) {
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
		
		TextView order_list_Code=(TextView) view.findViewById(R.id.order_list_Code);
		TextView order_list_Name=(TextView) view.findViewById(R.id.order_list_Name);
		TextView order_list_Writetime=(TextView) view.findViewById(R.id.order_list_Writetime);
		TextView order_list_id=(TextView) view.findViewById(R.id.order_list_id);
		TextView order_list_state=(TextView) view.findViewById(R.id.order_list_state);
		Order order=(Order) getItem(position);
		if(order!=null){
			//将订单变成列表形式
			order_list_Code.setText("单号："+order.getCode());
			order_list_Name.setText(order.getName());
			order_list_Writetime.setText(order.getWriteDate());
			order_list_id.setText(order.getId()+"");
			if(order.getState()<=1){
				order_list_state.setText("待审核");
			}else if(order.getState()==3){
				order_list_state.setText("已生效");
				order_list_state.setTextColor(Color.RED);
			}else if(order.getState()==4){
				order_list_state.setText("已作废");
				order_list_state.setTextColor(Color.RED);
			}
		}
		return view;
	}

}
