package com.example.app_android.ui;

import com.example.app_android.R;
import com.example.app_android.util.Utilities;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FragmentCourses extends ListFragment {
	
	private static final String TAG = "FragmentCourses";

	@Override
	public void onListItemClick(ListView l, View v, int pos, long id) {
		Intent intent = new Intent(getActivity().getApplicationContext(), ActivityDetailedCourse.class);
		intent.putExtra("courseCode", ((TextView)((RelativeLayout)getListView().getChildAt(pos)).getChildAt(0)).getText());	//Find the right courseCode in the course list and input it
		startActivity(intent);
	}

	@Override
	public void onAttach(Activity activity) {
		Utilities.VerboseLog(TAG, getClass().getSimpleName() + ":entered onAttach()");
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Utilities.VerboseLog(TAG, getClass().getSimpleName() + ":entered onCreate()");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		Utilities.VerboseLog(TAG, getClass().getSimpleName() + ":entered onCreate()");
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedState) {
		Utilities.VerboseLog(TAG, getClass().getSimpleName() + ":entered onActivityCreated()");
		super.onActivityCreated(savedState);
		setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.item_course, R.id.course_button, ActivityCourses.favouriteCoursesArray));
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	}

	@Override
	public void onStart() {
		Utilities.VerboseLog(TAG, getClass().getSimpleName() + ":entered onStart()");
		setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.item_course, R.id.course_button, ActivityCourses.favouriteCoursesArray));
		super.onStart();
	}

	@Override
	public void onResume() {
		Utilities.VerboseLog(TAG, getClass().getSimpleName() + ":entered onResume()");
		super.onResume();
	}

	@Override
	public void onPause() {
		Utilities.VerboseLog(TAG, getClass().getSimpleName() + ":entered onPause()");
		super.onPause();
	}

	@Override
	public void onStop() {
		Utilities.VerboseLog(TAG, getClass().getSimpleName() + ":entered onStop()");
		super.onStop();
	}

	@Override
	public void onDetach() {
		Utilities.VerboseLog(TAG, getClass().getSimpleName() + ":entered onDetach()");
		super.onDetach();
	}

	@Override
	public void onDestroy() {
		Utilities.VerboseLog(TAG, getClass().getSimpleName() + ":entered onDestroy()");
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		Utilities.VerboseLog(TAG, getClass().getSimpleName() + ":entered onDestroyView()");
		super.onDestroyView();
	}
}