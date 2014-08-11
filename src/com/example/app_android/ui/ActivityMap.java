package com.example.app_android.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.HashMap;

import com.example.app_android.MapPlaceIdentifiers;
import com.example.app_android.R;
import com.example.app_android.database.DatabaseManager;
import com.example.app_android.database.interfaces.IMapPlaceTable;
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
	
	private IMapPlaceTable 			mPlaceTable;
	
	private ActionBarDrawerToggle 	drawerToggle;
	private DrawerLayout 			drawerLayout;
	private RadioGroup 				campusRadioGroup;
	private RadioGroup 				viewRadioGroup;
	private AutoCompleteTextView	searchField;
	private ArrayAdapter<String> 	searchAdapter;
	
	private LatLng campusKarlskronaCoordinates;
	private LatLng campusKarlshamnCoordinates;
	
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
    	
    	mPlaceTable = DatabaseManager.getInstance().getMapCoordinateTable();
    	
    	 if(initilizeMap()) {
    		 initializeDrawer();
    		 
    		 getCampusCoordinates();
    		 
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
    			place = mPlaceTable.getMapCoordinate(room);
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
            initializeToggleableMarkers();
        }
        return true;
    }
	
	private void initializeDrawer() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":initializeDrawer()");}

		searchField 		= (AutoCompleteTextView) findViewById(R.id.course_search_input);
		drawerLayout 		= (DrawerLayout) findViewById(R.id.drawer_layout);
		campusRadioGroup 	= (RadioGroup) findViewById(R.id.radio_group_campus);
		viewRadioGroup 		= (RadioGroup) findViewById(R.id.radio_group_views);
		searchField 		= (AutoCompleteTextView) findViewById(R.id.map_place_search_field);
		
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
        
        initializeDropDownSearchField();
        
        //Look through the map marker layout and initialize the toggleButtons properly
        LinearLayout markersLayout = (LinearLayout)findViewById(R.id.markers_layout);
		for(int i = 0; i < markersLayout.getChildCount(); ++i) {
			View child = markersLayout.getChildAt(i);
			if(child instanceof LinearLayout) { //Find LinearLayout children
				LinearLayout childLayout = (LinearLayout) child;
				for(int j = 0; j < childLayout.getChildCount(); ++j) { // Find the relevant grandChildren
					ToggleButton grandChild = (ToggleButton) childLayout.getChildAt(j); //The childLayout contains only ToggleButtons
					if(grandChild.getId() == R.id.marker_toggle_houses || grandChild.getId() == R.id.marker_toggle_student_union ||
							grandChild.getId() == R.id.marker_toggle_student_pubs) {
						((ToggleButton)grandChild).setChecked(true);
					}
				}
			}
		}
	}
	
	private void initializeDropDownSearchField() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":initializeDropDownSearchField()");}
		
		String[] searchablesPlaceNames = mPlaceTable.getAllNamesByToggleId(MapPlaceIdentifiers.TOGGLE_ID_NO_TOGGLE, false);
		searchAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, searchablesPlaceNames);
		searchField.setAdapter(searchAdapter);
		searchField.setThreshold(0);
		searchField.setOnItemClickListener(new OnItemClickListener() {
			@Override
		    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) { 
				//Remove the input keyboard
				searchField.clearFocus();
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				if(imm.isActive())
					imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
				
				//Move the search marker
				MarkerOptions markerOptions = mPlaceTable.getMapMarkerOptions( (String) arg0.getItemAtPosition(arg2));
				if(markerOptions != null) {
					if(mapMarkers.containsKey("SearchMarker")) {
						Marker marker = mapMarkers.get("SearchMarker");
						marker.setPosition(markerOptions.getPosition());
						marker.setSnippet(markerOptions.getSnippet());
					}
					else {
						mapMarkers.put("SearchMarker", map.addMarker(markerOptions));
					}
					
					//Move the camera and close the drawer to show the search marker
					map.moveCamera( CameraUpdateFactory.newLatLngZoom(markerOptions.getPosition(), 17.0f));
					searchField.setText(""); //Make the text field empty so that the user doesn't have to erase text in it before searching again
					drawerLayout.closeDrawers();
				}
			}	
		});
	}
	
	private void moveToKarlskrona() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":moveToKarlskrona()");}
		
		map.moveCamera( CameraUpdateFactory.newLatLngZoom(campusKarlskronaCoordinates, 17.0f));
		if(map.getMapType() != GoogleMap.MAP_TYPE_NORMAL)  { //Only change if the normal map type is not set. (The satellite and hybrid view of campus Karlskrona is outdated)
			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			viewRadioGroup.check(R.id.radio_normal);
		}
	}
	
	private void moveToKarlshamn() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":moveToKarlshamn()");}
		
		map.moveCamera( CameraUpdateFactory.newLatLngZoom(campusKarlshamnCoordinates, 17.0f));
		if(map.getMapType() == GoogleMap.MAP_TYPE_NORMAL) { //Only change if the normal map type is set. (Google Maps currently has no good data for the map type for the Karlshamn Campus area)
			map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			viewRadioGroup.check(R.id.radio_satellite);
		}
	}
	
	private void initializeToggleableMarkers() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":addMarkers()");}
		
		String[] markerNames = mPlaceTable.getAllNamesByToggleId(MapPlaceIdentifiers.TOGGLE_ID_NO_TOGGLE, true);
		for(int i = 0; i < markerNames.length; ++i) {
			MarkerOptions options = mPlaceTable.getMapMarkerOptions(markerNames[i]);
			
			if(options.getPosition() != null)
				mapMarkers.put(markerNames[i], map.addMarker(options));
		};
 	}
	
	private void toggleMarkers (int toggleId ,boolean on) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":toggleMarkers()");}
		
		String[] markerNames = mPlaceTable.getAllNamesByToggleId(toggleId, false);
		for(int i = 0; i < markerNames.length; ++i) {
			mapMarkers.get(markerNames[i]).setVisible(on);
		}
	}
	
	// Gets Coordinates for the camera default location for respective campuses. Gets them from resource files.
	private void getCampusCoordinates() {
		TypedValue karlskronaLatitude = new TypedValue();
		TypedValue karlskronaLongitude = new TypedValue();
		TypedValue karlshamnLatitude = new TypedValue();
		TypedValue karlshamnLongitude = new TypedValue();
		
		getResources().getValue(R.dimen.karlskrona_campus_latitude, karlskronaLatitude, true);
		getResources().getValue(R.dimen.karlskrona_campus_longitude, karlskronaLongitude, true);
		getResources().getValue(R.dimen.karlshamn_campus_latitude, karlshamnLatitude, true);
		getResources().getValue(R.dimen.karlshamn_campus_longitude, karlshamnLongitude, true);
		
		campusKarlskronaCoordinates = new LatLng(karlskronaLatitude.getFloat(),karlskronaLongitude.getFloat());
		campusKarlshamnCoordinates = new LatLng(karlshamnLatitude.getFloat(), karlshamnLongitude.getFloat());
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
			toggleMarkers(MapPlaceIdentifiers.TOGGLE_ID_CAMPUS_HOUSES, on);
			break;
			
		case R.id.marker_toggle_student_union:
		toggleMarkers(MapPlaceIdentifiers.TOGGLE_ID_STUDENT_UNION, on);
		break;
		
		case R.id.marker_toggle_student_pubs:
			toggleMarkers(MapPlaceIdentifiers.TOGGLE_ID_STUDENT_PUBS, on);
			break;
		}
	}
		
	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onPostCreate()");}
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
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