package com.example.aohua;

import java.util.ArrayList;

import com.aohua.model.Se_Order;
import com.aohua.model.Se_OrderDt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class OrderdetailsActivity extends Activity{
	private TextView orderdetails_WriteDate;
	private TextView orderdetails_OrderCode;
	private TextView orderdetails_CustName;
	private TextView orderdetails_DeliveryDate;
	private TextView orderdetails_SignDate;
	private TextView orderdetails_DeptName;
	private TextView orderdetails_DeliveryAddr;
	private TextView orderdetails_TransportName;
	private TextView orderdetails_SettleName;
	private TextView orderdetails_Freight;
	private TextView orderdetails_SignAddr;
	private TextView orderdetails_ContractCode;
	private TextView orderdetails_ReceDays;
	private TextView orderdetails_SellerName;
	private ImageView orderdetails_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orderdetails);
		Intent intent=getIntent();
		String orderCode=intent.getStringExtra("orderCode");		
		
		//给返回上一层添加点击事件
		orderdetails_back=(ImageView) findViewById(R.id.orderdetails_back);
		orderdetails_back.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				 finish();				
			}
		});
		//测试数据
		Se_Order  se_order=new Se_Order();
		se_order.setOrderCode(orderCode);
		se_order.setCustName("XXXXXX客户");
		se_order.setDeliveryDate("2017-5-20");//交货日期
		se_order.setWriteDate("2017-5-17");//制单日期
		se_order.setDeliveryAddr("杭州XXX街");//交货地址
		se_order.setTransportName("自提");//运输方式
		se_order.setSettleName("现汇");//结算方式
		se_order.setFreight(20.78);//运输费用
		se_order.setSignAddr("XXXX小巷");//签订地址
		se_order.setDeptName("销售部");
		se_order.setSignDate("2017-4-20");//签订日期
		se_order.setContractCode("20170220");//合同号
		se_order.setReceDays(30);
		se_order.setSellerName("大灰狼");
		
		orderdetails_WriteDate=(TextView) findViewById(R.id.orderdetails_WriteDate);
		orderdetails_OrderCode=(TextView) findViewById(R.id.orderdetails_OrderCode);
		orderdetails_CustName=(TextView) findViewById(R.id.orderdetails_CustName);
		orderdetails_DeliveryDate=(TextView) findViewById(R.id.orderdetails_DeliveryDate);
		orderdetails_SignDate=(TextView) findViewById(R.id.orderdetails_SignDate);
		orderdetails_DeptName=(TextView) findViewById(R.id.orderdetails_DeptName);
		orderdetails_DeliveryAddr=(TextView) findViewById(R.id.orderdetails_DeliveryAddr);
		orderdetails_TransportName=(TextView) findViewById(R.id.orderdetails_TransportName);
		orderdetails_SettleName=(TextView) findViewById(R.id.orderdetails_SettleName);
		orderdetails_Freight=(TextView) findViewById(R.id.orderdetails_Freight);
		orderdetails_SignAddr=(TextView) findViewById(R.id.orderdetails_SignAddr);
		orderdetails_ContractCode=(TextView) findViewById(R.id.orderdetails_ContractCode);
		orderdetails_ReceDays=(TextView) findViewById(R.id.orderdetails_ReceDays);
		orderdetails_SellerName=(TextView) findViewById(R.id.orderdetails_SellerName);		
		orderdetails_WriteDate.setText(se_order.getWriteDate());
		orderdetails_OrderCode.setText(se_order.getOrderCode());
		orderdetails_CustName.setText(se_order.getCustName());
		orderdetails_DeliveryDate.setText(se_order.getDeliveryDate());
		orderdetails_SignDate.setText(se_order.getSignDate());
		orderdetails_DeptName.setText(se_order.getDeptName());
		orderdetails_DeliveryAddr.setText(se_order.getDeliveryAddr());		
		orderdetails_TransportName.setText(se_order.getTransportName());
		orderdetails_SettleName.setText(se_order.getOrderCode());
		orderdetails_CustName.setText(se_order.getSettleName());
		orderdetails_Freight.setText(se_order.getFreight().toString());
		orderdetails_SignAddr.setText(se_order.getSignAddr());
		orderdetails_ContractCode.setText(se_order.getContractCode());
		orderdetails_ReceDays.setText(se_order.getReceDays().toString());
		orderdetails_SellerName.setText(se_order.getSellerName());
		
		//测试数据(产品相关)
		//为明细生成假数据
		ArrayList<Se_OrderDt> orderdtlist=new ArrayList<Se_OrderDt>();
		Se_OrderDt se_orderDt=new Se_OrderDt();
		se_orderDt.setNumber(6.0);
		se_orderDt.setGoodsName("电灯泡");
		se_orderDt.setMoney(2344.0);
		for(int i=0;i<5;i++){
			orderdtlist.add(se_orderDt);
		}
		GoodsListViewAdapter goodsListViewAdapter=new GoodsListViewAdapter(orderdtlist, this);
		ListView listView=(ListView) findViewById(R.id.orderdetail_goodslist);
		listView.setDivider(null);
		listView.setAdapter(goodsListViewAdapter);
		setListViewHeightBasedOnChildren(listView);
	}
	
	//这个是结局外面的scroll和里面的listview冲突问题（listview只显示一条数据）
	private void setListViewHeightBasedOnChildren(ListView listView) {

		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}
}
