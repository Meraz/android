package com.example.app_android;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class StudentUnionActivity extends Activity {

	private static final String TAG = "MainActivity";
	final String packageName = "se.bthstudent.android.bsk";
    private final static boolean verbose = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	if (verbose)
    		Log.v(TAG, getClass().getSimpleName() + ":entered onCreate");
    	
    	LaunchStudentUnionApp();
        super.onCreate(savedInstanceState);
    }

    @Override
	protected void onDestroy() {
    	if (verbose)
    		Log.v(TAG, getClass().getSimpleName() + ":entered onDestroy()");
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		if (verbose)
    		Log.v(TAG, getClass().getSimpleName() + ":entered onPause()");
		super.onPause();
	}

	@Override
	protected void onRestart() {
		if (verbose)
    		Log.v(TAG, getClass().getSimpleName() + ":entered onRestart()");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		if (verbose)
    		Log.v(TAG, getClass().getSimpleName() + ":entered onResume()");
		super.onResume();
	}

	@Override
	protected void onStart() {
		if (verbose)
    		Log.v(TAG, getClass().getSimpleName() + ":entered onStart()");
		super.onStart();
	}

	@Override
	protected void onStop() {
		if (verbose)
    		Log.v(TAG, getClass().getSimpleName() + ":entered onStop()");
		super.onStop();
	}
	
	private void LaunchStudentUnionApp() {
		Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
		if (intent != null)
		{
		    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		    startActivity(intent);
		}
		else //If the app isn't installed, send the user to the apps store page
		{
		    intent = new Intent(Intent.ACTION_VIEW);
		    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		    intent.setData(Uri.parse("market://details?id=" + packageName));
		    startActivity(intent);
		}
	}
}