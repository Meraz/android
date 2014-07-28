package com.example.app_android.database;

import java.util.ArrayList;

public interface ICourseTable {
	
	public long insertData(String courseCode);
	
	public ArrayList<String> readAllCourses();
	
	public boolean empty();
	
	public boolean removeCourse(String course);
}