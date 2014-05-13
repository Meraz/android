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
	private String room;
	public static String[] mArray;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("cityId");
        room = bundle.getString("Room");
        if(id == 0) {
        	place = new LatLng(56.182242, 15.590712);
        } else if (id == 1){
        	place = new LatLng(56.164384, 14.866024);
        }
        
        if(room.equals("J1610")) {
        	place = new LatLng(56.183006, 15.590559);
        } 
        else if (room.equals("C230")){
        	place = new LatLng(56.181478, 15.592322);
        }
        connectDB con = new connectDB();
        con.execute();
        setContentView(R.layout.map_layout_full);
        initilizeMap();
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
	
	public class connectDB extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			URL url;
			String inputLine = "";
			String result = "";
			ArrayList<String> finalResult = new ArrayList<String>();
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
				JSONArray jsonArray = new JSONArray(result);
				//System.out.println(jsonArray);
				for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);	
					System.out.println(jsonArray.getJSONObject(i));
				}
				mArray = (String[]) finalResult.toArray(new String[finalResult.size()]);
				System.out.println(jsonArray);
				
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
			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
		
	}
	
}
