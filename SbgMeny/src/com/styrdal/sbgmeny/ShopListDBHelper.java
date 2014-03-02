package com.styrdal.sbgmeny;

import com.styrdal.sbgmeny.ShopListContract.ShopListEntry;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ShopListDBHelper extends SQLiteOpenHelper {
	
	public static final int VERSION = 1;
	public static final String DATABASE = "ShopList.db";
	
	public static final String TEXT_TYPE = " TEXT";
	public static final String INT_TYPE = " INTEGER";
	public static final String COMMA_SEP = ",";
	
	public static final String SQL_CREATE_TABLE = "CREATE TABLE " + ShopListEntry.TABLE_NAME + " (" +
			ShopListEntry._ID +  " INTEGER PRIMARY KEY," +
			ShopListEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
			ShopListEntry.COLUMN_NAME_NUMBER + TEXT_TYPE + COMMA_SEP +
			ShopListEntry.COLUMN_NAME_TOPPINGS + TEXT_TYPE + COMMA_SEP + 
			ShopListEntry.COLUMN_NAME_EXTRA + TEXT_TYPE + COMMA_SEP + 
			ShopListEntry.COLUMN_NAME_PRICE + INT_TYPE + COMMA_SEP + 
			ShopListEntry.COLUMN_NAME_ALTPRICE + INT_TYPE + COMMA_SEP +
			ShopListEntry.COLUMN_NAME_IDNAME + TEXT_TYPE + COMMA_SEP +
			ShopListEntry.COLUMN_NAME_ADDED + TEXT_TYPE + COMMA_SEP +
			ShopListEntry.COLUMN_NAME_REMOVED + TEXT_TYPE + ")";
	
	public ShopListDBHelper(Context context)
	{
		super(context, DATABASE, null, VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(SQL_CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
	}
}
