package com.example.app_android.services;

import com.example.app_android.rest.Processes;
import com.example.app_android.rest.RESTFunctions;
import com.example.app_android.rest.RestCommunicationException;
import com.example.app_android.util.Logger;

import android.content.Intent;

public class ServiceCheckLoginRequired extends MyService {
	
	private static final String TAG = "Services";

	@Override 
	public void onCreate() {	
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onCreate()");
		String className = getClass().getSimpleName() ;
		mStartBroadCast 	= className + "_START";
		mUpdateBroadCast 	= className + "_UPDATE";
		mStopBroadCast 		= className + "_STOP";
		
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onStartCommand()");
		
		CheckLogin checkLogin = new CheckLogin(this, intent);

		startThread(checkLogin);
				
		return super.onStartCommand(intent, flags, startId);
	}
	
	private class CheckLogin extends BaseRunnable{
		
		public CheckLogin(MyService service, Intent intent) {
	    	super(service, intent);
	    }
		
		@Override
	    public void run() {			
			informServiceAboutThreadStart();
			
			// Send start broadcast
			Intent intent = prepareDefaultIntent(mStartBroadcast);
			mService.mySendBroadcast(intent);

			boolean loginRequired = Processes.checkIfLoginIsNeeded();
		
			// Send stop broadcast
			intent = prepareDefaultIntent(mStopBroadcast);
			intent.putExtra("loginRequired", loginRequired);
			mService.mySendBroadcast(intent);				
			
			informServiceAboutThreadShutdown();
	    }
	}
}