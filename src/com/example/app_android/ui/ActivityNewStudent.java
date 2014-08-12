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
		mTitle = getString(R.string.infobox_title_newstudent_mainmenu);
		mInfoBoxMessage = getString(R.string.infobox_text_newstudent_mainmenu);
        
        setContentView(R.layout.activity_newstudent);        
    } 
}