package com.example.app_android.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class Utilities {

	public final static boolean verbose = true;
	public final static boolean error = true;
	
	private Utilities() {
		
	}
	
	public static boolean isNetworkAvailable(Context activityContext) {
	    ConnectivityManager connectivityManager = (ConnectivityManager) activityContext.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null;
	}
}
