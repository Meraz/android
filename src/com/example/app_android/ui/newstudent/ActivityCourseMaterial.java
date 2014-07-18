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

public class ActivityCourseMaterial extends Activity {
	
	private static final String TAG = "ActivityCourseMaterial";
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
    	
    	// Temporary list of groups
    	BaseExpandableListGroup group;

    	// TODO REMOVE HARDCODE
    	String groupText = "Allmän information kursmaterial";	    	
    	String childText = "För studenten är kurslitteraturen något av det viktigaste, inte bara att ha rätt kurslitteratur men även att man ska ha kvar pengar att leva för när denna är inhandlad. Nedan hittar du några nyttiga länkar.";
    	group = BaseExpandableListGroup.ConstructOneGroupWithOneChild(groupText, childText, null);
    	finalList.add(group);
    	
    	groupText = "Studentlitteratur";	    	
    	childText = "Med läromedel för förskola, grundskola och gymnasium, kurslitteratur för universitet och högskola samt kvalificerad facklitteratur, utbildningar och digitala informationstjänster för yrkesverksamma stöttar Studentlitteratur kontinuerlig kunskaps- och kompetensuppbyggnad längs hela kunskapsresan."+
    	"<br><br>https://www.studentlitteratur.se/";
    	group = BaseExpandableListGroup.ConstructOneGroupWithOneChild(groupText, childText, null);
    	finalList.add(group);
    	
    	groupText = "Bokus";	    	
    	childText = "Fortare än du hinner slå upp sidan i en bok, har en ny kommit på bokus.com. Och innan du vet ordet av kommer nästa. Och nästa...Så fortsätter det dygnet runt. Bokus.com är bokhandeln som alltid är vaken och som har nyheterna i samma sekund som de släpps. Här får de sällskap av miljoner andra böcker. Ändå hittar du enkelt och utan omvägar boken du söker. Det är på bokus.com det händer. Och det händer dygnet runt. Finns boken, finns den hos oss. " +
    			"<br><br>http://www.bokus.com/student";
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