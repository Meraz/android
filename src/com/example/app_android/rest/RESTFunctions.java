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
	public static int requestToken(final String url, final String[] parameterNames, final String[] parameterValues) {
		
		String uri = "http://194.47.131.73/database-files-and-server-script/Script/serverResponse.php";
		HttpPost httpPost = new HttpPost(uri);
		
		String grant_type = new String("password");
		String username = new String("npr");
		String password = new String("secret_password");
		String client_id = new String("21EC2020-3AEA-1069-A2DD-08002B30309D");
		
		httpPost.addHeader("grant_type", grant_type);
		httpPost.addHeader("username", username);
		httpPost.addHeader("password", password);
		httpPost.addHeader("client_id", client_id);
		
		ITokenTable tokenTable = DatabaseManager.getInstance().getTokenTable();
		tokenTable.updateTransactionFlag(1); // Update to pending transaction flag.  TODO, use enumerations
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response;
		try {
			response = httpClient.execute(httpPost);	// Execute 
			
		} catch (ClientProtocolException e) {
			tokenTable.updateTransactionFlag(-1); // Update to fail transaction flag. TODO, use enumerations
			e.printStackTrace();
			return -1;
		} catch (IOException e) {
			tokenTable.updateTransactionFlag(-1); // Update to fail transaction flag. TODO, use enumerations
			e.printStackTrace();
			return -1;
		}
		StatusLine statusLine = response.getStatusLine();		// Get status line
		int statusCode = statusLine.getStatusCode();			// Get http statuscode, 200 OK
		
		if(statusCode == 200) {
			String access_token = response.getFirstHeader("access_token").getValue();				// Get token value from header
			int expires_in = Integer.parseInt(response.getFirstHeader("expires_in").getValue());	// Get expireDate from header // TODO, convert from whatever to whatever; Needs to decide how this should work.
			expires_in += getCurrentTime();															// Add currentTime for expire_time
			tokenTable.updateToken(access_token, expires_in, 0);									// updateExistingToken // TODO use enumerations for transaction flag
		}
		else {
			tokenTable.updateTransactionFlag(-1); // Update to fail transaction flag. TODO, use enumerations
		}
		
		return 1; // TODO, create success/fail system
	}
	
	private static int getCurrentTime() { 
		return 0;	// TODO, return something of interest
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
