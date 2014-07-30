package com.example.app_android.rest;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import android.text.GetChars;

import com.example.app_android.database.DatabaseManager;
import com.example.app_android.database.ITokenTable;
import com.example.app_android.util.Logger;

public class Processes {
	
	private static final String TAG = "Login";

	private Processes() {
	}
	
	public static boolean checkIfLoginIsNeeded() {
		ITokenTable tokenTable = DatabaseManager.getInstance().getTokenTable();
		long experationdate = tokenTable.getExpireDate();
		
		long currentTime = (System.currentTimeMillis()/1000);
		if(experationdate < currentTime) {
			return false;				// No need to login
		}
		
		return true;
	}
	
	public static void requestToken(String username, String password) throws RestCommunicationException { //TODO RETURN ENUM?!
	
		ITokenTable tokenTable = DatabaseManager.getInstance().getTokenTable();
		
		tokenTable.updateTransactionFlag(ITokenTable.TransactionFlag.Pending); // Update to pending transaction flag.  TODO, use enumerations
		
		HttpPost httpPost = null;		
		
		String[] parameterName 	 = new String[4];
		parameterName[0] = "grant_type";
		parameterName[1] = "username";								
		parameterName[2] = "password";						
		parameterName[3] = "client_id";

		String[] parameterValues = new String[4];
		parameterValues[0] = "password";
		parameterValues[1] = username;									
		parameterValues[2] = password;								
		parameterValues[3] = "21EC2020-3AEA-1069-A2DD-08002B30309D";	// TODO hardcoded

		// TODO hardcoded v
		httpPost = preparePostCall("http://194.47.131.73/database-files-and-server-script/Script/serverResponse.php", parameterName, parameterValues);
		
		HttpResponse httpResponse = null;
		httpResponse = RESTFunctions.requestToken(httpPost);		
		
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		if(statusCode != 200) {			
			tokenTable.updateTransactionFlag(-1); 				// Update to fail transaction flag. TODO, use enumerations
			throw new RestCommunicationException("Could not access server. HTTPCODE: " + statusCode);
		}				
		
		String access_token = httpResponse.getFirstHeader("access_token").getValue();				// Get token value from header
		Long expires_in = Long.parseLong(httpResponse.getFirstHeader("expires_in").getValue());	// Get expireDate from header // TODO, convert from whatever to whatever; Needs to decide how this should work.
		if(access_token == null || expires_in == 0) {
			throw new RestCommunicationException("Server returned bad data.");
		}		
		
		expires_in += (System.currentTimeMillis()/1000);					// Add currentTime for expire_time
		tokenTable.PrintEntireToken();
		tokenTable.updateToken(access_token, expires_in, 0);										// updateExistingToken // TODO use enumerations for transaction flag
		tokenTable.PrintEntireToken();
	}
	
	private static HttpPost preparePostCall(String url, final String[] parameterNames, final String[] parameterValues) throws RestCommunicationException {
		
		HttpPost httpPost = new HttpPost(url);
				
		if(parameterNames.length != parameterValues.length)
			throw new RestCommunicationException("Not the same amount of parameters as there is values.");
		
		
		for(int i = 0; i < parameterNames.length; i++) {
			httpPost.addHeader(parameterNames[i], parameterValues[i]);
		}
		return httpPost;
	}

}
