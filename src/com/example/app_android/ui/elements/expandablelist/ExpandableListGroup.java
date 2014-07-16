package com.example.app_android.ui.elements.expandablelist;
	 
import java.util.ArrayList;
 
public class ExpandableListGroup {
  
    private String mName;
    private ArrayList<ExpandableListChild> mItems;
     
    public String getName() {
        return mName;
    }
    public void setName(String name) {
        mName = name;
    }
    public ArrayList<ExpandableListChild> getItems() {
        return mItems;
    }
    public void setItems(ArrayList<ExpandableListChild> Items) {
        mItems = Items;
    }
     
     
}