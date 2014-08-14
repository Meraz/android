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
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        mClassName = getClass().getSimpleName();
		mTag = TAG;
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_studentportal); 
        
		mExpandableList = (ExpandableListView) findViewById(R.id.ExpandableList);
		mExpandableListItems = SetStandardGroups();
		mInfoBoxTitle = getString(R.string.infobox_title_newstudent);
		mInfoBoxMessage = getString(R.string.infobox_text_newstudent);
		
		mActionBarTitle = getString(R.string.actionbar_newstudent_base);
    }        
    
	abstract protected ArrayList<ExpandableListGroup> SetStandardGroups();

	@Override
	protected void onStop() {
		if(Utilities.verbose) {Log.v(TAG, getClass().getSimpleName() + ":onStop()");}
		super.onStop();
	}

	@Override
	abstract public void onButtonClick(ExpandableListMetaButton metabutton);
}
