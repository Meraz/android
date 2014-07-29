package com.example.app_android.rest;

import org.apache.http.HttpResponse;

import org.apache.http.client.methods.HttpPost;

import com.example.app_android.database.DatabaseManager;
import com.example.app_android.database.ITokenTable;

public class Processes {

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
	
	public static void requestToken() throws RestCommunicationException { //TODO RETURN ENUM?!
	
		ITokenTable tokenTable = DatabaseManager.getInstance().getTokenTable();
		
		tokenTable.updateTransactionFlag(1); // Update to pending transaction flag.  TODO, use enumerations
		
		HttpPost httpPost = null;
		httpPost = preparePostCall("http://194.47.131.73/database-files-and-server-script/Script/serverResponse.php", null, null);
		
		HttpResponse httpResponse = null;
		httpResponse = RESTFunctions.requestToken(httpPost);		
		
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		if(statusCode != 200) {			
			tokenTable.updateTransactionFlag(-1); 				// Update to fail transaction flag. TODO, use enumerations
			throw new RestCommunicationException("Could not access server, error message: " + statusCode);
		}				
		
		String access_token = httpResponse.getFirstHeader("access_token").getValue();				// Get token value from header
		int expires_in = Integer.parseInt(httpResponse.getFirstHeader("expires_in").getValue());	// Get expireDate from header // TODO, convert from whatever to whatever; Needs to decide how this should work.
		if(access_token == null || expires_in == 0) {
			throw new RestCommunicationException("Server returned bad data.");
		}		
		
		//	expires_in += getCurrentTime();																// Add currentTime for expire_time

		tokenTable.updateToken(access_token, expires_in, 0);										// updateExistingToken // TODO use enumerations for transaction flag
	}
	
	private static HttpPost preparePostCall(String url, final String[] parameterNames, final String[] parameterValues) throws RestCommunicationException {
		
		HttpPost httpPost = new HttpPost(url);
		
		/*
		if(parameterNames.length != parameterValues.length)
			throw new RestCommunicationException("Not the same amount of parameters as there is values");
		
		
		for(int i = 0; i < parameterNames.length; i++) {
			httpPost.addHeader(parameterNames[i], parameterValues[i]);
		}
		*/
		
		 // TODO. IT'S HARDCODED ATM
		
		String grant_type = new String("password");
		String username = new String("npr");
		String password = new String("secret_password");
		String client_id = new String("21EC2020-3AEA-1069-A2DD-08002B30309D");
		
		httpPost.addHeader("grant_type", grant_type);
		httpPost.addHeader("username", username);
		httpPost.addHeader("password", password);
		httpPost.addHeader("client_id", client_id);
		return httpPost;
	}

}
