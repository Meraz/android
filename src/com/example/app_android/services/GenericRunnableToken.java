package com.example.app_android.services;

import android.content.Intent;


public class GenericRunnableToken implements Runnable {
	
	private Intent mIntent;
	private MyService mService;
	private int mIndex;
	
	public GenericRunnableToken(MyService service, Intent intent) {
    	mIntent = intent;	 
    	mService = service;
    }	
	
	public void setIndex(int index) {
		mIndex = index;
	}
	
    public void run() {
    	String startBroadCast = mIntent.getStringExtra("startBroadCast");
		String stopBroadCast = mIntent.getStringExtra("stopBroadCast");
		
		int broad_cast_id = mIntent.getIntExtra("id", -1); // Set it to -1 as default if it does not exist. id must be 0+. id should always exist..

		if(startBroadCast != null)
			mService.mySendBroadcast(new Intent(startBroadCast));
		
		
		// Simulate workload // TODO bult remove
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		//RESTFunctions.requestToken("", "", "");
		TestDatabase.changeSomeData("Second data");
		
		mService.mySendBroadcast(new Intent(stopBroadCast));
		
		MyService.RemoveThreadFromList(mIndex);
    }
}
