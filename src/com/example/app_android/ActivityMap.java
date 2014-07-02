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

public class ActivityMap extends Activity {
	private GoogleMap map;
	private static final String TAG = "ActivityMap";
	private final static boolean verbose = true;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String city = "";
        LatLng place = null;
        
        //Get input parameters
        Bundle bundle = getIntent().getExtras();
    	int entryID = bundle.getInt("entryID");
    	int startPositionID = bundle.getInt("startPositionID");
    	String room = bundle.getString("room");
    	
    	setContentView(R.layout.activity_maplayout_full);
    	
    	 if(initilizeMap()) {
    		 assert entryID >= 0 && entryID <= 1;
    		 if(entryID == 0) { 
    			 assert startPositionID >= 0 && startPositionID <= 2;
    			 if(startPositionID == 0) { //Karlskrona Selected
    				 city = "Karlskrona";
    			 } 
    			 else if (startPositionID == 1) { //Karlshamn Selected
    				 city = "Karlshamn";
    			 }
    			 place = (LatLng)Cache.getMapCoordinate(city);
				 map.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 17.0f)); //Center the camera over the chosen campus
    		 }
    		 else if(entryID == 1) { //Entered trough an entrypoint that specified a room
    			place = (LatLng)Cache.getMapCoordinate(room);
         		map.moveCamera( CameraUpdateFactory.newLatLngZoom(place, 17.0f));
         		map.addMarker(new MarkerOptions().position(place).title(room));
    		 }
    	 }
    }
	
	private boolean initilizeMap() {
        if (map == null) {
        	//Try to create the map
        	map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            // check if map is created successfully or not
            if (map == null) {
                Toast.makeText(getApplicationContext(), "Unable to start Google Maps. Sorry! :(", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
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
}