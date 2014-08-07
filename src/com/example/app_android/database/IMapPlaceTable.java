package com.example.app_android.database;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public interface IMapPlaceTable {

	LatLng getMapCoordinate(String name);
	
	MarkerOptions getMapMarkerOptions(String name);
	
	String[] getAllSearchableNames();
}