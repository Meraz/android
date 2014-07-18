package com.example.app_android.ui.newstudent;

import java.util.ArrayList;

import com.example.app_android.R;
import com.example.app_android.ui.elements.expandablelist.BaseExpandableListGroup;
import com.example.app_android.ui.elements.expandablelist.MyBaseExpandableListAdapter;
import com.example.app_android.ui.elements.expandablelist.MyResidenceExpandableListAdapter;
import com.example.app_android.util.Logger;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

public class ActivityStudentCentre extends Activity {
	
	private static final String TAG = "ActivityStudentPortal";
	private MyBaseExpandableListAdapter mExpandableListAdapter;
	private ArrayList<BaseExpandableListGroup> mExpandableListItems;
	private ExpandableListView mExpandableList;
	
	public interface ExpandableListCallBack{
		public void onChildClick();
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onCreate()a");
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_studentportal); 
        
		mExpandableList = (ExpandableListView) findViewById(R.id.ExpandableList);
		mExpandableListItems = SetStandardGroups();
		mExpandableListAdapter = new MyResidenceExpandableListAdapter(this, mExpandableListItems);
		mExpandableListAdapter.setAdapter(mExpandableList);
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
    	
    	groupText = "Frågor om registreringar på kurs eller program";	    	
    	childText = "TEXT";
		group = BaseExpandableListGroup.ConstructOneGroupWithOneChild(groupText, childText, null);
		finalList.add(group);
		
    	groupText = "BTH-kortet";	    	
    	childText = "TEXT";
		group = BaseExpandableListGroup.ConstructOneGroupWithOneChild(groupText, childText, null);
		finalList.add(group);
		
    	groupText = "BTH-kortet";	    	
    	childText = "TEXT";
		group = BaseExpandableListGroup.ConstructOneGroupWithOneChild(groupText, childText, null);
		finalList.add(group);
		
    	groupText = "Studentkonto";	    	
    	childText = "TEXT";
		group = BaseExpandableListGroup.ConstructOneGroupWithOneChild(groupText, childText, null);
		finalList.add(group);
		
    	groupText = "Parkeringstillstånd";	    	
    	childText = "TEXT";
		group = BaseExpandableListGroup.ConstructOneGroupWithOneChild(groupText, childText, null);
		finalList.add(group);
		
    	groupText = "Utlämning av skriftlig och digital tentamen";	    	
    	childText = "TEXT";
		group = BaseExpandableListGroup.ConstructOneGroupWithOneChild(groupText, childText, null);
		finalList.add(group);
	
		return finalList;
    }

    @Override
	protected void onDestroy() {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onDestroy()");
		super.onDestroy();
	}

	@Override
	protected void onPause() {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onPause()");
		super.onPause();
	}

	@Override
	protected void onRestart() {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onRestart()");
		super.onRestart();
	}

	@Override
	protected void onResume() {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onResume()");
		super.onResume();
	}

	@Override
	protected void onStart() {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onStart()");
		super.onStart();
	}

	@Override
	protected void onStop() {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onPause()");
		super.onStop();
	}     
}