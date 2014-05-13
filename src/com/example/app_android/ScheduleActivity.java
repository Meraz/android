package com.example.app_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ScheduleActivity extends Activity implements ScheduleFragment.Communicator{

	public static String[] mScheduleArray;
	private static final String TAG = "ScheduleActivity";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get resources from stored string array
        mScheduleArray = new String[]{"J1610", "C230"};
        
        setContentView(R.layout.schedule_main); 	
    }
    

    @Override
	protected void onDestroy() {
		Log.i(TAG, getClass().getSimpleName() + ":entered onDestroy()");
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		Log.i(TAG, getClass().getSimpleName() + ":entered onPause()");
		super.onPause();
	}

	@Override
	protected void onRestart() {
		Log.i(TAG, getClass().getSimpleName() + ":entered onRestart()");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		Log.i(TAG, getClass().getSimpleName() + ":entered onResume()");
		super.onResume();
	}

	@Override
	protected void onStart() {
		Log.i(TAG, getClass().getSimpleName() + ":entered onStart()");
		super.onStart();
	}

	@Override
	protected void onStop() {
		Log.i(TAG, getClass().getSimpleName() + ":entered onStop()");
		super.onStop();
	}

	@Override
	public void onListSelection(int index) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getApplicationContext(), FullMapActivity.class);
		intent.putExtra("cityId", -1);
		intent.putExtra("Room", mScheduleArray[index]);
		startActivity(intent);
	}
	
}
