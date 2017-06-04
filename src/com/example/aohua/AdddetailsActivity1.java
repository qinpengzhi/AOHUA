package com.example.aohua;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author qpz
 * ��������order���������
 */
public class AdddetailsActivity1 extends Activity implements OnTouchListener, android.view.View.OnClickListener{
	private String parenturl="http://120.25.73.114:8080/AOHUAServlet/";
	private Calendar calendar;//����װ���ڵ�
	private AlertDialog alert;
	private DatePickerDialog dialog;
	
	private Integer GoodsID;//����������洢����id��


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adddetails1);
		this.findViewById(R.id.adddetails1_GoodsID).setOnTouchListener(this);
		this.findViewById(R.id.adddetails1_Number).setOnTouchListener(this);
		this.findViewById(R.id.adddetails1_Price).setOnTouchListener(this);
		this.findViewById(R.id.adddetails1_Money).setOnTouchListener(this);
		this.findViewById(R.id.adddetails1_DtAogDate).setOnTouchListener(this);
		this.findViewById(R.id.adddetails1_DtNotes).setOnTouchListener(this);
		this.findViewById(R.id.adddetails1_PKGNum).setOnTouchListener(this);
		
		this.findViewById(R.id.adddetails1_img_cancel).setOnClickListener(this);
		this.findViewById(R.id.adddetails1_img_tick).setOnClickListener(this);
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		//��������ѡ��viewlist
		if(v.getId()==R.id.adddetails1_GoodsID){
			new Thread(){
				public void run() {
					String target=parenturl+"getgoodscode";
					HttpClient httpClient=new DefaultHttpClient();
					HttpPost httpRequest=new HttpPost(target);
					try{
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
		}//����������д��
		else if(v.getId()==R.id.adddetails1_Number){
			alert=new AlertDialog.Builder(AdddetailsActivity1.this).create();
			final EditText edit_text=new EditText(this);
			//ֻ���������ֺ�С����
			edit_text.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
			alert.setView(edit_text);
			alert.setButton(DialogInterface.BUTTON_NEGATIVE, "ȡ��", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {}
			});
			alert.setButton(DialogInterface.BUTTON_POSITIVE, "ȷ��", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialogIn, int which) {
					String aaa=edit_text.getText().toString().trim();
					 if(!edit_text.getText().toString().trim().equals(""))
						 ((TextView)findViewById(R.id.adddetails1_Number)).setText(edit_text.getText().toString().trim());
				}
			});
			alert.show();
		}//�����۸���д��
		else if(v.getId()==R.id.adddetails1_Price){
			alert=new AlertDialog.Builder(AdddetailsActivity1.this).create();
			final EditText edit_text=new EditText(this);
			//ֻ���������ֺ�С����
			edit_text.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
			alert.setView(edit_text);
			alert.setButton(DialogInterface.BUTTON_NEGATIVE, "ȡ��", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {}
			});
			alert.setButton(DialogInterface.BUTTON_POSITIVE, "ȷ��", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialogIn, int which) {
					 if(!edit_text.getText().toString().trim().equals(""))
						 ((TextView)findViewById(R.id.adddetails1_Price)).setText(edit_text.getText().toString().trim());;
				}
			});
			alert.show();
		}//���������д��
		else if(v.getId()==R.id.adddetails1_Money){
			alert=new AlertDialog.Builder(AdddetailsActivity1.this).create();
			final EditText edit_text=new EditText(this);
			//ֻ���������ֺ�С����
			edit_text.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
			alert.setView(edit_text);
			alert.setButton(DialogInterface.BUTTON_NEGATIVE, "ȡ��", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {}
			});
			alert.setButton(DialogInterface.BUTTON_POSITIVE, "ȷ��", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialogIn, int which) {
					 if(!edit_text.getText().toString().trim().equals(""))
						 ((TextView)findViewById(R.id.adddetails1_Money)).setText(edit_text.getText().toString().trim());;
				}
			});
			alert.show();
		}//������������ѡ���
		else if(v.getId()==R.id.adddetails1_DtAogDate){
			calendar=Calendar.getInstance();
			dialog=new DatePickerDialog(AdddetailsActivity1.this,new DatePickerDialog.OnDateSetListener() {
				public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
					 ((TextView)findViewById(R.id.adddetails1_DtAogDate)).setText(year+"-"+(month+1)+"-"+dayOfMonth);
				}
			}, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
			dialog.show();
		}//������ע��д��
		else if(v.getId()==R.id.adddetails1_DtNotes){
			alert=new AlertDialog.Builder(AdddetailsActivity1.this).create();
			final EditText edit_text=new EditText(this);
			alert.setView(edit_text);
			alert.setButton(DialogInterface.BUTTON_NEGATIVE, "ȡ��", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {}
			});
			alert.setButton(DialogInterface.BUTTON_POSITIVE, "ȷ��", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialogIn, int which) {
					 if(!edit_text.getText().toString().trim().equals(""))
						 ((TextView)findViewById(R.id.adddetails1_DtNotes)).setText(edit_text.getText().toString().trim());;
				}
			});
			alert.show();
		}//������װ����д��
		else if(v.getId()==R.id.adddetails1_PKGNum){
			alert=new AlertDialog.Builder(AdddetailsActivity1.this).create();
			final EditText edit_text=new EditText(this);
			//ֻ���������ֺ�С����
			edit_text.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
			alert.setView(edit_text);
			alert.setButton(DialogInterface.BUTTON_NEGATIVE, "ȡ��", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {}
			});
			alert.setButton(DialogInterface.BUTTON_POSITIVE, "ȷ��", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialogIn, int which) {
					 if(!edit_text.getText().toString().trim().equals(""))
						 ((TextView)findViewById(R.id.adddetails1_PKGNum)).setText(edit_text.getText().toString().trim());;
				}
			});
			alert.show();
		}		
		return false;
	}
	//����ѡ��listview
	private Handler handler1=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what==0){
				Toast.makeText(AdddetailsActivity1.this,"��������ʧ�ܣ�", Toast.LENGTH_LONG).show();
			}else{
				try {
					List<Integer> codelist=new ArrayList<Integer>();
					List<String> list=new ArrayList<String>();
					JSONArray json = new JSONArray( msg.obj.toString());
					for(int i=0;i<json.length();i++){
						JSONObject temp = (JSONObject) json.get(i);  
					//	departMentCode.add((Integer) temp.get("DeptID"));
						list.add(temp.getString("GoodsName")+"("+temp.getString("Unit")+")"); 
						codelist.add(temp.getInt("GoodsID"));
					}
					Builder builder =new AlertDialog.Builder(AdddetailsActivity1.this);
					final String[] items=list.toArray(new String[list.size()]);
					final Integer[] codeitems=codelist.toArray(new Integer[codelist.size()]);
					builder.setItems(items, new DialogInterface.OnClickListener()  
				       {  
				           @Override  
				           public void onClick(DialogInterface dialog, int which)  
				           {  
				               ((TextView)findViewById(R.id.adddetails1_GoodsID)).setText(items[which].toString().trim());
				               GoodsID=codeitems[which];
				               Toast.makeText(AdddetailsActivity1.this, "ѡ�������idΪ��" + GoodsID, Toast.LENGTH_SHORT).show();
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
		//���ȡ����ť
		 if(v.getId()==R.id.adddetails1_img_cancel){
			 new AlertDialog.Builder(this).setTitle("ȷ�������������ϸ��") 
	            .setIcon(android.R.drawable.ic_dialog_info) 
	            .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() { 	         
	                @Override 
	                public void onClick(DialogInterface dialog, int which) { 
	                // �����ȷ�ϡ���Ĳ��� 
	                    AdddetailsActivity1.this.finish(); 
	                } 
	            }) 
	            .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() { 
	                @Override 
	                public void onClick(DialogInterface dialog, int which) { 
	                // ��������ء���Ĳ���,���ﲻ����û���κβ��� 
	                } 
	            }).show(); 
		 }//����ύ��ť
		 else if(v.getId()==R.id.adddetails1_img_tick){
			 new AlertDialog.Builder(this).setTitle("ȷ���ύ������ϸ��") 
	            .setIcon(android.R.drawable.ic_dialog_info) 
	            .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() { 	         
	                @Override 
	                public void onClick(DialogInterface dialog, int which) { 
	                // �����ȷ�ϡ���Ĳ��� 
	                	Intent intent = AdddetailsActivity1.this.getIntent();
	                	Bundle bundle = intent.getExtras();
	                	//���Ҫ������һ��ҳ���ֵ
	                	bundle.putString("GoodsID", GoodsID.toString());
	                	bundle.putString("GoodsName", ((TextView)findViewById(R.id.adddetails1_GoodsID)).
	                			getText().toString().trim().equals(">")?"":((TextView)findViewById(R.id.adddetails1_GoodsID)).
	    	                			getText().toString().trim());
	                	bundle.putString("Number", ((TextView)findViewById(R.id.adddetails1_Number)).
	                			getText().toString().trim().equals(">")?"":((TextView)findViewById(R.id.adddetails1_Number)).
	    	                			getText().toString().trim());
	                	bundle.putString("Price", ((TextView)findViewById(R.id.adddetails1_Price)).
	                			getText().toString().trim().equals(">")?"":((TextView)findViewById(R.id.adddetails1_Price)).
	    	                			getText().toString().trim());
	                	bundle.putString("Money", ((TextView)findViewById(R.id.adddetails1_Money)).
	                			getText().toString().trim().equals(">")?"":((TextView)findViewById(R.id.adddetails1_Money)).
	    	                			getText().toString().trim());
	                	bundle.putString("DtAogDate", ((TextView)findViewById(R.id.adddetails1_DtAogDate)).
	                			getText().toString().trim().equals(">")?"":((TextView)findViewById(R.id.adddetails1_DtAogDate)).
	    	                			getText().toString().trim());
	                	bundle.putString("DtNotes", ((TextView)findViewById(R.id.adddetails1_DtNotes)).
	                			getText().toString().trim().equals(">")?"":((TextView)findViewById(R.id.adddetails1_DtNotes)).
	    	                			getText().toString().trim());
	                	bundle.putString("PKGNum", ((TextView)findViewById(R.id.adddetails1_PKGNum)).
	                			getText().toString().trim().equals(">")?"":((TextView)findViewById(R.id.adddetails1_PKGNum)).
	    	                			getText().toString().trim());
	                	intent.putExtras(bundle);
	                	AdddetailsActivity1.this.setResult(Activity.RESULT_OK, intent);//����ҳ��1
	                	AdddetailsActivity1.this.finish();
	                } 
	            }) 
	            .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() { 
	                @Override 
	                public void onClick(DialogInterface dialog, int which) { 
	                // ��������ء���Ĳ���,���ﲻ����û���κβ��� 
	                } 
	            }).show(); 
		 }
		
	}
}
