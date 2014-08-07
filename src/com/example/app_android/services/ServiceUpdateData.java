package com.example.app_android.services;

import com.example.app_android.util.Utilities;

import android.content.Intent;
import android.util.Log;

public class ServiceUpdateData extends BaseService {
	
	@Override 
	public void onCreate() {	
		// Do NOT put loggers here. This is done by superclass		
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onStartCommand()");}
		
		CheckLogin checkLogin = new CheckLogin(this, intent);

		startThread(checkLogin);
				
		return super.onStartCommand(intent, flags, startId);
	}
	
	private class CheckLogin extends BaseRunnable{
		
		public CheckLogin(BaseService service, Intent intent) {
	    	super(service, intent);
	    }
		
		@Override
	    public void run() {		
			if(Utilities.verbose) {Log.v(TAG, mClassName + ":run()");}
			informStart();
			
			// Send start broadcast
			Intent intent = prepareDefaultIntent(mStartBroadcast);
			mService.mySendBroadcast(intent);

			//boolean loginRequired = Processes.checkIfLoginIsNeeded();
		
			// Send stop broadcast
			intent = prepareDefaultIntent(mStopBroadcast);
	//		intent.putExtra("loginRequired", loginRequired);
			mService.mySendBroadcast(intent);				
			
			informStop();
	    }
	}
}