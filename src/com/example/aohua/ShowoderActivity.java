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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ShowoderActivity extends Activity implements OnItemClickListener{
	private String parenturl="http://120.25.73.114:8080/AOHUAServlet/";
	private int order_type=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showorder);
		TextView order_type_spinner=(TextView) findViewById(R.id.show_order_type);
		//ѡ��ͬ�Ķ�������
		order_type_spinner.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String[] items=new String[]{"���۶���","�ɹ�����"};
				Builder builder=new AlertDialog.Builder(ShowoderActivity.this);
				builder.setItems(items, new DialogInterface.OnClickListener()  
				{  
					@Override  
					public void onClick(DialogInterface dialog, int which)  
					{  
						((TextView)findViewById(R.id.show_order_type)).setText(items[which].toString().trim()+"  ��");
						showOrderlist(which);
						order_type=which;
					}  
				});  
				builder.create().show();

			}
		});
		//ˢ�½���
		findViewById(R.id.showorder_reload_img).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showOrderlist(order_type);
			}
		});
		//��һ�μ������۶�������
		showOrderlist(0);
	}
	public void showOrderlist(int type){
		if(type==0){
			//�������۶���
			new Thread(){
				public void run() {
					String target=parenturl+"getse_orderlist";
					HttpClient httpClient=new DefaultHttpClient();
					HttpPost httpRequest=new HttpPost(target);
					List<NameValuePair> params=new ArrayList<NameValuePair>();
					SharedPreferences sharedPreferences=getSharedPreferences("user", MODE_PRIVATE);
					params.add(new BasicNameValuePair("userid",sharedPreferences.getString("userid", "")));
					params.add(new BasicNameValuePair("examine",sharedPreferences.getString("examine", "")));
					try{
						httpRequest.setEntity(new UrlEncodedFormEntity(params,"utf-8"));
						HttpResponse httpResponse=httpClient.execute(httpRequest);
						if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
							//����ɹ�
							Message message=Message.obtain();
							message.what=1;
							message.obj=EntityUtils.toString(httpResponse.getEntity(),"utf-8");
							handler1.sendMessage(message);
						}else{
							//�����������ʧ��
							Message message=Message.obtain();
							message.what=0;
							handler1.sendMessage(message);
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				};
			}.start();
		}else if(type==1){
			//���Ҳɹ�����
			new Thread(){
				public void run() {
					String target=parenturl+"getpu_purorderlist";
					HttpClient httpClient=new DefaultHttpClient();
					HttpPost httpRequest=new HttpPost(target);
					
					List<NameValuePair> params=new ArrayList<NameValuePair>();
					SharedPreferences sharedPreferences=getSharedPreferences("user", MODE_PRIVATE);
					params.add(new BasicNameValuePair("userid",sharedPreferences.getString("userid", "")));
					params.add(new BasicNameValuePair("examine",sharedPreferences.getString("examine", "")));
					try{
						httpRequest.setEntity(new UrlEncodedFormEntity(params,"utf-8"));
						HttpResponse httpResponse=httpClient.execute(httpRequest);
						if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
							//����ɹ�
							Message message=Message.obtain();
							message.what=1;
							message.obj=EntityUtils.toString(httpResponse.getEntity(),"utf-8");
							handler2.sendMessage(message);
						}else{
							//�����������ʧ��
							Message message=Message.obtain();
							message.what=0;
							handler2.sendMessage(message);
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				};
			}.start();
		}
	}
	//�����۶������������ݽ��д���
	private Handler handler1=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what==0){
				Toast.makeText(ShowoderActivity.this,"ˢ��ʧ�ܣ�", Toast.LENGTH_LONG).show();
			}else{
				try {
					JSONArray json = new JSONArray( msg.obj.toString());
					//�����۶������ݴ���  Ȼ��ӳ�䵽order_list��
					ArrayList<Order> orderList=new ArrayList<Order>();
					for(int i=0;i<json.length();i++){
						JSONObject temp = (JSONObject) json.get(i);  
						Order order=new Order();
						order.setCode((String) temp.get("OrderCode"));
						order.setId((Integer) temp.get("OrderID"));
						if(!temp.get("CustName").equals(null))
							order.setName((String) temp.get("CustName"));
						else order.setName("");
						order.setWriteDate((String) temp.get("WriteDate"));
						order.setState((Integer) temp.get("State"));
						orderList.add(order);
						OrderListViewAdapter orderListViewAdapter=new OrderListViewAdapter(orderList, ShowoderActivity.this);
						ListView listView=(ListView) findViewById(R.id.viewlist_orderlist);
						listView.setAdapter(orderListViewAdapter);
						listView.setOnItemClickListener(ShowoderActivity.this);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			super.handleMessage(msg);
		}
	};
	//�Բɹ��������������ݽ��д���
	private Handler handler2=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what==0){
				Toast.makeText(ShowoderActivity.this,"ˢ��ʧ�ܣ�", Toast.LENGTH_LONG).show();
			}else{
				try {
					JSONArray json = new JSONArray( msg.obj.toString());
					//�����۶������ݴ���  Ȼ��ӳ�䵽order_list��
					ArrayList<Order> orderList=new ArrayList<Order>();
					for(int i=0;i<json.length();i++){
						JSONObject temp = (JSONObject) json.get(i);  
						Order order=new Order();
						order.setCode((String) temp.get("PurOrderCode"));
						order.setId((Integer) temp.get("PurOrderID"));
						if(!temp.get("Name").equals(null))
							order.setName((String) temp.get("Name"));
						else order.setName("");
						order.setWriteDate((String) temp.get("WriteDate"));
						order.setState((Integer) temp.get("State"));
						orderList.add(order);
						OrderListViewAdapter orderListViewAdapter=new OrderListViewAdapter(orderList, ShowoderActivity.this);
						ListView listView=(ListView) findViewById(R.id.viewlist_orderlist);
						listView.setAdapter(orderListViewAdapter);
						listView.setOnItemClickListener(ShowoderActivity.this);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			super.handleMessage(msg);
		}
	};
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(order_type==0){
			//�������۶�������ҳ��
			String orderid=((TextView)view.findViewById(R.id.order_list_id)).getText().toString().trim();
			Intent intent=new Intent();  
            intent.setClass(ShowoderActivity.this, OrderdetailsActivity.class);
            intent.putExtra("orderid", orderid);//��intent��Ӷ������� 
            startActivity(intent); 
		}else if(order_type==1){
			//����ɹ���������ҳ��
			String orderid=((TextView)view.findViewById(R.id.order_list_id)).getText().toString().trim();
			Intent intent=new Intent();  
            intent.setClass(ShowoderActivity.this, OrderdetailsActivity1.class);
            intent.putExtra("orderid", orderid);//��intent��Ӷ������� 
            startActivity(intent); 
		}
		 
		
	}
}

