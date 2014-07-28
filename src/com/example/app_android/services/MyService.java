package com.example.app_android.services;

import java.util.ArrayList;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyService extends Service {	
	
	protected static ArrayList<Thread> mThread;
	protected static int mThreadCount;	
	protected static int mId;
	
	@Override 
	public void onCreate() {
		mThread = new ArrayList<Thread>();
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override 
	public void onDestroy () {
		try {
			for(int i = 0; i < mThread.size(); i++) {
				mThread.get(i).join(10);
			}
		} catch (InterruptedException e) {
			e.printStackTrace(); // TODO
		}
	}	
	
	public static final synchronized int AddThreadToList(Thread thread) {
		mThread.add(thread);
		mThreadCount++;
		return mThread.size()-1; // returns index on newly added item
	}
	
	public static final synchronized void RemoveThreadFromList(int index) {
		mThread.remove(index);
		if(mThreadCount > 0)
			mThreadCount--;
		if(mThreadCount == 0)
			ServiceManager.getInstance().onServiceStop2(mId);		
	}
	
	public final void mySendBroadcast(Intent intent) {
		sendBroadcast(intent);
	}
	
}
