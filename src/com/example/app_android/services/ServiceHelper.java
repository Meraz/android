package com.example.app_android.services;

import com.example.app_android.util.MyBroadCastReceiver;
import android.content.Context;
import android.content.Intent;

public class ServiceHelper {
		
	// Private constructor to stop instantiating this class.
	private ServiceHelper(Context context) {
	}
			
	public static int loginStudentportal(Context context, int token, String parameters, MyBroadCastReceiver myBroadCastReceiver) {
		
		final int key = parameters.hashCode();
				
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
