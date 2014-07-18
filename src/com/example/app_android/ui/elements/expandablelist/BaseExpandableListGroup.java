package com.example.app_android.ui.elements.expandablelist;
	 
import java.util.ArrayList;
 
public class BaseExpandableListGroup {
  
    private String mName;
    private ArrayList<BaseExpandableListChild> mItems;
    
    public BaseExpandableListGroup(){
    	mName = null;
    	mItems = null;
    }
    
    public String getName() {
        return mName;
    }
    public void setName(String name) {
        mName = name;
    }
    public ArrayList<BaseExpandableListChild> getItems() {
        return mItems;
    }

    // Use this to 
    public void appendItem(BaseExpandableListChild Item) {
    	if(mItems == null)
    		  mItems = new ArrayList<BaseExpandableListChild>();
	     mItems.add(Item);
    }
    
    public void appendItems(ArrayList<BaseExpandableListChild> Item) {
    	if(mItems == null)
    		  mItems = new ArrayList<BaseExpandableListChild>();
	     mItems.addAll(Item);
    }
    
    public static BaseExpandableListGroup ConstructOneGroupWithOneChild(String groupName, String childText, String childtag){
    	BaseExpandableListGroup group = new BaseExpandableListGroup();	// Create new group
		group.setName(groupName);										// Set name on group
		BaseExpandableListChild child = new BaseExpandableListChild();	// Create new child					
		child.setName(childText);										// Set name on child
		child.setTag(childtag);											// Set tag on the child
		group.appendItem(child);  										// Add child to the list
		return group;
    }    
}