package com.example.app_android.database.interfaces;

import java.util.ArrayList;

import com.example.app_android.CourseBean;
import com.example.app_android.database.DBException;

public interface ICourseTable {
	
	public boolean add(CourseBean course) throws DBException; // TODO should throw exception NoRowsAffectedDBException 
	
	public ArrayList<String> getAllCourseCodes(); // TODO should throw exception if nothing is found
	
	public ArrayList<String> getAllCourseNames(); // TODO should throw exception if nothing is found
	
	public CourseBean getCourse(String CourseCode); // TODO should throw exception if nothing is found
}