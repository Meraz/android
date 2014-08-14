package com.example.app_android.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
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

import com.example.app_android.R;
import com.example.app_android.database.DBException;
import com.example.app_android.database.DatabaseManager;
import com.example.app_android.database.NoResultFoundDBException;
import com.example.app_android.database.interfaces.IMapPlaceTable;
import com.example.app_android.util.MapPlaceIdentifiers;
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
	
	private GoogleMap 				mMap;
	private HashMap<String, Marker> mMapMarkers = new HashMap<String, Marker>();
	
	private IMapPlaceTable 			mPlaceTable;
	
	private ActionBarDrawerToggle 	mDrawerToggle;
	private DrawerLayout 			mDrawerLayout;
	private RadioGroup 				mCampusRadioGroup;
	private RadioGroup 				mViewRadioGroup;
	private AutoCompleteTextView	mSearchField;
	private ArrayAdapter<String> 	mSearchAdapter;
	
	private LatLng 					mCampusKarlskronaCoordinates;
	private LatLng 					mCampusKarlshamnCoordinates;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		mClassName = getClass().getSimpleName();
		mTag = TAG;
		mActionBarTitle = getString(R.string.actionbar_map);
        super.onCreate(savedInstanceState);
		mInfoBoxTitle = getString(R.string.infobox_title_map);
		mInfoBoxMessage = getString(R.string.infobox_text_map);
                
        LatLng place = null;
        
        //Get input parameters
        Bundle bundle = getIntent().getExtras();
    	int entryID = bundle.getInt("entryID");
    	int startPositionID = bundle.getInt("startPositionID");
    	String placeSearchInput = bundle.getString("place");
    	
    	setContentView(R.layout.activity_map);
    	
    	mPlaceTable = DatabaseManager.getInstance().getMapCoordinateTable();
    	
    	 if(initilizeMap()) {
    		 initializeDrawer();
    		 
    		 getCampusCoordinates();
    		 
    		 assert entryID >= 0 && entryID <= 1;
    		 if(entryID == 0) { 
    			 assert startPositionID >= 0 && startPositionID <= 2;
    			 if(startPositionID == 0) { //Karlskrona Selected
    				 mCampusRadioGroup.check(R.id.radio_karlskrona);
    				 mViewRadioGroup.check(R.id.radio_normal);
    				 moveToKarlskrona();
    				 
    			 } 
    			 else if (startPositionID == 1) { //Karlshamn Selected
    				 mCampusRadioGroup.check(R.id.radio_karlshamn);
    				 mViewRadioGroup.check(R.id.radio_satellite);
    				 moveToKarlshamn();
    			 }
    		 }
    		 else if(entryID == 1) { //Entered trough an entrypoint that specified a room
    			try {
					place = mPlaceTable.getMapCoordinate(placeSearchInput);
				} catch (DBException e) {
					place = null;
					e.printStackTrace();
				} catch (NoResultFoundDBException e) {
					place = null;
					e.printStackTrace();
				}
    			if(place != null) {
    				mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(place, 17.0f));
         			if(!mMapMarkers.containsKey("SearchMarker")) {
         				MarkerOptions markerOptions = new MarkerOptions();
						markerOptions.position(place);
						markerOptions.snippet(placeSearchInput);
						mMapMarkers.put("SearchMarker", mMap.addMarker(markerOptions));
         			}
         			else {
						Marker marker = mMapMarkers.get("SearchMarker");
						marker.setPosition(place);
						marker.setSnippet(placeSearchInput);
					}
    			}
    			else {	//If the lookup fails toast the user about it and move to Karlskrona
    				Toast.makeText(getApplicationContext(), R.string.toast_map_failed_find_place, Toast.LENGTH_SHORT).show();
    				mCampusRadioGroup.check(R.id.radio_karlskrona);
    				mViewRadioGroup.check(R.id.radio_normal);
    				moveToKarlskrona();
    			}
    		 }
    	 }
    }
	
	private boolean initilizeMap() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":initilizeMap()");}
        if (mMap == null) {
        	mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            // check if map is created successfully or not
            if (mMap == null) {
                Toast.makeText(getApplicationContext(), R.string.toast_map_failed_start_google_maps, Toast.LENGTH_LONG).show();
                return false;
            }
            mMap.setInfoWindowAdapter(new SnippetInfoWindowAdapter()); //Changes the way marker descriptions are presented. If this is not done; multi line descriptions cannot be used.
            initializeToggleableMarkers();
        }
        return true;
    }
	
	private void initializeDrawer() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":initializeDrawer()");}

		mSearchField 		= (AutoCompleteTextView) findViewById(R.id.course_search_input);
		mDrawerLayout 		= (DrawerLayout) findViewById(R.id.drawer_layout);
		mCampusRadioGroup 	= (RadioGroup) findViewById(R.id.radio_group_campus);
		mViewRadioGroup 		= (RadioGroup) findViewById(R.id.radio_group_views);
		mSearchField 		= (AutoCompleteTextView) findViewById(R.id.map_place_search_field);
		
		//Initialize the drawer indicator/button
		mDrawerToggle		= new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close){
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
 
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
		mDrawerLayout.setDrawerListener(mDrawerToggle);
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
		String[] searchablesPlaceNames;
		try {
			searchablesPlaceNames = mPlaceTable.getAllNamesByToggleId(MapPlaceIdentifiers.TOGGLE_ID_NO_TOGGLE, false);
		} catch (DBException e) {
			searchablesPlaceNames = new String[0];
			e.printStackTrace();
		} catch (NoResultFoundDBException e) {
			searchablesPlaceNames = new String[0];
			e.printStackTrace();
		}
		mSearchAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, searchablesPlaceNames);
		mSearchField.setAdapter(mSearchAdapter);
		mSearchField.setThreshold(0);
		mSearchField.setOnItemClickListener(new OnItemClickListener() {
			@Override
		    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) { 
				//Remove the input keyboard
				mSearchField.clearFocus();
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				if(imm.isActive())
					imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
				
				//Move the search marker
				MarkerOptions markerOptions;
				try {
					markerOptions = mPlaceTable.getMapMarkerOptions( (String) arg0.getItemAtPosition(arg2));
				} catch (DBException e) {
					markerOptions = null;
					e.printStackTrace();
				} catch (NoResultFoundDBException e) {
					markerOptions = null;
					e.printStackTrace();
				}
				
				if(markerOptions != null) {
					if(mMapMarkers.containsKey("SearchMarker")) {
						Marker marker = mMapMarkers.get("SearchMarker");
						marker.setPosition(markerOptions.getPosition());
						marker.setSnippet(markerOptions.getSnippet());
						marker.showInfoWindow();
					}
					else {
						mMapMarkers.put("SearchMarker", mMap.addMarker(markerOptions));
						mMapMarkers.get("SearchMarker").showInfoWindow();
					}
					
					//Move the camera and close the drawer to show the search marker
					mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(markerOptions.getPosition(), 17.0f));
					mSearchField.setText(""); //Make the text field empty so that the user doesn't have to erase text in it before searching again
					mDrawerLayout.closeDrawers();
				}
			}	
		});
	}
	
	private void moveToKarlskrona() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":moveToKarlskrona()");}
		mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(mCampusKarlskronaCoordinates, 17.0f));
		if(mMap.getMapType() != GoogleMap.MAP_TYPE_NORMAL)  { //Only change if the normal map type is not set. (The satellite and hybrid view of campus Karlskrona is outdated)
			mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			mViewRadioGroup.check(R.id.radio_normal);
		}
	}
	
	private void moveToKarlshamn() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":moveToKarlshamn()");}
		mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(mCampusKarlshamnCoordinates, 17.0f));
		if(mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL) { //Only change if the normal map type is set. (Google Maps currently has no good data for the map type for the Karlshamn Campus area)
			mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			mViewRadioGroup.check(R.id.radio_satellite);
		}
	}
	
	private void initializeToggleableMarkers() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":initializeToggleableMarkers()");}
		//Get all non searchable places
		String[] markerNames;
		try {
			markerNames = mPlaceTable.getAllNamesByToggleId(MapPlaceIdentifiers.TOGGLE_ID_NO_TOGGLE, true);
		} catch (DBException e) {
			markerNames = null;
			e.printStackTrace();
		} catch (NoResultFoundDBException e) {
			markerNames = null;
			e.printStackTrace();
		}
		if(markerNames != null) {
			//Get the specifications for the found places and create the markers.
			for(int i = 0; i < markerNames.length; ++i) {
				MarkerOptions options;
				try {
					options = mPlaceTable.getMapMarkerOptions(markerNames[i]);
				} catch (DBException e) {
					options = null;
					e.printStackTrace();
				} catch (NoResultFoundDBException e) {
					options = null;
					e.printStackTrace();
				}
				if(options.getPosition() != null)
					mMapMarkers.put(markerNames[i], mMap.addMarker(options));
			}
		}
	}
	
	private void toggleMarkers (int toggleId ,boolean on) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":toggleMarkers()");}
		String[] markerNames;
		try {
			markerNames = mPlaceTable.getAllNamesByToggleId(toggleId, false);
		} catch (DBException e) {
			markerNames = null;
			e.printStackTrace();
		} catch (NoResultFoundDBException e) {
			markerNames = null;
			e.printStackTrace();
		}
		if(markerNames != null) {
			for(int i = 0; i < markerNames.length; ++i) {
				mMapMarkers.get(markerNames[i]).setVisible(on);
			}
		}
	}
	
	// Gets Coordinates for the camera default location for respective campuses. Gets them from resource files.
	private void getCampusCoordinates() {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":getCampusCoordinates()");}
		TypedValue karlskronaLatitude = new TypedValue();
		TypedValue karlskronaLongitude = new TypedValue();
		TypedValue karlshamnLatitude = new TypedValue();
		TypedValue karlshamnLongitude = new TypedValue();
		
		getResources().getValue(R.dimen.karlskrona_campus_latitude, karlskronaLatitude, true);
		getResources().getValue(R.dimen.karlskrona_campus_longitude, karlskronaLongitude, true);
		getResources().getValue(R.dimen.karlshamn_campus_latitude, karlshamnLatitude, true);
		getResources().getValue(R.dimen.karlshamn_campus_longitude, karlshamnLongitude, true);
		
		mCampusKarlskronaCoordinates = new LatLng(karlskronaLatitude.getFloat(),karlskronaLongitude.getFloat());
		mCampusKarlshamnCoordinates = new LatLng(karlshamnLatitude.getFloat(), karlshamnLongitude.getFloat());
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onOptionsItemSelected()");}
		if(mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
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
				mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				break;
			case R.id.radio_satellite:
				mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
				break;
			case R.id.radio_hybrid:
				mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
				break;
		}
		mDrawerLayout.closeDrawers();
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
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onResume()");}
		super.onResume();
		initilizeMap();
	}

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":onPostCreate()");}
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
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