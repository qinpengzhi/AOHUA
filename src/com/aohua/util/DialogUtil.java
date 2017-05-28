package com.aohua.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;

public class DialogUtil {
	
	//定义一个显示消息的对话框
	public static void showDialog(final Context ctx,String msg,boolean goHome){
		AlertDialog.Builder builder=new AlertDialog.Builder(ctx)
		.setMessage(msg).setCancelable(false);
		if(goHome){
			builder.setPositiveButton("确定", new OnClickListener( ) {
				
				//点击确定的事件
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
		}else{
			builder.setPositiveButton("确定", null);
		}
		builder.create().show();
	}
	public static void showDialog(Context ctx,View view){
		new AlertDialog.Builder(ctx)
		.setView(view).setCancelable(false)
		.setPositiveButton("确定", null);
	}
}
