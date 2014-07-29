package com.example.app_android;

public final class CourseBean {
	private String courseCode;
	private String courseName;
	private String courseResponsible;
	private String startDate;
	private String endDate;
	private String courseLiterature;
	private String nextExamDate;
	private String courseDescription;
	
	public CourseBean(String courseCode, String courseName, String courseResponsible, String startDate,
			String endDate, String courseLiterature, String nextExamDate, String courseDescription) {
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.courseResponsible = courseResponsible;
		this.startDate = startDate;
		this.endDate = endDate;
		this.courseLiterature = courseLiterature;
		this.nextExamDate = nextExamDate;
		this.courseDescription = courseDescription;
	}
	
	public String getCourseCode() {
		return courseCode;
	}
	
	public String getCourseName() {
		return courseName;
	}
	
	public String getCourseResponsible() {
		return courseResponsible;
	}
	
	public String getStartDate() {
		return startDate;
	}
	
	public String getEndDate() {
		return endDate;
	}
	
	public String getCourseLiterature() {
		return courseLiterature;
	}
	
	public String getNextExamDate() {
		return nextExamDate;
	}
	
	public String getCourseDescription() {
		return courseDescription;
	}
}