package com.example.app_android.database;

import java.util.ArrayList;

import com.example.app_android.CourseBean;

public interface ICourseTable {
	public ArrayList<String> getAllCourseCodes();
	
	public ArrayList<String> getAllCourseNames();
	
	public CourseBean getCourse(String CourseCode);
}