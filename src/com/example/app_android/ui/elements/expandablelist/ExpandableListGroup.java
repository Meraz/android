package com.example.app_android.ui.elements.expandablelist;
	 
import java.util.ArrayList;

import android.widget.Button;
 
public class ExpandableListGroup {
  
    private String mName;
    private ArrayList<ExpandableListChild> mItems;
    
    public ExpandableListGroup(){
    	mName = null;
    	mItems = null;
    }
    
    public String getName() {
        return mName;
    }
    public void setName(String name) {
        mName = name;
    }
    public ArrayList<ExpandableListChild> getItems() {
        return mItems;
    }

    // Use this to 
    public void appendItem(ExpandableListChild Item) {
    	if(mItems == null)
    		  mItems = new ArrayList<ExpandableListChild>();
	     mItems.add(Item);
    }
    
    public void appendItems(ArrayList<ExpandableListChild> Item) {
    	if(mItems == null)
    		  mItems = new ArrayList<ExpandableListChild>();
	     mItems.addAll(Item);
    }
    
    public static ExpandableListGroup ConstructOneGroupWithOneChild(String groupName, String childText, String childtag){
    	ExpandableListGroup group = new ExpandableListGroup();	// Create new group
		group.setName(groupName);										// Set name on group
		ExpandableListChild child = new ExpandableListChild();	// Create new child					
		child.setText(childText);										// Set name on child
		child.setTag(childtag);											// Set tag on the child
		group.appendItem(child);  										// Add child to the list
		return group;
    }    
    
    public static ExpandableListGroup ConstructOneGroupWithOneChild(String groupName, String childText, String childtag, ExpandableListMetaButton metaButton){
    	ExpandableListGroup group = new ExpandableListGroup();	// Create new group
		group.setName(groupName);								// Set name on group
		ExpandableListChild child = new ExpandableListChild();	// Create new child					
		child.setText(childText);								// Set name on child
		child.setTag(childtag);									// Set tag on the child
		child.setMetaButton(metaButton);						// Set button
		group.appendItem(child);  								// Add child to the list		
		return group;
    }   
}