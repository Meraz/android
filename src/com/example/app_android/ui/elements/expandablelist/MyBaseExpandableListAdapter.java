package com.example.app_android.ui.elements.expandablelist;
 
import java.util.ArrayList;
import com.example.app_android.R;
 
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ExpandableListView;
 
public class MyBaseExpandableListAdapter extends BaseExpandableListAdapter {
	 
    protected Context mContext;
    protected ArrayList<BaseExpandableListGroup> mGroups;
    protected ExpandableListView mExpandableList;
    protected boolean mOnlyOneGroupOpenAtTheTime = false;
    //protected boolean mFirstGroupCanBeClosed = true; // Future functionality
    protected boolean mUseHtmlTextInTextFields = false;
    protected int mLastExpandedGroup;
    ButtonCallback mButtonCallback;
    
    public MyBaseExpandableListAdapter(Context context, ArrayList<BaseExpandableListGroup> groups) {
    	mContext = context;
    	mGroups = groups;
    }
    
    public void setButtonCallBack(ButtonCallback buttonCallback) {
    	mButtonCallback = buttonCallback;
    }
    
    @Override
	public void onGroupExpanded(int groupPosition) {
    	
    	if(mOnlyOneGroupOpenAtTheTime == true) {		// if only one item can open at any given time
	    	if(groupPosition != mLastExpandedGroup){	// Check that it was not the last group

				mExpandableList.collapseGroup(mLastExpandedGroup);
	    		}	    		
	    	}
	    		
    	mLastExpandedGroup = groupPosition;    	
    	
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
    
    // Sets the behavior if only one group is supposed opened at one any time. Default is false
    public void setOnlyOneOpenBehavior(boolean onlyOneOpen) {
    	mOnlyOneGroupOpenAtTheTime = onlyOneOpen;
    }
    
    // Sets the behavior if text in textfields are html or not. Default is false
    public void setUseHtmlFormattingOnText(boolean useHtmlFormattingOnText) {
    	mUseHtmlTextInTextFields = useHtmlFormattingOnText;
    }
   
    /*
 // Future functionality NOT IMPLEMENTED // TODO
    public void setFirstGroupCanBeClosed(boolean firstGroupCanBeClosed){
    	//mFirstGroupCanBeClosed = firstGroupCanBeClosed;
    }
    
    // Future functionality. NOT IMPLEMENTED // TODO
    public void setAnimateGroupsOnExpand(boolean animateGroups){
  
    }
    */
    
    public void openAllGroups()
    {
    	int amountOfGroups = getGroupCount();    	
    	// Open all groups
		for(int i = 0; i < amountOfGroups; i++)

    	mLastExpandedGroup = amountOfGroups;
    }
    
    public void closeAllGroups()
    {    	
    	int amountOfGroups = getGroupCount();
    	int i = 0;
    	    		
    	// Close all groups
    	for(; i < amountOfGroups; i++)
    		mExpandableList.collapseGroup(i);
    }
    
    // Set which groups that are to be opened directly
    public void openSpecificGroups(int[] groupIndexes) {
    	if(groupIndexes == null)
    		return;
    	if(groupIndexes.length == 1) {
    		mExpandableList.expandGroup(groupIndexes[0]);
    		return;
    	}
    	// Open all 
    	int totalGroupsToOpen = groupIndexes.length;
    	for(int i = 0; i < totalGroupsToOpen; i++){
    		mExpandableList.expandGroup(groupIndexes[i]);
    	}	
    }    
    
    // Set which groups that are to be closed directly
    public void closeSpecificGroups(int[] groupIndexes) {
    	if(groupIndexes == null)
    		return;
    	if(groupIndexes.length == 1) {
    		mExpandableList.collapseGroup(groupIndexes[0]);
    		return;
    	}
    	// Close all 
    	int totalGroupsToOpen = groupIndexes.length;
    	for(int i = 0; i < totalGroupsToOpen; i++){
    		mExpandableList.collapseGroup(groupIndexes[i]);
    	}    	
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
        // TODO ??
        ArrayList<BaseExpandableListChild> chList = mGroups.get(groupPosition).getItems();
        return chList.get(childPosition);
    }
 
    public long getChildId(int groupPosition, int childPosition) {
        // TODO ??
        return childPosition;
    }
 
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
    	BaseExpandableListChild child = (BaseExpandableListChild) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.item_expandablelist_child, null);
	    }	 
        TextView tv = (TextView) view.findViewById(R.id.tvChild);
        

        if(mUseHtmlTextInTextFields == true)
        	tv.setText(Html.fromHtml(child.getName()));	// Read the text as html formatted
        else if(mUseHtmlTextInTextFields == false)
        	tv.setText(child.getName());					// Read the text as not html formatted
        tv.setTag(child.getTag());
    
    	Button button = (Button) view.findViewById(R.id.tvButton1);
        if(child.hasButton() == true) {	       	
			MyOnClickListener listener = new MyOnClickListener(mButtonCallback, child.getButtonID());
			button.setOnClickListener(listener);
			button.setVisibility(Button.VISIBLE);        	
        }
        else {
        	button.setVisibility(Button.INVISIBLE);
        }

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