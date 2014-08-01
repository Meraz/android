package com.example.app_android.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.app_android.Cache;
import com.example.app_android.R;
import com.example.app_android.util.Logger;

public class ActivityStudentUnion extends BaseActivity {
	
	private static final String TAG = "MainMenu";
	private static final String blekingeStudentUnionPackageName = "se.bthstudent.android.bsk"; // TODO, move it to xml or something

	/*
	 * This is the first function that is executed for this application.
	 * Specified in the file 'AndroidManifest.xml"
	 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onCreate()");
    	super.onCreate(savedInstanceState);
    	
    	mMessage = "Här ska en vy för Blekinge Studentkår finnas";
        // Sets the content specified in the file in res/layout/activity_main.xml
        // This also specifies which fragment to active
        setContentView(R.layout.activity_studentunion);
    }

    @Override
	protected void onDestroy() {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onDestroy()");
    	Cache.serializeToFile();
    	
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
	
	
    // Temporary step to have original function untouched and still have the functionality set in xml 	// TODO 
    private void launchApp(View view) {
    	launchApp(blekingeStudentUnionPackageName);
    }
	
    // Attempts to start to app with the inputed packageName. If it doesn't exist it opens the apps market page
    private void launchApp(String packageName) {
    	
    	Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
    	if (intent != null) {
    		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
   		    startActivity(intent);
   		}
   		else  { // If the app isn't installed, send the user to the apps store page
   		    intent = new Intent(Intent.ACTION_VIEW);
  		    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
   		    intent.setData(Uri.parse("market://details?id=" + packageName));
    	    startActivity(intent);
    	}
    }
    	
}
