package com.example.app_android.ui.newstudent;

import java.util.ArrayList;

import com.example.app_android.R;
import com.example.app_android.ui.elements.expandablelist.BaseExpandableListGroup;
import com.example.app_android.ui.elements.expandablelist.ButtonCallback;
import com.example.app_android.ui.elements.expandablelist.MyBaseExpandableListAdapter;
import com.example.app_android.util.Logger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

public class ActivityChecklist extends Activity implements ButtonCallback{
	
	private static final String TAG = "ActivityInformationBTH";
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
    	
    	// Temporary list of groups
    	BaseExpandableListGroup group;

    	// TODO REMOVE HARDCODE
    	String groupText = "Vad behöver jag göra som ny student?";	    	
    	String childText = "Registrera dig på ditt program. www.studentportal.bth.se <br> <br> Ansök om CSN www.csn.se <br> <br> Bli medlem i studentkåren www.bthstudent.se";
    	group = BaseExpandableListGroup.ConstructOneGroupWithOneChild(groupText, childText, null);
    	finalList.add(group);
    	
    	groupText = "Registrera dig";
    	childText = "Du kan registrera dig på ditt program eller kurs på studentportalen 2* veckor innan studierna börjar. Detta kan du göra efter du loggat in på www.studentportalen.bth.se under Registrering i menyn till vänster. Går även att göra i appen här [LINK].";
    	group = BaseExpandableListGroup.ConstructOneGroupWithOneChild(groupText, childText, null);
    	finalList.add(group);
    	
    	groupText = "Sök studiemedel";
    	childText = "Varje student söker studiemedel individuellt och allt detta sker via CSN www.csn.se";
    	group = BaseExpandableListGroup.ConstructOneGroupWithOneChild(groupText, childText, null);
    	finalList.add(group);
    	
    	groupText = "Bli medlem i studentkåren!";
    	childText = "Här skall finnas en kort förklarande text om varför man bör bli medlem i studentkåren.";
    
    	
    	group = BaseExpandableListGroup.ConstructOneGroupWithOneChild(groupText, childText, null, 1);
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

	@Override
	public void onButtonClick(int id) {

		System.out.println("This is button ya id=" + id);
	}     
}