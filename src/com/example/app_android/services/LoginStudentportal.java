package com.example.app_android.services;

import com.example.app_android.Logger;
import android.app.IntentService;
import android.content.Intent;

public class LoginStudentportal extends IntentService {
	
	private static final String TAG = "LoginStudentportal";
	
	public LoginStudentportal()	{
		super("NAME"); // TODO bult might just change this eh?
	}
	
	public LoginStudentportal(String name) {
		super(name);
		// TODO ??
	}
	
	@Override
	public void onCreate () {		
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onCreate()");
		super.onCreate();
	}
	
	@Override
	public void onDestroy () {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onDestroy()");
		super.onDestroy();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onHandleIntent()");
		
		String startBroadCast = intent.getStringExtra("startBroadCast");
		String stopBroadCast = intent.getStringExtra("stopBroadCast");
		if(startBroadCast != null)
			sendBroadcast(new Intent(startBroadCast));
		
		// Simulate workload // TODO bult remove
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		TestDatabase.changeSomeData("Second data");
		
		sendBroadcast(new Intent(stopBroadCast));
	}
}
