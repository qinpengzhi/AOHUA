package com.example.aohua;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OrderdetailsActivity1 extends AddActivity{
	private String parenturl="http://10.132.23.147:8080/AOHUAServlet/"; 
	private TextView orderdetails1_WriteDate;
	private TextView orderdetails1_WritePersonName;
	private TextView orderdetails1_PurOrderCode;
	private TextView orderdetails1_BuyerID;
	private TextView orderdetails1_AogDate;
	private TextView orderdetails1_SignDate;
	private TextView orderdetails1_DeliveryAddr;
	private TextView orderdetails1_SettleName;
	private TextView orderdetails1_SignAddr;
	private TextView orderdetails1_ContractCode;
	private TextView orderdetails1_PayDays;
	private TextView orderdetails1_SupplierID;
	private ImageView orderdetails1_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orderdetails1);
		Intent intent=getIntent();
		final String orderid=intent.getStringExtra("orderid");
		//从服务器端获取订单数据
		new Thread(){
			public void run() {
				String target=parenturl+"getpu_purorderdetails";
				HttpClient httpClient=new DefaultHttpClient();
				HttpPost httpRequest=new HttpPost(target);

				List<NameValuePair> params=new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("orderid",orderid ));
				try{
					httpRequest.setEntity(new UrlEncodedFormEntity(params,"utf-8"));
					HttpResponse httpResponse=httpClient.execute(httpRequest);
					if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
						//如果成功
						Message message=Message.obtain();
						message.what=1;
						message.obj=EntityUtils.toString(httpResponse.getEntity(),"utf-8");
						handler1.sendMessage(message);
					}else{
						//如果网络连接失败
						Message message=Message.obtain();
						message.what=0;
						handler1.sendMessage(message);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			};
		}.start();
		//从服务器端获取订单详情数据
		new Thread(){
			public void run() {
				String target=parenturl+"getpu_purorderdtdetails";
				HttpClient httpClient=new DefaultHttpClient();
				HttpPost httpRequest=new HttpPost(target);

				List<NameValuePair> params=new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("orderid",orderid ));
				try{
					httpRequest.setEntity(new UrlEncodedFormEntity(params,"utf-8"));
					HttpResponse httpResponse=httpClient.execute(httpRequest);
					if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
						//如果成功
						Message message=Message.obtain();
						message.what=1;
						message.obj=EntityUtils.toString(httpResponse.getEntity(),"utf-8");
						handler2.sendMessage(message);
					}else{
						//如果网络连接失败
						Message message=Message.obtain();
						message.what=0;
						handler2.sendMessage(message);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			};
		}.start();
		//给返回上一层添加点击事件
		orderdetails1_back=(ImageView) findViewById(R.id.orderdetails1_img_cancel);
		orderdetails1_back.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				finish();				
			}
		});
	}
	//对传过来的数据进行处理展示在前台界面上
	private Handler handler1=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what==0){
				Toast.makeText(OrderdetailsActivity1.this,"获取数据失败！", Toast.LENGTH_LONG).show();
			}else{
				try {
					JSONArray json = new JSONArray( msg.obj.toString());
					//将销售订单数据存入  然后映射到order_list中
					JSONObject temp = (JSONObject) json.get(0);  
					orderdetails1_WriteDate=(TextView) findViewById(R.id.orderdetails1_WriteDate);
					orderdetails1_WritePersonName=(TextView) findViewById(R.id.orderdetails1_WritePersonName);
					orderdetails1_PurOrderCode=(TextView) findViewById(R.id.orderdetails1_PurOrderCode);
					orderdetails1_BuyerID=(TextView) findViewById(R.id.orderdetails1_BuyerName);
					orderdetails1_AogDate=(TextView) findViewById(R.id.orderdetails1_AogDate);
					orderdetails1_SignDate=(TextView) findViewById(R.id.orderdetails1_SignDate);
					orderdetails1_DeliveryAddr=(TextView) findViewById(R.id.orderdetails1_DeliveryAddr);
					orderdetails1_SettleName=(TextView) findViewById(R.id.orderdetails1_SettleName);
					orderdetails1_SignAddr=(TextView) findViewById(R.id.orderdetails1_SignAddr);
					orderdetails1_ContractCode=(TextView) findViewById(R.id.orderdetails1_ContractCode);
					orderdetails1_PayDays=(TextView) findViewById(R.id.orderdetails1_PayDays);
					orderdetails1_SupplierID=(TextView) findViewById(R.id.orderdetails1_SupplierName);
					if(!temp.get("WritePersonName").equals(null))
						orderdetails1_WritePersonName.setText((CharSequence) temp.get("WritePersonName"));
					if(!temp.get("WriteDate").equals(null))
						orderdetails1_WriteDate.setText((CharSequence) temp.get("WriteDate"));
					if(!temp.get("PurOrderCode").equals(null))
						orderdetails1_PurOrderCode.setText((CharSequence) temp.get("PurOrderCode"));
					if(!temp.get("BuyerName").equals(null))
						orderdetails1_BuyerID.setText((CharSequence) temp.get("BuyerName"));
					if(!temp.get("AogDate").equals(null))
						orderdetails1_AogDate.setText((CharSequence) temp.get("AogDate"));
					if(!temp.get("SignDate").equals(null))
						orderdetails1_SignDate.setText((CharSequence) temp.get("SignDate"));
					if(!temp.get("DeliveryAddr").equals(null))
						orderdetails1_DeliveryAddr.setText((CharSequence) temp.get("DeliveryAddr"));	
					if(!temp.get("SettleName").equals(null))
						orderdetails1_SettleName.setText((CharSequence) temp.get("SettleName"));
					if(!temp.get("SignAddr").equals(null))
						orderdetails1_SignAddr.setText((CharSequence) temp.get("SignAddr"));
					if(!temp.get("ContractCode").equals(null))
						orderdetails1_ContractCode.setText((CharSequence) temp.get("ContractCode"));
					if(!temp.get("PayDays").equals(null))
						orderdetails1_PayDays.setText(temp.get("PayDays")+"");
					if(!temp.get("SupplierName").equals(null))
						orderdetails1_SupplierID.setText((CharSequence) temp.get("SupplierName"));	
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			super.handleMessage(msg);
		}
	};
	//对传过来的订单明细数据进行处理展示在前台界面上
	private Handler handler2=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what==0){
				Toast.makeText(OrderdetailsActivity1.this,"获取数据失败！", Toast.LENGTH_LONG).show();
			}else{
				try {
					JSONArray json = new JSONArray( msg.obj.toString());
					//将销售订单数据存入  然后映射到order_list中
					for(int i=0;i<json.length();i++){
						JSONObject temp = (JSONObject) json.get(i);  
						LinearLayout linearLayout=new LinearLayout(OrderdetailsActivity1.this);
						linearLayout.setOrientation(LinearLayout.VERTICAL);
						linearLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
						View view=new View(OrderdetailsActivity1.this);
						view.setBackgroundColor(getResources().getColor(R.color.darkgray));
						linearLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1));
						linearLayout.addView(view);
						TextView textView1=new TextView(OrderdetailsActivity1.this);
						textView1.setText(temp.get("GoodsName")+"");
						textView1.setTextColor(getResources().getColor(R.color.black));
						textView1.setTextSize(20);
						textView1.setPadding(20, 0, 0, 0);
						linearLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
						linearLayout.addView(textView1);
						TextView textView2=new TextView(OrderdetailsActivity1.this);
						linearLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
						textView2.setText("数量:"+temp.get("Number")+" 价格:"+temp.get("Price")
								+" 金额:"+ temp.get("Money")+" 交货日期:"+temp.get("DtAogDate")
								+" 备注:"+ temp.get("DtNotes")+" 包装量:"+temp.get("PKGNum"));
						textView2.setTextSize(12);
						textView2.setPadding(20, 0, 0, 0);
						linearLayout.addView(textView2);
						((LinearLayout)findViewById(R.id.orderdetails1_scroll_child_linear)).addView(linearLayout);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			super.handleMessage(msg);
		}
	};
}
