package com.example.app_android;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;


public final class RESTFunctions {

	// Private 
	private RESTFunctions() {
		
	}
	
	public static String doSendRequest(final String adress, final String message) {
		
		
		return new String("hej");
	}
	
	/*
	// Do a "Get request" according to the REST functionality.
	// returns "MalformedURLException" for the application to handle
	public static String doGetRequest(final String adress, final String message) throws MalformedURLException {
		
		URL url = new URL(adress);		
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		
		try {
			conn = (HttpURLConnection) url.openConnection();
			//conn.setRequestMethod("GET");
			//conn.setRequestProperty("Accept", "application/json");
			//conn.setRequestMethod(message);
			
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} catch (IOException e) {
			// TODO
			e.printStackTrace();
		}
				  
		String line;
		String result = "";			 
        try {
			while ((line = reader.readLine()) != null) {
			   result += line;
			}
			reader.close();
		} catch (IOException e) {
			// TODO
			e.printStackTrace();
		}
		
		return result;
	}
	*/
	
	// Do a "Get request" according to the REST functionality.
	// returns "MalformedURLException" for the application to handle
		public static String doGetRequest(final String adress, final String message) {
			
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
			protected void onPreExecute() {
				// TODO 
				super.onPreExecute();
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
			
			public String readRequest2(String request) {
				StringBuilder stringBuilder = new StringBuilder();
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(request);

				try {
					HttpResponse response = httpClient.execute(httpPost);
					StatusLine statusLine = response.getStatusLine();
					int statusCode = statusLine.getStatusCode();
					
					Header[] all = response.getAllHeaders();
					
					
					Header access_token = response.getFirstHeader("access_token");
					Header token_type = response.getFirstHeader("token_type");
					Header expires_in = response.getFirstHeader("expires_in");
					String a = expires_in.toString();
					
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
			
			
			@Override
			protected String doInBackground(String... params) {				
				String httpResponse = readRequest2("http://194.47.131.73/database-files-and-server-script/Script/serverResponse.php");
				return httpResponse;
				//   return new String("hej");
			}
				
			protected void onPostExecute(String result) {
				// TODO
				super.onPostExecute(result);
			}		
		}
	
	public static String doPutRequest(final String adress, final String message)	{
		
		return new String("hej");
	}	
	
}
