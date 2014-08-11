package com.example.app_android.database.interfaces;

import java.util.ArrayList;

import com.example.app_android.CourseBean;
import com.example.app_android.database.DBException;
import com.example.app_android.database.NoResultFoundDBException;
import com.example.app_android.database.NoRowsAffectedDBException;

public interface ICourseTable {
	
	public boolean add(CourseBean course) throws DBException, NoRowsAffectedDBException;
	
	public ArrayList<String> getAllCourseCodes() throws DBException, NoResultFoundDBException;
	
	public ArrayList<String> getAllCourseNames() throws DBException, NoResultFoundDBException;
	
	public CourseBean getCourse(String CourseCode) throws DBException, NoResultFoundDBException;
}