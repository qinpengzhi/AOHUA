package com.example.aohua;

import java.util.ArrayList;
import java.util.List;

import com.aohua.model.Se_Order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ShowoderActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showorder);
		//为viewlist生成假数据
		ArrayList<Se_Order> orderList=new ArrayList<Se_Order>();
		Se_Order se_order=new Se_Order();
		se_order.setCustName("浙江大学常州技术工研院");
		se_order.setOrderCode("HYU987656799");
		se_order.setWriteDate("2017-5-15");
		for(int i=0;i<30;i++){
			orderList.add(se_order);
		}
		OrderListViewAdapter orderListViewAdapter=new OrderListViewAdapter(orderList, this);
		ListView listView=(ListView) findViewById(R.id.viewlist_orderlist);
		listView.setAdapter(orderListViewAdapter);
		//给listView添加监听事件
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//先根据oderCode查找整个Se_order
				String orderCode=(String) ((TextView)view.findViewById(R.id.orderCode)).getText() ;
				//将单号传过去				
				Intent intent=new Intent(ShowoderActivity.this,OrderdetailsActivity.class);
				intent.putExtra("orderCode",orderCode);
				startActivity(intent);				
			}
		});
		//为spinner加载订单类型
		Spinner order_type_spinner=(Spinner) findViewById(R.id.order_type_spinner);
		List<String> order_type_list=new ArrayList<String>();
		order_type_list.add("销售订单");
		order_type_list.add("采购订单");
		ArrayAdapter<String> order_type_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, order_type_list);
		order_type_spinner.setAdapter(order_type_adapter);
	}
}

