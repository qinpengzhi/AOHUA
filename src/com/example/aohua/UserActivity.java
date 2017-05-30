package com.example.aohua;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class UserActivity extends Activity implements OnClickListener{
	private ImageView user_setting_img;
	private TextView user_message_details;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		user_setting_img=(ImageView) findViewById(R.id.user_setting_img);
		user_setting_img.setOnClickListener(this);
		user_message_details=(TextView) findViewById(R.id.user_message_details);
		user_message_details.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		 //进入的是设置界面
		if(v.getId()==R.id.user_setting_img){
			Intent intent=new Intent();
			intent.setClass(UserActivity.this, SettingActivity.class);
			startActivity(intent);
		}//进入的是个人信息详情页面
		else if(v.getId()==R.id.user_message_details){
			
		}
	}
}
