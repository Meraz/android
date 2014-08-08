package com.example.app_android.services;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.app_android.util.Utilities;
import com.example.app_android.util.MyBroadcastReceiver;

public class BaseService extends Service  {	
	
	protected static final String TAG = "Services";
	protected String mClassName;
	
	protected int mThreadCount;	
	protected MyBroadcastReceiver mBroadCastReceiver;
	protected ExecutorService mThreadPool;
	
	@Override 
	public void onCreate() {	
		mClassName = getClass().getSimpleName();
		
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onCreate()");}
		
		mThreadPool = Executors.newCachedThreadPool();		// TODO Optimally, this should be moved out from here and put in an override version of onCreate. This is true because it's not sure that all services would want to use the same kind of Executor
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
		mThreadPool.shutdownNow();
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
		decreaseThreadCount(id);
	}	
	
	// This is where thread starts. Put this in a function if ever a queue or other instructions are needed beyond just starting the thread.
	protected void startThread(Runnable runnable) {	
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":startThread()");}
		mThreadPool.execute(runnable);
	}	
}
