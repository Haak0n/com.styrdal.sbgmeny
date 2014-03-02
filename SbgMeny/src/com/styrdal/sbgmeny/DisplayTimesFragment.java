package com.styrdal.sbgmeny;

import java.io.IOException;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayTimesFragment extends Fragment {
	
	protected static final String TAG = "DisplayTimesFragment";
	public final static String EXTRA_MESSAGE = "com.styrdal.SbgMeny.MESSAGE";
	private String dbName = "restauranger.db";
	private SQLiteDatabase db;
	
	private String idname;
	private Restaurant restaurant;
	private View fragmentView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		fragmentView = inflater.inflate(R.layout.activity_display_times_fragment, container, false);
		
		SharedPreferences setting = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0);
		idname = setting.getString(MainActivity.EXTRA_MESSAGE, null);
		
		RestaurantsDBHelper dbHelper = null;
		try {
			dbHelper = new RestaurantsDBHelper(getActivity(), dbName, dbName);
		}
		catch (IOException e)
		{
			Log.e(TAG, e.toString() + " IOException db create.");
		}
		db = dbHelper.getWritableDatabase();
		
		restaurant = new Restaurant(idname, getActivity(), db);
		getActivity().setTitle(restaurant.getName());
		
		TextView monday = (TextView) fragmentView.findViewById(R.id.display_times_monday_time);
		monday.setText(restaurant.getMonday());
		getIcon("monday");
		
		TextView tuesday = (TextView) fragmentView.findViewById(R.id.display_times_tuesday_time);
		tuesday.setText(restaurant.getTuesday());
		getIcon("tuesday");
		
		TextView wednesday = (TextView) fragmentView.findViewById(R.id.display_times_wednesday_time);
		wednesday.setText(restaurant.getWednesday());
		getIcon("wednesday");
		
		TextView thursday = (TextView) fragmentView.findViewById(R.id.display_times_thursday_time);
		thursday.setText(restaurant.getThursday());
		getIcon("thursday");
		
		TextView friday = (TextView) fragmentView.findViewById(R.id.display_times_friday_time);
		friday.setText(restaurant.getFriday());
		getIcon("friday");
		
		TextView saturday = (TextView) fragmentView.findViewById(R.id.display_times_saturday_time);
		saturday.setText(restaurant.getSaturday());
		getIcon("saturday");
		
		TextView sunday = (TextView) fragmentView.findViewById(R.id.display_times_sunday_time);
		sunday.setText(restaurant.getSunday());
		getIcon("sunday");
		
		return fragmentView;
	}
	
	//Selecting the open icon. None if day is not today, open or closed depending on time if today
	private void getIcon(String day) {
		
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		int todayDay = today.weekDay;
		Resources res = getResources();
		
		if(day.equals("monday") && todayDay == today.MONDAY)
		{
			ImageView mondayImage = (ImageView) fragmentView.findViewById(R.id.display_times_monday_icon);
			Drawable mondayIcon;
			
			if(restaurant.isOpen())
			{
				mondayIcon = res.getDrawable(R.drawable.ic_green);
				mondayImage.setImageDrawable(mondayIcon);
			}
			else
			{
				mondayIcon = res.getDrawable(R.drawable.ic_red);
				mondayImage.setImageDrawable(mondayIcon);
			}
		}
		else if(day.equals("tuesday") && todayDay == today.TUESDAY)
		{
			ImageView tuesdayImage = (ImageView) fragmentView.findViewById(R.id.display_times_tuesday_icon);
			Drawable tuesdayIcon;
			
			if(restaurant.isOpen())
			{
				tuesdayIcon = res.getDrawable(R.drawable.ic_green);
				tuesdayImage.setImageDrawable(tuesdayIcon);
			}
			else
			{
				tuesdayIcon = res.getDrawable(R.drawable.ic_red);
				tuesdayImage.setImageDrawable(tuesdayIcon);
			}
		}
		else if(day.equals("wednesday") && todayDay == today.WEDNESDAY)
		{
			ImageView wednesdayImage = (ImageView) fragmentView.findViewById(R.id.display_times_wednesday_icon);
			Drawable wednesdayIcon;
			
			if(restaurant.isOpen())
			{
				wednesdayIcon = res.getDrawable(R.drawable.ic_green);
				wednesdayImage.setImageDrawable(wednesdayIcon);
			}
			else
			{
				wednesdayIcon = res.getDrawable(R.drawable.ic_red);
				wednesdayImage.setImageDrawable(wednesdayIcon);
			}
		}
		else if(day.equals("thursday") && todayDay == today.THURSDAY)
		{
			ImageView thursdayImage = (ImageView) fragmentView.findViewById(R.id.display_times_thursday_icon);
			Drawable thursdayIcon;
			
			if(restaurant.isOpen())
			{
				thursdayIcon = res.getDrawable(R.drawable.ic_green);
				thursdayImage.setImageDrawable(thursdayIcon);
			}
			else
			{
				thursdayIcon = res.getDrawable(R.drawable.ic_red);
				thursdayImage.setImageDrawable(thursdayIcon);
			}
		}
		else if(day.equals("friday") && todayDay == today.FRIDAY)
		{
			ImageView fridayImage = (ImageView) fragmentView.findViewById(R.id.display_times_friday_icon);
			Drawable fridayIcon;
			
			if(restaurant.isOpen())
			{
				fridayIcon = res.getDrawable(R.drawable.ic_green);
				fridayImage.setImageDrawable(fridayIcon);
			}
			else
			{
				fridayIcon = res.getDrawable(R.drawable.ic_red);
				fridayImage.setImageDrawable(fridayIcon);
			}
		}
		else if(day.equals("saturday") && todayDay == today.SATURDAY)
		{
			ImageView saturdayImage = (ImageView) fragmentView.findViewById(R.id.display_times_saturday_icon);
			Drawable saturdayIcon;
			
			if(restaurant.isOpen())
			{
				saturdayIcon = res.getDrawable(R.drawable.ic_green);
				saturdayImage.setImageDrawable(saturdayIcon);
			}
			else
			{
				saturdayIcon = res.getDrawable(R.drawable.ic_red);
				saturdayImage.setImageDrawable(saturdayIcon);
			}
		}
		else if(day.equals("sunday") && todayDay == today.SUNDAY)
		{
			ImageView sundayImage = (ImageView) fragmentView.findViewById(R.id.display_times_sunday_icon);
			Drawable sundayIcon;
			
			if(restaurant.isOpen())
			{
				sundayIcon = res.getDrawable(R.drawable.ic_green);
				sundayImage.setImageDrawable(sundayIcon);
			}
			else
			{
				sundayIcon = res.getDrawable(R.drawable.ic_red);
				sundayImage.setImageDrawable(sundayIcon);
			}
		}
	}
}
