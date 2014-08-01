package com.example.app_android.ui;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.app_android.R;
import com.example.app_android.database.DatabaseManager;
import com.example.app_android.services.ServiceManager;
import com.example.app_android.ui.FragmentMain.InterfaceActivityMain;
import com.example.app_android.util.Logger;
import com.example.app_android.util.Utilities;

public class ActivityMain extends BaseActivity implements InterfaceActivityMain {
	private static final String TAG = "Main";
	
	int mIDCheckLoginService;
	int mIDLoginService;
	
	/*
	 * This is the first function that is executed for this application.
	 * Specified in the file 'AndroidManifest.xml"
	 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onCreate()");
    	super.onCreate(savedInstanceState);
    	ServiceManager.initialize(getApplicationContext());
    	DatabaseManager.initialize(getApplicationContext());
    	
        // Sets the content specified in the file in res/layout/activity_main.xml
        // This also specifies which fragment(s) to activate
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

	public void aFunction()
	{
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered aFunction()");
	}
	
	public void onButtonClicked(View view) {
		Intent intent;
		
		switch (view.getId()) {
		
		case R.id.newStudentButton:
			intent = new Intent(getApplicationContext(), ActivityNewStudent.class);
	        startActivity(intent);
			break;
			
		case R.id.coursesButton:
			intent = new Intent(getApplicationContext(), ActivityCourses.class);
	        startActivity(intent);
			break;
			
		case R.id.mapButton:
			if(Utilities.isNetworkAvailable(this)) {
        		int startLocation = -1; //TODO - Get saved startlocation and use it instead of bringing up the dialog every time
        		if(startLocation >= 0 && startLocation <= 1) {
					intent = new Intent(getApplicationContext(), ActivityMap.class);
					intent.putExtra("entryID", 0);
					intent.putExtra("startPositionID", startLocation);
					intent.putExtra("room", "unknown");
					startActivity(intent);
        		}
        		else
        		showDialog(); //Starts the map activity with user input
        	}
        	else
        		Toast.makeText(getApplicationContext(), "Missing internet connection", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.imageButton4:
			intent = new Intent(getApplicationContext(), ActivityStudentUnion.class);
	        startActivity(intent);
			break;
			
		case R.id.imageButton5:
			//TODO - add call to the mock login thingamabob here
			break;
			
		case R.id.imageButton6:

			break;
		}
	}
	
	private void showDialog() {
		FragmentManager manager = getFragmentManager();
		DialogChooseCity dialog = new DialogChooseCity();
		dialog.show(manager, "chooseCityDialog");
	}
}