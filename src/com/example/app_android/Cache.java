package com.example.app_android;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;


//This class fetches data from the web and stores it locally for later use
public class Cache {
	private static HashMap<DataCategories, Map<String, Object>> categoryMap = new HashMap<DataCategories, Map<String, Object>>();
	private static HashMap<String, Object> googleMapCoordinates = new HashMap<String, Object>();
	
	public static enum DataCategories{
		CITYCOORDINATES,
		ROOMCOORDINATES;
	}
	
	public static void Initialize() {
		FillCategoryMap();
	}
	
	//Adds all data maps to the main 
	private static void FillCategoryMap() {
		categoryMap.put(DataCategories.CITYCOORDINATES, googleMapCoordinates);
		categoryMap.put(DataCategories.ROOMCOORDINATES, googleMapCoordinates);
	}
	
	public static void GetAllWebResources() {
		GetCityCoordinates();
	}
	
	//Checks if the requested data is within the cache and return it if it is. Otherwise, fetch it from the web, cache it and return it.
	public static Object GetData(DataCategories category, String dataKey) {
		Object toReturn = null;
		
		switch (category) {
		case CITYCOORDINATES:
			if(!categoryMap.get(category).containsKey(dataKey)) {
				GetCityCoordinates();
			}
			toReturn = categoryMap.get(category).get(dataKey);
			break;
			
		case ROOMCOORDINATES:
			if(!categoryMap.get(category).containsKey(dataKey)) {
				GetRoomCoordinates(dataKey);
			}
			toReturn = categoryMap.get(category).get(dataKey);
			break;
		}
		return toReturn;
	}
	
	//Gets all city adresses from the database and puts them in the correct map for later use
	private static void GetCityCoordinates() {
		final String adress = "http://194.47.131.73/database-files-and-server-script/Script/getCity.php";
		String fetchResult = "";
		fetchResult = Utilities.GetDataFromWeb(adress);	//Gets the raw data from the database asynchronously
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
	private static void GetRoomCoordinates(String room) {
		final String adress = "http://194.47.131.73/database-files-and-server-script/Script/getRoom.php?p=" + room;
		String fetchResult = "";
		fetchResult = Utilities.GetDataFromWeb(adress);
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