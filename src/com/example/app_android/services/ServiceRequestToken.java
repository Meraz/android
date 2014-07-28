package com.example.app_android.services;

import com.example.app_android.util.Logger;

import android.content.Intent;

public class ServiceRequestToken extends MyService {
	
	private static final String TAG = "ServiceRequestToken";
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onStartCommand()");
		mId = intent.getIntExtra("id", -1);
		
		GenericRunnableToken genericRunnableToken = new GenericRunnableToken(this, intent);
		Thread thread = new Thread(genericRunnableToken);	
		int index = AddThreadToList(thread);
		genericRunnableToken.setIndex(index);
		thread.start();
				
		return super.onStartCommand(intent, flags, startId);
	}
}