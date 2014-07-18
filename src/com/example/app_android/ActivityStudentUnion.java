package com.example.app_android;

import com.example.app_android.Logger;
import com.example.app_android.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ActivityStudentUnion extends Activity {
	
	private static final String TAG = "ActivityStudentUnion";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onCreate()a");
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_studentunion); 
        TextView tv = (TextView) findViewById(R.id.textView1);
        tv.setText("studentunion");
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
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onPause()");
		super.onStop();
	}     
}