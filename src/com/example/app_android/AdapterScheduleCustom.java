package com.example.app_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterScheduleCustom extends BaseAdapter{
	
	private String[] startTime, endTime, type, place;
	
	private LayoutInflater inflater;


	public AdapterScheduleCustom(Context context, String[] startTime, String [] endTime, String[] type, String[] place) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.type = type;
		this.place = place;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

	private static class Holder {
		TextView startTime;
		TextView endTime;
		TextView type;
		TextView place;
		ImageView img;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {		
		
		View row;
		Holder holder;
		if (convertView == null) {
		
			row = inflater.inflate(R.layout.schedule_item, parent, false);
			holder = new Holder();
			holder.startTime = (TextView) row.findViewById(R.id.textViewStartTime); 
			holder.endTime = (TextView) row.findViewById(R.id.textViewEndTime); 
			holder.type = (TextView) row.findViewById(R.id.textViewType); 
			holder.place = (TextView) row.findViewById(R.id.textViewPlace);
		    holder.img =(ImageView) row.findViewById(R.id.mapMarker);
		    row.setTag(holder);
		}
		else {
			row = convertView;
			holder = (Holder) row.getTag();
		}
	    holder.startTime.setText(startTime[position]);
	    holder.endTime.setText(endTime[position]);
	    holder.type.setText(type[position]);
	    holder.place.setText(place[position]);
	    holder.img.setImageResource(R.drawable.ic_action_forward);

	    return row;
	}
}
