package com.example.app_android;


import android.app.Activity;

import android.os.Bundle;

public class ActivityMain extends Activity {

	private static final String TAG = "ActivityMain";

	/*
	 * This is the first function that is executed for this application.
	 * Specified in the file 'AndroidManifest.xml"
	 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onCreate()");
    	super.onCreate(savedInstanceState);
    	Cache.initialize(getApplicationContext());
    	
        // Sets the content specified in the file in res/layout/activity_main.xml
        // This also specifies which fragment to active
        setContentView(R.layout.activity_main);       
    }

    @Override
	protected void onDestroy() {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onDestroy()");
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onPause()");
		super.onPause();
	}

	@Override
	protected void onRestart() {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onRestart()");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onResume()");
		super.onResume();
	}

	@Override
	protected void onStart() {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onStart()");
		super.onStart();
	}

	@Override
	protected void onStop() {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onStop()");
		super.onStop();
	}
}
