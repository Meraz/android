package com.example.app_android.ui.newstudent;

import java.util.ArrayList;

import com.example.app_android.R;
import com.example.app_android.ui.elements.expandablelist.BaseExpandableListGroup;
import com.example.app_android.ui.elements.expandablelist.MyBaseExpandableListAdapter;
import com.example.app_android.util.Logger;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

public class ActivityBTHInfo extends Activity {
	
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
		mExpandableListAdapter.openSpecificGroups(new int[]{0}); // Open first
		mExpandableListAdapter.setOnlyOneOpenBehavior(true);	// only one group can be opened at the time
		mExpandableListAdapter.setUseHtmlFormattingOnText(true);	// name says it all
    }        
    
    public ArrayList<BaseExpandableListGroup> SetStandardGroups() { // TODO engrish / swedrish
    	
    	// Return list of groups
    	ArrayList<BaseExpandableListGroup> finalList = new ArrayList<BaseExpandableListGroup>();
    	
    	// Temporary list of groups
    	BaseExpandableListGroup group;

    	// TODO REMOVE HARDCODE
    	String groupText = "Allmän information Blekinge Tekniska Högskola";	    	
    	String childText = "Blekinge Tekniska Högskola, förkortat \"BTH\", är en statlig teknisk högskola med en tydlig profil mot tillämpad IT och innovation för hållbar tillväxt. Högskolans utbildning och forskning bedrivs på två orter: Karlskrona och Karlshamn i Blekinge, tidigare även i Ronneby. År 1999 fick BTH rätt att ge doktorsexamen inom teknik.";
    	childText += "<br>Rektor för BTH är Anders Hederstierna";
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