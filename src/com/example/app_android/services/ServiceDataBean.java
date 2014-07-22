package com.example.app_android.services;

import android.content.Intent;

// import com.example.app_android.services.ServiceHelper.ActivityCallback; // TODO
import com.example.app_android.util.MyBroadCastReceiver;

public class ServiceDataBean {

	private int mKey;
	private String mParameter;
//	private ActivityCallback mCallback;// TODO
	private MyBroadCastReceiver mBroadCastReceiver;
	private Intent mIntent;
		
	public ServiceDataBean() {
		// TODO 
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
	// TODO
	/* 
	public ActivityCallback getCallback()
	{
		return mCallback;
	}
	
	public void setCallback(ActivityCallback callback) {
		mCallback = callback;
	}	*/
	
	public MyBroadCastReceiver getBroadCastReceiver()
	{
		return mBroadCastReceiver;
	}
	
	public void settBroadCastReceiver(MyBroadCastReceiver broadCastReceiver) {
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