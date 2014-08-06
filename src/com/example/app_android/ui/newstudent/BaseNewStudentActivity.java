package com.example.app_android.ui.newstudent;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;

import com.example.app_android.R;
import com.example.app_android.ui.BaseActivity;
import com.example.app_android.ui.elements.expandablelist.ExpandableListGroup;
import com.example.app_android.ui.elements.expandablelist.ExpandableListMetaButton;
import com.example.app_android.ui.elements.expandablelist.IButtonCallback;
import com.example.app_android.ui.elements.expandablelist.MyBaseExpandableListAdapter;
import com.example.app_android.util.Utilities;

abstract public class BaseNewStudentActivity extends BaseActivity implements IButtonCallback{
	
	protected static final String TAG = "NewstudentMenu";
	protected MyBaseExpandableListAdapter mExpandableListAdapter;
	protected ArrayList<ExpandableListGroup> mExpandableListItems;
	protected ExpandableListView mExpandableList;
	
	protected String mClass;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		if(Utilities.verbose) {Log.v(TAG, getClass().getSimpleName() + ":onCreate()");}
        super.onCreate(savedInstanceState);
        
        mClass = getClass().getSimpleName();
        
        setContentView(R.layout.activity_studentportal); 
        
		mExpandableList = (ExpandableListView) findViewById(R.id.ExpandableList);
		mExpandableListItems = SetStandardGroups();
    }        
    
	abstract protected ArrayList<ExpandableListGroup> SetStandardGroups();

    @Override
	protected void onDestroy() {
    	if(Utilities.verbose) {Log.v(TAG, getClass().getSimpleName() + ":onDestroy()");}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		if(Utilities.verbose) {Log.v(TAG, getClass().getSimpleName() + ":onPause()");}
		super.onPause();
	}

	@Override
	protected void onRestart() {
		if(Utilities.verbose) {Log.v(TAG, getClass().getSimpleName() + ":onRestart()");}
		super.onRestart();
	}

	@Override
	protected void onResume() {
		if(Utilities.verbose) {Log.v(TAG, getClass().getSimpleName() + ":onResume()");}
		super.onResume();
		
		mTitle = "About";
		mMessage = "Add text here";
	}

	@Override
	protected void onStart() {
		if(Utilities.verbose) {Log.v(TAG, getClass().getSimpleName() + ":onStart()");}
		super.onStart();
	}

	@Override
	protected void onStop() {
		if(Utilities.verbose) {Log.v(TAG, getClass().getSimpleName() + ":onStop()");}
		super.onStop();
	}

	@Override
	abstract public void onButtonClick(ExpandableListMetaButton metabutton);
}
