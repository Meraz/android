package com.example.app_android.database.interfaces;

import java.util.ArrayList;

import com.example.app_android.database.DBException;
import com.example.app_android.database.NoRowsAffectedDBException;

public interface IFavouriteCourseTable {
	
	public boolean add(String courseCode) throws DBException; // TODO should throw NoRowsAffectedDBException
	
	public boolean remove(String courseCode) throws DBException, NoRowsAffectedDBException;
	
	public ArrayList<String> getAll();	// TODO should throw exception if nothing is found
	
	public boolean isEmpty(); // TODO should throw error if nothing is found ?? Or removed maybe.
}