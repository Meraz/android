package com.example.app_android.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.util.Log;

import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.HashMap;
import java.util.Locale;

import com.example.app_android.R;
import com.example.app_android.database.DatabaseManager;
import com.example.app_android.database.IMapCoordinateTable;
import com.example.app_android.util.Utilities;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ActivityMap extends BaseActivity {
	
	private static final String TAG = "Map";
	
	private GoogleMap map;
	private HashMap<String, Marker> mapMarkers = new HashMap<String, Marker>();
	private IMapCoordinateTable coordinateTable;
	private ActionBarDrawerToggle drawerToggle 	= null;
	private DrawerLayout 	drawerLayout 		= null;
	private RadioGroup 		campusRadioGroup 	= null;
	private RadioGroup 		viewRadioGroup 		= null;
	private EditText		searchField			= null;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		mClassName = getClass().getSimpleName();
		mTag = TAG;
        super.onCreate(savedInstanceState);
        
        mInfoBoxMessage = "This dialog should contain information about how the map view works. But it doesn't, this is just hard code! :<";
        
        LatLng place = null;
        
        //Get input parameters
        Bundle bundle = getIntent().getExtras();
    	int entryID = bundle.getInt("entryID");
    	int startPositionID = bundle.getInt("startPositionID");
    	String room = bundle.getString("room");
    	
    	setContentView(R.layout.activity_map);
    	
    	coordinateTable = DatabaseManager.getInstance().getMapCoordinateTable();
    	
    	 if(initilizeMap()) {
    		 initializeDrawer();
    		 
    		 assert entryID >= 0 && entryID <= 1;
    		 if(entryID == 0) { 
    			 assert startPositionID >= 0 && startPositionID <= 2;
    			 if(startPositionID == 0) { //Karlskrona Selected
    				 campusRadioGroup.check(R.id.radio_karlskrona);
    				 viewRadioGroup.check(R.id.radio_normal);
    				 moveToKarlskrona();
    				 
    			 } 
    			 else if (startPositionID == 1) { //Karlshamn Selected
    				 campusRadioGroup.check(R.id.radio_karlshamn);
    				 viewRadioGroup.check(R.id.radio_satellite);
    				 moveToKarlshamn();
    			 }
    		 }
    		 else if(entryID == 1) { //Entered trough an entrypoint that specified a room
    			place = coordinateTable.getMapCoordinate(room);
    			if(place != null) {
    				map.moveCamera( CameraUpdateFactory.newLatLngZoom(place, 17.0f));
         			if(!mapMarkers.containsKey("SearchMarker")) {
         				MarkerOptions markerOptions = new MarkerOptions();
						markerOptions.position(place);
						markerOptions.snippet(room);
						mapMarkers.put("SearchMarker", map.addMarker(markerOptions));
         			}
         			else {
						Marker marker = mapMarkers.get("SearchMarker");
						marker.setPosition(place);
						marker.setSnippet(room);
					}
    			}
    			else {	//If the lookup fails toast the user about it and move to Karlskrona
    				Toast.makeText(getApplicationContext(), "Unable to find room :(", Toast.LENGTH_SHORT).show();
    				campusRadioGroup.check(R.id.radio_karlskrona);
    				viewRadioGroup.check(R.id.radio_normal);
    				moveToKarlskrona();
    			}
    		 }
    	 }
    }
	
	private boolean initilizeMap() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":initilizeMap()");}
        if (map == null) {
        	map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            // check if map is created successfully or not
            if (map == null) {
                Toast.makeText(getApplicationContext(), "Unable to start Google Maps. Sorry! :(", Toast.LENGTH_LONG).show();
                return false;
            }
            map.setInfoWindowAdapter(new SnippetInfoWindowAdapter()); //Changes the way marker descriptions is presented. If this is not done; multi line descriptions cannot be used.
            addMarkers();
        }
        return true;
    }
	
	private void initializeDrawer() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":initializeDrawer()");}
		drawerLayout 		= (DrawerLayout) findViewById(R.id.drawer_layout);
		campusRadioGroup 	= (RadioGroup) findViewById(R.id.radio_group_campus);
		viewRadioGroup 		= (RadioGroup) findViewById(R.id.radio_group_views);
		searchField 		= (EditText) findViewById(R.id.search_field);
		
		//Initialize the drawer indicator/button
		drawerToggle		= new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close){
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
 
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
		drawerLayout.setDrawerListener(drawerToggle);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        //Look through the map marker layout and initialize the toggleButtons properly
        LinearLayout markersLayout = (LinearLayout)findViewById(R.id.markers_layout);
		for(int i = 0; i < markersLayout.getChildCount(); ++i) {
			View child = markersLayout.getChildAt(i);
			if(child instanceof LinearLayout) { //Find LinearLayout children
				LinearLayout childLayout = (LinearLayout) child;
				for(int j = 0; j < childLayout.getChildCount(); ++j) { // Find the relevant grandChildren
					ToggleButton grandChild = (ToggleButton) childLayout.getChildAt(j); //The childLayout contains only ToggleButtons
					if(grandChild.getId() == R.id.marker_toggle_houses) {
						((ToggleButton)grandChild).setChecked(true);
					}
				}
			}
		}
	}
	
	private void moveToKarlskrona() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":moveToKarlskrona()");}
		map.moveCamera( CameraUpdateFactory.newLatLngZoom(coordinateTable.getMapCoordinate("CAMPUS_KARLSKRONA"), 17.0f));
		if(map.getMapType() != GoogleMap.MAP_TYPE_NORMAL)  { //Only change if the normal map type is set. (The satellite and hybrid view of campus Karlskrona is outdated)
			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			viewRadioGroup.check(R.id.radio_normal);
		}
	}
	
	private void moveToKarlshamn() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":moveToKarlshamn()");}
		map.moveCamera( CameraUpdateFactory.newLatLngZoom(coordinateTable.getMapCoordinate("CAMPUS_KARLSHAMN"), 17.0f));
		if(map.getMapType() == GoogleMap.MAP_TYPE_NORMAL) { //Only change if the normal map type is set. (Google Maps currently has no good data for the map type for the Karlshamn Campus area)
			map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			viewRadioGroup.check(R.id.radio_satellite);
		}
	}
	
	private void addMarkers() { //TODO add icons to the markers
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":addMarkers()");}
		addMarker("HOUSE_A");
		addMarker("HOUSE_B");
		addMarker("HOUSE_C");
		addMarker("HOUSE_D");
		addMarker("HOUSE_G");
		addMarker("HOUSE_H");
		addMarker("HOUSE_J");
		addMarker("HOUSE_K");
		addMarker("BSK_OFFICE");
		addMarker("KARLSHAMN_HOUSE_A");
		addMarker("KARLSHAMN_HOUSE_B");
 	}
	
	private void addMarker(String name) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":addMarker()");}
		MarkerOptions options = coordinateTable.getMapMarkerOptions(name);
		
		if(options.getPosition() != null)
			mapMarkers.put(name, map.addMarker(options));
	}
	
	private void toggleHouseMarkers (boolean on) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":toggleHouseMarkers()");}
			mapMarkers.get("HOUSE_A").setVisible(on);
			mapMarkers.get("HOUSE_B").setVisible(on);
			mapMarkers.get("HOUSE_C").setVisible(on);
			mapMarkers.get("HOUSE_D").setVisible(on);
			mapMarkers.get("HOUSE_G").setVisible(on);
			mapMarkers.get("HOUSE_H").setVisible(on);
			mapMarkers.get("HOUSE_J").setVisible(on);
			mapMarkers.get("HOUSE_K").setVisible(on);
			mapMarkers.get("BSK_OFFICE").setVisible(on);
			mapMarkers.get("KARLSHAMN_HOUSE_A").setVisible(on);
			mapMarkers.get("KARLSHAMN_HOUSE_B").setVisible(on);
	}
	
	public void onSearchButtonClicked(View view) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onSearchButtonClicked()");}
		searchField.clearFocus();
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		if(imm.isActive())
			imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
		
		String searchString = searchField.getText().toString();
		if(!searchString.isEmpty()) {
			searchString = searchString.toUpperCase(new Locale("sv_SE"));
			MarkerOptions markerOptions = coordinateTable.getMapMarkerOptions(searchString);
			if(markerOptions != null) {
				if(mapMarkers.containsKey("SearchMarker")) {
					Marker marker = mapMarkers.get("SearchMarker");
					marker.setPosition(markerOptions.getPosition());
					marker.setSnippet(markerOptions.getSnippet());
				}
				else {
					mapMarkers.put("SearchMarker", map.addMarker(markerOptions));
				}
				
				//TODO - Add data to database regarding if the searched element is on campus Karlskrona och Karlshamn and switch map view accordingly
				//TODO - Add a special search icon to this marker to make it easier to differentiate from the other markers
				map.moveCamera( CameraUpdateFactory.newLatLngZoom(markerOptions.getPosition(), 17.0f));
				drawerLayout.closeDrawers();
				
				Toast.makeText(getApplicationContext(), "Found it! :D", Toast.LENGTH_SHORT).show();
			}
			else
				Toast.makeText(getApplicationContext(), "Nothing found :(", Toast.LENGTH_SHORT).show();
		}
		else {
			searchField.requestFocus(); //TODO - Fix the annoying behavior of the soft keyboard. We want it to be closed at all times except when searchField has focus
			Toast.makeText(getApplicationContext(), "The search field cannot be empty!", Toast.LENGTH_SHORT).show();;
		}
	}
	
	public void onRadioButtonClicked(View view) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onRadioButtonClicked()");}
		switch(view.getId()) {
			case R.id.radio_karlskrona:
				moveToKarlskrona();
				break;
			case R.id.radio_karlshamn:
				moveToKarlshamn();
				break;
			case R.id.radio_normal:
				map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				break;
			case R.id.radio_satellite:
				map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
				break;
			case R.id.radio_hybrid:
				map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
				break;
		}
		drawerLayout.closeDrawers();
	}
	
	public void onToggleClicked(View view) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onToggleClicked()");}
		boolean on = ((ToggleButton)view).isChecked();
		switch(view.getId()) {
		case R.id.marker_toggle_houses:
				toggleHouseMarkers(on);
			break;
		}
	}
		
	@Override
	protected void onResume() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onResume()");}
		Utilities.VerboseLog(TAG, getClass().getSimpleName() + ":entered onResume()");
		super.onResume();
		initilizeMap();
	}

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onPostCreate()");}
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onOptionsItemSelected()");}
        if (drawerToggle.onOptionsItemSelected(item)) {	// TODO what is this code? ~bult
            return true;	
        }        
        return super.onOptionsItemSelected(item);
    }
    
	
	//Used to display HTML-based text as marker snippet
	private class SnippetInfoWindowAdapter implements InfoWindowAdapter{	

		@Override
		public View getInfoContents(Marker marker) {
            View view = getLayoutInflater().inflate(R.layout.item_marker_snippet, null);
            TextView info = (TextView) view.findViewById(R.id.info);
            info.setText(Html.fromHtml(marker.getSnippet()));
            
            return view;
		}

		@Override
		public View getInfoWindow(Marker marker) {
			return null;
		}
	}
}