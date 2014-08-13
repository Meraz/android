package com.example.app_android.database.interfaces;

import java.util.ArrayList;

import com.example.app_android.CourseBean;
import com.example.app_android.database.DBException;
import com.example.app_android.database.NoResultFoundDBException;
import com.example.app_android.database.NoRowsAffectedDBException;

public interface ICourseTable {
	
	/*
	 * Saves the inputed course as a database entry
	 */
	public boolean add(CourseBean course) throws DBException, NoRowsAffectedDBException;
	
	/*
	 * Returns the course code of all entries in this table
	 */
	public ArrayList<String> getAllCourseCodes() throws DBException, NoResultFoundDBException;
	
	/*
	 * Returns the names of all courses in this table
	 */
	public ArrayList<String> getAllCourseNames() throws DBException, NoResultFoundDBException;
	
	/*
	 * Finds a course by the inputed course coide and returns all its fields as a CourseBean
	 */
	public CourseBean getCourse(String CourseCode) throws DBException, NoResultFoundDBException;
}