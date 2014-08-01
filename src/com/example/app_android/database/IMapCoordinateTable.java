package com.example.app_android.database;

import com.google.android.gms.maps.model.MarkerOptions;

public interface IMapCoordinateTable {

	MarkerOptions getMapMarkerOptions(String name);
}