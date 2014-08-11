package com.example.app_android.database.interfaces;

import com.example.app_android.database.DBException;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public interface IMapPlaceTable {
	
	boolean add(String name, String description, float latitude, float longitude, int iconId, int toggleId) throws DBException; // TODO  Should throw NoRowsAffectedError

	LatLng getMapCoordinate(String name); // TODO should throw error if nothing is found
	
	MarkerOptions getMapMarkerOptions(String name); // TODO should throw error if nothing is found
	
	String[] getAllNamesByToggleId(int toggleId, boolean getNonEqual); // TODO should throw error if nothing is found
}