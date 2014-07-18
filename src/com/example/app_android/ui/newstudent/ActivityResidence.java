package com.example.app_android.ui.newstudent;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.example.app_android.Logger;
import com.example.app_android.R;
import com.example.app_android.ui.elements.expandablelist.BaseExpandableListChild;
import com.example.app_android.ui.elements.expandablelist.BaseExpandableListGroup;
import com.example.app_android.ui.elements.expandablelist.MyBaseExpandableListAdapter;
import com.example.app_android.ui.elements.expandablelist.MyResidenceExpandableListAdapter;
import com.example.app_android.ui.elements.expandablelist.MyStudentCentreExpandableListAdapter;

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

	
    	String groupText = "Allmän information om bostäder";	    	
    	String childText = "[Hardcoded] Alla studenter måste ansöka om bostad på egen hand. Se till att du söker bostad så snart du kan." +
    						"Har du frågor kring detta ska du kontakta Studentkåren.";
    	group = BaseExpandableListGroup.ConstructOneGroupWithOneChild(groupText, childText, null);
    	finalList.add(group);
    	
    	
    	groupText = "AB Karlskronahem";	    	
    	childText = "[Hardcoded] AB Karlskronahem är Karlskronas största bostadsbolag med fastigheter på ett flertal orter " +
    			"i kommunen. Bolaget ägs av Karlskrona kommun och förvaltar cirka 3 900 hyreslägenheter varav vissa " +
    			"studentboenden. Bland fastigheterna finns även särskilda boenden och ett mindre antal kommersiella lokaler." +
    			"I kontoret på Norra Smedjegatan 12 finns bobutiken som ansvarar för uthyrning och felanmälningar. " +
    			"Där finns också teknikavdelningen med ansvar för skötsel och underhåll samt ekonomiavdelningen med ansvar" +
    			" för budget, bokföring, fakturering, hyresdebitering och personaladministration. Utöver det finns en" +
    			" boendesekreterare som arbetar med störningsärenden.I ett kontor på Kungsmarksplan finns en servicechef, " +
    			"sex bovärdar och fyra reparatörer. Bovärdarna ansvarar för mindre reparationer i lägenheterna, besiktningar," +
    			" och hyresgästkontakter. Reparatörerna tar hand om större lägenhetsreparationer och underhåll. I övrigt sköter " +
    			"entreprenörer de dagliga uppgifterna i bostadsområderna. På företaget finns drygt 30 anställda. 0455-30 49 00"
    			+"\n http://www.karlskronahem.se/";
    	group = BaseExpandableListGroup.ConstructOneGroupWithOneChild(groupText, childText, null);
    	finalList.add(group);
    	
    	
    	groupText = "Heimstaden/Gallionen";	    	
    	childText = "[Hardcoded] Karlskrona är  uppbyggt på öar i den vackra blekingska skärgården, det är  alltid nära till hav" +
				" och avkoppling, och det är lätt att hitta Dig ett alldeles eget 'smultronställe'" +
				" längs havskanten. Här äger vi bl a vackra, äldre lägenheter med högt i tak och stuckaturer" +
				" men även mer moderna områden. Vår stolthet har Karlskronas bästa bostadsläge på sjötomt där " +
				"Du även kan hyra båtplats av oss!" +
    			"\n http://www.heimstaden.com/";
    	group = BaseExpandableListGroup.ConstructOneGroupWithOneChild(groupText, childText, null);
    	finalList.add(group);
    	
    	for(int i = 0; i < 10; i++) {
    		groupText = "Group" + i;	    	
    		String text = new String();
    		for(int j = 0; j < 200; j++)
    			text += "TEXT";
    		group = BaseExpandableListGroup.ConstructOneGroupWithOneChild(groupText, text, null);
    		finalList.add(group);
    	}
    	
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
