package com.styrdal.sbgmeny;

import android.provider.BaseColumns;

public class ItemsContract 
{
	public ItemsContract() {}
	
	public static abstract class ItemsEntry implements BaseColumns
	{
		public static final String COLUMN_NAME_NAME = "name";
		public static final String COLUMN_NAME_NUMBER = "number";
		public static final String COLUMN_NAME_TOPPINGS = "toppings";
		public static final String COLUMN_NAME_EXTRA = "extra";
		public static final String COLUMN_NAME_PRICE = "price";
		public static final String COLUMN_NAME_ALTPRICE = "altprice";
		
		public static final String TEXT_TYPE = "TEXT";
		public static final String INT_TYPE = "INTEGER";
		public static final String COMMA_SEP = ", ";
	}

}
