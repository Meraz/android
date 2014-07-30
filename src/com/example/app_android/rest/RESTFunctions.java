package com.example.app_android.rest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.entity.*;

import com.example.app_android.database.DatabaseManager;
import com.example.app_android.database.ITokenTable;

public final class RESTFunctions {

	// Private constructor to avoid instancing of this class. 
	private RESTFunctions() {
		
	}	
	
	/* REST TOKEN
	 * 1. Build message; header, url and if needed some body information
	 * 2. (Send data) Receive result and take appropriate action
	 * 3. (if success) Parse data
	 * 3.1 Extract data from headers. (Token, and expire time.
	 * 4. Save appropriate data in database. Token and expire date
	 * 4.1 Calculate expire data by doing. expires_in += current_time  
	 */
	
	
	public static HttpResponse requestToken(HttpPost httpPost ) throws RestCommunicationException {
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response;
		try {
			response = httpClient.execute(httpPost);
		} catch (ClientProtocolException e) {
			throw new RestCommunicationException(e.getMessage());
		} catch (IOException e) {
			throw new RestCommunicationException(e.getMessage());
		}
		return response;
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
