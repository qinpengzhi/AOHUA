package com.example.aohua;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class UserdetailsActivity extends Activity{
	private ImageView activity_userdetals_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userdetails);
		activity_userdetals_back=(ImageView) findViewById(R.id.activity_userdetals_back);
		activity_userdetals_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
