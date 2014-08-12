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
import android.util.Log;
import android.widget.Button;

public class ActivityStudentCentre extends BaseNewStudentActivity implements IButtonCallback {

	private enum ButtonAction{
		StudentCentreWebsite
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
    
    public ArrayList<ExpandableListGroup> SetStandardGroups() {
		if(Utilities.verbose) {Log.v(TAG, getClass().getSimpleName() + ":SetStandardGroups()");}
    	// Return list of groups
    	ArrayList<ExpandableListGroup> finalList = new ArrayList<ExpandableListGroup>();
    	
    	// Temporary group
    	ExpandableListGroup group;
   	
    	Resources res = getResources();
    	String[] header = res.getStringArray(R.array.new_student_menu_studentcentre_header);
    	String[] text = res.getStringArray(R.array.new_student_menu_studentcentre_text);
    	
    	for(int i = 0; i < header.length; i++) {
    		group = ExpandableListGroup.ConstructOneGroupWithOneChild(header[i], text[i], null);
    		
    		ExpandableListChild child = new ExpandableListChild();
    		child.setText(null); child.setTag(null);
    		ExpandableListMetaButton metaButton = new ExpandableListMetaButton();
    		metaButton.setButton(Button.VISIBLE, getString(R.string.new_student_menu_studentcentre_button), ButtonAction.StudentCentreWebsite.ordinal());
    		child.setMetaButton(metaButton);
    		group.appendItem(child);    
    		
    		finalList.add(group);
    	}
    	  	
		return finalList;
    }
    
	@Override
	public void onButtonClick(ExpandableListMetaButton metabutton) {
		if(Utilities.verbose) {Log.v(TAG, getClass().getSimpleName() + ":onButtonClick()");}
		
		int actionID = metabutton.getAction();
		ButtonAction actionEnum = ButtonAction.values()[actionID];
		
		if(actionEnum == ButtonAction.StudentCentreWebsite) {
		    Uri uriUrl = Uri.parse("https://studentportal.bth.se/web/studentportal.nsf/web.xsp/studentcentrum");	// TODO
	        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
	        startActivity(launchBrowser);
		}		
	} 	
}