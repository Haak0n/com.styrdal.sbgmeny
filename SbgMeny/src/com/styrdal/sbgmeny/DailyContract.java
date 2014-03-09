package com.styrdal.sbgmeny;

import android.provider.BaseColumns;

public class DailyContract 
{
	public DailyContract() {}
	
	public static abstract class DailyEntry implements BaseColumns
	{
		public static final String TABLE_NAME = "daily";
		public static final String COLUMN_NAME_DAY = "day";
		public static final String COLUMN_NAME_WEEK = "week";
		public static final String COLUMN_NAME_TEXT = "text";
		public static final String COLUMN_NAME_IDNAME = "idname";
		
		public static final String TEXT_TYPE = "TEXT";
		public static final String INT_TYPE = "INTEGER";
		public static final String COMMA_SEP = ", ";
		
	}

}
