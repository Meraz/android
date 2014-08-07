package com.example.app_android.database.interfaces;

import java.util.ArrayList;

import com.example.app_android.database.DBException;
import com.example.app_android.database.NoRowsAffectedDBException;

public interface IFavouriteCourseTable {
	
	public boolean add(String courseCode) throws DBException;
	
	public boolean remove(String courseCode) throws DBException, NoRowsAffectedDBException;
	
	public ArrayList<String> getAll();
	
	public boolean isEmpty();
}