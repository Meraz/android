package com.example.app_android.ui.newstudent;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.example.app_android.R;
import com.example.app_android.ui.elements.expandablelist.BaseExpandableListGroup;
import com.example.app_android.ui.elements.expandablelist.MyBaseExpandableListAdapter;
import com.example.app_android.ui.elements.expandablelist.MyResidenceExpandableListAdapter;
import com.example.app_android.util.Logger;

public class ActivityResidence extends Activity {
	
	private static final String TAG = "ActivityResidence";

	private MyBaseExpandableListAdapter mExpandableListAdapter;
	private ArrayList<BaseExpandableListGroup> mExpandableListItems;
	private ExpandableListView mExpandableList;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onCreate()a");
    	super.onCreate(savedInstanceState);
        
    	// Sets the content specified in the file in res/layout
        // This also specifies which fragment to active
        setContentView(R.layout.activity_residence);  
        
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

	
    	String groupText = "Allmänt";	    	
    	String childText = "[Hardcoded] De flesta studenterna bor i närheten av skolan i områdena Galgamarken och Annebo. Skolan ligger enbart några hundra meter från områdena och med god tillgång till kollektivtrafiken.";
    	group = BaseExpandableListGroup.ConstructOneGroupWithOneChild(groupText, childText, null);
    	finalList.add(group);
    	
    	
    	groupText = "Karlskronahem AB";	    	
    	childText = "[Hardcoded] Karlskromahem är Karlskronas största bostadsbolag och hyr ut både vanliga lägenheter och studentlägenheter."
    			+"<br> http://www.karlskronahem.se/";
    	group = BaseExpandableListGroup.ConstructOneGroupWithOneChild(groupText, childText, null);
    	finalList.add(group);
    	
    	
    	groupText = "Heimstaden/Gallionen";	    	
    	childText = "[Hardcoded] Karlskrona är  uppbyggt på öar i den vackra blekingska skärgården, det är  alltid nära till hav" +
				" och avkoppling, och det är lätt att hitta Dig ett alldeles eget 'smultronställe'" +
				" längs havskanten. Här äger vi bl a vackra, äldre lägenheter med högt i tak och stuckaturer" +
				" men även mer moderna områden. Vår stolthet har Karlskronas bästa bostadsläge på sjötomt där " +
				"Du även kan hyra båtplats av oss!" +
    			"<br> http://www.heimstaden.com/";
    	group = BaseExpandableListGroup.ConstructOneGroupWithOneChild(groupText, childText, null);
    	finalList.add(group);
    	
    	// Done
    	// Return final list
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
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onStop()");
		super.onStop();
	}

	public void aFunction()
	{
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered aFunction()");
	}
}
