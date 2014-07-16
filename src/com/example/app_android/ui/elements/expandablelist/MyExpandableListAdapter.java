package com.example.app_android.ui.elements.expandablelist;
 
import java.util.ArrayList;
import com.example.app_android.R;
 
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
 
public class MyExpandableListAdapter extends BaseExpandableListAdapter {
 
    private Context mContext;
    private ArrayList<ExpandableListGroup> mGroups;
    
    public MyExpandableListAdapter(Context context, ArrayList<ExpandableListGroup> groups) {
    	mContext = context;
    	mGroups = groups;
    }
    
    @Override
	public void onGroupExpanded(int groupPosition) {
    	int amountofGroups = getGroupCount();
    	
		// Close all other groups when opening a new one
    	for(int i = 0; i < amountofGroups; i++){
    		//if(i != groupPosition)
    		// I'M HERE
    		// 	onGroupCollapsed(i);
    	}
    	super.onGroupExpanded(groupPosition);
    }
     
    public void addItem(ExpandableListChild item, ExpandableListGroup group) {
        if (!mGroups.contains(group)) {
        	mGroups.add(group);
        }
        int index = mGroups.indexOf(group);
        ArrayList<ExpandableListChild> ch = mGroups.get(index).getItems();
        ch.add(item);
        mGroups.get(index).setItems(ch);
    }
    public Object getChild(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        ArrayList<ExpandableListChild> chList = mGroups.get(groupPosition).getItems();
        return chList.get(childPosition);
    }
 
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }
 
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view,
            ViewGroup parent) {
    	ExpandableListChild child = (ExpandableListChild) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.item_expandablelist_child, null);
        }
        TextView tv = (TextView) view.findViewById(R.id.tvChild);
        tv.setText(child.getName().toString());
        tv.setTag(child.getTag());
        // TODO Auto-generated method stub
        return view;
    }
 
    public int getChildrenCount(int groupPosition) {
        // TODO Auto-generated method stub
        ArrayList<ExpandableListChild> chList = mGroups.get(groupPosition).getItems();
 
        return chList.size();
 
    }
 
    public Object getGroup(int groupPosition) {
        // TODO Auto-generated method stub
        return mGroups.get(groupPosition);
    }
 
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return mGroups.size();
    }
 
    public long getGroupId(int groupPosition) {
        // TODO Auto-generated method stub
        return groupPosition;
    }
 
    public View getGroupView(int groupPosition, boolean isLastChild, View view,
            ViewGroup parent) {
    	ExpandableListGroup group = (ExpandableListGroup) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.item_expandablelist_group, null);
        }
        TextView tv = (TextView) view.findViewById(R.id.tvGroup);
        tv.setText(group.getName());
        // TODO Auto-generated method stub
        return view;
    }
 
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return true;
    }
 
    public boolean isChildSelectable(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return true;
    }
 
}