package com.example.app_android.services;

import android.content.Intent;

// import com.example.app_android.services.ServiceHelper.ActivityCallback; // TODO
import com.example.app_android.util.MyBroadcastReceiver;

public class ServiceDataBean {

	private int mKey;
	private String mParameter;
	private MyBroadcastReceiver mBroadCastReceiver;
	private Intent mIntent; 
		
	public ServiceDataBean() {
	}
	
	public int getKey() {
		return mKey;
	}
	
	public void setKey(int key) {
		mKey = key;
	}
	
	public String getParameter() {
		return mParameter;
	}
	
	public void setParameter(String parameter) {
		mParameter = parameter;
	}

	public MyBroadcastReceiver getBroadCastReceiver()
	{
		return mBroadCastReceiver;
	}
	
	public void settBroadCastReceiver(MyBroadcastReceiver broadCastReceiver) {
		mBroadCastReceiver = broadCastReceiver;
	}	
	
	public Intent getIntent()
	{
		return mIntent;
	}
	
	public void setIntent(Intent intent) {
		mIntent = intent;
	}		
}