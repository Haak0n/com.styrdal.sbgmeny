package com.styrdal.sbgmeny;

import android.provider.BaseColumns;

public class ShopListContract {
	
	public ShopListContract() {}
	
	public static abstract class ShopListEntry implements BaseColumns
	{
		public static final String COLUMN_NAME_NAME = "name";
		public static final String COLUMN_NAME_NUMBER = "number";
		public static final String COLUMN_NAME_TOPPINGS = "toppings";
		public static final String COLUMN_NAME_EXTRA = "extra";
		public static final String COLUMN_NAME_PRICE = "price";
		public static final String COLUMN_NAME_ALTPRICE = "altprice";
		public static final String COLUMN_NAME_IDNAME = "idname";
		public static final String COLUMN_NAME_ADDED = "added";
		public static final String COLUMN_NAME_REMOVED = "removed";
		
		public static final String TABLE_NAME = "ShopList";
		
	}

}
