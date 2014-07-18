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
    
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
    	BaseExpandableListChild child = (BaseExpandableListChild) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.item_expandablelist_child, null);
	    }	 
        TextView tv = (TextView) view.findViewById(R.id.tvChild);
      
        // HTML        
        tv.setText(Html.fromHtml(child.getName()));
    	
        tv.setTag(child.getTag());
        return view;
    }
}
