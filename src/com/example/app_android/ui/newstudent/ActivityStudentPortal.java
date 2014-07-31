package com.example.app_android.ui.newstudent;

import java.util.ArrayList;

import com.example.app_android.R;
import com.example.app_android.ui.elements.expandablelist.BaseExpandableListGroup;
import com.example.app_android.ui.elements.expandablelist.IButtonCallback;
import com.example.app_android.ui.elements.expandablelist.MyBaseExpandableListAdapter;
import com.example.app_android.util.Logger;

import android.content.res.Resources;

import android.os.Bundle;

public class ActivityStudentPortal extends BaseNewStudentActivity implements IButtonCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		mExpandableListAdapter = new MyBaseExpandableListAdapter(this, mExpandableListItems);
		mExpandableListAdapter.setAdapter(mExpandableList);
		mExpandableListAdapter.openSpecificGroups(new int[]{0}); 	// Open first
		mExpandableListAdapter.setOnlyOneOpenBehavior(true);		// only one group can be opened at the time
		mExpandableListAdapter.setUseHtmlFormattingOnText(true);	// name says it all
		mExpandableListAdapter.setButtonCallBack(this);
    }        
    
    public ArrayList<BaseExpandableListGroup> SetStandardGroups() { // TODO engrish / swedrish

    	// Return list of groups
    	ArrayList<BaseExpandableListGroup> finalList = new ArrayList<BaseExpandableListGroup>();
    	
    	// Temporary group
    	BaseExpandableListGroup group;

    	Resources res = getResources();
    	String[] header = res.getStringArray(R.array.new_student_menu_studentportal_header);
    	String[] text = res.getStringArray(R.array.new_student_menu_studentportal_text);
    	
    	for(int i = 0; i < header.length; i++) {
    		group = BaseExpandableListGroup.ConstructOneGroupWithOneChild(header[i], text[i], null);
    		finalList.add(group);
    	}
    	
    	return finalList;
    }
	@Override
	public void onButtonClick(int id) {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onButtonClick()");		
	} 	
}