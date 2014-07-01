package com.example.app_android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;

public class Utilities {
	//Fetches string data from the specified address asynchronously (Although blocking on get)
	public static String fetchDataFromWeb(final String adress) {
		String fetchResult = "";
		
    	DataFromWeb task = new DataFromWeb();
    	task.execute(adress);
		try {
			fetchResult = task.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		return fetchResult;
	}
	
	private static class DataFromWeb extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			URL url;
			String inputLine = "";
			String result = "";
			
			try {
				url = new URL(params[0]);	//Create URL from the inputed address
				
				HttpURLConnection urlCon = (HttpURLConnection)url.openConnection();
				InputStream inStream = urlCon.getInputStream();
				BufferedReader readBuff = new BufferedReader(new InputStreamReader(inStream));
				while((inputLine = readBuff.readLine()) != null) {
					result = result + inputLine;
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				result = "";		//Reset the result string in case something has already been written to it before this happens
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
		
	}
}
