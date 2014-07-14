package com.example.app_android;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentResidence extends Fragment{

	public interface ResidenceListener {
			
	}	
	private static final String TAG = "FragmentResidence";
	private String mData;
	private ResidenceListener mActivity;

	@Override
	public void onAttach(Activity activity) {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onAttach()");
		super.onAttach(activity); 
		
		mData = "Karlskronahem AB \n Karlskronahem har det största antalet lägenheter i Karlskrona. " +
				"Företaget ägs av Karlskrona kommun och förvaltar cirka 3.900 hyreslägenheter." +
				"www.karlskronahem.se" +
				"\n\n Blekinge Bostadsportal" +
				"Bostadsportalen är till för dig som är student vid Blekinge Tekniska Högskola. " +
				"Här kan du hitta generell information om vad som gäller när du ska söka bostad i Blekinge." +
				"www.bostad.bthstudent.se" +
				"\n\n Krebo" +
				"Krebo bygger bostadsområden som ska ge trygghet, känsla av tillhörighet och underlätta för god gemenskap." +
				"Krebo har en huvudinriktning på studentbostäder. www.krebo.se" +
				"\n \n Heimstaden" + 
				"Heimstaden har ett 10-tal hyresfastigheter i Karlskrona. www.heimstaden.com";
        
        // Try to cast activity to the listener interface
		try {
			mActivity = (ResidenceListener) activity;
		} 
		// Throw an exception if failed
		catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement listener interface defined in " + getClass().getSimpleName());
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
    	
		View view = inflater.inflate(R.layout.item_newstudent, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedState) {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onActivityCreated()");
		super.onActivityCreated(savedState);
		
		TextView view = (TextView) getActivity().findViewById(R.id.detailsText);
		view.setText(mData);
	}
	
	@Override
	public void onStart() {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onStart()");
		super.onStart();
	}

	@Override
	public void onResume() {
    	Logger.VerboseLog(TAG, getClass().getSimpleName() + ":entered onResume()");
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
}
