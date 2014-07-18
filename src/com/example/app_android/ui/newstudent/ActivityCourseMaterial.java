package com.example.app_android.ui.newstudent;

import java.util.ArrayList;

import com.example.app_android.R;
import com.example.app_android.ui.elements.expandablelist.BaseExpandableListGroup;
import com.example.app_android.ui.elements.expandablelist.MyBaseExpandableListAdapter;
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
		mExpandableListAdapter = new MyBaseExpandableListAdapter(this, mExpandableListItems);
		mExpandableListAdapter.setAdapter(mExpandableList);
    }        
    
    public ArrayList<BaseExpandableListGroup> SetStandardGroups() { // TODO engrish / swedrish
    	
    	// Return list of groups
    	ArrayList<BaseExpandableListGroup> finalList = new ArrayList<BaseExpandableListGroup>();
    	
    	// Temporary list of groups
    	BaseExpandableListGroup group;

    	// TODO REMOVE HARDCODE
    	String groupText = "Allmän information kursmaterial";	    	
    	String childText = "kursmaterial är bra";
    	group = BaseExpandableListGroup.ConstructOneGroupWithOneChild(groupText, childText, null);
    	finalList.add(group);
    	
    	groupText = "Studentlitteratur";	    	
    	childText = "Finns många bokhandlare, både fysiska och på Internet. Go find one, 10 points each. You win at 960 points";
    	group = BaseExpandableListGroup.ConstructOneGroupWithOneChild(groupText, childText, null);
    	finalList.add(group);
    	
    	for(int i = 0; i < 3; i++) {
    		String text = new String();							
    		for(int j = 0; j < 200; j++)
    			text += "TEXT";
    		group = BaseExpandableListGroup.ConstructOneGroupWithOneChild("Group" + i, text, null);
    		finalList.add(group);
    	}
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