package com.example.app_android;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;


//This class fetches data from the web and stores it locally for later use
public class Cache {
	private static HashMap<String, LatLng> googleMapCoordinates = new HashMap<String, LatLng>();
	private static Context appContext = null;
	
	//Blocking instantiation
	private Cache() {
		
	}
	
	public static void initialize(Context context) {
		appContext = context; //Needed for later use in serialization methods
		deSerializeFromFile();
	}
	
	public static void fetchAllWebResources() {
		fetchCityCoordinates();
	}
	
	//Checks if the requested data is within the cache and return it if it is. Otherwise, fetch it from the web, cache it and return it.
	public static LatLng getMapCoordinate(String dataKey) {
			if(!googleMapCoordinates.containsKey(dataKey)) {	//TODO: check if the web script can be made to get rooms and cities from the same script
				fetchCityCoordinates();
				fetchRoomCoordinates(dataKey);
				serializeToFile();
			}
			return googleMapCoordinates.get(dataKey);
	}
	
	private static void serializeToFile() { //TODO test this and finish the serialization functionality
		FileOutputStream fileOutStream;
		ObjectOutputStream objectOutStream;
		try {
			fileOutStream = appContext.openFileOutput("Cache.binary", Context.MODE_PRIVATE);
			objectOutStream = new ObjectOutputStream(fileOutStream);
			serializeCoordinateMap(objectOutStream);
			objectOutStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void deSerializeFromFile() {
		try {
			FileInputStream fileInStream = appContext.openFileInput("Cache.binary");
			ObjectInputStream objectInStream = new ObjectInputStream(fileInStream);
			deSerializeCoordinateMap(objectInStream);
			objectInStream.close();
		} catch (FileNotFoundException e) {
			serializeToFile();
			System.out.println("Cache.binary not found. Writing new cache file");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Manually serializes the coordinate map
	private static void serializeCoordinateMap(ObjectOutputStream objectOutStream) throws IOException {
		objectOutStream.writeInt(googleMapCoordinates.size());
		Iterator<Entry<String, LatLng>> it = googleMapCoordinates.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<String, LatLng> pair = (Map.Entry<String, LatLng>)it.next();
				objectOutStream.writeUTF(pair.getKey());
				objectOutStream.writeDouble(pair.getValue().latitude);
				objectOutStream.writeDouble(pair.getValue().longitude);
		}
	}
	
	//Manually deserializes the coordinate map
	private static void deSerializeCoordinateMap(ObjectInputStream objectInStream) throws IOException {
		int coordinateCount = objectInStream.readInt();
		for(int i = 0; i < coordinateCount; ++i ) {
			String key = objectInStream.readUTF();
			double latitude = objectInStream.readDouble();
			double longitude = objectInStream.readDouble();
			googleMapCoordinates.put(key, new LatLng(latitude, longitude));
		}
	}
	
	//Gets all city adresses from the database and puts them in the correct map for later use
	private static void fetchCityCoordinates() {
		final String adress = "http://194.47.131.73/database-files-and-server-script/Script/getCity.php";
		String fetchResult = "";
		fetchResult = Utilities.fetchDataFromWeb(adress);	//Gets the raw data from the database asynchronously
		JSONArray jsonArray = null;
		try {
			jsonArray = new JSONArray(fetchResult);
			if(jsonArray != null) { 
				//Convert the string inte a JSONArray and use it to get the data contained within the string
				LatLng coordinate;
				for(int i = 0; i < jsonArray.length(); ++i) {
					JSONObject jsonObj = jsonArray.getJSONObject(i);
					coordinate = new LatLng(jsonObj.getDouble("lat"), jsonObj.getDouble("lng"));
					googleMapCoordinates.put(jsonObj.getString("cityName"), coordinate); //Store the fetched data in the hashmap so we don't have to call this function again
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	//Gets the room with the inputed room number and puts it in the correct map for later use
	private static void fetchRoomCoordinates(String room) {
		final String adress = "http://194.47.131.73/database-files-and-server-script/Script/getRoom.php?p=" + room;
		String fetchResult = "";
		fetchResult = Utilities.fetchDataFromWeb(adress);
		JSONArray jsonArray = null;
		try{
			jsonArray = new JSONArray(fetchResult);
			if(jsonArray != null){
				LatLng coordinate;
				for(int i = 0; i < jsonArray.length(); ++i) {
					JSONObject jsonObj = jsonArray.getJSONObject(i);
					coordinate = new LatLng(jsonObj.getDouble("lat"), jsonObj.getDouble("lng"));
					googleMapCoordinates.put(jsonObj.getString("roomNr"), coordinate);
				}
			}
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
	}
}