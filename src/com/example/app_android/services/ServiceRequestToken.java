package com.example.app_android.services;

import com.example.app_android.rest.Processes;
import com.example.app_android.rest.RestCommunicationException;
import com.example.app_android.util.Utilities;

import android.content.Intent;
import android.util.Log;

public class ServiceRequestToken extends BaseService {
	
	@Override 
	public void onCreate() {	
		// Do NOT put loggers here. This is done by superclass		
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onStartCommand()");}
		
		GenericRunnableToken genericRunnableToken = new GenericRunnableToken(this, intent);

		startThread(genericRunnableToken);
				
		return super.onStartCommand(intent, flags, startId);
	}
	
	private class GenericRunnableToken extends BaseRunnable {
			
		public GenericRunnableToken(BaseService service, Intent intent) {
				super(service, intent);
	    }	
		
	    public void run() {
			if(Utilities.verbose) {Log.v(TAG, mClassName + ":run()");}
	    	
			informStart();
			// Send start broadcast
			Intent intent = prepareDefaultIntent(mStartBroadcast);
			mService.mySendBroadcast(intent);

			String username = mIntent.getStringExtra("username");
			String password = mIntent.getStringExtra("password"); // TODO is this safe?
			try {
				Processes.requestToken(username, password);
			} catch (RestCommunicationException e) {
				// Send stop broadcast
				intent = prepareDefaultIntent(mStopBroadcast);
				intent.putExtra("errorMessageShort", e.getMessage());
				intent.putExtra("success", false);
				mService.mySendBroadcast(intent);				
				
				informStop();
				return;
			}
					
			// Send stop broadcast
			intent = prepareDefaultIntent(mStopBroadcast);
			intent.putExtra("success", true);
			mService.mySendBroadcast(intent);			
			
			informStop();
	    }
	}
}