package com.example.app_android;

import com.example.app_android.services.TestDatabase;
import com.example.app_android.services.ServiceHelper;
import com.example.app_android.util.MyBroadCastReceiver;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentMain extends ListFragment implements MyBroadCastReceiver.Receiver{

	private static final String TAG = "MainPagesFragment";
	private static final String blekingeStudentUnionPackageName = "se.bthstudent.android.bsk";
	private static String[] mMainMenu;
	private TextView test;
	private MyBroadCastReceiver b = null;
	
	// Interface for communication between fragment and activity
	public interface InterfaceActivityMain {
		void aFunction(); // Test function for fragment to communicate with activity
	}
	
	// This is the method that is invoked when clicking a menuobject on the mainpage.
	@Override
	public void onListItemClick(ListView l, View v, int pos, long id) {
		onListSelection(pos);
	}	
	private void onListSelection(int index) {
    	Logger.VerboseLog(TAG, "::Tapped on index " + index);
    	Intent intent;
    	switch (index) {
        case 0:
          intent = new Intent(getActivity().getApplicationContext(), ActivityNewStudent.class);
          startActivity(intent);
          break;

        case 1:
        	//Start the calendar app
        	Uri uri = Uri.parse("content://com.android.calendar/time");
    		intent = new Intent("android.intent.action.VIEW", uri);		//TODO Check if there is a way to force google calendar to start in week view
    		startActivity(intent);
          break;

        case 2:
          intent = new Intent(getActivity().getApplicationContext(), ActivityMyCoursesAndProgram.class);
          startActivity(intent);
          break;
          
        case 4:
        	int startLocation = Cache.getDefaultMapLocation();
        	if(startLocation >= 0 && startLocation <= 1) {
				intent = new Intent(getActivity().getApplicationContext(), ActivityMap.class);
				intent.putExtra("entryID", 0);
				intent.putExtra("startPositionID", startLocation);
				intent.putExtra("room", "unknown");
				startActivity(intent);
        	}
        	else
        		showDialog(); //Starts the map activity with user input
          break;
          
        case 6:
          launchApp(blekingeStudentUnionPackageName);
          break;

        default:
          break;
      }
	}
    
    private void showDialog() {
    	FragmentManager manager = getFragmentManager();
    	DialogChooseCity dialog = new DialogChooseCity();
    	dialog.show(manager, "chooseCityDialog");
    }
    
    // Attempts to start to app with the inputed packageName. If it doesn't exist it opens the apps market page
    private void launchApp(String packageName) {
    	
    	Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage(packageName);
    	if (intent != null) {
    		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
   		    startActivity(intent);
   		}
   		else  { // If the app isn't installed, send the user to the apps store page
   		    intent = new Intent(Intent.ACTION_VIEW);
  		    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
   		    intent.setData(Uri.parse("market://details?id=" + packageName));
    	    startActivity(intent);
    	}
    }
    	
	// Called when a fragment is first attached to its activity. 
	// onCreate() will be called after this
	@Override
	public void onAttach(Activity activity) {		
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onAttach()");
		super.onAttach(activity);
		
    	// Get resources from stored string array
    	// This can be found in /res/values/strings.xml
        mMainMenu = getResources().getStringArray(R.array.main_page_list);

		try {
			//mListener = (InterfaceListSelectionListener) activity; // TODO
		} 
		catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
		}		
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onCreate()");		
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onCreateView()");
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedState) {		
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onActivityCreated()");
		super.onActivityCreated(savedState);
		
		setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.item_main, mMainMenu));
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
    	test = (TextView) getActivity().findViewById(R.id.textView1);
    	test.setText(TestDatabase.getSomeData());   
	}

	@Override
	public void onStart() {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onStart()");
		
		super.onStart();
	}

	@Override
	public void onResume() {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onResume()");
		if(b == null)
		{
			b = new MyBroadCastReceiver("FRAGMENTMAINUPDATESTART", "FRAGMENTMAINUPDATESDONE");
			b.registerCallback(this);
		}
    	b.registerBroadCastReceiver(getActivity());
    	ServiceHelper.loginStudentportal(getActivity(), 5, "http://194.47.131.73/database-files-and-server-script/Script/serverResponse.php", b);
    	
		super.onResume();
	}

	@Override
	public void onPause() {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onPause()");
		super.onPause();
	}

	@Override
	public void onStop() {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onStop()");
    	b.unregisterBroadCastReceiver(getActivity());
		super.onStop();
	}

	@Override
	public void onDetach() {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onDetach()");
		super.onDetach();
	}

	@Override
	public void onDestroy() {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onDestroy()");
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onDestroyView()");
		super.onDestroyView();
	}

	@Override
	public void onReceiveResult(int resultCode) {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onReceiveResult()");
		Toast.makeText(getActivity(), "Update successful", Toast.LENGTH_SHORT).show(); // TODO Engrish/swenglish
    	test.setText(TestDatabase.getSomeData());
	}
	
	@Override
	public void onServiceStart() {
		Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onServiceStart()");
    	Toast.makeText(getActivity(), "Attempting to update content", Toast.LENGTH_SHORT).show(); // TODO Engrish/swenglish
	}	
}
