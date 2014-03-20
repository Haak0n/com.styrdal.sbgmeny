package com.styrdal.sbgmeny;

import java.io.IOException;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DisplayDailyFragment extends Fragment 
{
	
	private View fragmentView;
	private String idname;
	private SQLiteDatabase db;
	private int week;
	
	public final static String EXTRA_MESSAGE = "com.styrdal.SbgMeny.MESSAGE";
	private String dbName = "restauranger.db";
	
	protected static final String TAG = "DailyMenuFragment";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		fragmentView = inflater.inflate(R.layout.activity_display_daily_fragment, container, false);
		
		SharedPreferences setting = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0);
		idname = setting.getString(MainActivity.EXTRA_MESSAGE, null);
		
		Intent intent = getActivity().getIntent();
		
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		int currentWeek = today.getWeekNumber();	
		
		week = intent.getIntExtra(EXTRA_MESSAGE, currentWeek);
			
		
		RestaurantsDBHelper dbHelper = null;
		try {
			dbHelper = new RestaurantsDBHelper(getActivity(), dbName, dbName);
		}
		catch (IOException e)
		{
			Log.e(TAG, e.toString() + " IOException db create.");
		}
		db = dbHelper.getWritableDatabase();
		
		DailyMenu dailyMenu = new DailyMenu(idname, db, week);
		
		if(dailyMenu.getMonday() != null)
		{
			TextView mondayText = (TextView) fragmentView.findViewById(R.id.display_daily_monday_text);
			mondayText.setText(dailyMenu.getMonday());
			Log.i(TAG, dailyMenu.getMonday());
		}
		
		if(dailyMenu.getTuesday() != null)
		{
			TextView tuesdayText = (TextView) fragmentView.findViewById(R.id.display_daily_tuesday_text);
			tuesdayText.setText(dailyMenu.getTuesday());
		}
		
		if(dailyMenu.getWednesday() != null)
		{
			TextView wednesdayText = (TextView) fragmentView.findViewById(R.id.display_daily_wednesday_text);
			wednesdayText.setText(dailyMenu.getWednesday());
		}
		
		if(dailyMenu.getThursday() != null)
		{
			TextView thursdayText = (TextView) fragmentView.findViewById(R.id.display_daily_thursday_text);
			thursdayText.setText(dailyMenu.getThursday());
		}
		
		if(dailyMenu.getFriday() != null)
		{
			TextView fridayText = (TextView) fragmentView.findViewById(R.id.display_daily_friday_text);
			fridayText.setText(dailyMenu.getFriday());
		}
		
		if(dailyMenu.getSaturday() != null)
		{
			TextView saturdayText = (TextView) fragmentView.findViewById(R.id.display_daily_saturday_text);
			saturdayText.setText(dailyMenu.getSaturday());
		}
		
		if(dailyMenu.getSunday() != null)
		{
			TextView sundayText = (TextView) fragmentView.findViewById(R.id.display_daily_sunday_text);
			sundayText.setText(dailyMenu.getSunday());
		}
		
		getActivity().setTitle("Dagens v." + Integer.toString(week));
		return fragmentView;
	}
}
