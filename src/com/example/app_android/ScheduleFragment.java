package com.example.app_android;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ScheduleFragment extends ListFragment {
	
	private static final String TAG = "ScheduleFragment";
	private Communicator mListener = null;
	MyScheduleHelperAdapter mMySchemaHelper;

	public interface Communicator {
		public void onListSelection(int index);
	}
	
	public void getRoomLocation(View view) {
    	 
    	System.out.println(getListView().getCount());
    	Toast.makeText(getActivity().getApplicationContext(), view.getParentForAccessibility().toString(), Toast.LENGTH_SHORT).show();
    }
	@Override
	public void onListItemClick(ListView l, View v, int pos, long id) {
		getListView().setItemChecked(pos, true);
		mListener.onListSelection(pos);
	}
		
	@Override
	public void onAttach(Activity activity) {
		Log.i(TAG, getClass().getSimpleName() + ":entered onAttach()");
		super.onAttach(activity);
		
		try {
			mListener = (Communicator) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnArticleSelectedListener");
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
		return super.onCreateView(inflater, container, savedInstanceState);
	}


	@Override
	public void onActivityCreated(Bundle savedState) {
		Log.i(TAG, getClass().getSimpleName() + ":entered onActivityCreated()");
		super.onActivityCreated(savedState);
		mMySchemaHelper = new MyScheduleHelperAdapter(getActivity().getApplicationContext());
		String[] t1 = mMySchemaHelper.readStartTime();
		String[] t2 = mMySchemaHelper.readEndTime();
		String[] t3 = mMySchemaHelper.readMoment();
		String[] t4 = mMySchemaHelper.readRoom();
		
		
		ScheduleCustomAdapter adapter = new ScheduleCustomAdapter(getActivity(), t1, t2, t3, t4);
		//for	(int i = 0; i< 2; i++) {
		//	adapter.addAll(t);
		//}
		getListView().getChildCount();
		setListAdapter(adapter);
		getListView().setChoiceMode(ListView.CHOICE_MODE_NONE);					
	}

	@Override
	public void onStart() {
		Log.i(TAG, getClass().getSimpleName() + ":entered onStart()");
		super.onStart();
	}

	@Override
	public void onResume() {
		Log.i(TAG, getClass().getSimpleName() + ":entered onResume()");
		super.onResume();
	}

	@Override
	public void onPause() {
		Log.i(TAG, getClass().getSimpleName() + ":entered onPause()");
		super.onPause();
	}

	@Override
	public void onStop() {
		Log.i(TAG, getClass().getSimpleName() + ":entered onStop()");
		super.onStop();
	}

	@Override
	public void onDetach() {
		Log.i(TAG, getClass().getSimpleName() + ":entered onDetach()");
		super.onDetach();
	}

	@Override
	public void onDestroyView() {
		Log.i(TAG, getClass().getSimpleName() + ":entered onDestroyView()");
		super.onDestroyView();
	}
}
