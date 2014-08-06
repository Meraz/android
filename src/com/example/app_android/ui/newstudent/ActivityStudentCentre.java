package com.example.app_android.ui.newstudent;

import java.util.ArrayList;

import com.example.app_android.ui.elements.expandablelist.ExpandableListGroup;
import com.example.app_android.ui.elements.expandablelist.ExpandableListMetaButton;
import com.example.app_android.ui.elements.expandablelist.IButtonCallback;
import com.example.app_android.ui.elements.expandablelist.MyBaseExpandableListAdapter;
import com.example.app_android.util.Utilities;

import android.content.Intent;
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
    
    public ArrayList<ExpandableListGroup> SetStandardGroups() { // TODO engrish / swedrish
		if(Utilities.verbose) {Log.v(TAG, getClass().getSimpleName() + ":SetStandardGroups()");}
    	// Return list of groups
    	ArrayList<ExpandableListGroup> finalList = new ArrayList<ExpandableListGroup>();
    	
    	// Temporary group
    	ExpandableListGroup group;
    	String groupText;	    	
    	String childText;

    	// TODO REMOVE HARDCODE
    	groupText = "Studentcentrum ";	    	
    	childText = "Studentcentrum hjälper dig med att få svar på dina frågor kring registreringar på program/kurser, BTH-kortet, ditt studentkonto, parkeringstillstånd på campus Gräsvik, utelämning av tentamen och intyg för tidigare studenter." +
    	"<br><br> Studentcentrums öppetider är:"+ 
    			"<br>Mån-Tors 10-14"+
    	"<br>10-11:30"+
    	"<br><br>Man kan även nå studentcentrum via telefonnummer 0455-385700 mån-fre mellan 10-11:30"+
    	"<br><br> För ytterliggare information besök studentcentrum på andra våningen i J-huset, ovanför Bistron.";
    	ExpandableListMetaButton metaButton = new ExpandableListMetaButton();
    	metaButton.setButton(Button.VISIBLE, "Gå till studentcentrums hemsida", ButtonAction.StudentCentreWebsite.ordinal());
    	
    	group = ExpandableListGroup.ConstructOneGroupWithOneChild(groupText, childText, null, metaButton);
    	finalList.add(group);
  	
		return finalList;
    }
    
	@Override
	public void onButtonClick(ExpandableListMetaButton metabutton) {
		if(Utilities.verbose) {Log.v(TAG, getClass().getSimpleName() + ":onButtonClick()");}
		
		int actionID = metabutton.getAction();
		ButtonAction actionEnum = ButtonAction.values()[actionID];
		
		if(actionEnum == ButtonAction.StudentCentreWebsite) {
		    Uri uriUrl = Uri.parse("https://studentportal.bth.se/web/studentportal.nsf/web.xsp/studentcentrum");
	        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
	        startActivity(launchBrowser);
		}		
	} 	
}