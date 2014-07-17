package com.example.app_android;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ActivityMap extends Activity {
	private GoogleMap map;
	private HashMap<String, Marker> mapMarkers = new HashMap<String, Marker>();
	private static final String TAG = "ActivityMap";
	private ActionBarDrawerToggle drawerToggle 	= null;
	private DrawerLayout 	drawerLayout 		= null;
	private RadioGroup 		campusRadioGroup 	= null;
	private RadioGroup 		viewRadioGroup 		= null;
	private EditText		searchField			= null;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LatLng place = null;
        
        //Get input parameters
        Bundle bundle = getIntent().getExtras();
    	int entryID = bundle.getInt("entryID");
    	int startPositionID = bundle.getInt("startPositionID");
    	String room = bundle.getString("room");
    	
    	setContentView(R.layout.activity_map);
    	
    	 if(initilizeMap()) {
    		 initializeDrawer();
    		 
    		 assert entryID >= 0 && entryID <= 1;
    		 if(entryID == 0) { 
    			 assert startPositionID >= 0 && startPositionID <= 2;
    			 if(startPositionID == 0) { //Karlskrona Selected
    				 campusRadioGroup.check(R.id.radio_karlskrona);
    				 moveToKarlskrona();
    				 
    			 } 
    			 else if (startPositionID == 1) { //Karlshamn Selected
    				 campusRadioGroup.check(R.id.radio_karlshamn);
    				 moveToKarlshamn();
    			 }
    		 }
    		 else if(entryID == 1) { //Entered trough an entrypoint that specified a room
    			place = (LatLng)Cache.getMapCoordinate(room);
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
    	 }
    }
	
	private boolean initilizeMap() {
        if (map == null) {
        	map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            // check if map is created successfully or not
            if (map == null) {
                Toast.makeText(getApplicationContext(), "Unable to start Google Maps. Sorry! :(", Toast.LENGTH_LONG).show();
                return false;
            }
            map.setInfoWindowAdapter(new SnippetInfoWindowAdapter()); //Changes the way marker descriptions is presented. If this is not done; multi line descriptions cannot be used.
            addHouseMarkers();
            
        }
        return true;
    }
	
	private void initializeDrawer() {
		drawerLayout 		= (DrawerLayout) findViewById(R.id.drawer_layout);
		campusRadioGroup 	= (RadioGroup) findViewById(R.id.radio_group_campus);
		viewRadioGroup 		= (RadioGroup) findViewById(R.id.radio_group_views);
		searchField 		= (EditText) findViewById(R.id.search_field);
		
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
	}
	
	private void moveToKarlskrona() {
		map.moveCamera( CameraUpdateFactory.newLatLngZoom(Cache.getMapCoordinate("Karlskrona"), 17.0f));
		if(map.getMapType() != GoogleMap.MAP_TYPE_NORMAL)  { //Only change if the normal map type is set. (The satellite and hybrid view of campus Karlskrona is outdated)
			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			viewRadioGroup.check(R.id.radio_normal);
		}
	}
	
	private void moveToKarlshamn() {
		map.moveCamera( CameraUpdateFactory.newLatLngZoom(Cache.getMapCoordinate("Karlshamn"), 17.0f));
		if(map.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {//Only change if the normal map type is set. (Google Maps currently has no good data for the map type for the Karlshamn Campus area)
			map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			viewRadioGroup.check(R.id.radio_satellite);
		}
	}
	
	private void addHouseMarkers() { //TODO add icons to the markers
		addHouseMarker("HOUSE_A");
		addHouseMarker("HOUSE_B");
		addHouseMarker("HOUSE_C");
		addHouseMarker("HOUSE_D");
		addHouseMarker("HOUSE_G");
		addHouseMarker("HOUSE_H");
		addHouseMarker("HOUSE_J");
		addHouseMarker("HOUSE_K");
		addHouseMarker("KARLSHAMN_HOUSE_A");
		addHouseMarker("KARLSHAMN_HOUSE_B");
	}
	
	//Helper function to add makers to the houses on campus
	private void addHouseMarker(String house) {
		MarkerOptions options = new MarkerOptions();
		
		options.title("");
		options.position(Cache.getMapCoordinate(house));
		options.snippet(Cache.getMapMarkerSnippet(house));
		
		if(options.getPosition() != null)
			mapMarkers.put(house, map.addMarker(options));
	}
	
	public void onSearchButtonClicked(View view) {
		searchField.clearFocus();
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		if(imm.isActive())
			imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
		
		String searchString = searchField.getText().toString();
		if(!searchString.isEmpty()) {
			searchString = searchString.toUpperCase(new Locale("sv_SE"));
			LatLng markerCoordinates = Cache.getMapCoordinate(searchString);
			if(markerCoordinates != null) {
				if(!mapMarkers.containsKey("SearchMarker")) {
					MarkerOptions markerOptions = new MarkerOptions();
					markerOptions.position(markerCoordinates);
					markerOptions.snippet(searchString);
					mapMarkers.put("SearchMarker", map.addMarker(markerOptions));
				}
				else {
					Marker marker = mapMarkers.get("SearchMarker");
					marker.setPosition(markerCoordinates);
					marker.setSnippet(searchString);
				}
				
				//TODO - Add data to database regarding if the searched element is on campus Karlskrona och Karlshamn and switch map view accordingly
				//TODO - Add a special search icon to this marker to make it easier to differentiate from the other markers
				map.moveCamera( CameraUpdateFactory.newLatLngZoom(markerCoordinates, 17.0f));
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.layout.activity_map_action, menu);
	    return super.onCreateOptionsMenu(menu);
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
		initilizeMap();
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
	
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if(item.getItemId() == R.id.map_action_info) {
        	Builder alert = new AlertDialog.Builder(this);
        	alert.setTitle("Map");
        	alert.setMessage("This dialog should contain information about how the map view works. But it doesn't, this is just hard code! :<");
        	alert.setPositiveButton("OK",null);
        	alert.show();
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