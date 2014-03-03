package com.styrdal.sbgmeny;

import android.provider.BaseColumns;

public class RestaurantsContract 
{
	public RestaurantsContract() {}
	
	public static abstract class RestaurantsEntry implements BaseColumns
	{
		public static final String TABLE_NAME = "restaurants";
		public static final String COLUMN_NAME_NAME = "name";
		public static final String COLUMN_NAME_ADDRESS = "address";
		public static final String COLUMN_NAME_NUMBER = "number";
		public static final String COLUMN_NAME_URL = "url";
		public static final String COLUMN_NAME_EXTRA = "extra";
		public static final String COLUMN_NAME_MONDAY_OPEN = "monday_open";
		public static final String COLUMN_NAME_TUESDAY_OPEN = "tuesday_open";
		public static final String COLUMN_NAME_WEDNESDAY_OPEN = "wednesday_open";
		public static final String COLUMN_NAME_THURSDAY_OPEN = "thursday_open";
		public static final String COLUMN_NAME_FRIDAY_OPEN = "friday_open";
		public static final String COLUMN_NAME_SATURDAY_OPEN = "saturday_open";
		public static final String COLUMN_NAME_SUNDAY_OPEN = "sunday_open";
		public static final String COLUMN_NAME_MONDAY_CLOSE = "monday_close";
		public static final String COLUMN_NAME_TUESDAY_CLOSE = "tuesday_close";
		public static final String COLUMN_NAME_WEDNESDAY_CLOSE = "wednesday_close";
		public static final String COLUMN_NAME_THURSDAY_CLOSE = "thursday_close";
		public static final String COLUMN_NAME_FRIDAY_CLOSE = "friday_close";
		public static final String COLUMN_NAME_SATURDAY_CLOSE = "saturday_close";
		public static final String COLUMN_NAME_SUNDAY_CLOSE = "sunday_close";
		public static final String COLUMN_NAME_STANDARD = "standard";
		public static final String COLUMN_NAME_DAILY = "daily";
		public static final String COLUMN_NAME_IDNAME = "idname";
		
		public static final String TEXT_TYPE = "TEXT";
		public static final String INT_TYPE = "INTEGER";
		public static final String COMMA_SEP = ", ";
		
	}

}
