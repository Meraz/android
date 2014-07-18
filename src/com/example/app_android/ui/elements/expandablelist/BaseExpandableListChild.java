package com.example.app_android.ui.elements.expandablelist;
 
public class BaseExpandableListChild {
 
    private String mName;
    private String mTag;
    
    public BaseExpandableListChild() {
    	mName = null;
    	mTag = null;
    }
     
    public String getName() {
        return mName;
    }
    public void setName(String Name) {
        mName = Name;
    }
    public String getTag() {
        return mTag;
    }
    public void setTag(String Tag) {
        mTag = Tag;
    }
}