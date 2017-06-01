package com.example.aohua;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;

public class HomeActivity extends ActivityGroup{
	private  TabHost tabhoat;
	private View tab1,tab2,tab3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		tab1=LayoutInflater.from(this).inflate(R.layout.tab_showorder, null);
		tab2=LayoutInflater.from(this).inflate(R.layout.tab_addorder, null);
		tab3=LayoutInflater.from(this).inflate(R.layout.tab_user, null);
		initTabs();
	}
	//初始化tabhost
	@SuppressWarnings("deprecation")
	private void initTabs(){
		tabhoat=(TabHost) findViewById(R.id.tabhost);
		tabhoat.setup(this.getLocalActivityManager());
		  //去掉底部菜单的分割线
		tabhoat.getTabWidget().setDividerDrawable(android.R.color.transparent);
		//为tanhost添加 
		tabhoat.addTab(tabhoat.newTabSpec("showorder")
				.setIndicator(tab1)
				.setContent(new Intent(this,ShowoderActivity.class)));
		tabhoat.addTab(tabhoat.newTabSpec("addorder")
				.setIndicator(tab2)
				.setContent(new Intent(this,AddActivity.class)));
		tabhoat.addTab(tabhoat.newTabSpec("user")
				.setIndicator(tab3)
				.setContent(new Intent(this,UserActivity.class)));
		tabhoat.setCurrentTab(0);
	}
}

