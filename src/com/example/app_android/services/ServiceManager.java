package com.example.app_android.services;

/* If an service is already started when calling startservice, the service will not be created, however,
	the function onCommand will be called for that service. 
	And in this we create a new instance of a thread in which is unique.Thus one unique service can have several instances of a single thread in it.

	This is why the functions that add and remove services is synchronized.
	So if I try to stop a service there wont start one at the same time.
	
	Also, if I try to use one when it's open and delete at the same time. Not good
*/

import java.util.HashMap;

import com.example.app_android.util.Logger;
import com.example.app_android.util.MyBroadCastReceiver;

import android.content.Context;
import android.content.Intent;


public class ServiceManager {

	private static final String TAG = "ServiceManager";
	private Context mContext;
	private static ServiceManager mServiceHelper = null;
	
	// This must be called before using it in the app. And should also be called with applicationContext to avoid contextleaks
	public static void initialize(Context context) {
		if(mServiceHelper == null)
			mServiceHelper = new ServiceManager(context);	
	}
	
	public static ServiceManager getInstance() {
		return mServiceHelper;
	}
	
	// TODO SPARSEARRAY
	private HashMap<Integer, ServiceDataBean> mServices = new HashMap<Integer, ServiceDataBean>();
	
	// Private constructor to stop instantiating this class.
	private ServiceManager(Context context) {
		mContext = context;
	}
	
	public synchronized int requestData(Context context, MyBroadCastReceiver myBroadCastReceiver) {
		
		final int key = myBroadCastReceiver.getAllBroadcasts().hashCode();
				
		// Not sure which kind of intent it has to be
		//final Intent intent = new Intent(context, LoginStudentportal.class);
		final Intent intent = new Intent(context, ServiceRequestToken.class);
		if(myBroadCastReceiver != null) {
			intent.putExtra("startBroadCast", myBroadCastReceiver.getStartBroadCast());
			intent.putExtra("stopBroadCast", myBroadCastReceiver.getStopBroadCast());
		}

		context.startService(intent);	
		
		return key;
	}
	
	public synchronized int requestToken(Context context, String parameters, MyBroadCastReceiver myBroadCastReceiver) {
		
		final int key = parameters.hashCode();
		if(mServices.containsKey(key))
			return key; 					// This service is already ongoing. Return key for it.
		
		ServiceDataBean bean = createServiceDataBean(key, parameters, myBroadCastReceiver);	// Create databean with information
		
		Intent intent = new Intent(context, ServiceRequestToken.class); 	// Create intent for specific class
		intent = prepareDefaultIntent(intent, bean);				// Prepare intent with data needed for all services
		bean.setIntent(intent);										// Save intent as it's needed if I want to abort service 	
		
		mServices.put(key, bean); 							// Save service in map.
		context.startService(intent); 						// Start service
		return key;
	}
	
	public boolean checkOngoing(int key) {
		if(mServices.containsKey(key))
			return true; // Already exists
		return false;
	}
	
	// Called by a broadcastReciever when receiving a broadcast 
	public synchronized void onServiceStop2(int id) {
		Logger.VerboseLog(TAG, TAG + ":entered CallBack()");
		if(id > 0 ) {
			Intent intent = mServices.get(id).getIntent();
			mContext.stopService(intent);
			mServices.remove(id);
		}
	}
	
	private static Intent prepareDefaultIntent(Intent intent, ServiceDataBean databean) {	
		intent.putExtra("id", databean.getKey());
		intent.putExtra("startBroadCast", databean.getBroadCastReceiver().getStartBroadCast());
		intent.putExtra("stopBroadCast",  databean.getBroadCastReceiver().getStopBroadCast());
		
		return intent;
	}
	
	private static ServiceDataBean createServiceDataBean(int key, String parameters, MyBroadCastReceiver myBroadCastReceiver){
		ServiceDataBean databean = new ServiceDataBean();
		databean.setKey(key);
		databean.setParameter(parameters);
		databean.settBroadCastReceiver(myBroadCastReceiver);
		return databean;
	}
}
