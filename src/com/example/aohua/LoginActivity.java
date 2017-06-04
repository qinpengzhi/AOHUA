package com.example.aohua;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.aohua.util.HttpUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//登录界面
public class LoginActivity extends Activity {
	private Button login_bt;
	private SharedPreferences sharedPreferences;//本地保存sessionid
	private Editor editor ;//针对sharedPerference的编辑
	//对数据进行处理
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what==0){
				Toast.makeText(LoginActivity.this,"网络连接失败！", Toast.LENGTH_LONG).show();
			}else{
				try {
					JSONObject json = new JSONObject( msg.obj.toString());
					if(json.getInt("userid")==0){
						Toast.makeText(LoginActivity.this,"账户或者密码错误！", Toast.LENGTH_LONG).show();
					}else{
						//连接成功
						//首先在本地保存数据
						sharedPreferences =getSharedPreferences("user", MODE_PRIVATE);
						editor=sharedPreferences.edit();
						editor.putString("userid",json.get("userid")+"");
						if(json.getBoolean("examine"))
							editor.putString("examine",1+"");
						else editor.putString("examine",0+"");
						editor.putBoolean("login",true);
						editor.commit();
						Intent  intent=new Intent();
						intent.setClass(LoginActivity.this, HomeActivity.class);
						startActivity(intent);
						finish();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			super.handleMessage(msg);
		}
	};
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
//		sharedPreferences =getSharedPreferences("user", MODE_PRIVATE);
//		if(sharedPreferences.getBoolean("login", false)){
//			Intent  intent=new Intent();
//			intent.setClass(LoginActivity.this, HomeActivity.class);
//			startActivity(intent);
//			finish();
//		}
		login_bt=(Button) findViewById(R.id.login_bt);
		//点击登录按钮的事件
		login_bt.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				new Thread(){
					public void run() {
						String username=((EditText)(findViewById(R.id.login_id))).getText().toString();
						String password=((EditText)(findViewById(R.id.login_password))).getText().toString();
						String target="http://120.25.73.114:8080/AOHUAServlet/login";
						HttpClient httpClient=new DefaultHttpClient();
						HttpPost httpRequest=new HttpPost(target);
						List<NameValuePair> params=new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("user", username));
						params.add(new BasicNameValuePair("pwd", password));
						try{
							httpRequest.setEntity(new UrlEncodedFormEntity(params,"utf-8"));
							HttpResponse httpResponse=httpClient.execute(httpRequest);
							if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
								//如果成功
								Message message=Message.obtain();
								message.what=1;
								message.obj=EntityUtils.toString(httpResponse.getEntity());
								handler.sendMessage(message);
							}else{
								//如果网络连接失败
								Message message=Message.obtain();
								message.what=0;
								handler.sendMessage(message);
							}
						}catch(Exception e){
							e.printStackTrace();
						}
					};
				}.start();
			}
		});
	}
	

}
