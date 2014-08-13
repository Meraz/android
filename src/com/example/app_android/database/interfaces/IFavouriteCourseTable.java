package com.example.app_android.database.interfaces;

import java.util.ArrayList;

import com.example.app_android.database.DBException;
import com.example.app_android.database.NoResultFoundDBException;
import com.example.app_android.database.NoRowsAffectedDBException;

public interface IFavouriteCourseTable {
	
	/*
	 * Saves the inputed data as a database entry
	 */
	public void add(String courseCode) throws DBException, NoRowsAffectedDBException;
	
	/*
	 * Finds and removes the entry with the inputed course code
	 */
	public void remove(String courseCode) throws DBException, NoRowsAffectedDBException;
	
	/*
	 * Returns all entries in this table
	 */
	public ArrayList<String> getAll() throws DBException, NoResultFoundDBException;
	
	/*
	 * Checks if the table is empty or not
	 */
	public boolean isEmpty() throws DBException;
}