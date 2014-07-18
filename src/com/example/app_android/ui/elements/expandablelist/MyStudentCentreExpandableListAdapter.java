package com.example.app_android.ui.elements.expandablelist;

import java.util.ArrayList;

import com.example.app_android.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class MyStudentCentreExpandableListAdapter extends MyBaseExpandableListAdapter{

    private int mLastExpandedGroup;
	
	public MyStudentCentreExpandableListAdapter(Context context, ArrayList<BaseExpandableListGroup> groups) {
		super(context, groups);
	}    	

	@Override
    public void setAdapter(ExpandableListView expandableList) {
    	super.setAdapter(expandableList);  // Must be runned first
    	
    	// Have first group expanded when opening this
    	mExpandableList.expandGroup(0);
    	mLastExpandedGroup = 0;
    }
    
    @Override
	public void onGroupExpanded(int groupPosition) {
    	if(groupPosition != mLastExpandedGroup)
    		mExpandableList.collapseGroup(mLastExpandedGroup);
    	mLastExpandedGroup = groupPosition;
    	
		mExpandableList.smoothScrollToPosition(groupPosition);
    	super.onGroupExpanded(groupPosition);
    }	
    
    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View view, ViewGroup parent) {
    	return super.getGroupView(groupPosition, isLastChild, view, parent);
    }
    
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
    	return super.getChildView(groupPosition, childPosition, isLastChild, view, parent);
    }
}
