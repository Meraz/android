package com.example.app_android.services;

public class TestDatabase {
	
	private TestDatabase() {
	}
	
	static private String mData = "FirstData";
	
	static public void changeSomeData(String data)
	{
		mData = data;
	}
	
	static public String getSomeData()
	{
		return mData;
	}
		
	
}
