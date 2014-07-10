package com.example.app_android.services;

import com.example.app_android.services.ServiceHelper.ActivityCallback;;

public class MetaServiceBean implements java.io.Serializable {

	private int mKey;
	private String mParameter;
	private ActivityCallback mCallback;	
		
	public MetaServiceBean() {
		// TODO Auto
	}
	
	public int getKey() {
		return mKey;
	}
	
	public void setKey(int key) {
		mKey = key;
	}
	
	public String getParameter()
	{
		return mParameter;
	}
	
	public void setParameter(String parameter) {
		mParameter = parameter;
	}
	
	public ActivityCallback getCallback()
	{
		return mCallback;
	}
	
	public void setCallback(ActivityCallback callback) {
		mCallback = callback;
	}		
}
