package com.example.app_android.ui.newstudent;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.app_android.Logger;
import com.example.app_android.R;
import com.example.app_android.R.layout;
import com.example.app_android.ui.elements.expandablelist.ExpandableListChild;
import com.example.app_android.ui.elements.expandablelist.ExpandableListGroup;
import com.example.app_android.ui.elements.expandablelist.MyExpandableListAdapter;

public class ActivityStudentPortal extends Activity {
	
	private static final String TAG = "ActivityStudentPortal";

	private MyExpandableListAdapter mExpandableListAdapter;
	private ArrayList<ExpandableListGroup> mExpandableListItems;
	private ExpandableListView mExpandableList;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onCreate()a");
    	super.onCreate(savedInstanceState);
        
    	// Sets the content specified in the file in res/layout
        // This also specifies which fragment to active
        setContentView(R.layout.activity_studentportal);  
        
		mExpandableList = (ExpandableListView) findViewById(R.id.ExpandableList);
		mExpandableListItems = SetStandardGroups();
		mExpandableListAdapter = new MyExpandableListAdapter(this, mExpandableListItems);
		mExpandableList.setAdapter(mExpandableListAdapter);
    }
    
    public ArrayList<ExpandableListGroup> SetStandardGroups() {// TODO engrish / swedrish
    	
    	// Return list of groups
    	ArrayList<ExpandableListGroup> finalList = new ArrayList<ExpandableListGroup>();
    	
    	// Temporary list of children
    	ArrayList<ExpandableListChild> children = new ArrayList<ExpandableListChild>();
    	
    	// Temporary list of groups
    	ExpandableListGroup group = new ExpandableListGroup();
    	group.setName("Bostäder allmänt");	
    	
    	ExpandableListChild child = new ExpandableListChild();
    	child.setName(	"[Hardcoded] Alla studenter måste ansöka om bostad på egen hand. Se till att du söker bostad så snart du kan." +
    					"Har du frågor kring detta ska du kontakta Studentkåren.");
    	child.setTag(null);
    	children.add(child);
    	group.setItems(children);
    	finalList.add(group);
    	
    	// Done
    	// Reset temporary list of children
    	children = new ArrayList<ExpandableListChild>();

    	// Next company
    	// TODO bult remove hardcoded
    	// Create a new group
    	group = new ExpandableListGroup();
    	group.setName("AB Karlskronahem");
    	
    	// Create a new child
    	child = new ExpandableListChild();
    	child.setName("AB Karlskronahem är Karlskronas största bostadsbolag med fastigheter på ett flertal orter " +
    			"i kommunen. Bolaget ägs av Karlskrona kommun och förvaltar cirka 3 900 hyreslägenheter varav vissa " +
    			"studentboenden. Bland fastigheterna finns även särskilda boenden och ett mindre antal kommersiella lokaler." +
    			"I kontoret på Norra Smedjegatan 12 finns bobutiken som ansvarar för uthyrning och felanmälningar. " +
    			"Där finns också teknikavdelningen med ansvar för skötsel och underhåll samt ekonomiavdelningen med ansvar" +
    			" för budget, bokföring, fakturering, hyresdebitering och personaladministration. Utöver det finns en" +
    			" boendesekreterare som arbetar med störningsärenden.I ett kontor på Kungsmarksplan finns en servicechef, " +
    			"sex bovärdar och fyra reparatörer. Bovärdarna ansvarar för mindre reparationer i lägenheterna, besiktningar," +
    			" och hyresgästkontakter. Reparatörerna tar hand om större lägenhetsreparationer och underhåll. I övrigt sköter " +
    			"entreprenörer de dagliga uppgifterna i bostadsområderna. På företaget finns drygt 30 anställda. 0455-30 49 00");
    	child.setTag(null);
    	
    	// Save child to temporary list
    	children.add(child);
    	
    	// Move temporary list of children to a group
    	group.setItems(children);    
    	
    	// Add this group containing a list of children to the list of groups that is to be returned
    	finalList.add(group);
    	
    	// Done
    	// Reset temporary list of children
    	children = new ArrayList<ExpandableListChild>();
    	
    	// Next company
    	// TODO bult remove hardcoded
    	// Create a new group
    	group = new ExpandableListGroup();
    	group.setName("Heimstaden/Gallionen");
    	
    	// Create a new child
    	child = new ExpandableListChild();
    	child.setName(	"Karlskrona är  uppbyggt på öar i den vackra blekingska skärgården, det är  alltid nära till hav" +
    					" och avkoppling, och det är lätt att hitta Dig ett alldeles eget 'smultronställe'" +
    					" längs havskanten. Här äger vi bl a vackra, äldre lägenheter med högt i tak och stuckaturer" +
    					" men även mer moderna områden. Vår stolthet har Karlskronas bästa bostadsläge på sjötomt där " +
    					"Du även kan hyra båtplats av oss!");
    	child.setTag(null);
    	
    	// Save child to temporary list
    	children.add(child);
    	
    	// Move temporary list of children to a group
    	group.setItems(children);    
    	
    	// Add this group containing a list of children to the list of groups that is to be returned
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
