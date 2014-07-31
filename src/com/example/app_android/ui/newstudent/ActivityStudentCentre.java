package com.example.app_android.ui.newstudent;

import java.util.ArrayList;

import com.example.app_android.ui.elements.expandablelist.BaseExpandableListGroup;
import com.example.app_android.ui.elements.expandablelist.IButtonCallback;
import com.example.app_android.ui.elements.expandablelist.MyBaseExpandableListAdapter;
import com.example.app_android.util.Logger;

import android.os.Bundle;

public class ActivityStudentCentre extends BaseNewStudentActivity implements IButtonCallback {

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
    	String groupText;	    	
    	String childText;

    	// TODO REMOVE HARDCODE
    	groupText = "Allmän information om studentcentrum";	    	
    	childText = "Studentcentrum hjälper dig med att få svar på dina frågor kring registreringar på program/kurser, BTH-kortet, ditt studentkonto, parkeringstillstånd på campus Gräsvik, utelämning av tentamen och intyg för tidigare studenter." +
    	"<br><br> Studentcentrums öppetider är:"+ 
    			"<br>Mån-Tors 10-14"+
    	"<br>10-11:30"+
    	"<br><br>Man kan även nå studentcentrum via telefonnummer 0455-385700 mån-fre mellan 10-11:30"+
    	"<br><br> För ytterliggare information besök studentcentrum på andra våningen i J-huset, ovanför Bistron."+
    	"<br><br> https://studentportal.bth.se/web/studentportal.nsf/web.xsp/studentcentrum";
    	group = BaseExpandableListGroup.ConstructOneGroupWithOneChild(groupText, childText, null);
    	finalList.add(group);
  	
		return finalList;
    }
	@Override
	public void onButtonClick(int id) {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onButtonClick()");		
	} 	
}