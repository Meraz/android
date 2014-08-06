package com.example.app_android.services;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

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
		Utilities.VerboseLog(TAG, mClassName + "::entered onCreate()");
		mClassName = getClass().getSimpleName();
		mThread = Executors.newCachedThreadPool();		
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		Utilities.VerboseLog(TAG, mClassName + "::entered onBind()");
		return null;
	}	
	
	@Override 
	public void onDestroy () {
		Utilities.VerboseLog(TAG, mClassName + "::entered onDestroy()");
		mThread.shutdownNow();
	}	
	
	private final synchronized void increaseThreadCount() {
		Utilities.VerboseLog(TAG, mClassName + "::entered IncreaseThreadCount()");
		mThreadCount++;
	}
	
	private final synchronized void decreaseThreadCount(int id) {
		Utilities.VerboseLog(TAG, mClassName + "::entered DecreaseThreadCount()");
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
		Utilities.VerboseLog(TAG, mClassName + "::entered mySendBroadcast()");
		sendBroadcast(intent);
	}
	
	protected void informWorkerStart() {
		Utilities.VerboseLog(TAG, mClassName + "::entered informWorkerStart()");
		increaseThreadCount();
	}

	// Gets called from broadcastreceiver a thread broadcasts stop 
	protected void informWorkerStop(int id) {
		Utilities.VerboseLog(TAG, mClassName + "::entered informWorkerStop()");
		decreaseThreadCount(id); // TODO might crash here?
	}	
	
	protected void startThread(Runnable runnable) {	
		Utilities.VerboseLog(TAG, mClassName + "::entered startThread()");
		mThread.execute(runnable);
	}	
}
