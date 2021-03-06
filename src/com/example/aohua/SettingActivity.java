package com.example.aohua;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class SettingActivity extends Activity implements OnClickListener{
	private ImageView activity_setting_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		activity_setting_back=(ImageView) findViewById(R.id.activity_setting_back);
		activity_setting_back.setOnClickListener(this);
		findViewById(R.id.activity_setting_message_details).setOnClickListener(this);
		findViewById(R.id.activity_setting_password_modify).setOnClickListener(this);
		findViewById(R.id.setting_return_to_login).setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		//点击的是返回按钮
		if(v.getId()==R.id.activity_setting_back){
			SettingActivity.this.finish();
		}//点击的是个人信息页面
		else if(v.getId()==R.id.activity_setting_message_details){
			Intent intent=new Intent();
			intent.setClass(SettingActivity.this, UserdetailsActivity.class);
			startActivity(intent);
		}//退出登录
		else if(v.getId()==R.id.setting_return_to_login){
			SharedPreferences sharedPreferences =getSharedPreferences("user", MODE_PRIVATE);
			Editor editor=sharedPreferences.edit();
			editor.clear();  
            editor.commit(); 
            //这里写整个程序的退出代码
		}
	}
}
