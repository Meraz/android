package com.example.app_android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.IntentService;
import android.content.Intent;

public class ServiceSchemaUpdate extends IntentService {

	AdapterScheduleHelper mMySchemaHelper;
	
	public ServiceSchemaUpdate() {
		super("SchemaUpdateService");
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unused")//Supressed until this function actually does something
	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		mMySchemaHelper = new AdapterScheduleHelper(getApplicationContext());
		mMySchemaHelper.resetData();
		//String inputLine;
		String urlString = intent.getStringExtra("URL");
		try {
			URL url = new URL(urlString);
			HttpURLConnection urlCon = (HttpURLConnection)url.openConnection();
			InputStream inStream = urlCon.getInputStream();
			BufferedReader readBuff = new BufferedReader(new InputStreamReader(inStream));
			//DEBUG CODE
			//int count = 0;
			//Print all result in log
			//while((inputLine = readBuff.readLine()) != null) {
				//System.out.println(inputLine);
				//if(count > 6) {
				//	String[] tokens = inputLine.split(",");
				//	long id = mMySchemaHelper.insertData(tokens);
				//}
				//count++;
			//}			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
