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

public class ServiceHelper {
	
	private static final String TAG = "ServiceHelper";
	private Context mContext;
	private static ServiceHelper mServiceHelper = null;
	
	// TODO
	/* 
	// Interface that must be implemented when calling this class.
	public interface ActivityCallback {
		public void ACallback(int result);
	}
	*/
	
	public static void initialize(Context context) {
		if(mServiceHelper == null)
			mServiceHelper = new ServiceHelper(context);	
	}
	
	public static ServiceHelper getServiceHelper() {
		return mServiceHelper;
	}
	
	// TODO SPARSEARRAY
	private static HashMap<Integer, ServiceDataBean> mServices = new HashMap<Integer, ServiceDataBean>();
	
	// Private constructor to stop instantiating this class.
	private ServiceHelper(Context context) {
		mContext = context;
	}
	
	public int loginStudentportal(Context context, int token, String parameters, MyBroadCastReceiver myBroadCastReceiver) {
		
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
	
	public int loginStudentportala(Context context, int token, String parameters, MyBroadCastReceiver broadCastReceiver) {
		
		final int key = parameters.hashCode();
		if(mServices.containsKey(key))
			return key; // Already exists, return key

		final Intent intent = new Intent(context, LocalService.class);
		intent.putExtra("startBroadCast", broadCastReceiver.getStartBroadCast());
		intent.putExtra("stopBroadCast", broadCastReceiver.getStopBroadCast());
		intent.putExtra("id", key);
		
		ServiceDataBean databean = new ServiceDataBean();
		databean.setKey(key);
		databean.setParameter(parameters);
		databean.settBroadCastReceiver(broadCastReceiver);
		databean.setIntent(intent);

		mServices.put(key, databean); // Save service in map.
		context.startService(intent); // Start service
		//saveAndStartService(context, intent, key, databean);
		
		return key;
	}
	
	public boolean checkOngoing(int key) {
		if(mServices.containsKey(key))
			return true; // Already exists
		return false;
	}
	
	@SuppressWarnings("unused") // TODO
	private void saveAndStartService(Context context, Intent intent, int key, ServiceDataBean databean) {
		mServices.put(key, databean); // Save service in map.
		context.startService(intent); // Start service
	}

	public void CallBack(int id) {
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
