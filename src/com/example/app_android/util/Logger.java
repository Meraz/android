package com.example.app_android.util;

import android.util.Log;

public final class Logger {

	private final static boolean verbose = true;
	private final static boolean error = true;
	private final static String APP_TAG = "com.example.app_android";
	
	
	private Logger() {
		
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
}
