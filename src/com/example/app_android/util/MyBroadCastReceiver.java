package com.example.app_android.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class MyBroadCastReceiver extends BroadcastReceiver {
	
	// Callback interface to implement own method on how to handle the result.
	public interface Receiver {

		public void onWorkerStart(Intent intent);	
		
		// 
		public void onWorkerUpdate(Intent intent);
		
		// 
		public void onWorkerStop(Intent intent);
	}

	private static final String TAG = "Callback";
	private String mClassName; 
	
	protected String mStartBroadCast = null;
	protected String mStopBroadCast = null;
	protected String mUpdateBroadCast = null;
	protected Receiver mReceiver = null;
	
	public MyBroadCastReceiver(String startBroadCast, String updateBroadCast, String stopBroadCast)
	{
		mStartBroadCast 	= startBroadCast;
		mUpdateBroadCast 	= updateBroadCast;
		mStopBroadCast 		= stopBroadCast;
		
		mClassName = getClass().getSimpleName();
	}
	
	public MyBroadCastReceiver(String stopBroadCast)
	{
		mStopBroadCast = stopBroadCast;
	}
	
	public void registerCallback(Receiver receiver)
	{
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":registerCallback()");}
		mReceiver = receiver;
	}
	
	public void registerBroadCastReceiver(Context context) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":registerBroadCastReceiver()");}
		IntentFilter filter = new IntentFilter();
		if(mStartBroadCast != null)
		{
			filter.addAction(mStartBroadCast);
		}
		
		if(mUpdateBroadCast != null)
		{
			filter.addAction(mUpdateBroadCast);
		}
		
		if(mStopBroadCast != null)
		{
			filter.addAction(mStopBroadCast);
		}	
		context.registerReceiver(this, filter);
	}
	
	public void unregisterBroadCastReceiver(Context context) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":unregisterBroadCastReceiver()");}
		context.unregisterReceiver(this);
	}
	
	public String getStartBroadCast()
	{
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":getStartBroadCast()");}
		return mStartBroadCast;
	}
	
	public String getUpdateBroadCast()
	{
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":getUpdateBroadCast()");}
		return mUpdateBroadCast;
	}
	
	public String getStopBroadCast()
	{
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":getStopBroadCast()");}
		return mStopBroadCast;
	}
	
	public String getAllBroadcastStringAsOne() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":getAllBroadcastStringAsOne()");}
		return mStartBroadCast+mStopBroadCast+mUpdateBroadCast;
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onReceive()");}

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