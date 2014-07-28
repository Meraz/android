package com.example.app_android.services;


/*
 * All functions takes 'myBroadCastReceiver' in case broadcast is useful aswell
 * 
 * 
 */

import java.util.HashMap;

import com.example.app_android.util.Logger;
import com.example.app_android.util.MyBroadCastReceiver;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

public class ServiceManager implements IServiceManager {
	
	private static final String TAG = "ServiceHelper";
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
	private static HashMap<Integer, ServiceDataBean> mServices = new HashMap<Integer, ServiceDataBean>();
	
	// Private constructor to stop instantiating this class.
	private ServiceManager(Context context) {
		mContext = context;
	}
	
	public int requestToken(Context context, int token, String parameters, MyBroadCastReceiver myBroadCastReceiver) {
		
		final int key = parameters.hashCode();
				
		// Not sure which kind of intent it has to be
		//final Intent intent = new Intent(context, LoginStudentportal.class);
		final Intent intent = new Intent(context, ServiceRequestToken.class);
		//intent.putExtra("api_url", parameters);
		//intent.putExtra("token", token);
		//intent.putExtra("access_type", "POST");
		if(myBroadCastReceiver != null) {
			intent.putExtra("startBroadCast", myBroadCastReceiver.getStartBroadCast());
			intent.putExtra("stopBroadCast", myBroadCastReceiver.getStopBroadCast());
		}

		context.startService(intent);	
		
		return key;
	}
	
	public int loginStudentportala(Context context, int token, String parameters, MyBroadCastReceiver myBroadCastReceiver) {
		
		final int key = parameters.hashCode();
		if(mServices.containsKey(key))
			return key; 					// This service is already ongoing. Return key for it.
		
		myBroadCastReceiver.setServiceManager(this);	// Set servicemanager for the broadcast receiver, so it can call this on broadcast if needed. 
		
		ServiceDataBean bean = createServiceDataBean(key, parameters, myBroadCastReceiver);	// Create databean with information
		
		Intent intent = new Intent(context, LocalService.class); 	// Create intent for specific class
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
	
	// Called by a broadcastReciever when receiving a broadcast 
	@Override
	public void onServiceStop(int id) {
		Logger.VerboseLog(TAG, ":entered CallBack()");
		if(id > 0) {
			if(mServices.containsKey(id) == true) {
				Intent intent = mServices.get(id).getIntent();
				mContext.stopService(intent);
				mServices.remove(id);
			}
		}
	}
}
