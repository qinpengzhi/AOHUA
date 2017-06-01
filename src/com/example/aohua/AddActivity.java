package com.example.aohua;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class AddActivity extends Activity implements OnClickListener{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		findViewById(R.id.add_goaddorder).setOnClickListener(this);
		findViewById(R.id.add_goaddorder1).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.add_goaddorder){
			//�����������۶���ҳ��
			Intent intent=new Intent();
			intent.setClass(AddActivity.this, AddorderActivity.class);
			startActivity(intent);
		}else if(v.getId()==R.id.add_goaddorder1){
			//���������ɹ�����ҳ��
			Intent intent=new Intent();
			intent.setClass(AddActivity.this, AddorderActivity1.class);
			startActivity(intent);
		}
		
	}
}
