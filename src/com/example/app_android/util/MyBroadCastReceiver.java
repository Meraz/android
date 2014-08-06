package com.example.app_android.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class MyBroadCastReceiver extends BroadcastReceiver {
	
	// Callback interface to implement own method on how to handle the result.
	public interface Receiver {

		// Returns the same id as ServiceHelper
		public void onWorkerStart(Intent intent);	
		
		// 
		public void onWorkerUpdate(Intent intent);
		
		// 
		public void onWorkerStop(Intent intent);
	}

	private static final String TAG = "MyResultReceiver";
	private String mStartBroadCast = null;
	private String mStopBroadCast = null;
	private String mUpdateBroadCast = null;
	private Receiver mReceiver = null;
	
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
	
	public String getAllBroadcasts() {
		return mStartBroadCast+mStopBroadCast+mUpdateBroadCast;
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Utilities.VerboseLog(TAG, getClass().getSimpleName() + ":entered onReceive()");

		if(mReceiver != null) {
			String action = intent.getAction();
			
			if(action.equalsIgnoreCase(mStopBroadCast)) {
				mReceiver.onWorkerStop(intent);
			}			
			else if(action.equalsIgnoreCase(mUpdateBroadCast)) {
				mReceiver.onWorkerUpdate(intent);
			}			
			else if(action.equalsIgnoreCase(mStartBroadCast)) {
				mReceiver.onWorkerStart(intent);
			}
		}
	}
	
}