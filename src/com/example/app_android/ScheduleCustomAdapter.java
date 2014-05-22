package com.example.app_android;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ScheduleCustomAdapter extends BaseAdapter{
	
	private String[] startTime, endTime, type, place;
	private Context context;


	public ScheduleCustomAdapter(Context context, String[] startTime, String [] endTime, String[] type, String[] place) {
		super();
		this.context = context;
		this.startTime = startTime;
		this.endTime = endTime;
		this.type = type;
		this.place = place;
	}



	public int getCount() {
		return type.length;
	}



	public Object getItem(int position) {
		return position;
	}



	public long getItemId(int position){
	    return position;
	}



	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row;
		
		row = inflater.inflate(R.layout.schedule_item, parent, false);
		
		TextView tStartTime = (TextView) row.findViewById(R.id.textViewStartTime); 
		TextView tEndTime = (TextView) row.findViewById(R.id.textViewEndTime); 
		TextView tType = (TextView) row.findViewById(R.id.textViewType); 
		TextView tPlace = (TextView) row.findViewById(R.id.textViewPlace);
	    ImageView img =(ImageView) row.findViewById(R.id.mapMarker);
	    
	    tStartTime.setText(startTime[position]);
	    tEndTime.setText(endTime[position]);
	    tType.setText(type[position]);
	    tPlace.setText(place[position]);
	    img.setImageResource(R.drawable.ic_action_forward);

	    return row;
	}
}
