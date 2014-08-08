package com.example.app_android.services;

import com.example.app_android.util.Utilities;

import android.content.Intent;
import android.util.Log;

public abstract class BaseRunnable implements Runnable  {
	
	protected static final String TAG = "Services";
	protected String mClassName;

	protected Intent mIntent;
	protected BaseService mService;

	protected int mBroadcastID;
	
	protected String mStartBroadcast;
	protected String mUpdateBroadcast;
	protected String mStopBroadcast;
	
	public BaseRunnable(BaseService service, Intent intent) {
    	mIntent = intent;	 
    	mService = service;
    	mClassName = getClass().getSimpleName();
    	mBroadcastID = intent.getIntExtra("id", -1);
    	
    	mStartBroadcast 	= mIntent.getStringExtra("startBroadcast");
    	mUpdateBroadcast 	= mIntent.getStringExtra("updateBroadcast");
    	mStopBroadcast 		= mIntent.getStringExtra("stopBroadcast");
    }	
	
	// Put thread in threadpool
	// This is made as a function so it can be logged and if ever the future brings on functionality that has to be done before actually starting. Also increased readability in the run function.
    protected void informStart() {
    	if(Utilities.verbose) {Log.v(TAG, mClassName + ":informStart()");}
    	mService.informWorkerStart();
    }
	
	// Remove thread from threadpool
    // Read informStop comment
    protected void informStop() {
    	if(Utilities.verbose) {Log.v(TAG, mClassName + ":informStop()");}
    	mService.informWorkerStop(mBroadcastID);
    }
    
    // This reduced the amount of code as this was repeated quite often. It also made it much easier to read
    protected Intent prepareDefaultIntent(String broadCastString) {
    	if(Utilities.verbose) {Log.v(TAG, mClassName + ":prepareDefaultIntent()");}
		Intent intent = new Intent(broadCastString);
		intent.putExtra("id", mBroadcastID);
		return intent;
    }

	@Override 
	abstract public void run();

}
