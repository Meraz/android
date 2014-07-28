package com.example.app_android.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class MyBroadCastReceiver extends BroadcastReceiver {
	
	// Callback interface to implement own method on how to handle the result.
	public interface Receiver {

		// Returns the same id as ServiceHelper
		public void onServiceStart(int id);	
		
		// 
		public void onServiceUpdate(int id, int statusCode, String message);
		
		// 
		public void onServiceStop(int id, int statusCode, String message);
	}

	private static final String TAG = "MyResultReceiver";
	private String mStartBroadCast = null;
	private String mStopBroadCast = null;
	private String mUpdateBroadCast = null;
	private Receiver mReceiver = null;
	private IServiceManager mServiceManager  = null;
	
	public MyBroadCastReceiver(String startBroadCast, String updateBroadCast, String stopBroadCast)
	{
		mStartBroadCast 	= startBroadCast;
		mUpdateBroadCast 	= updateBroadCast;
		mStopBroadCast 		= stopBroadCast;
	}
	
	public MyBroadCastReceiver(String stopBroadCast)
	{
		mStopBroadCast = stopBroadCast;
	}
	
	public void registerCallback(Receiver receiver)
	{
		mReceiver = receiver;
	}
	
	public void registerBroadCastReceiver(Context context) {
		IntentFilter filter = new IntentFilter();
		if(mStartBroadCast != null)
		{
			filter.addAction(mStartBroadCast);
		}
		filter.addAction(mStopBroadCast);		
		context.registerReceiver(this, filter);
	}
	
	public void unregisterBroadCastReceiver(Context context) {
		context.unregisterReceiver(this);
	}
	
	public String getStartBroadCast()
	{
		return mStartBroadCast;
	}
	
	public String getUpdateBroadCast()
	{
		return mUpdateBroadCast;
	}
	
	public String getStopBroadCast()
	{
		return mStopBroadCast;
	}
	
	public void setServiceManager(IServiceManager serviceManager) {
		mServiceManager = serviceManager;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onReceive()");

		if(mReceiver != null) {
			int id = intent.getIntExtra("id", -1);
			
			if(intent.getAction() == mStopBroadCast) {
				if(mServiceManager != null)
					mServiceManager.onServiceStop(id);	// Tell a servicemanager that this service has stopped.
				int statusCode = intent.getIntExtra("statusCode", 1);
				String statusMessage = intent.getStringExtra("statusMessage");	
				mReceiver.onServiceStop(id, statusCode, statusMessage);
			}
			
			else if(intent.getAction() == mUpdateBroadCast) {
				int statusCode = intent.getIntExtra("statusCode", 1);
				String statusMessage = intent.getStringExtra("statusMessage");
				mReceiver.onServiceUpdate(id, statusCode, statusMessage);
			}
			
			else if(intent.getAction() == mStartBroadCast) {
				mReceiver.onServiceStart(id);
			}
		}
	}
	
}