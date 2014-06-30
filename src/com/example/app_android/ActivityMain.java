package com.example.app_android;

import com.example.app_android.InterfaceListSelectionListener;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class ActivityMain extends Activity implements InterfaceListSelectionListener {

	//public static String[] mMainPageArray;
	private static final String TAG = "MainActivity";
	private static final String blekingeStudentUnionPackageName = "se.bthstudent.android.bsk";

	/*
	 * This is the first function that is executed for this application.
	 * Specified in the file 'AndroidManifest.xml"
	 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onCreate()");
    	super.onCreate(savedInstanceState);

    	Cache.Initialize();
    	
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

    @Override
	public void onListSelection(int index) {
    	Logger.VerboseLog(TAG, "Tapped on index " + index);
    	Intent intent;
    	switch (index) {
        case 0:
          intent = new Intent(getApplicationContext(), ActivityNewStudent.class);
          startActivity(intent);
          break;

        case 1:
          intent = new Intent(getApplicationContext(), ActivitySchedule.class);
          startActivity(intent);
          break;

        case 2:
          intent = new Intent(getApplicationContext(), ActivityMyCoursesAndProgram.class);
          startActivity(intent);
          break;
          
        case 4:
          showDialog();
          break;
          
        case 6:
          launchApp(blekingeStudentUnionPackageName);
          break;

        default:
          break;
      }
	}
    
    public void showDialog() {
    	FragmentManager manager = getFragmentManager();
    	DialogChooseCity dialog = new DialogChooseCity();
    	dialog.show(manager, "chooseCityDialog");
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
