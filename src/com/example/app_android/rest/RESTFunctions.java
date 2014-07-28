package com.example.app_android.rest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.concurrent.ExecutionException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.os.AsyncTask;

public final class RESTFunctions {

	// Private 
	private RESTFunctions() {
		
	}
	
	// Do a "Get request" according to the REST functionality.
	// returns "MalformedURLException" for the application to handle
	public static String doGetRequest(final String url, final String[] parameterNames, final String[] parameterValues) {
		
		
		
		/* REST TOKEN
		 * 1. Build message; header, url and if needed some body information
		 * 2. (Send data) Receive result and take appropirate action
		 * 3. (if success) Parse data
		 * 3.1 Extract data from headers. (Token, and expire time.
		 * 4. Save appropirate data in database. Token and expire date
		 * 4.1 Calculate expire data by doing. currenttime += expires_in
		 * 

		 */
		
		String fetchResult = "";			
		//fetchResult = readRequest2();
	//	fetchResult = task.get();

		
		return fetchResult;
	}
	
	private JSONObject convertHeadersToJSON(Header[] all_headers) {
		return new JSONObject();
	}
	
	public String readRequest2(String request) {
		StringBuilder stringBuilder = new StringBuilder();
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(request);

		try {
			HttpResponse response = httpClient.execute(httpPost);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			
			Header[] all_headers = response.getAllHeaders();
			
			if(statusCode == 200) {				
				HttpEntity httpEntity = response.getEntity();
				InputStream content = httpEntity.getContent();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(content));
				String line;
				while((line = bufferedReader.readLine()) != null) {
					stringBuilder.append(line);
				}
			} else {
				//					Log.e(RoomActivity.class.toString(), "Failed to download file!");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}
	
	public String readRequest(String request) {
		StringBuilder stringBuilder = new StringBuilder();
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(request);
	
		try {
			HttpResponse response = httpClient.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if(statusCode == 200) {
				HttpEntity httpEntity = response.getEntity();
				InputStream content = httpEntity.getContent();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(content));
				String line;
				while((line = bufferedReader.readLine()) != null) {
					stringBuilder.append(line);
				}
			} else {
				//					Log.e(RoomActivity.class.toString(), "Failed to download file!");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}
}
