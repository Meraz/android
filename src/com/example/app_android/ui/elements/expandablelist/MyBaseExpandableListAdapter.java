package com.example.app_android.ui.elements.expandablelist;
 
import java.util.ArrayList;
import com.example.app_android.R;
 
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ExpandableListView;
 
public class MyBaseExpandableListAdapter extends BaseExpandableListAdapter {
 
    protected Context mContext;
    protected ArrayList<BaseExpandableListGroup> mGroups;
    protected ExpandableListView mExpandableList;
    
    public MyBaseExpandableListAdapter(Context context, ArrayList<BaseExpandableListGroup> groups) {
    	mContext = context;
    	mGroups = groups;
    }
    
    @Override
	public void onGroupExpanded(int groupPosition) {
    	// Scroll down to the new item
		mExpandableList.smoothScrollToPosition(groupPosition);
    	super.onGroupExpanded(groupPosition);
    }
        
    // setAdpater from adapter to let the adapter have access to the list view
    // Should maybe be called something more in the style of
    // Send list to adapter so list can set the adapter in itself to access it's functions 
    // and then save the list in the adapter so it can access list functionality
    // NEEDED TO DO THIS FOR SELF CLOSING GROUPS
    public void setAdapter(ExpandableListView expandableList) {
    	mExpandableList = expandableList;
    	mExpandableList.setAdapter(this);
    }
        
    public void addItem(BaseExpandableListChild item, BaseExpandableListGroup group) {
        if (!mGroups.contains(group)) {
        	mGroups.add(group);
        }
        int index = mGroups.indexOf(group);
        ArrayList<BaseExpandableListChild> ch = mGroups.get(index).getItems();
        ch.add(item);
        mGroups.get(index).appendItems(ch);
    }
    
    public Object getChild(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        ArrayList<BaseExpandableListChild> chList = mGroups.get(groupPosition).getItems();
        return chList.get(childPosition);
    }
 
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }
 
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
    	BaseExpandableListChild child = (BaseExpandableListChild) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.item_expandablelist_child, null);
	    }	 
        TextView tv = (TextView) view.findViewById(R.id.tvChild);
        tv.setText(child.getName().toString());
        tv.setTag(child.getTag());
        return view;
    }
 
    public int getChildrenCount(int groupPosition) {
        ArrayList<BaseExpandableListChild> chList = mGroups.get(groupPosition).getItems();
        return chList.size();
    }
 
    public Object getGroup(int groupPosition) {
        return mGroups.get(groupPosition);
    }
 
    public int getGroupCount() {
        return mGroups.size();
    }
 
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
 
    public View getGroupView(int groupPosition, boolean isLastChild, View view, ViewGroup parent) {
    	BaseExpandableListGroup group = (BaseExpandableListGroup) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.item_expandablelist_group, null);
        }
        TextView tv = (TextView) view.findViewById(R.id.tvGroup);
        tv.setText(group.getName().toString());
        return view;
    }
 
    public boolean hasStableIds() {
        return true; // TODO ??
    }
 
    public boolean isChildSelectable(int arg0, int arg1) {
        return true; // TODO ??
    }
 
}