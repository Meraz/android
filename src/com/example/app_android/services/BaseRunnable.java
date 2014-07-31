package com.example.app_android.services;

import android.content.Intent;

public abstract class BaseRunnable implements Runnable  {
	
	protected static final String TAG = "Services";

	protected Intent mIntent;
	protected MyService mService;
	protected String mClassName;
	protected int mBroadcastID;
	
	protected String mStartBroadcast;
	protected String mUpdateBroadcast;
	protected String mStopBroadcast;
	
	public BaseRunnable(MyService service, Intent intent) {
    	mIntent = intent;	 
    	mService = service;
    	mClassName = getClass().getSimpleName();
    	mBroadcastID = intent.getIntExtra("id", -1);
    	
    	mStartBroadcast 	= mIntent.getStringExtra("startBroadcast");
    	mUpdateBroadcast 	= mIntent.getStringExtra("updateBroadcast");
    	mStopBroadcast 		= mIntent.getStringExtra("stopBroadcast");
    }	
	
	// Add threadcount
    protected void informServiceAboutThreadStart() {

		Intent intent = new Intent(mService.mStartBroadCast);
		intent.putExtra("id", mBroadcastID);
		mService.mySendBroadcast(intent);
    }
	
	// Remove thread from service
    protected void informServiceAboutThreadShutdown() {
		Intent intent = new Intent(mService.mStopBroadCast);
		intent.putExtra("id", mBroadcastID);
		mService.mySendBroadcast(intent);
    }
    
    protected Intent prepareDefaultIntent(String broadCastString) {
		Intent intent = new Intent(broadCastString);
		intent.putExtra("id", mBroadcastID);
		return intent;
    }

	@Override 
	abstract public void run();

}
