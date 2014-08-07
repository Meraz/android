package com.example.app_android.ui;

import com.example.app_android.R;
import com.example.app_android.ui.FragmentNewStudent.NewStudentListener;

import android.os.Bundle;

public class ActivityNewStudent extends BaseActivity implements NewStudentListener {
	
	private static final String TAG = "MainMenu";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	mClassName = getClass().getSimpleName();
    	mTag = TAG;
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_newstudent);        
    } 
}