package com.example.app_android.services;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.app_android.util.Logger;
import com.example.app_android.util.MyBroadCastReceiver;

public class MyService extends Service implements MyBroadCastReceiver.Receiver {	
	
	private static final String TAG = "Services";
	
	protected int mThreadCount;	
	protected MyBroadCastReceiver mBroadCastReceiver;
	protected String mStartBroadCast;
	protected String mUpdateBroadCast;
	public static String mStopBroadCast;		// TODO test with public
	protected ExecutorService mThread;
	
	@Override 
	public void onCreate() {		
		mThread = Executors.newCachedThreadPool();		
		setBroadcastReceiever();
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}	
	
	@Override 
	public void onDestroy () {
		mBroadCastReceiver.unregisterBroadCastReceiver(getApplicationContext());
	}	
	
	private final synchronized void IncreaseThreadCount() {
		mThreadCount++;
	}
	
	private final synchronized void DecreaseThreadCount(int id) {
		mThreadCount--;
		
		if(mThreadCount == 0)
			ServiceManager.getInstance().onServiceStop(id); // Stop entire service, means there's no more threads to run
		else
			ServiceManager.getInstance().onThreadStop(id);	 // Only removes thread metadata from manager, means there's more threads in this service to run
	}
	
	public final void mySendBroadcast(Intent intent) {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered mySendBroadcast()");
		sendBroadcast(intent);
	}

	// Gets called from broadcastreceiver a thread broadcasts start 
	@Override
	public void onServiceStart(Intent intentS) {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onServiceStart()");
	}

	// Gets called from broadcastreceiver a thread broadcasts update
	@Override
	public void onServiceUpdate(Intent intent) {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onServiceUpdate()");
	}

	// Gets called from broadcastreceiver a thread broadcasts stop 
	@Override
	public void onServiceStop(Intent intent) {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onServiceStop()");
		int id = intent.getIntExtra("id", -1);
		if(id != -1)
			DecreaseThreadCount(id);
	}		
	
	private void setBroadcastReceiever() {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered setBroadcastReceiever()");
		mBroadCastReceiver = new MyBroadCastReceiver(mStartBroadCast, mUpdateBroadCast, mStopBroadCast);
		mBroadCastReceiver.registerCallback(this);
		mBroadCastReceiver.registerBroadCastReceiver(getApplicationContext());
	}
	
	public void startThread(Runnable runnable) {
		
		mThread.execute(runnable);
	}
}
