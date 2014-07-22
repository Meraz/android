package com.example.app_android.services;

import com.example.app_android.util.Logger;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


public class LocalService extends Service {
	
	private static final String TAG = "LocalService";
	private Intent mIntent;
	private Thread mThread;	
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	public class Test implements Runnable {

	    public void run() {
	    	String startBroadCast = mIntent.getStringExtra("startBroadCast");
			String stopBroadCast = mIntent.getStringExtra("stopBroadCast");
			int id = mIntent.getIntExtra("id", -1); // -1 default
			if(startBroadCast != null)
				sendBroadcast(new Intent(startBroadCast));
			
			// Simulate workload // TODO bult remove
			try {
				Thread.sleep(8000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
			TestDatabase.changeSomeData("Second data");
			
			sendBroadcast(new Intent(stopBroadCast));
			
			ServiceHelper.getServiceHelper().CallBack(id);
	    }
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onHandleIntent()");
		
		mIntent = intent;
		mThread = new Thread(new Test());
		mThread.start();		
				
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override 
	public void onDestroy () {
		try {
			mThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace(); // TODO
		}
	}	
}