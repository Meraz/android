package com.example.app_android.ui.newstudent;

import android.app.Activity;
import android.os.Bundle;

import com.example.app_android.Logger;

public class ActivityStudentCentre extends Activity {
	
	private static final String TAG = "ActivityStudentCentre";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onCreate()");
    	super.onCreate(savedInstanceState);
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

	public void aFunction()
	{
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered aFunction()");
	}
}
