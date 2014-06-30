package com.example.app_android;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class ActivityMap extends Activity{
	
	private GoogleMap mMap;
	private static final String TAG = "ActivityMap";
	private LatLng place; 
	private int entryID;
	private String city;
	private String room;
	private final static boolean verbose = true;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        entryID = bundle.getInt("cityId");
        room = bundle.getString("Room");
        
        if(entryID == 0) { //Karlskrona Selected
        	city = "Karlskrona";
        	place = (LatLng)Cache.getMapCoordinate(city);
			}
         else if (entryID == 1){ //Karlshamn Selected
        	city = "Karlshamn";
        	place = (LatLng)Cache.getMapCoordinate(city);
        } else { //Entered trough an entrypoint that specified a room
        	place = (LatLng)Cache.getMapCoordinate(room);
        }
        
        setContentView(R.layout.activity_maplayout_full);
        if(initilizeMap()) {
        	if(place != null) {
        		if(entryID == 0 || entryID == 1) {
            		mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(place, 17.0f));	//Center the camera over the chosen campus
            	}
            	else if(entryID == -1) { //Add room marker and center on it if we are coming from the schedule
            		mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(place, 17.0f));
            		mMap.addMarker(new MarkerOptions().position(place).title(room));
            	}
        	}
        	else { //Show both campuses and center near Ronneby
        		mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(56.169908, 15.225654), 9.0f));
        	}
        }
    }
	
    @Override
	protected void onDestroy() {
    	if (verbose)
    		Log.v(TAG, getClass().getSimpleName() + ":entered onDestroy()");
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		if (verbose)
    		Log.v(TAG, getClass().getSimpleName() + ":entered onPause()");
		super.onPause();
	}

	@Override
	protected void onRestart() {
		if (verbose)
    		Log.v(TAG, getClass().getSimpleName() + ":entered onRestart()");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		if (verbose)
    		Log.v(TAG, getClass().getSimpleName() + ":entered onResume()");
		super.onResume();
		initilizeMap();
	}

	@Override
	protected void onStart() {
		if (verbose)
    		Log.v(TAG, getClass().getSimpleName() + ":entered onStart()");
		super.onStart();
	}

	@Override
	protected void onStop() {
		if (verbose)
    		Log.v(TAG, getClass().getSimpleName() + ":entered onStop()");
		super.onStop();
	}

	//
	private boolean initilizeMap() {
        if (mMap == null) {
        	//Try to create the map
            mMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
 
            // check if map is created successfully or not
            if (mMap == null) {
                Toast.makeText(getApplicationContext(), "Unable to start Google Maps. Sorry! :(", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }	
}