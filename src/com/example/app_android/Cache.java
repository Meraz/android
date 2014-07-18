package com.example.app_android;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;


/*How to add data to the cache:
1. Create a data structure to hold the data to be cached(Map is a nice structure :3) 
2. Make a get function for it that returns the data if it is present in the cache or gets it from its source if it's not.
3. Serialize the data in the serializeToFile() function. (Either use objectOutStream.writeObject or make your own serilization function)
4. Deserialize the data in the deSerializeFromFile() function. (Use same order and a corresponding deserialize function as you did in step 3)
*/

//This class fetches data from the web and stores it locally for later use
public class Cache {
	private static Context appContext = null;
	
	//Cached data
	private static HashMap<String, LatLng> googleMapCoordinates = new HashMap<String, LatLng>();
	private static HashMap<String, String> fetchedDataStrings = new HashMap<String, String>(); //Contains all pure string data.
	
	//Settings
	private static int defaultMapLocation = -1;
	
	//Blocking instantiation
	private Cache() {
		
	}
	
	public static void initialize(Context context) {
		appContext = context; //Needed for later use in serialization methods
		deSerializeFromFile();
		if(!googleMapCoordinates.containsKey("HOUSE_A"))	//If it doesn't contain this key it probably doesn't contain the other house marker keys either.
			addMapHouseMarkerCoordinates();
	}
	
	//Checks if the requested data is within the cache and return it if it is. Otherwise, fetch it from the web, cache it and return it.(or null if it couldn't be fetched)
	public static LatLng getMapCoordinate(String dataKey) {
			if(!googleMapCoordinates.containsKey(dataKey)) {
				fetchCityCoordinates();
				fetchRoomCoordinates(dataKey);
				if(!googleMapCoordinates.containsKey(dataKey))
					return null;
			}
			return googleMapCoordinates.get(dataKey);
	}
	
	public static String getMapMarkerSnippet(String dataKey) {
		if(!fetchedDataStrings.containsKey(dataKey)) {
			//TODO fetch from API
			//serializeToFile();
			return "<b>Test Title</b><br>Test snippet line 1<br>Test snippet line 2"; //TODO remove test code

		}
		return fetchedDataStrings.get(dataKey);
	}
	
	public static String getNewStudentData()
	{
		//if(!fetchedDataStrings.containsKey("newStudent")) // TODO
		{
			fetchNewStudentData("newStudent");			
			// serializeToFile();		
		}
		return fetchedDataStrings.get("newStudent");
	}
	
	public static int getDefaultMapLocation() {
		return defaultMapLocation;
	}
	
	public static void setDefaultMapLocation(int newLocation) {
		defaultMapLocation = newLocation;
	}
	
	public static void serializeToFile() {
		FileOutputStream fileOutStream;
		ObjectOutputStream objectOutStream;
		try {
			fileOutStream = appContext.openFileOutput("Cache.binary", Context.MODE_PRIVATE);
			objectOutStream = new ObjectOutputStream(fileOutStream);
			
			//Write more objects to the file here
			serializeSettings(objectOutStream);
			serializeWebResources(objectOutStream);
			
			objectOutStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void serializeWebResources(ObjectOutputStream outputStream) throws IOException {
		serializeCoordinateMap(outputStream);
		//outputStream.writeObject(fetchedDataStrings);
	}
	
	private static void serializeSettings(ObjectOutputStream outputStream) throws IOException {
		outputStream.writeInt(defaultMapLocation);
	}
	

	private static void deSerializeFromFile() {
		try {
			FileInputStream fileInStream = appContext.openFileInput("Cache.binary");
			ObjectInputStream objectInStream = new ObjectInputStream(fileInStream);
			
			//Read more objects from the file here (Make sure it is read in the same order it is written in serializeToFile())
			deSerializeSettings(objectInStream);
			deSerializeWebResources(objectInStream);
			
			objectInStream.close();
		} catch (FileNotFoundException e) {
			serializeToFile();	//If the file doesn't exist. Write a default version of it.
			System.out.println("Cache.binary not found. Writing new cache file");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private static void deSerializeWebResources(ObjectInputStream inputStream) throws OptionalDataException, ClassNotFoundException, IOException {
		deSerializeCoordinateMap(inputStream);
		//fetchedDataStrings = (HashMap<String, String>)inputStream.readObject();	
	}
	
	private static void deSerializeSettings(ObjectInputStream inputStream) throws IOException {
		defaultMapLocation = inputStream.readInt();
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

	// Fetch all data needed to generate menu and more detailed view for new students
	private static void fetchNewStudentData(String key)
	{
		//http://bthapp.bthinnovation.se/?newstudent
		final String adress = "http://bthapp.bthinnovation.se/JsonEditor/?newstudent=5";
			
		String fetchResult = Utilities.fetchDataFromWeb(adress);
		
		// Save value
		fetchedDataStrings.put(key, fetchResult);
	}
	
	private static void addMapHouseMarkerCoordinates() {
		googleMapCoordinates.put("HOUSE_A", new LatLng(56.182016, 15.590502));
		googleMapCoordinates.put("HOUSE_B", new LatLng(56.180673, 15.590691));
		googleMapCoordinates.put("HOUSE_C", new LatLng(56.181237, 15.592322));
		googleMapCoordinates.put("HOUSE_D", new LatLng(56.181670, 15.592375));
		googleMapCoordinates.put("HOUSE_G", new LatLng(56.181891, 15.591308));
		googleMapCoordinates.put("HOUSE_H", new LatLng(56.182348, 15.590819));
		googleMapCoordinates.put("HOUSE_J", new LatLng(56.182933, 15.590401));
		googleMapCoordinates.put("HOUSE_K", new LatLng(56.181816, 15.589894));
		googleMapCoordinates.put("KARLSHAMN_HOUSE_A", new LatLng(56.163626, 14.866623));
		googleMapCoordinates.put("KARLSHAMN_HOUSE_B", new LatLng(56.164464, 14.866012));
	}
}