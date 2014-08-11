package com.example.app_android.database.interfaces;

import com.example.app_android.database.DBException;
import com.example.app_android.database.NoResultFoundDBException;
import com.example.app_android.database.NoRowsAffectedDBException;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public interface IMapPlaceTable {
	
	/*
	 * TODO
	 */
	void add(String name, String description, float latitude, float longitude, int iconId, int toggleId) throws DBException, NoRowsAffectedDBException;

	/*
	 * TODO
	 */	
	LatLng getMapCoordinate(String name)  throws DBException, NoResultFoundDBException;
	
	/*
	 * TODO
	 */
	MarkerOptions getMapMarkerOptions(String name); // TODO should throw error if nothing is found
	
	/*
	 * TODO
	 */
	String[] getAllNamesByToggleId(int toggleId, boolean getNonEqual); // TODO should throw error if nothing is found
}