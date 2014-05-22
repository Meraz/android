package com.example.app_android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Tile;




import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class FullMapActivity extends Activity{
	
	private GoogleMap mMap;
	private static final String TAG = "FullMapActivity";
	private LatLng place; 
	private LatLng roomMarker;
	private int id;
	private String city;
	private String room;
	private static JSONArray mJsonArray;
	private static String[] mArray;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("cityId");
        room = bundle.getString("Room");
        connectDB con = new connectDB();
        if(id == 0) {
        	city = "Karlskrona";
        	con.execute(city);
        	try {
				mJsonArray = con.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } else if (id == 1){
        	city = "Karlshamn";
        	con.execute(city);
        	try {
				mJsonArray = con.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        //Parse JSON to LatLng coordinates
        try {
			parseJsonToLanLng();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        if(room.equals("J1610")) {
        	place = new LatLng(56.183006, 15.590559);
        } 
        else if (room.equals("C230")){
        	place = new LatLng(56.181478, 15.592322);
        }
        
        
        setContentView(R.layout.map_layout_full);
        initilizeMap();
        //Add marker if id is -1 (comes from schema)
        if(id == -1) {
        	mMap.addMarker(new MarkerOptions()
            				.position(place)
            				.title(room));
        }
       
        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(place, 17.0f));
    }
	

    @Override
	protected void onDestroy() {
		Log.i(TAG, getClass().getSimpleName() + ":entered onDestroy()");
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		Log.i(TAG, getClass().getSimpleName() + ":entered onPause()");
		super.onPause();
	}

	@Override
	protected void onRestart() {
		Log.i(TAG, getClass().getSimpleName() + ":entered onRestart()");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		Log.i(TAG, getClass().getSimpleName() + ":entered onResume()");
		super.onResume();
		initilizeMap();
	}

	@Override
	protected void onStart() {
		Log.i(TAG, getClass().getSimpleName() + ":entered onStart()");
		super.onStart();
	}

	@Override
	protected void onStop() {
		Log.i(TAG, getClass().getSimpleName() + ":entered onStop()");
		super.onStop();
	}

	
	private void initilizeMap() {
        if (mMap == null) {
            mMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
 
            // check if map is created successfully or not
            if (mMap == null) {
                Toast.makeText(getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
            }
        }
    }
	
	private void parseJsonToLanLng() throws JSONException {
		for(int i = 0; i < mJsonArray.length(); i++) {
			JSONObject jsonObj = mJsonArray.getJSONObject(i);
			if(jsonObj.getString("cityName").equals(city)) {
				place = new LatLng(jsonObj.getDouble("lat"), jsonObj.getDouble("lng"));
				System.out.println(place);
			}
		}
	}
	
	public class connectDB extends AsyncTask<String, Void, JSONArray> {

		@Override
		protected JSONArray doInBackground(String... params) {
			URL url;
			String inputLine = "";
			String result = "";
			JSONArray jsonArray = null;
			try {
				url = new URL("http://www.student.bth.se/~pael10/BTHApp/getCity.php");
				
				HttpURLConnection urlCon = (HttpURLConnection)url.openConnection();
				InputStream inStream = urlCon.getInputStream();
				BufferedReader readBuff = new BufferedReader(new InputStreamReader(inStream));
				//Print all result in log
				while((inputLine = readBuff.readLine()) != null) {
					//System.out.println(inputLine);
					result = result + inputLine;
				}			
				jsonArray = new JSONArray(result);	
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			return jsonArray;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(JSONArray result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
		
	}
	
}
