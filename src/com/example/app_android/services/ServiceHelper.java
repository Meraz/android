package com.example.app_android.services;

import java.util.HashMap;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;


public class ServiceHelper {
		

	
	// Activites that starts services must implement this.
	public interface ActivityCallback {
		public void receiveResult(int i);
	}
	
	public interface ServiceHelperCallback {
		
	}	

	private static HashMap<Integer, MetaServiceBean> mServices = new HashMap<Integer, MetaServiceBean>();
	private static BroadcastReceiver mReceiver;
	private static ServiceHelper mServiceHelper = null;
	private static Context mContext; 
	
	// TODO
	// Create queue implementation here mayhaps?
	
	// Private constructor to stop instantiating this class.
	private ServiceHelper(Context context) {
	}
	
	public static void initialize(Context context)
	{
		  mReceiver = new BroadcastReceiver() {
			    @Override
			    public void onReceive(Context context, Intent intent) {
			    ServiceHelper.informResult();
			    }
			  };
			IntentFilter filter = new IntentFilter();
			  filter.addAction("SOME_ACTION");
			  filter.addAction("SOME_OTHER_ACTION");
			context.registerReceiver(mReceiver, filter);
	}
	
	/*
	public static int GetOpenInformation(Context context, ActivityCallback receiver, int match, String parameters)
	{
		
		return 1;
	}*/
	
	public static int loginStudentportal(Context context, ActivityCallback receiver, int token, String parameters) {
		
		final int key = parameters.hashCode();
		
		if(checkIfAlreadyExists(key, parameters)) {
			return key;
		}
		// Not sure which kind of intent it has to be
		final Intent intent = new Intent(Intent.ACTION_SYNC, null, context,	LoginStudentportal.class);
		intent.putExtra("api_url", parameters);
		intent.putExtra("token", token);
		intent.putExtra("access_type", "POST");
		context.startService(intent);	
		
		return key;
	}
	
	public static void informResult()
	{
		
	}
	
	public static int stopService()
	{
		return 1;
	}
	
	private static boolean checkIfAlreadyExists(int key, String parameters) {
		if(mServices.containsKey(key)) {
			return true; 				// This service already exists in hashmap
		}
		return false;
	}
}
