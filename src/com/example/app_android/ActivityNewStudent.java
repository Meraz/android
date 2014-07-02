package com.example.app_android;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.app_android.InterfaceListSelectionListener;
import com.example.app_android.FragmentNewStudent.connectTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ActivityNewStudent extends Activity implements InterfaceListSelectionListener {
	
	private static String mData = "";
	
	private static final String TAG = "ActivityNewStudent";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
		
        connectTask task = new connectTask();
        task.execute();
        
        setContentView(R.layout.activity_newstudent);        
    }    

    @Override
	protected void onDestroy() {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onDestroy()");
		super.onDestroy();
	}

	@Override
	protected void onPause() {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onPause()");
		super.onPause();
	}

	@Override
	protected void onRestart() {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onRestart()");
		super.onRestart();
	}

	@Override
	protected void onResume() {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onResume()");
		super.onResume();
	}

	@Override
	protected void onStart() {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onStart()");
		super.onStart();
	}

	@Override
	protected void onStop() {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onPause()");
		super.onStop();
	}

    //Listener to handle interaction on the list 
    @Override
	public void onListSelection(int index) {    	
		
    	//Create new activity
		Intent intent = new Intent(getApplicationContext(), ActivityNewStudentContent.class);
		intent.putExtra("res", mData);
		intent.putExtra("id", index);
		startActivity(intent);	
	}
    
    // AsyncTask for connecting to server and print response in log
    public class connectTask extends AsyncTask<Void, Void, String> {
    	//ProgressDialog mProgressDialog; // is this ever used?
    	
    	@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
    	
    	//Connect to server and fetch entire json string
    	@Override
		protected String doInBackground(Void... params) {
			String result = "";
			InputStream inStream = null;
			try {
				URL url = new URL("http://194.47.131.73/database-files-and-server-script/Script/newstudent.php");				
				HttpURLConnection urlCon = (HttpURLConnection)url.openConnection();
				inStream = urlCon.getInputStream();				
			} 
			catch (MalformedURLException e) {
				e.printStackTrace();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}	
			
			// Createreader
			BufferedReader readBuff = new BufferedReader(new InputStreamReader(inStream));
			
			try {
				String inputLine = "";
				while((inputLine = readBuff.readLine()) != null) {
					result = result + inputLine;
				}
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO 
			super.onProgressUpdate(values);
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			mData = result;
		}
    }     
}