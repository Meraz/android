package com.example.app_android.services;

import java.util.HashMap;

import com.example.app_android.util.MyBroadCastReceiver;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;


public class ServiceHelper {
	
	// TODO
	// Create queue implementation here mayhaps?
	
	// Private constructor to stop instantiating this class.
	private ServiceHelper(Context context) {
	}
	
	// TODO make this class instanceable that is initialized everytime it's needed and is in
	// scope until it's not needed anymore. ** Not sure how this class
	// This would require the initialization of this class more than before but in the same time
	// also save memory when it's not used.
	// Would that be better than current build?
	// Would not be able to use the queue in that case
	public static void initialize(Context context)
	{
		 /* mReceiver = new BroadcastReceiver() {
			    @Override
			    public void onReceive(Context context, Intent intent) {
			    	ServiceHelper.informResult();
			    }
			  };
			IntentFilter filter = new IntentFilter();
			  filter.addAction("SOME_ACTION");
			  filter.addAction("SOME_OTHER_ACTION");
			context.registerReceiver(mReceiver, filter);*/
	}
		
	public static int loginStudentportal(Context context, int token, String parameters, MyBroadCastReceiver myBroadCastReceiver) {
		
		final int key = parameters.hashCode();
		
	//	if(checkIfAlreadyExists(key, parameters)) {
	//		return key;
	//	}
	//	MetaServiceBean serviceBean = new MetaServiceBean();
	//	serviceBean.setKey(key);
	//	serviceBean.setParameter(parameters);
	//	serviceBean.setCallback(receiver);
	//	mServices.put(key, serviceBean);
				
		// Not sure which kind of intent it has to be
		final Intent intent = new Intent(context, LoginStudentportal.class);
		//intent.putExtra("api_url", parameters);
		//intent.putExtra("token", token);
		//intent.putExtra("access_type", "POST");
		intent.putExtra("startBroadCast", myBroadCastReceiver.getStartBroadCast());
		intent.putExtra("stopBroadCast", myBroadCastReceiver.getStopBroadCast());
		context.startService(intent);	
		
		return key;
	}	
}
