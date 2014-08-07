package com.example.app_android.services;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.app_android.util.Utilities;
import com.example.app_android.util.MyBroadCastReceiver;

public class BaseService extends Service  {	
	
	protected static final String TAG = "Services";
	protected String mClassName;
	
	protected int mThreadCount;	
	protected MyBroadCastReceiver mBroadCastReceiver;
	protected ExecutorService mThread;
	
	@Override 
	public void onCreate() {	
		mClassName = getClass().getSimpleName();
		
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onCreate()");}
		
		mThread = Executors.newCachedThreadPool();		
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onBind()");}
		return null;
	}	
	
	@Override 
	public void onDestroy () {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onDestroy()");}
		mThread.shutdownNow();
	}	
	
	private final synchronized void increaseThreadCount() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":increaseThreadCount()");}
		mThreadCount++;
	}
	
	private final synchronized void decreaseThreadCount(int id) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":decreaseThreadCount()");}
		mThreadCount--;
		
		try {
			ServiceManager.getInstance().informWorkerStop(id);
		}catch(NullPointerException e) {		
			// TODO
		}
		
		if(mThreadCount == 0)
			this.stopSelf();
		}
	
	protected final void mySendBroadcast(Intent intent) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":mySendBroadcast()");}
		sendBroadcast(intent);
	}
	
	protected void informWorkerStart() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":informWorkerStart()");}
		increaseThreadCount();
	}

	// Gets called from broadcastreceiver a thread broadcasts stop 
	protected void informWorkerStop(int id) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":informWorkerStop()");}
		decreaseThreadCount(id); // TODO might crash here?
	}	
	
	protected void startThread(Runnable runnable) {	
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":startThread()");}
		mThread.execute(runnable);
	}	
}
