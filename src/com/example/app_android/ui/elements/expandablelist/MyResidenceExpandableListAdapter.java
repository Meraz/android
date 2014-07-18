package com.example.app_android.ui.elements.expandablelist;

import java.util.ArrayList;
import android.widget.ExpandableListView.OnChildClickListener;

import com.example.app_android.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class MyResidenceExpandableListAdapter extends MyBaseExpandableListAdapter{

	
	public MyResidenceExpandableListAdapter(Context context, ArrayList<BaseExpandableListGroup> groups) {
		super(context, groups);
	}    	
	
	protected int currentGroup;
	@Override
    public void setAdapter(ExpandableListView expandableList) {
    	super.setAdapter(expandableList);  // Must be runned first
  	
    	// Open all groups
    	int amountOfGroups = getGroupCount();
    	for(int i = 0; i < amountOfGroups; i++)
    		mExpandableList.expandGroup(i);
    	// Start at first item 
    	mExpandableList.smoothScrollToPosition(0);
    }
    
    @Override
	public void onGroupExpanded(int groupPosition) {
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
