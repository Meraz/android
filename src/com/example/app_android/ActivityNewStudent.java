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
	
	private static String[] mNewStudentArray;
	private JSONArray mJsonArray;
	
	private static final String TAG = "NewStudentActivity";
	private String jsonString;
	
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
		intent.putExtra("res", jsonString);
		intent.putExtra("id", index);
		startActivity(intent);	
	}
    
    // AsyncTask for connecting to server and print response in log
    public class connectTask extends AsyncTask<Void, Void, Void> {
    	ProgressDialog mProgressDialog;
    	
    	@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
    	//Connect to server and handle response
		@Override
		protected Void doInBackground(Void... params) {
			URL url;
			String inputLine = "";
			String result = "";
			ArrayList<String> finalResult = new ArrayList<String>();
			try {
				url = new URL("http://194.47.131.73/database-files-and-server-script/Script/newstudent.php");
				
				HttpURLConnection urlCon = (HttpURLConnection)url.openConnection();
				InputStream inStream = urlCon.getInputStream();
				BufferedReader readBuff = new BufferedReader(new InputStreamReader(inStream));
				//Print all result in log
				while((inputLine = readBuff.readLine()) != null) {
					//System.out.println(inputLine);
					result = result + inputLine;
				}			
				jsonString = result;
				mJsonArray = new JSONArray(result);
				//System.out.println(jsonArray);
				for (int i = 0; i < mJsonArray.length(); i++) {
					JSONObject jsonObject = mJsonArray.getJSONObject(i);					
					finalResult.add(jsonObject.getString("header"));					
				}
				mNewStudentArray = (String[]) finalResult.toArray(new String[finalResult.size()]);
				
			} catch (MalformedURLException e) {
				// TODO
				e.printStackTrace();
			} catch (IOException e) {
				// TODO
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO
				e.printStackTrace();
			}			
			return null; // TODO RETURN mNewStudentArray
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}
		
		@Override
		protected void onPostExecute(Void result) {
			
			// TODO Move mJsonArray = new JSONArray(result); to down here
			super.onPostExecute(result);
		}
    }
    
}