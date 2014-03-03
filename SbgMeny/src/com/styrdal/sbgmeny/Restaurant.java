package com.styrdal.sbgmeny;

import com.styrdal.sbgmeny.RestaurantsContract.RestaurantsEntry;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;
import android.util.Log;

public class Restaurant {
	
	protected static final String TAG = "Restaurant";
	
	private String name;
	private String address;
	private String number;
	private String url;
	private String extra;
	private String monday;
	private String tuesday;
	private String wednesday;
	private String thursday;
	private String friday;
	private String saturday;
	private String sunday;
	private String idname;
	private String today;
	
	public Restaurant()	{}
	
	//Getting all the attributes for the Restaurant from the database from idname. Needs context for database connection.
	public Restaurant(String idname, Context context, SQLiteDatabase db)
	{
		String[] cursorProjection = { RestaurantsEntry.COLUMN_NAME_NAME,
				RestaurantsEntry.COLUMN_NAME_ADDRESS, 
				RestaurantsEntry.COLUMN_NAME_NUMBER, 
				RestaurantsEntry.COLUMN_NAME_URL, 
				RestaurantsEntry.COLUMN_NAME_EXTRA,
				RestaurantsEntry.COLUMN_NAME_MONDAY_OPEN,
				RestaurantsEntry.COLUMN_NAME_TUESDAY_OPEN,
				RestaurantsEntry.COLUMN_NAME_WEDNESDAY_OPEN,
				RestaurantsEntry.COLUMN_NAME_THURSDAY_OPEN,
				RestaurantsEntry.COLUMN_NAME_FRIDAY_OPEN,
				RestaurantsEntry.COLUMN_NAME_SATURDAY_OPEN,
				RestaurantsEntry.COLUMN_NAME_SUNDAY_OPEN,
				RestaurantsEntry.COLUMN_NAME_MONDAY_CLOSE,
				RestaurantsEntry.COLUMN_NAME_TUESDAY_CLOSE,
				RestaurantsEntry.COLUMN_NAME_WEDNESDAY_CLOSE,
				RestaurantsEntry.COLUMN_NAME_THURSDAY_CLOSE,
				RestaurantsEntry.COLUMN_NAME_FRIDAY_CLOSE,
				RestaurantsEntry.COLUMN_NAME_SATURDAY_CLOSE,
				RestaurantsEntry.COLUMN_NAME_SUNDAY_CLOSE
				};
		
		String sortOrder = RestaurantsEntry._ID + " ASC";
		String[] selectionArgs = {idname};
		String selection = RestaurantsEntry.COLUMN_NAME_IDNAME + " = ?";
		
		Cursor c = db.query(
				RestaurantsEntry.TABLE_NAME,
				cursorProjection,
				selection,
				selectionArgs,
				null,
				null,
				sortOrder
				);

		c.moveToFirst();
		
		this.name = c.getString(c.getColumnIndex(RestaurantsEntry.COLUMN_NAME_NAME));
		this.address = c.getString(c.getColumnIndex(RestaurantsEntry.COLUMN_NAME_ADDRESS));
		this.number = c.getString(c.getColumnIndex(RestaurantsEntry.COLUMN_NAME_NUMBER));
		this.url = c.getString(c.getColumnIndex(RestaurantsEntry.COLUMN_NAME_URL));
		this.extra = c.getString(c.getColumnIndex(RestaurantsEntry.COLUMN_NAME_EXTRA));

		this.monday = c.getString(c.getColumnIndex(RestaurantsEntry.COLUMN_NAME_MONDAY_OPEN)) + " - " + c.getString(c.getColumnIndex(RestaurantsEntry.COLUMN_NAME_MONDAY_CLOSE));
		if(this.monday.equals("Stängt - Stängt"))
		{
			this.monday = "Stängt";
		}
		
		this.tuesday = c.getString(c.getColumnIndex(RestaurantsEntry.COLUMN_NAME_TUESDAY_OPEN)) + " - " + c.getString(c.getColumnIndex(RestaurantsEntry.COLUMN_NAME_TUESDAY_CLOSE));
		if(this.tuesday.equals("Stängt - Stängt"))
		{
			this.tuesday = "Stängt";
		}
		
		this.wednesday = c.getString(c.getColumnIndex(RestaurantsEntry.COLUMN_NAME_WEDNESDAY_OPEN)) + " - " + c.getString(c.getColumnIndex(RestaurantsEntry.COLUMN_NAME_WEDNESDAY_CLOSE));
		if(this.wednesday.equals("Stängt - Stängt"))
		{
			this.wednesday = "Stängt";
		}
		
		this.thursday = c.getString(c.getColumnIndex(RestaurantsEntry.COLUMN_NAME_THURSDAY_OPEN)) + " - " + c.getString(c.getColumnIndex(RestaurantsEntry.COLUMN_NAME_THURSDAY_CLOSE));
		if(this.thursday.equals("Stängt - Stängt"))
		{
			this.thursday = "Stängt";
		}
		
		this.friday = c.getString(c.getColumnIndex(RestaurantsEntry.COLUMN_NAME_FRIDAY_OPEN)) + " - " + c.getString(c.getColumnIndex(RestaurantsEntry.COLUMN_NAME_FRIDAY_CLOSE));
		if(this.friday.equals("Stängt - Stängt"))
		{
			this.friday = "Stängt";
		}
		
		this.saturday = c.getString(c.getColumnIndex(RestaurantsEntry.COLUMN_NAME_SATURDAY_OPEN)) + " - " + c.getString(c.getColumnIndex(RestaurantsEntry.COLUMN_NAME_SATURDAY_CLOSE));
		if(this.saturday.equals("Stängt - Stängt"))
		{
			this.saturday = "Stängt";
		}
		
		this.sunday = c.getString(c.getColumnIndex(RestaurantsEntry.COLUMN_NAME_SUNDAY_OPEN)) + " - " + c.getString(c.getColumnIndex(RestaurantsEntry.COLUMN_NAME_SUNDAY_CLOSE));
		if(this.sunday.equals("Stängt - Stängt"))
		{
			this.sunday = "Stängt";
		}
		
		//Check todays open times
		this.today = timesToday();
	}
	
	//Creating restaurant from values. Needs name, adress, number, url, extra, monday, tuesday, wednesday, thursday, friday, saturday, sunday, idname. In that order.
	public Restaurant(String name, String address, String number, String url, String extra, String monday, String tuesday, String wednesday, String thursday, String friday, String saturday, String sunday, String idname)
	{
		this.name = name;
		this.address = address;
		this.number = number;
		this.url = url;
		this.extra = extra;
		this.monday = monday;
		this.tuesday = tuesday;
		this.wednesday = wednesday;
		this.thursday = thursday;
		this.friday = friday;
		this.saturday = saturday;
		this.sunday = sunday;
		this.idname = idname;
		
		//Check todays open times
		this.today = timesToday();
	}
	
	//Getting the times for the current day
	private String timesToday()
	{
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		int day = today.weekDay;
		
		if (day == today.MONDAY)
		{
			return monday;
		}
		else if (day == today.TUESDAY)
		{
			return tuesday;
		}
		else if (day == today.WEDNESDAY)
		{
			return wednesday;
		}
		else if (day == today.THURSDAY)
		{
			return thursday;
		}
		else if (day == today.FRIDAY)
		{
			return friday;
		}
		else if (day == today.SATURDAY)
		{
			return saturday;
		}
		else if (day == today.SUNDAY)
		{
			return sunday;
		}
		else
		{
			Log.e(TAG, "Invalid day of week. Exiting.");
			System.exit(0);
			return null;
		}
	}
	
	public String toString()
	{
		return name + " - " + today;
	}
	
	
	
	//Getters
	
	public String getName()
	{
		return name;
	}
	
	public String getAddress()
	{
		return address;
	}
	
	public String getNumber()
	{

		return number;
	}
	
	public String getUrl()
	{
		return url;
	}
	
	public String getExtra()
	{
		return extra;
	}
	
	public String getMonday()
	{
		return monday;
	}
	
	public String getTuesday()
	{
		return tuesday;
	}
	
	public String getWednesday()
	{
		return wednesday;
	}
	
	public String getThursday()
	{
		return thursday;
	}
	
	public String getFriday()
	{
		return friday;
	}
	
	public String getSaturday()
	{
		return saturday;
	}
	
	public String getSunday()
	{
		return sunday;
	}
	
	public String getToday()
	{
		return today;
	}
	
	public String getIdname()
	{
		return idname;
	}
	
	//Setters
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setAddress(String address)
	{
		this.address = address;
	}
	
	public void setNumber(String number)
	{
		this.number = number;
	}
	
	public void setUrl(String url)
	{
		this.url = url;
	}
	
	public void setExtra(String extra)
	{
		this.extra = extra;
	}
	
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
	
	
	//Checking if the restaurant is currently open
	public boolean isOpen()
	{
		if (today.equals("Stängt"))
		{
			return false;
		}
		else
		{
			String[] split = today.split(" - ");
			
			String open[] = split[0].split(":");
			String openHour = open[0];
			String openMinute = open[1];
			String openString = openHour + "." + openMinute;
			float openFloat = Float.parseFloat(openString);
			
			String close[] = split[1].split(":");
			String closeHour = close[0];
			if(Integer.parseInt(closeHour) < Integer.parseInt(openHour))
			{
				closeHour = "24";
			}
			String closeMinute = close[1];
			String closeString = closeHour + "." + closeMinute;
			float closeFloat = Float.parseFloat(closeString);
			
			Time now = new Time(Time.getCurrentTimezone());
			now.setToNow();
			String currentTime = now.format("%k:%M");
			String current[] = currentTime.split(":");
			String currentHour = current[0];
			String currentMinute = current[1];
			String currentString = currentHour + "." + currentMinute;
			float currentFloat = Float.parseFloat(currentString);
			
			if (openFloat < currentFloat && currentFloat < closeFloat)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}
}