package com.example.app_android.ui.newstudent;

import java.util.ArrayList;

import com.example.app_android.R;
import com.example.app_android.ui.elements.expandablelist.ExpandableListChild;
import com.example.app_android.ui.elements.expandablelist.ExpandableListGroup;
import com.example.app_android.ui.elements.expandablelist.ExpandableListMetaButton;
import com.example.app_android.ui.elements.expandablelist.IButtonCallback;
import com.example.app_android.ui.elements.expandablelist.MyBaseExpandableListAdapter;
import com.example.app_android.util.Utilities;

import android.content.res.Resources;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class ActivityStudentPortal extends BaseNewStudentActivity implements IButtonCallback {

	private enum ButtonAction {
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
		mExpandableListAdapter.setClickableHtmlLinks(true);
		mExpandableListAdapter.setButtonCallBack(this);
		
//		mInfoBoxTitle = getString(R.string.infobox_title_newstudent_residence);			// TODO
//		mInfoBoxMessage = getString(R.string.infobox_text_newstudent_residence);		// TODO 	
		mActionBarTitle += getString(R.string.actionbar_nextsign) + getString(R.string.actionbar_newstudent_student_portal);
    }        
    
    public ArrayList<ExpandableListGroup> SetStandardGroups() {
		if(Utilities.verbose) {Log.v(TAG, getClass().getSimpleName() + ":SetStandardGroups()");}
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
    		child.setText(getString(R.string.new_student_menu_studentcentre_website_url));
    		group.appendItem(child);
    		
    		child = new ExpandableListChild();
    		child.setText(null); child.setTag(null);
    		ExpandableListMetaButton metaButton = new ExpandableListMetaButton();
    		metaButton.setButton(Button.VISIBLE, getString(R.string.new_student_menu_studentcentre_interapp_button), ButtonAction.StudentportalInterApp.ordinal());
    		child.setMetaButton(metaButton);
    		group.appendItem(child); 
    		
    		finalList.add(group);
    	}
    	
    	return finalList;
    }
	@Override
	public void onButtonClick(ExpandableListMetaButton metabutton) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onButtonClick()");}
		
		int actionID = metabutton.getAction();
		ButtonAction actionEnum = ButtonAction.values()[actionID];
		
		if(actionEnum == ButtonAction.StudentportalInterApp) {
			// Do something here // TODO
		}	
	} 	
}