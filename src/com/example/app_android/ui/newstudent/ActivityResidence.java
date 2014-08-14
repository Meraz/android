package com.example.app_android.ui.newstudent;

import java.util.ArrayList;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;


import com.example.app_android.R;
import com.example.app_android.ui.elements.expandablelist.ExpandableListGroup;
import com.example.app_android.ui.elements.expandablelist.ExpandableListMetaButton;
import com.example.app_android.ui.elements.expandablelist.IButtonCallback;
import com.example.app_android.ui.elements.expandablelist.MyBaseExpandableListAdapter;
import com.example.app_android.util.Utilities;

public class ActivityResidence extends BaseNewStudentActivity implements IButtonCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);

		mExpandableListAdapter = new MyBaseExpandableListAdapter(this, mExpandableListItems);
		mExpandableListAdapter.setAdapter(mExpandableList);
		mExpandableListAdapter.openSpecificGroups(new int[]{0}); 	// Open first
		mExpandableListAdapter.setOnlyOneOpenBehavior(true);		// only one group can be opened at the time
		mExpandableListAdapter.setUseHtmlFormattingOnText(true);	// name says it all
		mExpandableListAdapter.setButtonCallBack(this);
		
		mActionBarTitle += getString(R.string.actionbar_newstudent_residence);
    }
    
    public ArrayList<ExpandableListGroup> SetStandardGroups() {
		if(Utilities.verbose) {Log.v(TAG, getClass().getSimpleName() + ":SetStandardGroups()");}
    	// Return list of groups
    	ArrayList<ExpandableListGroup> finalList = new ArrayList<ExpandableListGroup>();
    	
    	// Temporary group
    	ExpandableListGroup group;

    	Resources res = getResources();
    	String[] header = res.getStringArray(R.array.new_student_menu_residence_header);
    	String[] text = res.getStringArray(R.array.new_student_menu_residence_text);
    	
    	for(int i = 0; i < header.length; i++) {
    		group = ExpandableListGroup.ConstructOneGroupWithOneChild(header[i], text[i], null);
    		finalList.add(group);
    	}    	
    	return finalList;
    }

	@Override
	public void onButtonClick(ExpandableListMetaButton metabutton) {
		if(Utilities.verbose) {Log.v(TAG, getClass().getSimpleName() + ":onButtonClick()");}	
	} 	
}
