package com.styrdal.sbgmeny;

import com.styrdal.sbgmeny.DailyContract.DailyEntry;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;
import android.util.Log;

public class DailyMenu 
{
	private SQLiteDatabase db;
	private String monday;
	private String tuesday;
	private String wednesday;
	private String thursday;
	private String friday;
	private String saturday;
	private String sunday;
	private int week;
	private Time today;
	private int day;
	
	protected static final String TAG = "DailyMenu";
	
	//Gets the daily menu for the current week
	public DailyMenu(String idname, SQLiteDatabase db)
	{		
		today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		day = today.weekDay;
		week = today.getWeekNumber();
		String weekString = Integer.toString(week);
		
		String[] cursorProjection = {DailyEntry.COLUMN_NAME_DAY, DailyEntry.COLUMN_NAME_WEEK, DailyEntry.COLUMN_NAME_TEXT};
		String sortOrder = DailyEntry.COLUMN_NAME_DAY + " ASC";
		String[] selectionArgs = {idname, weekString};
		String selection = DailyEntry.COLUMN_NAME_IDNAME + " = ? AND " + DailyEntry.COLUMN_NAME_WEEK + " = ?";
		String table = DailyEntry.TABLE_NAME;
		
		
		Cursor c = db.query(
				table, 
				cursorProjection,
				selection,
				selectionArgs,
				null,
				null,
				sortOrder);
		setDays(c);
	}
	
	public DailyMenu(String idname, SQLiteDatabase db, int week)
	{
		today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		day = today.weekDay;
		this.week = week;
		String weekString = Integer.toString(week);
		
		String[] cursorProjection = {DailyEntry.COLUMN_NAME_DAY, DailyEntry.COLUMN_NAME_WEEK, DailyEntry.COLUMN_NAME_TEXT};
		String sortOrder = DailyEntry.COLUMN_NAME_DAY + " ASC";
		String[] selectionArgs = {idname, weekString};
		String selection = DailyEntry.COLUMN_NAME_IDNAME + " = ? AND " + DailyEntry.COLUMN_NAME_WEEK + " = ?";
		String table = DailyEntry.TABLE_NAME;
		
		Cursor c = db.query(
				table, 
				cursorProjection,
				selection,
				selectionArgs,
				null,
				null,
				sortOrder);
		setDays(c);
	}
	
	private void setDays(Cursor c)
	{
		if(c.moveToFirst())
		{
		this.monday = unescape(c.getString(c.getColumnIndex(DailyEntry.COLUMN_NAME_TEXT)));
		if(c.moveToNext())
			{
				this.tuesday = unescape(c.getString(c.getColumnIndex(DailyEntry.COLUMN_NAME_TEXT)));
				if(c.moveToNext())
				{
					this.wednesday = unescape(c.getString(c.getColumnIndex(DailyEntry.COLUMN_NAME_TEXT)));
					if(c.moveToNext())
					{
						this.thursday = unescape(c.getString(c.getColumnIndex(DailyEntry.COLUMN_NAME_TEXT)));
						if(c.moveToNext())
						{
							this.friday = unescape(c.getString(c.getColumnIndex(DailyEntry.COLUMN_NAME_TEXT)));
							if(c.moveToNext())
							{
								this.saturday = unescape(c.getString(c.getColumnIndex(DailyEntry.COLUMN_NAME_TEXT)));
								if(c.moveToNext())
								{
									this.sunday = unescape(c.getString(c.getColumnIndex(DailyEntry.COLUMN_NAME_TEXT)));
								}
							}
						}
					}
				}
			}
		}
	}
	
	public String today()
	{
		if(day == Time.MONDAY)
		{
			return this.monday;
		}
		else if(day == Time.TUESDAY)
		{
			return this.tuesday;
		}
		else if(day == Time.WEDNESDAY)
		{
			return this.wednesday;
		}
		else if(day == Time.THURSDAY)
		{
			return this.thursday;
		}
		else if(day == Time.FRIDAY)
		{
			return this.friday;
		}
		else if(day == Time.SATURDAY)
		{
			return this.saturday;
		}
		else if(day == Time.SUNDAY)
		{
			return this.sunday;
		}
		else
		{
			return "ERROR";
		}
	}

	//Getters
	public String getMonday()
	{
		return this.monday;
	}
	
	public String getTuesday()
	{
		return this.tuesday;
	}
	
	public String getWednesday()
	{
		return this.wednesday;
	}
	
	public String getThursday()
	{
		return this.thursday;
	}
	
	public String getFriday()
	{
		return this.friday;
	}
	
	public String getSaturday()
	{
		return this.saturday;
	}
	
	public String getSunday()
	{
		return this.sunday;
	}
	
	public int getWeek()
	{
		return this.week;
	}
	
	//Setters
	public void setMonday(String monday)
	{
		this.monday = monday;
	}
	
	public void setTuesday(String tuesday)
	{
		this.tuesday = tuesday;
	}
	
	public void setWednesday(String wednesday)
	{
		this.wednesday = wednesday;
	}
	
	public void setThursday(String thursday)
	{
		this.thursday = thursday;
	}
	
	public void setFriday(String friday)
	{
		this.friday = friday;
	}
	
	public void setSaturday(String saturday)
	{
		this.saturday = saturday;
	}
	
	public void setSunday(String sunday)
	{
		this.sunday = sunday;
	}
	
	private String unescape(String description) {
	    return description.replaceAll("\\\\n", "\\\n");
	}
}
