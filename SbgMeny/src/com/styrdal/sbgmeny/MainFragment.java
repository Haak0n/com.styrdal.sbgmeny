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
			
			String[] cursorProjection = cursorProjection();
			String sortOrder = RestaurantsEntry.COLUMN_NAME_NAME + " ASC";
			
			Cursor c = db.query(RestaurantsEntry.TABLE_NAME, cursorProjection, null, null, null, null, sortOrder);
			
			String[] selection =  {RestaurantsEntry.COLUMN_NAME_NAME, cursorProjection[4], cursorProjection[5]};
			int[] displays =  {R.id.main_name, R.id.main_open, R.id.main_close};
			
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
	private String[] cursorProjection()
	{
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		int day = today.weekDay;
		
		if (day == today.MONDAY)
		{
			String[] projection = {RestaurantsEntry.COLUMN_NAME_NAME, RestaurantsEntry._ID, RestaurantsEntry.COLUMN_NAME_IDNAME, RestaurantsEntry.COLUMN_NAME_MONDAY_OPEN, RestaurantsEntry.COLUMN_NAME_MONDAY_CLOSE};
			return projection;
		}
		else if (day == today.TUESDAY)
		{
			String[] projection = {RestaurantsEntry.COLUMN_NAME_NAME, RestaurantsEntry._ID, RestaurantsEntry.COLUMN_NAME_IDNAME, RestaurantsEntry.COLUMN_NAME_TUESDAY_OPEN, RestaurantsEntry.COLUMN_NAME_TUESDAY_CLOSE};
			return projection;
		}
		else if (day == today.WEDNESDAY)
		{
			String[] projection = {RestaurantsEntry.COLUMN_NAME_NAME, RestaurantsEntry._ID, RestaurantsEntry.COLUMN_NAME_IDNAME, RestaurantsEntry.COLUMN_NAME_WEDNESDAY_OPEN, RestaurantsEntry.COLUMN_NAME_WEDNESDAY_CLOSE};
			return projection;
		}
		else if (day == today.THURSDAY)
		{
			String[] projection = {RestaurantsEntry.COLUMN_NAME_NAME, RestaurantsEntry._ID, RestaurantsEntry.COLUMN_NAME_IDNAME, RestaurantsEntry.COLUMN_NAME_THURSDAY_OPEN, RestaurantsEntry.COLUMN_NAME_THURSDAY_CLOSE};
			return projection;
		}
		else if (day == today.FRIDAY)
		{
			String[] projection = {RestaurantsEntry.COLUMN_NAME_NAME, RestaurantsEntry._ID, RestaurantsEntry.COLUMN_NAME_IDNAME, RestaurantsEntry.COLUMN_NAME_FRIDAY_OPEN, RestaurantsEntry.COLUMN_NAME_FRIDAY_CLOSE};
			return projection;
		}
		else if (day == today.SATURDAY)
		{
			String[] projection = {RestaurantsEntry.COLUMN_NAME_NAME, RestaurantsEntry._ID, RestaurantsEntry.COLUMN_NAME_IDNAME, RestaurantsEntry.COLUMN_NAME_SATURDAY_OPEN, RestaurantsEntry.COLUMN_NAME_SATURDAY_CLOSE};
			return projection;
		}
		else if (day == today.SUNDAY)
		{
			String[] projection = {RestaurantsEntry.COLUMN_NAME_NAME, RestaurantsEntry._ID, RestaurantsEntry.COLUMN_NAME_IDNAME, RestaurantsEntry.COLUMN_NAME_SUNDAY_OPEN, RestaurantsEntry.COLUMN_NAME_SUNDAY_CLOSE};
			return projection;
		}
		else
		{
			Log.e(TAG, "Invalid day of week. Exiting.");
			System.exit(0);
			return null;
		}
	}
}
