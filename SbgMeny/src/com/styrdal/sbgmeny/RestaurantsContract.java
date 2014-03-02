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
		public static final String COLUMN_NAME_MONDAY = "monday";
		public static final String COLUMN_NAME_TUESDAY = "tuesday";
		public static final String COLUMN_NAME_WEDNESDAY = "wednesday";
		public static final String COLUMN_NAME_THURSDAY = "thursday";
		public static final String COLUMN_NAME_FRIDAY = "friday";
		public static final String COLUMN_NAME_SATURDAY = "saturday";
		public static final String COLUMN_NAME_SUNDAY = "sunday";
		public static final String COLUMN_NAME_IDNAME = "idname";
		
		public static final String TEXT_TYPE = "TEXT";
		public static final String INT_TYPE = "INTEGER";
		public static final String COMMA_SEP = ", ";
		
	}

}
