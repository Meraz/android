package com.example.app_android;

import com.example.app_android.database.DatabaseManager;
import com.example.app_android.services.ServiceManager;

import android.app.Application;

public class BTHApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		
    	ServiceManager.initialize(getApplicationContext());
    	DatabaseManager.initialize(getApplicationContext());
	}
}
