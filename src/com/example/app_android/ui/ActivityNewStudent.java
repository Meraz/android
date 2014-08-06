package com.example.app_android.ui;

import com.example.app_android.R;
import com.example.app_android.ui.FragmentNewStudent.NewStudentListener;
import com.example.app_android.util.Utilities;

import android.os.Bundle;

public class ActivityNewStudent extends BaseActivity implements NewStudentListener {
	
	private static final String TAG = "MainMenu";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	Utilities.VerboseLog(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_newstudent);        
    }    

    @Override
	protected void onDestroy() {
    	Utilities.VerboseLog(TAG, getClass().getSimpleName() + ":entered onDestroy()");
		super.onDestroy();
	}

	@Override
	protected void onPause() {
    	Utilities.VerboseLog(TAG, getClass().getSimpleName() + ":entered onPause()");
		super.onPause();
	}

	@Override
	protected void onRestart() {
    	Utilities.VerboseLog(TAG, getClass().getSimpleName() + ":entered onRestart()");
		super.onRestart();
	}

	@Override
	protected void onResume() {
    	Utilities.VerboseLog(TAG, getClass().getSimpleName() + ":entered onResume()");
		super.onResume();
	}

	@Override
	protected void onStart() {
    	Utilities.VerboseLog(TAG, getClass().getSimpleName() + ":entered onStart()");
		super.onStart();
	}

	@Override
	protected void onStop() {
    	Utilities.VerboseLog(TAG, getClass().getSimpleName() + ":entered onPause()");
		super.onStop();
	}     
}