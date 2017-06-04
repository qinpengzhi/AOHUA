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

import com.aohua.model.Order;
import com.aohua.model.Se_Order;
import com.aohua.model.Se_OrderDt;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class OrderdetailsActivity extends Activity implements OnClickListener{
	private TextView orderdetails_WriteDate;
	private TextView orderdetails_WritePersonName;
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
	private String parenturl="http://120.25.73.114:8080/AOHUAServlet/"; 
	private String detailsorderid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orderdetails);
		Intent intent=getIntent();
		final String orderid=intent.getStringExtra("orderid");
		detailsorderid=intent.getStringExtra("orderid");
		findViewById(R.id.orderdetails_state3).setOnClickListener(this);
		findViewById(R.id.orderdetails_state4).setOnClickListener(this);
		//从服务器端获取订单数据
		new Thread(){
			public void run() {
				String target=parenturl+"getse_orderdetails";
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
				String target=parenturl+"getse_orderdtdetails";
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
		orderdetails_back=(ImageView) findViewById(R.id.orderdetails_img_cancel);
		orderdetails_back.setOnClickListener(new OnClickListener() {			
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
				Toast.makeText(OrderdetailsActivity.this,"获取数据失败！", Toast.LENGTH_LONG).show();
			}else{
				try {
					JSONArray json = new JSONArray( msg.obj.toString());
					//将销售订单数据存入  然后映射到order_list中
					JSONObject temp = (JSONObject) json.get(0);  
					orderdetails_WriteDate=(TextView) findViewById(R.id.orderdetails_WriteDate);
					orderdetails_WritePersonName=(TextView) findViewById(R.id.orderdetails_WritePersonName);
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
					if(!temp.get("WritePersonName").equals(null))
						orderdetails_WritePersonName.setText((CharSequence) temp.get("WritePersonName"));
					if(!temp.get("WriteDate").equals(null))
						orderdetails_WriteDate.setText((CharSequence) temp.get("WriteDate"));
					if(!temp.get("OrderCode").equals(null))
						orderdetails_OrderCode.setText((CharSequence) temp.get("OrderCode"));
					if(!temp.get("CustName").equals(null))
						orderdetails_CustName.setText((CharSequence) temp.get("CustName"));
					if(!temp.get("DeliveryDate").equals(null))
						orderdetails_DeliveryDate.setText((CharSequence) temp.get("DeliveryDate"));
					if(!temp.get("SignDate").equals(null))
						orderdetails_SignDate.setText((CharSequence) temp.get("SignDate"));
					if(!temp.get("DeptName").equals(null))
						orderdetails_DeptName.setText((CharSequence) temp.get("DeptName"));
					if(!temp.get("DeliveryAddr").equals(null))
						orderdetails_DeliveryAddr.setText((CharSequence) temp.get("DeliveryAddr"));	
					if(!temp.get("TransportName").equals(null))
						orderdetails_TransportName.setText((CharSequence) temp.get("TransportName"));
					if(!temp.get("SettleName").equals(null))
						orderdetails_SettleName.setText((CharSequence) temp.get("SettleName"));
					if(!temp.get("Freight").equals(null))
						orderdetails_Freight.setText(temp.get("Freight")+"");
					if(!temp.get("SignAddr").equals(null))
						orderdetails_SignAddr.setText((CharSequence) temp.get("SignAddr"));
					if(!temp.get("ContractCode").equals(null))
						orderdetails_ContractCode.setText((CharSequence) temp.get("ContractCode"));
					if(!temp.get("ReceDays").equals(null))
						orderdetails_ReceDays.setText(temp.get("ReceDays")+"");
					if(!temp.get("SellerName").equals(null))
						orderdetails_SellerName.setText((CharSequence) temp.get("SellerName"));	
					if(!temp.get("AuditOpinion").equals(null))
						((EditText)findViewById(R.id.orderdetails_AuditOpinion)).setText((CharSequence) temp.get("AuditOpinion"));
					SharedPreferences sharedPreferences=getSharedPreferences("user", MODE_PRIVATE);
					if(sharedPreferences.getString("examine", "").equals("0")){
						//如果没有权限就禁止审核
						findViewById(R.id.orderdetails_state3).setBackgroundColor(getResources().getColor(R.color.darkgray));
						findViewById(R.id.orderdetails_state3).setEnabled(false);
						findViewById(R.id.orderdetails_state4).setBackgroundColor(getResources().getColor(R.color.darkgray));
						findViewById(R.id.orderdetails_state4).setEnabled(false);
					}
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
				Toast.makeText(OrderdetailsActivity.this,"获取数据失败！", Toast.LENGTH_LONG).show();
			}else{
				try {
					JSONArray json = new JSONArray( msg.obj.toString());
					//将销售订单数据存入  然后映射到order_list中
					for(int i=0;i<json.length();i++){
						JSONObject temp = (JSONObject) json.get(i);  
						LinearLayout linearLayout=new LinearLayout(OrderdetailsActivity.this);
						linearLayout.setOrientation(LinearLayout.VERTICAL);
						linearLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
						View view=new View(OrderdetailsActivity.this);
						view.setBackgroundColor(getResources().getColor(R.color.darkgray));
						linearLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1));
						linearLayout.addView(view);
						TextView textView1=new TextView(OrderdetailsActivity.this);
						textView1.setText(temp.get("GoodsName")+"");
						textView1.setTextColor(getResources().getColor(R.color.black));
						textView1.setTextSize(20);
						textView1.setPadding(20, 0, 0, 0);
						linearLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
						linearLayout.addView(textView1);
						TextView textView2=new TextView(OrderdetailsActivity.this);
						linearLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
						textView2.setText("数量:"+temp.get("Number")+" 价格:"+temp.get("Price")
								+" 金额:"+ temp.get("Money")+" 交货日期:"+temp.get("DtDeliveryDate")
								+" 备注:"+ temp.get("DtNotes")+" 包装量:"+temp.get("PKGNum"));
						textView2.setTextSize(12);
						textView2.setPadding(20, 0, 0, 0);
						linearLayout.addView(textView2);
						((LinearLayout)findViewById(R.id.orderdetails_scroll_child_linear)).addView(linearLayout);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			super.handleMessage(msg);
		}
	};
	//提交更新结果成功与否
	private Handler handler3=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what==0){
				Toast.makeText(OrderdetailsActivity.this,"审核失败！", Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(OrderdetailsActivity.this,"审核成功！", Toast.LENGTH_LONG).show();
			}
			super.handleMessage(msg);
		}
	};
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
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.orderdetails_state3){
			//如果同意
			new Thread(){
				@SuppressWarnings("deprecation")
				public void run() {
					String target=parenturl+"updatese_order";
					HttpClient httpClient=new DefaultHttpClient();
					HttpPost httpRequest=new HttpPost(target);

					List<NameValuePair> params=new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("orderid",detailsorderid ));
					params.add(new BasicNameValuePair("state",3+"" ));
					params.add(new BasicNameValuePair("AuditOpinion",((EditText)findViewById(R.id.orderdetails_AuditOpinion)).getText().toString()));
					try{
						httpRequest.setEntity(new UrlEncodedFormEntity(params,"utf-8"));
						HttpResponse httpResponse=httpClient.execute(httpRequest);
						if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
							//如果成功
							Message message=Message.obtain();
							message.what=1;
							handler3.sendMessage(message);
						}else{
							//如果网络连接失败
							Message message=Message.obtain();
							message.what=0;
							handler3.sendMessage(message);
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				};
			}.start();

		}else if(v.getId()==R.id.orderdetails_state4){
			//如果不同意
			//如果同意
			new Thread(){
				@SuppressWarnings("deprecation")
				public void run() {
					String target=parenturl+"updatese_order";
					HttpClient httpClient=new DefaultHttpClient();
					HttpPost httpRequest=new HttpPost(target);

					List<NameValuePair> params=new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("orderid",detailsorderid ));
					params.add(new BasicNameValuePair("state",4+"" ));
					params.add(new BasicNameValuePair("AuditOpinion",((EditText)findViewById(R.id.orderdetails_AuditOpinion)).getText().toString()));
					try{
						httpRequest.setEntity(new UrlEncodedFormEntity(params,"utf-8"));
						HttpResponse httpResponse=httpClient.execute(httpRequest);
						if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
							//如果成功
							Message message=Message.obtain();
							message.what=1;
							handler3.sendMessage(message);
						}else{
							//如果网络连接失败
							Message message=Message.obtain();
							message.what=0;
							handler3.sendMessage(message);
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				};
			}.start();
		}

	}
}
