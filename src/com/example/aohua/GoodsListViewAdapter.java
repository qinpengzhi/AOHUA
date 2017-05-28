package com.example.aohua;

import java.util.ArrayList;
import com.aohua.model.Se_OrderDt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GoodsListViewAdapter extends BaseAdapter{
	private ArrayList<Se_OrderDt> goodsList=null;
	private Context context=null;
	//构造函数
	public GoodsListViewAdapter(ArrayList<Se_OrderDt> goodsList,Context context) {
		this.context=context;
		this.goodsList=goodsList;
	}
	@Override
	public int getCount() {
		return goodsList==null?0:goodsList.size();
	}

	@Override
	public Object getItem(int position) {
		return goodsList==null?null:goodsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater layoutInflater=LayoutInflater.from(this.context);
		View view=layoutInflater.inflate(R.layout.goods_list, null);
		
		TextView goodslist_GoodsName=(TextView) view.findViewById(R.id.goodslist_GoodsName);
		TextView goodslist_Number=(TextView) view.findViewById(R.id.goodslist_Number);
		TextView goodslist_Money=(TextView) view.findViewById(R.id.goodslist_Money);
		
		Se_OrderDt se_orderdt=(Se_OrderDt) getItem(position);
		if(se_orderdt!=null){
			//将订单变成列表形式
			goodslist_GoodsName.setText(se_orderdt.getGoodsName());
			goodslist_Number.setText("×"+se_orderdt.getNumber().toString());
			goodslist_Money.setText(se_orderdt.getMoney().toString());
		}
		return view;
	}

}
