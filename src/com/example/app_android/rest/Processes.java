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

import com.example.app_android.database.DBException;
import com.example.app_android.database.DatabaseManager;
import com.example.app_android.database.NoRowsAffectedDBException;
import com.example.app_android.database.interfaces.ITokenTable;
import com.example.app_android.database.interfaces.ITokenTable.TransactionFlag;
import com.example.app_android.util.Utilities;

public class Processes {
	
	private static final String TAG = "Login";

	private Processes() {
	}
	
	public enum LoginStatus {
		Waiting,
		LoginRequired,
		AlreadyLoggedIn
	}
	
	public static LoginStatus checkIfLoginIsNeeded() {
		Utilities.VerboseLog(TAG, "Processes::entered checkIfLoginIsNeeded()");
		
		ITokenTable tokenTable = DatabaseManager.getInstance().getTokenTable();
		
		// Get current transaction flag for the token
		TransactionFlag transactionFlag = tokenTable.getTransactionFlag();
		
		// Already in the proccess of logging in.
		if(transactionFlag == TransactionFlag.Pending) {
			return LoginStatus.Waiting;
		}
		
		long experationdate = tokenTable.getExpireDate();
		
		long currentTime = (System.currentTimeMillis()/1000);
		if(experationdate < currentTime) {
			return LoginStatus.LoginRequired;					// LoginRequired!
		}		
		//return LoginStatus.LoginRequired; // It's always needed to login. Debug mode
		return LoginStatus.AlreadyLoggedIn; // No need to login
	}
	
	public static void requestToken(String username, String password) throws RestCommunicationException {
		try {
			requestTokenFromServer(username, password);
		}
		catch(RestCommunicationException e) {
			
			// Attempt to update database transaction flag.
			ITokenTable tokenTable = DatabaseManager.getInstance().getTokenTable();
			
			// This might throw some unexpected Exceptions
			try {
				tokenTable.updateTransactionFlag(ITokenTable.TransactionFlag.Failed);
			} catch (DBException e2) {
				// TODO
				e.printStackTrace();
			} catch (NoRowsAffectedDBException e1) {
				// TODO
				e1.printStackTrace();
			}
			// Rethrow the error upwards
			throw e;
		}
	}
	
	private static void requestTokenFromServer(String username, String password) throws RestCommunicationException {
		Utilities.VerboseLog(TAG, "Processes::entered requestToken()");
		
		ITokenTable tokenTable = DatabaseManager.getInstance().getTokenTable();
		
		// Update to pending transaction flag.  
		try {
			tokenTable.updateTransactionFlag(ITokenTable.TransactionFlag.Pending);
		} catch (DBException e1) {
			// TODO
			e1.printStackTrace();
		} catch (NoRowsAffectedDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
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
		httpResponse = RESTFunctions.requestToken(httpPost); 			// Actual call to server
		
		int statusCode = httpResponse.getStatusLine().getStatusCode();	// Get statuscode
		if(statusCode != 200) {			
			throw new RestCommunicationException("Somewhere in the wast void between your click and the server respsone we found an error. HTTPCODE: " + statusCode);
		}				
		
		// Get token value from header	
		String access_token;	
		Header header = httpResponse.getFirstHeader("access_token");
		if(header == null) {
				throw new RestCommunicationException("Server returned bad data.");
		} else {
			access_token = header.getValue();
		}
		
		// Get expireDate from header
		Long expires_in;
		header = httpResponse.getFirstHeader("expires_in");		
		if(header == null) {
			throw new RestCommunicationException("Server returned bad data.");
		} else {
			expires_in = Long.parseLong(header.getValue());
		}	
		
		expires_in += (System.currentTimeMillis()/1000); // Add currentTime for expire_time
		tokenTable.PrintEntireToken();
		
		try {
			tokenTable.updateToken(access_token, expires_in, ITokenTable.TransactionFlag.Success);
		} catch (DBException e) {
			// TODO 
			e.printStackTrace();
		} catch (NoRowsAffectedDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tokenTable.PrintEntireToken();
	}
	
	private static HttpPost preparePostCall(String url, final String[] parameterNames, final String[] parameterValues) throws RestCommunicationException {
		Utilities.VerboseLog(TAG, "Processes::entered requestToken()");
		
		if(parameterNames.length != parameterValues.length)
			throw new RestCommunicationException("Not the same amount of parameters as there is values.");
		
		HttpPost httpPost = new HttpPost(url);
		
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();

		int items = parameterNames.length;
		for(int i = 0; i < items; i++) {
			parameters.add(new BasicNameValuePair(parameterNames[i],parameterValues[i]));
		}
		
		try {
			HttpEntity httpEntity = new UrlEncodedFormEntity(parameters); // Middle step for debugging purposes
			httpPost.setEntity(httpEntity);

		} catch (UnsupportedEncodingException e) {
			throw new RestCommunicationException("UnsupportedEncodingException. " + e.getMessage());
		}
		return httpPost;
	}
}
