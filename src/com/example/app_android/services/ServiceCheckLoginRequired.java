package com.example.app_android.services;

import com.example.app_android.rest.Processes;
import com.example.app_android.rest.Processes.LoginStatus;
import com.example.app_android.util.Logger;

import android.content.Intent;

public class ServiceCheckLoginRequired extends BaseService {
	
	@Override 
	public void onCreate() {	
		
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

			LoginStatus loginStatus = Processes.checkIfLoginIsNeeded();
			
			boolean loginRequired = true;
			String message = "";
			if(loginStatus == LoginStatus.AlreadyLoggedIn) {
				loginRequired = false;
				message = "Already logged in";
			}
			else if(loginStatus == LoginStatus.Waiting) {
				loginRequired = false;
				message = "Already waiting on login notice";
			}
			else if(loginStatus == LoginStatus.LoginRequired) {
				message = "You are clear to login!";
			}
		
			// Send stop broadcast
			intent = prepareDefaultIntent(mStopBroadcast);
			intent.putExtra("loginRequired", loginRequired);
			intent.putExtra("message", message);
			mService.mySendBroadcast(intent);				
			
			informStop();
	    }
	}
}