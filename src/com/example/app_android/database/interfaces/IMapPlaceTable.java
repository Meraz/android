package com.example.app_android.database.interfaces;

import com.example.app_android.database.DBException;
import com.example.app_android.database.NoResultFoundDBException;
import com.example.app_android.database.NoRowsAffectedDBException;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public interface IMapPlaceTable {
	
	/*
	 * Saves the inputed values as a database entry
	 */
	void add(String name, String description, float latitude, float longitude, int iconId, int toggleId) throws DBException, NoRowsAffectedDBException;

	/*
	 * Gets a coordinate pair by searching the inputed name
	 */	
	LatLng getMapCoordinate(String name)  throws DBException, NoResultFoundDBException;
	
	/*
	 * Gets a finished markerOptions object by searching the inputed name. The markerOptions object is ready to be added to a marker object upon return
	 */
	MarkerOptions getMapMarkerOptions(String name) throws DBException, NoResultFoundDBException;
	
	/*
	 * Gets the names of all toggleable markers with the inputed toggle id.
	 * Setting the getNonEqual flag inverts the function so that it returns all names but those that have the inputed toggle id
	 */
	String[] getAllNamesByToggleId(int toggleId, boolean getNonEqual) throws DBException, NoResultFoundDBException;
}