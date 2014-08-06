package com.example.app_android.ui.newstudent;

import java.util.ArrayList;

import com.example.app_android.R;
import com.example.app_android.ui.elements.expandablelist.ExpandableListChild;
import com.example.app_android.ui.elements.expandablelist.ExpandableListGroup;
import com.example.app_android.ui.elements.expandablelist.ExpandableListMetaButton;
import com.example.app_android.ui.elements.expandablelist.IButtonCallback;
import com.example.app_android.ui.elements.expandablelist.MyBaseExpandableListAdapter;
import com.example.app_android.util.Utilities;

import android.content.Intent;
import android.content.res.Resources;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

public class ActivityStudentPortal extends BaseNewStudentActivity implements IButtonCallback {

	private enum ButtonAction {
		StudentportalWebsite,
		StudentportalInterApp
	}
	
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
    
    public ArrayList<ExpandableListGroup> SetStandardGroups() { // TODO engrish / swedrish

    	// Return list of groups
    	ArrayList<ExpandableListGroup> finalList = new ArrayList<ExpandableListGroup>();
    	
    	// Temporary group
    	ExpandableListGroup group;

    	Resources res = getResources();
    	String[] header = res.getStringArray(R.array.new_student_menu_studentportal_header);
    	String[] text = res.getStringArray(R.array.new_student_menu_studentportal_text);
    	
    	for(int i = 0; i < header.length; i++) {
    		group = ExpandableListGroup.ConstructOneGroupWithOneChild(header[i], text[i], null);
    		
    		ExpandableListChild child = new ExpandableListChild();
    		child.setText(null); child.setTag(null);
    		ExpandableListMetaButton metaButton = new ExpandableListMetaButton();
    		metaButton.setButton(Button.VISIBLE, "Studentportalens hemsida", ButtonAction.StudentportalWebsite.ordinal());
    		child.setMetaButton(metaButton);
    		group.appendItem(child);    
    		
    		child = new ExpandableListChild();
    		child.setText(null); child.setTag(null);
    		metaButton = new ExpandableListMetaButton();
    		metaButton.setButton(Button.VISIBLE, "Studentportalen i applikationen", ButtonAction.StudentportalInterApp.ordinal());
    		child.setMetaButton(metaButton);
    		group.appendItem(child); 
    		
    		finalList.add(group);
    	}
    	
    	return finalList;
    }
	@Override
	public void onButtonClick(ExpandableListMetaButton metabutton) {
		Utilities.VerboseLog(TAG, getClass().getSimpleName() + ":entered onButtonClick()");	
		
		int actionID = metabutton.getAction();
		ButtonAction actionEnum = ButtonAction.values()[actionID];
		
		if(actionEnum == ButtonAction.StudentportalWebsite) {
		    Uri uriUrl = Uri.parse("https://studentportal.bth.se/");
	        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
	        startActivity(launchBrowser);
		}			
		else if(actionEnum == ButtonAction.StudentportalInterApp) {
			// Do something here // TODO
		}	
	} 	
}