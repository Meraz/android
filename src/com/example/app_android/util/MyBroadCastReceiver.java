package com.example.app_android.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import com.example.app_android.Logger;

public class MyBroadCastReceiver extends BroadcastReceiver {

	private static final String TAG = "MyResultReceiver";
	private String mStartBroadCast = null;
	private String mStopBroadCast = null;
	private Receiver mReceiver = null;
	
	public MyBroadCastReceiver(String startBroadCast, String stopBroadCast)
	{
		mStartBroadCast = startBroadCast;
		mStopBroadCast = stopBroadCast;
	}
	
	public MyBroadCastReceiver(String stopBroadCast)
	{
		mStopBroadCast = stopBroadCast;
	}
	
	public void registerCallback(Receiver receiver)
	{
		mReceiver = receiver;
	}
	
	// TODO bult <~ easier to find for grep
	// Remove if not needed anymore, was needed before
	/*
	public void finalizeFilter(Context context)
	{
		IntentFilter filter = new IntentFilter();
		if(mStartBroadCast != null)
		{
			filter.addAction(mStartBroadCast);
		}
		filter.addAction(mStopBroadCast);		
		context.registerReceiver(this, filter);
	}
	*/
	
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
	
	public String getStopBroadCast()
	{
		return mStopBroadCast;
	}
	
	// Callback interface to implement own method on how to handle the result.
	public interface Receiver {
		// Callback method wich needs to be implement to handle the sync result.
		public void onServiceStart();
		public void onReceiveResult(int resultCode);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onAttach()");

		// check which broadcast it is
		if(intent.getAction() == mStopBroadCast) {
			if(mReceiver != null) {
				mReceiver.onReceiveResult(0);
			}			
		}
		else // only one other case
		{
			if(mReceiver != null) {
				mReceiver.onServiceStart();
			}
		}
	}
}