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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AddorderActivity1 extends AddActivity implements OnTouchListener{
	private String parenturl="http://10.132.23.147:8080/AOHUAServlet/";
	private Calendar calendar;//用来装日期的
	private AlertDialog alert;
	private DatePickerDialog dialog;
	private Integer SettleID;//用来存储存储方式id的
	private Integer BuyerID;//用来存储采购员id
	private Integer SupplierID;//用来存储供应商id的	
	private JSONArray orderdetails_jsonarray1=new JSONArray();//存储订单明细的json串

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addorder1);
		this.findViewById(R.id.addorder1_PurOrderCode).setOnTouchListener(this);
		this.findViewById(R.id.addorder1_BuyerName).setOnTouchListener(this);
		this.findViewById(R.id.addorder1_SupplierName).setOnTouchListener(this);
		this.findViewById(R.id.addorder1_ContractCode).setOnTouchListener(this);
		this.findViewById(R.id.addorder1_SettleName).setOnTouchListener(this);
		this.findViewById(R.id.addorder1_PayDays).setOnTouchListener(this);
		this.findViewById(R.id.addorder1_SignAddr).setOnTouchListener(this);
		this.findViewById(R.id.addorder1_SignDate).setOnTouchListener(this);
		this.findViewById(R.id.addorder1_DeliveryAddr).setOnTouchListener(this);
		this.findViewById(R.id.addorder1_AogDate).setOnTouchListener(this);

		findViewById(R.id.addorder1_go_adddetails).setOnClickListener(this);
		findViewById(R.id.adddorder1_img_tick).setOnClickListener(this);
		findViewById(R.id.adddorder1_img_cancel).setOnClickListener(this);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		//采购订单号
		if(v.getId()==R.id.addorder1_PurOrderCode){
			alert=new AlertDialog.Builder(AddorderActivity1.this).create();
			final EditText edit_text=new EditText(this);
			alert.setView(edit_text);
			alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {}
			});
			alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialogIn, int which) {
					if(!edit_text.getText().toString().trim().equals(""))
						((TextView)findViewById(R.id.addorder1_PurOrderCode)).setText(edit_text.getText().toString().trim());;
				}
			});
			alert.show();
		}//采购员id
		else if(v.getId()==R.id.addorder1_BuyerName){
			new Thread(){
				public void run() {
					String target=parenturl+"getemployee";
					HttpClient httpClient=new DefaultHttpClient();
					HttpPost httpRequest=new HttpPost(target);
					try{
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
		}//供应商id
		else if(v.getId()==R.id.addorder1_SupplierName){
			new Thread(){
				public void run() {
					String target=parenturl+"getsupplier";
					HttpClient httpClient=new DefaultHttpClient();
					HttpPost httpRequest=new HttpPost(target);
					try{
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
		}else if(v.getId()==R.id.addorder1_ContractCode){
			alert=new AlertDialog.Builder(AddorderActivity1.this).create();
			final EditText edit_text=new EditText(this);
			alert.setView(edit_text);
			alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {}
			});
			alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialogIn, int which) {
					if(!edit_text.getText().toString().trim().equals(""))
						((TextView)findViewById(R.id.addorder1_ContractCode)).setText(edit_text.getText().toString().trim());;
				}
			});
			alert.show();
		}else if(v.getId()==R.id.addorder1_SettleName){
			new Thread(){
				public void run() {
					String target=parenturl+"getsettlemode";
					HttpClient httpClient=new DefaultHttpClient();
					HttpPost httpRequest=new HttpPost(target);
					try{
						HttpResponse httpResponse=httpClient.execute(httpRequest);
						if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
							//如果成功
							Message message=Message.obtain();
							message.what=1;
							message.obj=EntityUtils.toString(httpResponse.getEntity(),"utf-8");
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
		}else if(v.getId()==R.id.addorder1_PayDays){
			alert=new AlertDialog.Builder(AddorderActivity1.this).create();
			final EditText edit_text=new EditText(this);
			//只能输入数字
			edit_text.setInputType( InputType.TYPE_CLASS_NUMBER);
			alert.setView(edit_text);
			alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {}
			});
			alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialogIn, int which) {
					if(!edit_text.getText().toString().trim().equals(""))
						((TextView)findViewById(R.id.addorder1_PayDays)).setText(edit_text.getText().toString().trim());;
				}
			});
			alert.show();
		}//签订地址
		else if(v.getId()==R.id.addorder1_SignAddr){
			alert=new AlertDialog.Builder(AddorderActivity1.this).create();
			final EditText edit_text=new EditText(this);
			alert.setView(edit_text);
			alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {}
			});
			alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialogIn, int which) {
					if(!edit_text.getText().toString().trim().equals(""))
						((TextView)findViewById(R.id.addorder1_SignAddr)).setText(edit_text.getText().toString().trim());
				}
			});
			alert.show();
		}else if(v.getId()==R.id.addorder1_SignDate){
			calendar=Calendar.getInstance();
			dialog=new DatePickerDialog(AddorderActivity1.this,new DatePickerDialog.OnDateSetListener() {
				public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
					((TextView)findViewById(R.id.addorder1_SignDate)).setText(year+"-"+(month+1)+"-"+dayOfMonth);
				}
			}, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
			dialog.show();
		}else if(v.getId()==R.id.addorder1_DeliveryAddr){
			alert=new AlertDialog.Builder(AddorderActivity1.this).create();
			final EditText edit_text=new EditText(this);
			alert.setView(edit_text);
			alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {}
			});
			alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialogIn, int which) {
					if(!edit_text.getText().toString().trim().equals(""))
						((TextView)findViewById(R.id.addorder1_DeliveryAddr)).setText(edit_text.getText().toString().trim());
				}
			});
			alert.show();
		}else if(v.getId()==R.id.addorder1_AogDate){
			calendar=Calendar.getInstance();
			dialog=new DatePickerDialog(AddorderActivity1.this,new DatePickerDialog.OnDateSetListener() {
				public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
					((TextView)findViewById(R.id.addorder1_AogDate)).setText(year+"-"+(month+1)+"-"+dayOfMonth);
				}
			}, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
			dialog.show();
		}
		return false;
	}
	//采购员获取
	private Handler handler1=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what==0){
				Toast.makeText(AddorderActivity1.this,"网络连接失败！", Toast.LENGTH_LONG).show();
			}else{
				try {
					List<Integer> codelist=new ArrayList<Integer>();
					List<String> list=new ArrayList<String>();
					JSONArray json = new JSONArray( msg.obj.toString());
					for(int i=0;i<json.length();i++){
						JSONObject temp = (JSONObject) json.get(i);  
						//	departMentCode.add((Integer) temp.get("DeptID"));
						list.add(temp.getString("EmpName")); 
						codelist.add(temp.getInt("EmpID"));
					}
					Builder builder =new AlertDialog.Builder(AddorderActivity1.this);
					final String[] items=list.toArray(new String[list.size()]);
					final Integer[] codeitems=codelist.toArray(new Integer[codelist.size()]);
					builder.setItems(items, new DialogInterface.OnClickListener()  
					{  
						@Override  
						public void onClick(DialogInterface dialog, int which)  
						{  
							((TextView)findViewById(R.id.addorder1_BuyerName)).setText(items[which].toString().trim());
							BuyerID=codeitems[which];
							Toast.makeText(AddorderActivity1.this, "选择的采购员id为：" + BuyerID, Toast.LENGTH_SHORT).show();
						}  
					});  
					builder.create().show();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			super.handleMessage(msg);
		}
	};
	//供应商获取
	private Handler handler2=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what==0){
				Toast.makeText(AddorderActivity1.this,"网络连接失败！", Toast.LENGTH_LONG).show();
			}else{
				try {
					List<Integer> codelist=new ArrayList<Integer>();
					List<String> list=new ArrayList<String>();
					JSONArray json = new JSONArray( msg.obj.toString());
					for(int i=0;i<json.length();i++){
						JSONObject temp = (JSONObject) json.get(i);  
						//	departMentCode.add((Integer) temp.get("DeptID"));
						list.add(temp.getString("Name")); 
						codelist.add(temp.getInt("SupplierID"));
					}
					Builder builder =new AlertDialog.Builder(AddorderActivity1.this);
					final String[] items=list.toArray(new String[list.size()]);
					final Integer[] codeitems=codelist.toArray(new Integer[codelist.size()]);
					builder.setItems(items, new DialogInterface.OnClickListener()  
					{  
						@Override  
						public void onClick(DialogInterface dialog, int which)  
						{  
							((TextView)findViewById(R.id.addorder1_SupplierName)).setText(items[which].toString().trim());
							SupplierID=codeitems[which];
							Toast.makeText(AddorderActivity1.this, "选择的供应商id为：" + SupplierID, Toast.LENGTH_SHORT).show();
						}  
					});  
					builder.create().show();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			super.handleMessage(msg);
		}
	};
	//结算方式选择框
	private Handler handler3=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what==0){
				Toast.makeText(AddorderActivity1.this,"网络连接失败！", Toast.LENGTH_LONG).show();
			}else{
				try {
					List<Integer> codelist=new ArrayList<Integer>();
					List<String> list=new ArrayList<String>();
					JSONArray json = new JSONArray( msg.obj.toString());
					for(int i=0;i<json.length();i++){
						JSONObject temp = (JSONObject) json.get(i);  
						//	departMentCode.add((Integer) temp.get("DeptID"));
						list.add(temp.getString("SettleName")); 
						codelist.add(temp.getInt("SettleID"));
					}
					Builder builder =new AlertDialog.Builder(AddorderActivity1.this);
					final String[] items=list.toArray(new String[list.size()]);
					final Integer[] codeitems=codelist.toArray(new Integer[codelist.size()]);
					builder.setItems(items, new DialogInterface.OnClickListener()  
					{  
						@Override  
						public void onClick(DialogInterface dialog, int which)  
						{  
							((TextView)findViewById(R.id.addorder1_SettleName)).setText(items[which].toString().trim());
							SettleID=codeitems[which];
							Toast.makeText(AddorderActivity1.this, "选择的结算方式id为：" + SettleID, Toast.LENGTH_SHORT).show();
						}  
					});  
					builder.create().show();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			super.handleMessage(msg);
		}
	};
	//提交后的显示
		private Handler handler4=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(msg.what==0){
					Toast.makeText(AddorderActivity1.this,"网络连接失败！", Toast.LENGTH_LONG).show();
				}else{
					//
					Toast.makeText(AddorderActivity1.this,"提交成功！", Toast.LENGTH_LONG).show();
					refresh();
				}
				super.handleMessage(msg);
			}
		};
		//刷新界面
		private void refresh() {  
			finish();  
			Intent intent = new Intent(AddorderActivity1.this, AddorderActivity1.class);  
			startActivity(intent);  
		} 
	@Override
	public void onClick(View v) {
		//进入添加详情jiemian
		if(v.getId()==R.id.addorder1_go_adddetails){
			Intent intent=new Intent();
			intent.setClass(AddorderActivity1.this, AdddetailsActivity1.class);
			Bundle bundle = new Bundle();
			intent.putExtras(bundle);//将Bundle添加到Intent,也可以在Bundle中添加相应数据传递给下个页面,例如：bundle.putString("abc", "bbb");
			startActivityForResult(intent, 101);// 跳转并要求返回值，0代表请求值(可以随便写)
		}//取消订单
		else if(v.getId()==R.id.adddorder1_img_cancel){

			AddorderActivity1.this.finish(); 

		}
		//提交订单
		else if(v.getId()==R.id.adddorder1_img_tick){
			new AlertDialog.Builder(this).setTitle("确定提交订单吗？") 
			.setIcon(android.R.drawable.ic_dialog_info) 
			.setPositiveButton("确定", new DialogInterface.OnClickListener() { 	         
				@Override 
				public void onClick(DialogInterface dialog, int which) { 
					// 点击“确认”后的操作 
					JSONObject order=new JSONObject();
										try {
											order.put("PurOrderCode", ((TextView)findViewById(R.id.addorder1_PurOrderCode)).
													getText().toString().trim().equals(">")?"":((TextView)findViewById(R.id.addorder1_PurOrderCode)).
															getText().toString().trim());
											order.put("BuyerID", BuyerID);
											order.put("ContractCode", ((TextView)findViewById(R.id.addorder1_ContractCode)).
													getText().toString().trim().equals(">")?"":((TextView)findViewById(R.id.addorder1_ContractCode)).
															getText().toString().trim());
											order.put("SettleID", SettleID);
											order.put("PayDays", ((TextView)findViewById(R.id.addorder1_PayDays)).
													getText().toString().trim().equals(">")?"":((TextView)findViewById(R.id.addorder1_PayDays)).
															getText().toString().trim());
											order.put("SignAddr", ((TextView)findViewById(R.id.addorder1_SignAddr)).
													getText().toString().trim().equals(">")?"":((TextView)findViewById(R.id.addorder1_SignAddr)).
															getText().toString().trim());
											order.put("SignDate", ((TextView)findViewById(R.id.addorder1_SignDate)).
													getText().toString().trim().equals(">")?"":((TextView)findViewById(R.id.addorder1_SignDate)).
															getText().toString().trim());
											order.put("DeliveryAddr", ((TextView)findViewById(R.id.addorder1_DeliveryAddr)).
													getText().toString().trim().equals(">")?"":((TextView)findViewById(R.id.addorder1_DeliveryAddr)).
															getText().toString().trim());
											order.put("AogDate", ((TextView)findViewById(R.id.addorder1_AogDate)).
													getText().toString().trim().equals(">")?"":((TextView)findViewById(R.id.addorder1_AogDate)).
															getText().toString().trim());
											
											SimpleDateFormat    sDateFormat    =   new    SimpleDateFormat("yyyy-MM-dd");       
											String    curDate    =    sDateFormat.format(new    java.util.Date()); 
											order.put("WriteDate", curDate);
											order.put("Pu_PurOrderDt", orderdetails_jsonarray1);
					
											final String orderstr=order.toString();
											//将数据提交给服务器
											new Thread(){
												public void run() {
													String target=parenturl+"addpur_order";
													HttpClient httpClient=new DefaultHttpClient();
													HttpPost httpRequest=new HttpPost(target);
					
													List<NameValuePair> params=new ArrayList<NameValuePair>();
													params.add(new BasicNameValuePair("order",orderstr ));
													try{
														httpRequest.setEntity(new UrlEncodedFormEntity(params,"utf-8"));
														HttpResponse httpResponse=httpClient.execute(httpRequest);
														if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
															//如果成功
															Message message=Message.obtain();
															message.what=1;
															message.obj=EntityUtils.toString(httpResponse.getEntity(),"utf-8");
															handler4.sendMessage(message);
														}else{
															//如果网络连接失败
															Message message=Message.obtain();
															message.what=0;
															handler4.sendMessage(message);
														}
													}catch(Exception e){
														e.printStackTrace();
													}
												};
											}.start();
										} catch (JSONException e) {
											e.printStackTrace();
										}

				} 
			}) 
			.setNegativeButton("取消", new DialogInterface.OnClickListener() { 
				@Override 
				public void onClick(DialogInterface dialog, int which) { 
					// 点击“返回”后的操作,这里不设置没有任何操作 
				} 
			}).show(); 

		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode ==101&& resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			try {
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("GoodsID", bundle.getString("GoodsID"));
				jsonObject.put("Number", bundle.getString("Number"));
				jsonObject.put("Price", bundle.getString("Price"));
				jsonObject.put("Money", bundle.getString("Money"));
				jsonObject.put("DtAogDate", bundle.getString("DtAogDate"));
				jsonObject.put("DtNotes", bundle.getString("DtNotes"));
				jsonObject.put("PKGNum", bundle.getString("PKGNum"));
				//将某个详情存入jsonarray中
				orderdetails_jsonarray1.put((Object)jsonObject);
				//动态生成布局 显示新增详情
				LinearLayout linearLayout=new LinearLayout(AddorderActivity1.this);
				linearLayout.setOrientation(LinearLayout.VERTICAL);
				linearLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
				View view=new View(AddorderActivity1.this);
				view.setBackgroundColor(getResources().getColor(R.color.darkgray));
				linearLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1));
				linearLayout.addView(view);
				TextView textView1=new TextView(AddorderActivity1.this);
				textView1.setText(bundle.getString("GoodsName"));
				textView1.setTextColor(getResources().getColor(R.color.black));
				textView1.setTextSize(20);
				textView1.setPadding(20, 0, 0, 0);
				linearLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
				linearLayout.addView(textView1);
				TextView textView2=new TextView(AddorderActivity1.this);
				linearLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
				textView2.setText("数量:"+bundle.getString("Number")+" 价格:"+bundle.getString("Price")
						+" 金额:"+ bundle.getString("Money")+" 到货日期:"+bundle.getString("DtAogDate")
						+" 备注:"+ bundle.getString("DtNotes")+" 包装量:"+ bundle.getString("PKGNum"));
				textView2.setTextSize(12);
				textView2.setPadding(20, 0, 0, 0);
				linearLayout.addView(textView2);
				((LinearLayout)findViewById(R.id.addorder1_scroll_child_linear)).addView(linearLayout);
				Toast.makeText(AddorderActivity1.this, jsonObject.toString(), Toast.LENGTH_SHORT).show();
			} catch (JSONException e) {
				Toast.makeText(AddorderActivity1.this, "报错啦啦", Toast.LENGTH_SHORT).show();
			}
			//			 Toast.makeText(AddorderActivity.this, bundle.toString(), Toast.LENGTH_SHORT).show();

		}
	}
}
