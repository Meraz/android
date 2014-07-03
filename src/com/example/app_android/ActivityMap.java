package com.example.app_android;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ActivityMap extends Activity {
	private GoogleMap map;
	private DrawerLayout drawerLayout;
	private ListView drawerList;
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
    		 initializeDrawer();
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
        }
        return true;
    }
	
	private void initializeDrawer() {
		String[] listItems = getResources().getStringArray(R.array.map_drawer_items);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ListView) findViewById(R.id.left_drawer);
		
		drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.item_map_drawer, listItems));
		drawerList.setOnItemClickListener(new DrawerItemClickListener());
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
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @Override
	    public void onItemClick(AdapterView parent, View view, int position, long id) {
	        selectItem(position);
	    }
	    private void selectItem(int position) {
	        drawerList.setItemChecked(position, true);
	        drawerLayout.closeDrawer(drawerList);
	        
	    	if(position == 0) {
	    		map.moveCamera( CameraUpdateFactory.newLatLngZoom(Cache.getMapCoordinate("Karlskrona"), 17.0f));
	    	}
	    	else if(position == 1) {
	    		map.moveCamera( CameraUpdateFactory.newLatLngZoom(Cache.getMapCoordinate("Karlshamn"), 17.0f));
	    	}
	    	else if(position == 2) {
	    		MarkerOptions o = new MarkerOptions();
	    		o.position(Cache.getMapCoordinate("J1270"));
	    		o.title("J1270");
	    		o.snippet("Här ligger J1270");
	    		map.addMarker(o);
	    		map.moveCamera( CameraUpdateFactory.newLatLngZoom(Cache.getMapCoordinate("J1270"), 17.0f));
	    	}
	    }
	}
}