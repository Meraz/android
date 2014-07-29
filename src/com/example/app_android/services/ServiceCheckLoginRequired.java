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
		
		checkLogin genericRunnableToken = new checkLogin(this, intent);

		startThread(genericRunnableToken);
				
		return super.onStartCommand(intent, flags, startId);
	}
	
	private class checkLogin implements Runnable {
		
		private Intent mIntent;
		private MyService mService;
		
		@SuppressWarnings("unused") // TODO
		private static final String TAG = "Services";
		
		public checkLogin(MyService service, Intent intent) {
	    	mIntent = intent;	 
	    	mService = service;
	    }	
		
	    public void run() {
	    	String startBroadcast = mIntent.getStringExtra("startBroadcast");
			String updateBroadcast = mIntent.getStringExtra("updateBroadcast");
			String stopBroadcast = mIntent.getStringExtra("stopBroadcast");
			
			int broad_cast_id = mIntent.getIntExtra("id", -1); // Set it to -1 as default if it does not exist. id must be 0+. id should always exist..
			Logger.ErrorLog("broad_cast_id" + broad_cast_id);

			// Send start broadcast
			Intent intent = new Intent(startBroadcast);
			intent.putExtra("id", broad_cast_id);
			mService.mySendBroadcast(intent);

			boolean loginRequired = Processes.checkIfLoginIsNeeded();
		
			// Send stop broadcast
			intent = new Intent(stopBroadcast);
			intent.putExtra("id", broad_cast_id);
			intent.putExtra("login_required", loginRequired);
			mService.mySendBroadcast(intent);				
			
			// Remove thread from service
			intent = new Intent(MyService.mStopBroadCast);
			intent.putExtra("id", broad_cast_id);
			mService.mySendBroadcast(intent);
	    }
	}
}