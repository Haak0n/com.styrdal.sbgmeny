package com.styrdal.sbgmeny;

import java.io.IOException;

import com.styrdal.sbgmeny.RestaurantsContract.RestaurantsEntry;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class MainFragment extends ListFragment 
{
	protected static final String TAG = "MainActivityFragment";
	public final static String EXTRA_MESSAGE = "com.styrdal.SbgMeny.MESSAGE";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{

		try
		{
			inflater.inflate(R.layout.activity_main_fragment, container, false);
			
			RestaurantsDBHelper mDbHelper;
			
			Context context = getActivity();
			
			String dbName = "restauranger.db";
			mDbHelper = new RestaurantsDBHelper(context, dbName, dbName);
			SQLiteDatabase db = mDbHelper.getWritableDatabase();
			
			String[] cursorProjection = {RestaurantsEntry.COLUMN_NAME_NAME, RestaurantsEntry._ID, RestaurantsEntry.COLUMN_NAME_IDNAME, timesToday()};
			String sortOrder = RestaurantsEntry.COLUMN_NAME_NAME + " ASC";
			
			Cursor c = db.query(RestaurantsEntry.TABLE_NAME, cursorProjection, null, null, null, null, sortOrder);
			
			String[] selection =  {RestaurantsEntry.COLUMN_NAME_NAME, timesToday()};
			int[] displays =  {R.id.main_name, R.id.main_open};
			
			SimpleCursorAdapter adapter = new SimpleCursorAdapter(context, R.layout.main_list, c, selection, displays, 0);
			
			setListAdapter(adapter);	
		}
		catch (IOException e)
		{
			Log.e(TAG, e.toString() + " IOException db create.");
		}
		return inflater.inflate(R.layout.activity_main_fragment, container, false);
	}
	
	//Listener for the ListView
	public void onListItemClick(ListView l, View v, int position, long id)
	{
    	Intent intent = new Intent(getActivity(), DisplayRestaurant.class);
   	
    	Cursor c = (Cursor) l.getItemAtPosition(position);
    	c.moveToPosition(position);
    	String idname = c.getString(c.getColumnIndex(RestaurantsEntry.COLUMN_NAME_IDNAME));
    	
	   	SharedPreferences settings = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0);
	   	SharedPreferences.Editor editor = settings.edit();
	   	editor.putString(MainActivity.EXTRA_MESSAGE, idname);
	   	
	   	editor.commit();
	    	
    	startActivity(intent);
	    
	}
	//Getting the correct day column for today
	private String timesToday()
	{
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		int day = today.weekDay;
		
		if (day == today.MONDAY)
		{
			return RestaurantsEntry.COLUMN_NAME_MONDAY;
		}
		else if (day == today.TUESDAY)
		{
			return RestaurantsEntry.COLUMN_NAME_TUESDAY;
		}
		else if (day == today.WEDNESDAY)
		{
			return RestaurantsEntry.COLUMN_NAME_WEDNESDAY;
		}
		else if (day == today.THURSDAY)
		{
			return RestaurantsEntry.COLUMN_NAME_THURSDAY;
		}
		else if (day == today.FRIDAY)
		{
			return RestaurantsEntry.COLUMN_NAME_FRIDAY;
		}
		else if (day == today.SATURDAY)
		{
			return RestaurantsEntry.COLUMN_NAME_SATURDAY;
		}
		else if (day == today.SUNDAY)
		{
			return RestaurantsEntry.COLUMN_NAME_SUNDAY;
		}
		else
		{
			Log.e(TAG, "Invalid day of week. Exiting.");
			System.exit(0);
			return null;
		}
	}
}
