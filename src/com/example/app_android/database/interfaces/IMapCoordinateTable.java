package com.example.app_android.database.interfaces;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public interface IMapCoordinateTable {

	LatLng getMapCoordinate(String name);
	
	MarkerOptions getMapMarkerOptions(String name);
}