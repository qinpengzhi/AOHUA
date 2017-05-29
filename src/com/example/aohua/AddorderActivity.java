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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddorderActivity extends Activity implements OnTouchListener{
	private AutoCompleteTextView autotext;
	private ArrayAdapter<String> arrayAdapter;
	private String parenturl="http://10.132.23.147:8080/AOHUAServlet/";
	private Calendar calendar;//用来装日期的
	private DatePickerDialog dialog;
	private Integer DeptID;//这个是用来存储部门的id的
	private Integer TransportID;//这个是用来存储运输方式id的
	private Integer SettleID;//这个是用来存储结算方式的id的
	private Integer CustID;//这个是用来存储选择的客户的id的
	private Integer SellerID;//这个是用来存储业务员的id的
	private AlertDialog alert;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addorder);
		//弹出交货日期
		this.findViewById(R.id.relative_DeliveryDate).setOnTouchListener(this);
		//弹出签订日期
		this.findViewById(R.id.relative_SignDate).setOnTouchListener(this);
		//填写交货地点
		this.findViewById(R.id.relative_DeliveryAddr).setOnTouchListener(this);
		//填写运输费用
		this.findViewById(R.id.relative_Freight).setOnTouchListener(this);
		//SignAddr填写签订地址
		this.findViewById(R.id.relative_SignAddr).setOnTouchListener(this);
		//ContractCode填写合同号
		this.findViewById(R.id.relative_ContractCode).setOnTouchListener(this);
		//ReceDays填写收款天
		this.findViewById(R.id.relative_ReceDays).setOnTouchListener(this);
		//DeptName弹出所属部门选择列表
		this.findViewById(R.id.relative_DeptName).setOnTouchListener(this);
		//TransportName弹出运输方式选择列表
		this.findViewById(R.id.relative_TransportName).setOnTouchListener(this);
		//SettleName弹出结算方式选择列表
		this.findViewById(R.id.relative_SettleName).setOnTouchListener(this);
		//CustName弹出客户选择列表
		this.findViewById(R.id.relative_CustName).setOnTouchListener(this);
		//SellerName弹出业务员选择列表
		this.findViewById(R.id.relative_SellerName).setOnTouchListener(this);	
		
		//这是对客户的自动提示框
//        autotext =(AutoCompleteTextView) findViewById(R.id.addorder_CustName);
//        String [] arr={"浙江工业研究院","杭州科技","浙江大学","杭州有限公司"};
//        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arr);
//        autotext.setAdapter(arrayAdapter);
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		//这边是选择时间的
		if(v.getId()==R.id.relative_DeliveryDate){
			calendar=Calendar.getInstance();
			dialog=new DatePickerDialog(AddorderActivity.this,new DatePickerDialog.OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
					 ((TextView)findViewById(R.id.addorder_DeliveryDate)).setText(year+"-"+(month+1)+"-"+dayOfMonth);
				}
			}, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
			dialog.show();
		}else if(v.getId()==R.id.relative_SignDate){
			calendar=Calendar.getInstance();
			dialog=new DatePickerDialog(AddorderActivity.this,new DatePickerDialog.OnDateSetListener() {
				public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
					 ((TextView)findViewById(R.id.addorder_SignDate)).setText(year+"-"+(month+1)+"-"+dayOfMonth);
				}
			}, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
			dialog.show();
		}else if(v.getId()==R.id.relative_DeliveryAddr){
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
					 ((TextView)findViewById(R.id.addorder_DeliveryAddr)).setText(edit_text.getText().toString().trim());;
				}
			});
			alert.show();
		}else if(v.getId()==R.id.relative_Freight){
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
					 ((TextView)findViewById(R.id.addorder_Freight)).setText(edit_text.getText().toString().trim());;
				}
			});
			alert.show();
		}else if(v.getId()==R.id.relative_SignAddr){
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
					 ((TextView)findViewById(R.id.addorder_SignAddr)).setText(edit_text.getText().toString().trim());
				}
			});
			alert.show();
		}else if(v.getId()==R.id.relative_ContractCode){
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
					 ((TextView)findViewById(R.id.addorder_ContractCode)).setText(edit_text.getText().toString().trim());;
				}
			});
			alert.show();
		}else if(v.getId()==R.id.relative_ReceDays){
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
					 ((TextView)findViewById(R.id.addorder_ReceDays)).setText(edit_text.getText().toString().trim());;
				}
			});
			alert.show();
		}//弹出选择框，这边是需要选择的
		else if(v.getId()==R.id.relative_DeptName){
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
		}else if(v.getId()==R.id.relative_TransportName){
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
		}else if(v.getId()==R.id.relative_SettleName){
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
		}else if(v.getId()==R.id.relative_CustName){
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
		}else if(v.getId()==R.id.relative_SellerName){
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
}
