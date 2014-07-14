package com.example.app_android;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ActivityMap extends Activity {
	private GoogleMap map;
	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private static final String TAG = "ActivityMap";
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    				 moveToKarlskrona();
    			 } 
    			 else if (startPositionID == 1) { //Karlshamn Selected
    				 moveToKarlshamn();
    			 }
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
            map.setInfoWindowAdapter(new SnippetInfoWindowAdapter()); //Changes the way marker descriptions is presented. If this is not done; multi line descriptions cannot be used.
            addHouseMarkers();
            
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
	
	private void moveToKarlskrona() {
		map.moveCamera( CameraUpdateFactory.newLatLngZoom(Cache.getMapCoordinate("Karlskrona"), 17.0f));
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	}
	
	private void moveToKarlshamn() {
		map.moveCamera( CameraUpdateFactory.newLatLngZoom(Cache.getMapCoordinate("Karlshamn"), 17.0f));
		map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
	}
	
	private void addHouseMarkers() { //TODO add icons to the markers
		addHouseMarker("HOUSE_A");
		addHouseMarker("HOUSE_B");
		addHouseMarker("HOUSE_C");
		addHouseMarker("HOUSE_D");
		addHouseMarker("HOUSE_G");
		addHouseMarker("HOUSE_H");
		addHouseMarker("HOUSE_J");
	}
	
	private void addHouseMarker(String house) {
		MarkerOptions options = new MarkerOptions();
		
		options.title("");
		options.position(Cache.getMapCoordinate(house));
		options.snippet(Cache.getMapMarkerSnippet(house));
		
		if(options.getPosition() != null)
			map.addMarker(options);
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
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @SuppressWarnings("rawtypes")
		@Override
	    public void onItemClick(AdapterView parent, View view, int position, long id) {
	    	 drawerList.setItemChecked(position, true);
		     drawerLayout.closeDrawer(drawerList);
		        
		    if(position == 0) {
		    	moveToKarlskrona();
		   	}

		   	else if(position == 1) {
		   		moveToKarlshamn();
		   	}
	    	else if(position == 2) { //TODO remove test code
	    		MarkerOptions o = new MarkerOptions();
		    	o.position(Cache.getMapCoordinate("J1270"));
		   		o.title("");
		   		o.snippet("Här ligger J1270");
		   		map.addMarker(o);
		   		map.moveCamera( CameraUpdateFactory.newLatLngZoom(Cache.getMapCoordinate("J1270"), 17.0f));
		    }
	    }
	}
	//Used to display HTML-based text as marker snippets
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