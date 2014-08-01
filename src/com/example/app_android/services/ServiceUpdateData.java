package com.example.app_android.services;

import com.example.app_android.util.Logger;

import android.content.Intent;

public class ServiceUpdateData extends BaseService {
	
	@Override 
	public void onCreate() {	
		// Do NOT put loggers here. This is done by superclass		
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Logger.VerboseLog(TAG, mClassName + ":entered onStartCommand()");
		
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