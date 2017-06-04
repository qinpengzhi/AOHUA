package com.example.aohua;

import java.util.ArrayList;
import java.util.Date;
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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AddorderActivity extends Activity implements OnTouchListener, android.view.View.OnClickListener{
	private String parenturl="http://10.132.23.147:8080/AOHUAServlet/";
	private Calendar calendar;//用来装日期的
	private Integer DeptID;//这个是用来存储部门的id的
	private Integer TransportID;//这个是用来存储运输方式id的
	private Integer SettleID;//这个是用来存储结算方式的id的
	private Integer CustID;//这个是用来存储选择的客户的id的
	private Integer SellerID;//这个是用来存储业务员的id的
	private AlertDialog alert;
	private DatePickerDialog dialog;
	private TextView addorder_go_adddetails;//进入添加明细界面的按钮
	private JSONArray orderdetails_jsonarray=new JSONArray();//存储订单明细的json串

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addorder);
		//弹出交货日期
		this.findViewById(R.id.addorder_DeliveryDate).setOnTouchListener(this);
		//弹出签订日期
		this.findViewById(R.id.addorder_SignDate).setOnTouchListener(this);
		//填写交货地点
		this.findViewById(R.id.addorder_DeliveryAddr).setOnTouchListener(this);
		//填写运输费用
		this.findViewById(R.id.addorder_Freight).setOnTouchListener(this);
		//OrderCode填写订单单号
		this.findViewById(R.id.addorder_OrderCode).setOnTouchListener(this);
		//SignAddr填写签订地址
		this.findViewById(R.id.addorder_SignAddr).setOnTouchListener(this);
		//ContractCode填写合同号
		this.findViewById(R.id.addorder_ContractCode).setOnTouchListener(this);
		//ReceDays填写收款天
		this.findViewById(R.id.addorder_ReceDays).setOnTouchListener(this);
		//DeptName弹出所属部门选择列表
		this.findViewById(R.id.addorder_DeptName).setOnTouchListener(this);
		//TransportName弹出运输方式选择列表
		this.findViewById(R.id.addorder_TransportName).setOnTouchListener(this);
		//SettleName弹出结算方式选择列表
		this.findViewById(R.id.addorder_SettleName).setOnTouchListener(this);
		//CustName弹出客户选择列表
		this.findViewById(R.id.addorder_CustName).setOnTouchListener(this);
		//SellerName弹出业务员选择列表
		this.findViewById(R.id.addorder_SellerName).setOnTouchListener(this);	

		addorder_go_adddetails=(TextView) findViewById(R.id.addorder_go_adddetails);
		addorder_go_adddetails.setOnClickListener(this);
		findViewById(R.id.adddorder_img_tick).setOnClickListener(this);
		findViewById(R.id.adddorder_img_cancel).setOnClickListener(this);
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		//这边是选择时间的
		if(v.getId()==R.id.addorder_DeliveryDate){
			calendar=Calendar.getInstance();
			dialog=new DatePickerDialog(AddorderActivity.this,new DatePickerDialog.OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
					((TextView)findViewById(R.id.addorder_DeliveryDate)).setText(year+"-"+(month+1)+"-"+dayOfMonth);
				}
			}, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
			dialog.show();
		}else if(v.getId()==R.id.addorder_SignDate){
			calendar=Calendar.getInstance();
			dialog=new DatePickerDialog(AddorderActivity.this,new DatePickerDialog.OnDateSetListener() {
				public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
					((TextView)findViewById(R.id.addorder_SignDate)).setText(year+"-"+(month+1)+"-"+dayOfMonth);
				}
			}, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
			dialog.show();
		}else if(v.getId()==R.id.addorder_DeliveryAddr){
			alert=new AlertDialog.Builder(AddorderActivity.this).create();
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
						((TextView)findViewById(R.id.addorder_DeliveryAddr)).setText(edit_text.getText().toString().trim());;
				}
			});
			alert.show();
		}else if(v.getId()==R.id.addorder_Freight){
			alert=new AlertDialog.Builder(AddorderActivity.this).create();
			final EditText edit_text=new EditText(this);
			//只能输入数字和小数点
			edit_text.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
			alert.setView(edit_text);
			alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {}
			});
			alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialogIn, int which) {
					if(!edit_text.getText().toString().trim().equals(""))
						((TextView)findViewById(R.id.addorder_Freight)).setText(edit_text.getText().toString().trim());;
				}
			});
			alert.show();
		}else if(v.getId()==R.id.addorder_SignAddr){
			alert=new AlertDialog.Builder(AddorderActivity.this).create();
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
						((TextView)findViewById(R.id.addorder_SignAddr)).setText(edit_text.getText().toString().trim());
				}
			});
			alert.show();
		}else if(v.getId()==R.id.addorder_OrderCode){
			alert=new AlertDialog.Builder(AddorderActivity.this).create();
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
						((TextView)findViewById(R.id.addorder_OrderCode)).setText(edit_text.getText().toString().trim());
				}
			});
			alert.show();
		}else if(v.getId()==R.id.addorder_ContractCode){
			alert=new AlertDialog.Builder(AddorderActivity.this).create();
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
						((TextView)findViewById(R.id.addorder_ContractCode)).setText(edit_text.getText().toString().trim());;
				}
			});
			alert.show();
		}else if(v.getId()==R.id.addorder_ReceDays){
			alert=new AlertDialog.Builder(AddorderActivity.this).create();
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
						((TextView)findViewById(R.id.addorder_ReceDays)).setText(edit_text.getText().toString().trim());;
				}
			});
			alert.show();
		}//弹出选择框，这边是需要选择的
		else if(v.getId()==R.id.addorder_DeptName){
			new Thread(){
				public void run() {
					String target=parenturl+"getdepartment";
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
		}else if(v.getId()==R.id.addorder_TransportName){
			new Thread(){
				public void run() {
					String target=parenturl+"gettransportmode";
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
		}else if(v.getId()==R.id.addorder_SettleName){
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
		}else if(v.getId()==R.id.addorder_CustName){
			new Thread(){
				public void run() {
					String target=parenturl+"getse_customer";
					HttpClient httpClient=new DefaultHttpClient();
					HttpPost httpRequest=new HttpPost(target);
					try{
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
		}else if(v.getId()==R.id.addorder_SellerName){
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
							handler5.sendMessage(message);
						}else{
							//如果网络连接失败
							Message message=Message.obtain();
							message.what=0;
							handler5.sendMessage(message);
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				};
			}.start();
		}

		return false;
	}
	//部门选择框
	private Handler handler1=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what==0){
				Toast.makeText(AddorderActivity.this,"网络连接失败！", Toast.LENGTH_LONG).show();
			}else{
				try {
					List<Integer> codelist=new ArrayList<Integer>();
					List<String> list=new ArrayList<String>();
					JSONArray json = new JSONArray( msg.obj.toString());
					for(int i=0;i<json.length();i++){
						JSONObject temp = (JSONObject) json.get(i);  
						//	departMentCode.add((Integer) temp.get("DeptID"));
						list.add(temp.getString("DeptName")); 
						codelist.add(temp.getInt("DeptID"));
					}
					Builder builder =new AlertDialog.Builder(AddorderActivity.this);
					final String[] items=list.toArray(new String[list.size()]);
					final Integer[] codeitems=codelist.toArray(new Integer[codelist.size()]);
					builder.setItems(items, new DialogInterface.OnClickListener()  
					{  
						@Override  
						public void onClick(DialogInterface dialog, int which)  
						{  
							((TextView)findViewById(R.id.addorder_DeptName)).setText(items[which].toString().trim());
							DeptID=codeitems[which];
							Toast.makeText(AddorderActivity.this, "选择的部门id为：" + DeptID, Toast.LENGTH_SHORT).show();
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
	//运输方式选择框
	private Handler handler2=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what==0){
				Toast.makeText(AddorderActivity.this,"网络连接失败！", Toast.LENGTH_LONG).show();
			}else{
				try {
					List<Integer> codelist=new ArrayList<Integer>();
					List<String> list=new ArrayList<String>();
					JSONArray json = new JSONArray( msg.obj.toString());
					for(int i=0;i<json.length();i++){
						JSONObject temp = (JSONObject) json.get(i);  
						//	departMentCode.add((Integer) temp.get("DeptID"));
						list.add(temp.getString("TransportName")); 
						codelist.add(temp.getInt("TransportID"));
					}
					Builder builder =new AlertDialog.Builder(AddorderActivity.this);
					final String[] items=list.toArray(new String[list.size()]);
					final Integer[] codeitems=codelist.toArray(new Integer[codelist.size()]);
					builder.setItems(items, new DialogInterface.OnClickListener()  
					{  
						@Override  
						public void onClick(DialogInterface dialog, int which)  
						{  
							((TextView)findViewById(R.id.addorder_TransportName)).setText(items[which].toString().trim());
							TransportID=codeitems[which];
							Toast.makeText(AddorderActivity.this, "选择的运输方式id为：" + TransportID, Toast.LENGTH_SHORT).show();
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
				Toast.makeText(AddorderActivity.this,"网络连接失败！", Toast.LENGTH_LONG).show();
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
					Builder builder =new AlertDialog.Builder(AddorderActivity.this);
					final String[] items=list.toArray(new String[list.size()]);
					final Integer[] codeitems=codelist.toArray(new Integer[codelist.size()]);
					builder.setItems(items, new DialogInterface.OnClickListener()  
					{  
						@Override  
						public void onClick(DialogInterface dialog, int which)  
						{  
							((TextView)findViewById(R.id.addorder_SettleName)).setText(items[which].toString().trim());
							SettleID=codeitems[which];
							Toast.makeText(AddorderActivity.this, "选择的结算方式id为：" + SettleID, Toast.LENGTH_SHORT).show();
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
	//客户选择框
	private Handler handler4=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what==0){
				Toast.makeText(AddorderActivity.this,"网络连接失败！", Toast.LENGTH_LONG).show();
			}else{
				try {
					List<Integer> codelist=new ArrayList<Integer>();
					List<String> list=new ArrayList<String>();
					JSONArray json = new JSONArray( msg.obj.toString());
					for(int i=0;i<json.length();i++){
						JSONObject temp = (JSONObject) json.get(i);  
						//	departMentCode.add((Integer) temp.get("DeptID"));
						list.add(temp.getString("CustName")); 
						codelist.add(temp.getInt("CustID"));
					}
					Builder builder =new AlertDialog.Builder(AddorderActivity.this);
					final String[] items=list.toArray(new String[list.size()]);
					final Integer[] codeitems=codelist.toArray(new Integer[codelist.size()]);
					builder.setItems(items, new DialogInterface.OnClickListener()  
					{  
						@Override  
						public void onClick(DialogInterface dialog, int which)  
						{  
							((TextView)findViewById(R.id.addorder_CustName)).setText(items[which].toString().trim());
							CustID=codeitems[which];
							Toast.makeText(AddorderActivity.this, "选择的客户id为：" + CustID, Toast.LENGTH_SHORT).show();
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
	//业务员选择框
	private Handler handler5=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what==0){
				Toast.makeText(AddorderActivity.this,"网络连接失败！", Toast.LENGTH_LONG).show();
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
					Builder builder =new AlertDialog.Builder(AddorderActivity.this);
					final String[] items=list.toArray(new String[list.size()]);
					final Integer[] codeitems=codelist.toArray(new Integer[codelist.size()]);
					builder.setItems(items, new DialogInterface.OnClickListener()  
					{  
						@Override  
						public void onClick(DialogInterface dialog, int which)  
						{  
							((TextView)findViewById(R.id.addorder_SellerName)).setText(items[which].toString().trim());
							SellerID=codeitems[which];
							Toast.makeText(AddorderActivity.this, "选择的业务员id为：" + SellerID, Toast.LENGTH_SHORT).show();
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
	@Override
	public void onClick(View v) {
		//进入添加详情jiemian
		if(v.getId()==R.id.addorder_go_adddetails){
			Intent intent=new Intent();
			intent.setClass(AddorderActivity.this, AdddetailsActivity.class);
			Bundle bundle = new Bundle();
			intent.putExtras(bundle);//将Bundle添加到Intent,也可以在Bundle中添加相应数据传递给下个页面,例如：bundle.putString("abc", "bbb");
			startActivityForResult(intent, 100);// 跳转并要求返回值，0代表请求值(可以随便写)
		}//取消订单
		else if(v.getId()==R.id.adddorder_img_cancel){
			
	       AddorderActivity.this.finish(); 
	            
		}
		//提交订单
		else if(v.getId()==R.id.adddorder_img_tick){
			new AlertDialog.Builder(this).setTitle("确定提交订单吗？") 
			.setIcon(android.R.drawable.ic_dialog_info) 
			.setPositiveButton("确定", new DialogInterface.OnClickListener() { 	         
				@Override 
				public void onClick(DialogInterface dialog, int which) { 
					// 点击“确认”后的操作 
					JSONObject order=new JSONObject();
					try {
						order.put("OrderCode", ((TextView)findViewById(R.id.addorder_OrderCode)).
								getText().toString().trim().equals(">")?"":((TextView)findViewById(R.id.addorder_OrderCode)).
										getText().toString().trim());
						order.put("CustID", CustID);
						order.put("ContractCode", ((TextView)findViewById(R.id.addorder_ContractCode)).
								getText().toString().trim().equals(">")?"":((TextView)findViewById(R.id.addorder_ContractCode)).
										getText().toString().trim());
						order.put("SellerID", SellerID);
						order.put("SignAddr", ((TextView)findViewById(R.id.addorder_SignAddr)).
								getText().toString().trim().equals(">")?"":((TextView)findViewById(R.id.addorder_SignAddr)).
										getText().toString().trim());
						order.put("SignDate", ((TextView)findViewById(R.id.addorder_SignDate)).
								getText().toString().trim().equals(">")?"":((TextView)findViewById(R.id.addorder_SignDate)).
										getText().toString().trim());
						order.put("DeliveryAddr", ((TextView)findViewById(R.id.addorder_DeliveryAddr)).
								getText().toString().trim().equals(">")?"":((TextView)findViewById(R.id.addorder_DeliveryAddr)).
										getText().toString().trim());
						order.put("DeliveryDate", ((TextView)findViewById(R.id.addorder_DeliveryDate)).
								getText().toString().trim().equals(">")?"":((TextView)findViewById(R.id.addorder_DeliveryDate)).
										getText().toString().trim());
						order.put("DeliveryDate", ((TextView)findViewById(R.id.addorder_DeliveryDate)).
								getText().toString().trim().equals(">")?"":((TextView)findViewById(R.id.addorder_DeliveryDate)).
										getText().toString().trim());
						order.put("Freight", ((TextView)findViewById(R.id.addorder_Freight)).
								getText().toString().trim().equals(">")?"":((TextView)findViewById(R.id.addorder_Freight)).
										getText().toString().trim());
						order.put("TransportID", TransportID);
						order.put("SettleID", SettleID);
						order.put("ReceDays", ((TextView)findViewById(R.id.addorder_ReceDays)).
								getText().toString().trim().equals(">")?"":((TextView)findViewById(R.id.addorder_ReceDays)).
										getText().toString().trim());
						order.put("DeptID", DeptID);
						SimpleDateFormat    sDateFormat    =   new    SimpleDateFormat("yyyy-MM-dd");       
						String    curDate    =    sDateFormat.format(new    java.util.Date()); 
						order.put("WriteDate", curDate);
						order.put("Se_OrderDt", orderdetails_jsonarray);

						final String orderstr=order.toString();
						//将数据提交给服务器
						new Thread(){
							public void run() {
								String target=parenturl+"addorder";
								HttpClient httpClient=new DefaultHttpClient();
								HttpPost httpRequest=new HttpPost(target);

								List<NameValuePair> params=new ArrayList<NameValuePair>();
								params.add(new BasicNameValuePair("order",orderstr ));
								SharedPreferences sharedPreferences=getSharedPreferences("user", MODE_PRIVATE);
								params.add(new BasicNameValuePair("userid",sharedPreferences.getString("userid", "")));
								try{
									httpRequest.setEntity(new UrlEncodedFormEntity(params,"utf-8"));
									HttpResponse httpResponse=httpClient.execute(httpRequest);
									if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
										//如果成功
										Message message=Message.obtain();
										message.what=1;
										message.obj=EntityUtils.toString(httpResponse.getEntity(),"utf-8");
										handler6.sendMessage(message);
									}else{
										//如果网络连接失败
										Message message=Message.obtain();
										message.what=0;
										handler6.sendMessage(message);
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
	//提交后的显示
	private Handler handler6=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what==0){
				Toast.makeText(AddorderActivity.this,"网络连接失败！", Toast.LENGTH_LONG).show();
			}else{
				//
				Toast.makeText(AddorderActivity.this,"提交成功！", Toast.LENGTH_LONG).show();
				refresh();
			}
			super.handleMessage(msg);
		}
	};
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode ==100&& resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			try {
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("GoodsID", bundle.getString("GoodsID"));
				jsonObject.put("Number", bundle.getString("Number"));
				jsonObject.put("Price", bundle.getString("Price"));
				jsonObject.put("Money", bundle.getString("Money"));
				jsonObject.put("DtDeliveryDate", bundle.getString("DtDeliveryDate"));
				jsonObject.put("DtNotes", bundle.getString("DtNotes"));
				jsonObject.put("PKGNum", bundle.getString("PKGNum"));
				//将某个详情存入jsonarray中
				orderdetails_jsonarray.put((Object)jsonObject);
				//动态生成布局 显示新增详情
				LinearLayout linearLayout=new LinearLayout(AddorderActivity.this);
				linearLayout.setOrientation(LinearLayout.VERTICAL);
				linearLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
				View view=new View(AddorderActivity.this);
				view.setBackgroundColor(getResources().getColor(R.color.darkgray));
				linearLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1));
				linearLayout.addView(view);
				TextView textView1=new TextView(AddorderActivity.this);
				textView1.setText(bundle.getString("GoodsName"));
				textView1.setTextColor(getResources().getColor(R.color.black));
				textView1.setTextSize(20);
				textView1.setPadding(20, 0, 0, 0);
				linearLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
				linearLayout.addView(textView1);
				TextView textView2=new TextView(AddorderActivity.this);
				linearLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
				textView2.setText("数量:"+bundle.getString("Number")+" 价格:"+bundle.getString("Price")
						+" 金额:"+ bundle.getString("Money")+" 交货日期:"+bundle.getString("DtDeliveryDate")
						+" 备注:"+ bundle.getString("DtNotes")+" 包装量:"+ bundle.getString("PKGNum"));
				textView2.setTextSize(12);
				textView2.setPadding(20, 0, 0, 0);
				linearLayout.addView(textView2);
				((LinearLayout)findViewById(R.id.addorder_scroll_child_linear)).addView(linearLayout);
			} catch (JSONException e) {
				Toast.makeText(AddorderActivity.this, "报错啦啦", Toast.LENGTH_SHORT).show();
			}
			//			 Toast.makeText(AddorderActivity.this, bundle.toString(), Toast.LENGTH_SHORT).show();

		}
	}
	//刷新界面
	private void refresh() {  
		finish();  
		Intent intent = new Intent(AddorderActivity.this, AddorderActivity.class);  
		startActivity(intent);  
	} 
}
