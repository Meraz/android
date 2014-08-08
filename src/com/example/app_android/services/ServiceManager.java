package com.example.app_android.services;

/* If an service is already started when calling startservice, the service will not be created, however,
	the function onCommand will be called for that service. 
	And in this we create a new instance of a thread in which is unique. Thus one unique service can have several instances of a single thread in it.

	This is why the functions that add and remove services is synchronized.
	So if I try to stop a service there wont start one at the same time.
	
	Also, if I try to use one when it's open and delete at the same time. Not good
	
	POTENTIAL PROBLEM
	Explaination
	This, however, means that if there's several calls wanting to create a workingtask and/or remove such from the metadata table
	AND the mainthread executes and says it wants to execute new working task. 
	
	Result
	The main thread is blocked on synchronized methods
	
	Solution
	Mainthread starts thread that does everything, including queuing itself and starting a new thread.
	
*/

import java.util.HashMap;

import com.example.app_android.util.Utilities;
import com.example.app_android.util.MyBroadcastReceiver;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class ServiceManager {

	private static final String TAG = "Services";
	private static String mClassName;
	
	@SuppressWarnings("unused") // TODO
	private Context mContext;
	private static ServiceManager mServiceHelper = null;
	
	public static void initialize(Context context) {
		if(mServiceHelper == null)
			mServiceHelper = new ServiceManager(context);	
	}
	
	public static ServiceManager getInstance() {
		return mServiceHelper;
	}
	
	// TODO SPARSEARRAY
	@SuppressLint("UseSparseArrays")
	private HashMap<Integer, ServiceDataBean> mServices = new HashMap<Integer, ServiceDataBean>();
	
	// Private constructor to stop instantiating this class.
	private ServiceManager(Context context) {
		mContext = context;
		mClassName = getClass().getSimpleName();
	}

	// Returns unique hashcode
	public synchronized int requestToken(Context context, MyBroadcastReceiver myBroadCastReceiver, String username, String password) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":requestToken()");}

		
		final int key = myBroadCastReceiver.getAllBroadcastStringAsOne().hashCode();
		if(mServices.containsKey(key))
			return key; 					// This service is already ongoing. Return key for it.
				
		ServiceDataBean bean = createServiceDataBean(key, "", myBroadCastReceiver);	// Create databean with information
		
		Intent intent = new Intent(context, ServiceRequestToken.class); 	// Create intent for specific class
		intent = prepareDefaultIntent(intent, bean);				// Prepare intent with data needed for all services
		intent.putExtra("username", username);
		intent.putExtra("password", password);	// TODO is this safe?
		bean.setIntent(intent);										// Save intent as it's needed if I want to abort service 	
		
		mServices.put(key, bean); 							// Save service in map.
		context.startService(intent); 						// Start service
		return key;
	}
	
	// Returns unique hashcode
	public synchronized int checkIfLoginIsRequired(Context context, MyBroadcastReceiver myBroadCastReceiver) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":checkIfLoginIsRequired()");}
		
		final int key = myBroadCastReceiver.getAllBroadcastStringAsOne().hashCode();
		if(mServices.containsKey(key))
			return key; 					// This service is already ongoing. Return key for it.
				
		ServiceDataBean bean = createServiceDataBean(key, "", myBroadCastReceiver);	// Create databean with information
		
		Intent intent = new Intent(context, ServiceCheckLoginRequired.class); 	// Create intent for specific class
		intent = prepareDefaultIntent(intent, bean);				// Prepare intent with data needed for all services
		bean.setIntent(intent);										// Save intent as it's needed if I want to abort service 	
		
		mServices.put(key, bean); 							// Save service in map.
		context.startService(intent); 						// Start service
		return key;
	}
	
	// TODO. Test this function
	public boolean checkOngoing(int key) {
		if(mServices.containsKey(key))
			return true;
		return false;
	}
	

	// Called by a thread when it wants to remove itself from the metatable
	public synchronized void informWorkerStop(int id) {
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":requestToken()");}
		mServices.remove(id);
	}
	
	private static Intent prepareDefaultIntent(Intent intent, ServiceDataBean databean) {	
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":prepareDefaultIntent()");}
		
		intent.putExtra("id", databean.getKey());
		intent.putExtra("startBroadcast", databean.getBroadCastReceiver().getStartBroadCast());
		intent.putExtra("updateBroadcast", databean.getBroadCastReceiver().getUpdateBroadCast());
		intent.putExtra("stopBroadcast",  databean.getBroadCastReceiver().getStopBroadCast());
		
		return intent;
	}
	
	// Only important thing that I needed to save was the Intent. This was saved for the possibility to stop an service. 
	// Nowdays this information stored is of no use, however, we'd want to stop services in the future, and as such we need to save data.
	private static ServiceDataBean createServiceDataBean(int key, String parameters, MyBroadcastReceiver myBroadCastReceiver){
		if(Utilities.verbose) {Log.v(TAG, mClassName + ":createServiceDataBean()");}
		
		ServiceDataBean databean = new ServiceDataBean();
		databean.setKey(key);
		databean.setParameter(parameters);
		databean.settBroadCastReceiver(myBroadCastReceiver);
		return databean;
	}
}
