/*
 * This class was first built for the possible future 
 * where Expandablelists could hold different items and have 
 * completely  different functionality from eachother. 
 * If this text still exist in the future this class could probably be removed safely.
 * 
 * Rasmus Tilljander - tilljander.rasmus@gmail.com 
 */



package com.example.app_android.ui.elements.expandablelist;

import java.util.ArrayList;

import com.example.app_android.R;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class MyResidenceExpandableListAdapter extends MyBaseExpandableListAdapter{
	
	public MyResidenceExpandableListAdapter(Context context, ArrayList<BaseExpandableListGroup> groups) {
		super(context, groups);
	}    	
	
	@Override
    public void setAdapter(ExpandableListView expandableList) {
    	super.setAdapter(expandableList);
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
