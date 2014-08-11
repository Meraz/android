package com.example.app_android.database.interfaces;

import java.util.ArrayList;

import com.example.app_android.database.DBException;
import com.example.app_android.database.NoResultFoundDBException;
import com.example.app_android.database.NoRowsAffectedDBException;

public interface IFavouriteCourseTable {
	
	/*
	 * TODO
	 */
	public void add(String courseCode) throws DBException, NoRowsAffectedDBException;
	
	/*
	 * TODO
	 */
	public void remove(String courseCode) throws DBException, NoRowsAffectedDBException;
	
	/*
	 * TODO
	 */
	public ArrayList<String> getAll() throws DBException, NoResultFoundDBException;
	
	/*
	 * TODO
	 */
	public boolean isEmpty() throws DBException;
}