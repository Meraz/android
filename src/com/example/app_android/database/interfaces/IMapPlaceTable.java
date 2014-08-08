package com.example.app_android.database.interfaces;

import com.example.app_android.database.DBException;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public interface IMapPlaceTable {
	
	boolean add(String name, String description, float latitude, float longitude, int iconId, int toggleId) throws DBException;

	LatLng getMapCoordinate(String name);
	
	MarkerOptions getMapMarkerOptions(String name);
	
	String[] getAllSearchableNames();
}