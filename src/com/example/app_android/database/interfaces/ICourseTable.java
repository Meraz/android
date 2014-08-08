package com.example.app_android.database.interfaces;

import java.util.ArrayList;

import com.example.app_android.CourseBean;
import com.example.app_android.database.DBException;

public interface ICourseTable {
	
	public boolean add(CourseBean course) throws DBException;
	
	public ArrayList<String> getAllCourseCodes();
	
	public ArrayList<String> getAllCourseNames();
	
	public CourseBean getCourse(String CourseCode);
}