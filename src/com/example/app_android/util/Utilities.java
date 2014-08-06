package com.example.app_android.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public final class Utilities {

	public final static boolean verbose = true;
	private final static boolean error = true;
	private final static String APP_TAG = "com.example.app_android";
	
	
	private Utilities() {
		
	}
	


	static public void VerboseLog(String tag, String message)
	{
		if(verbose)
			Log.v(tag, message);
	}
	
	static public void ErrorLog(String message)
	{
		if(error)
			Log.e(APP_TAG, message);
	}
	
	public static boolean isNetworkAvailable(Context activityContext) {
	    ConnectivityManager connectivityManager = (ConnectivityManager) activityContext.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null;
	}
}
