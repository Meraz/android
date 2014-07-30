package com.example.app_android.services;

import com.example.app_android.rest.Processes;
import com.example.app_android.rest.RESTFunctions;
import com.example.app_android.rest.RestCommunicationException;
import com.example.app_android.util.Logger;

import android.content.Intent;

public class ServiceRequestToken extends MyService {
	
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
		
		GenericRunnableToken genericRunnableToken = new GenericRunnableToken(this, intent);

		startThread(genericRunnableToken);
				
		return super.onStartCommand(intent, flags, startId);
	}
	
	private class GenericRunnableToken extends BaseRunnable {
			
		public GenericRunnableToken(MyService service, Intent intent) {
				super(service, intent);
	    }	
		
	    public void run() {
			// Send start broadcast
			Intent intent = prepareDefaultIntent(mStartBroadCast);
			intent.putExtra("id", 42);	// TODO hardcoded
			mService.mySendBroadcast(intent);

			String username = mIntent.getStringExtra("username");
			String password = mIntent.getStringExtra("password"); // TODO is this safe?
			try {
				Processes.requestToken(username, password);
			} catch (RestCommunicationException e) {
				// Send stop broadcast
				intent = prepareDefaultIntent(mStopBroadcast);
				intent.putExtra("id", 42);	// TODO hardcoded because this is done on a overidden function in a button so the activity cannot access. Might redesign this
				intent.putExtra("errorMessageShort", e.getMessage());
				intent.putExtra("success", false);
				mService.mySendBroadcast(intent);				
				
				informServiceAboutThreadShutdown();
				return;
			}
					
			// Send stop broadcast
			intent = prepareDefaultIntent(mStopBroadcast);
			intent.putExtra("id", 42);// TODO hardcoded
			intent.putExtra("success", true);
			mService.mySendBroadcast(intent);			
			
			informServiceAboutThreadShutdown();
	    }
	}
}