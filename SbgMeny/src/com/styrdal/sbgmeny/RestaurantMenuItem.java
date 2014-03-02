package com.styrdal.sbgmeny;

import com.styrdal.sbgmeny.ItemsContract.ItemsEntry;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class RestaurantMenuItem 
{
	protected static final String TAG = "MenuItem";
	
	private int id;
	private String name;
	private String number;
	private String toppings;
	private int price;
	private int altPrice;
	private String extra;
	private String idname;
	private String added;
	private String removed;
	
	public RestaurantMenuItem() {}
	
	public RestaurantMenuItem(int id, String idname, Context context, SQLiteDatabase db)
	{
		this.idname = idname;
		this. id = id;

		String[] cursorProjection = { ItemsEntry._ID,
				ItemsEntry.COLUMN_NAME_NAME,
				ItemsEntry.COLUMN_NAME_NUMBER,
				ItemsEntry.COLUMN_NAME_TOPPINGS,
				ItemsEntry.COLUMN_NAME_PRICE,
				ItemsEntry.COLUMN_NAME_ALTPRICE,
				ItemsEntry.COLUMN_NAME_EXTRA 
		};
		
		String sortOrder = null;
		String[] selectionArgs = { Integer.toString(id) };
		String selection = ItemsEntry._ID + " = ?";
		
		Cursor c = db.query(idname,
				cursorProjection,
				selection,
				selectionArgs,
				null,
				null,
				sortOrder);
		
		c.moveToFirst();
		
		this.name = c.getString(c.getColumnIndex(ItemsEntry.COLUMN_NAME_NAME));
		this.number = c.getString(c.getColumnIndex(ItemsEntry.COLUMN_NAME_NUMBER));
		this.toppings = c.getString(c.getColumnIndex(ItemsEntry.COLUMN_NAME_TOPPINGS));
		this.price = c.getInt(c.getColumnIndex(ItemsEntry.COLUMN_NAME_PRICE));
		this.altPrice = c.getInt(c.getColumnIndex(ItemsEntry.COLUMN_NAME_ALTPRICE));
		this.extra = c.getString(c.getColumnIndex(ItemsEntry.COLUMN_NAME_EXTRA));
	}
	
	public RestaurantMenuItem(String name, String idname, Context context, SQLiteDatabase db)
	{
		this.idname = idname;
		this.name = name;
		
		String[] cursorProjection = { ItemsEntry._ID,
				ItemsEntry.COLUMN_NAME_NAME,
				ItemsEntry.COLUMN_NAME_NUMBER,
				ItemsEntry.COLUMN_NAME_TOPPINGS,
				ItemsEntry.COLUMN_NAME_PRICE,
				ItemsEntry.COLUMN_NAME_ALTPRICE,
				ItemsEntry.COLUMN_NAME_EXTRA 
		};
		
		String sortOrder = null;
		String[] selectionArgs = { name };
		String selection = ItemsEntry.COLUMN_NAME_NAME + " = ?";
		
		Cursor c = db.query(idname,
				cursorProjection,
				selection,
				selectionArgs,
				null,
				null,
				sortOrder);
		
		c.moveToFirst();
		
		this.id = c.getInt(c.getColumnIndex(ItemsEntry._ID));
		this.name = c.getString(c.getColumnIndex(ItemsEntry.COLUMN_NAME_NAME));
		this.number = c.getString(c.getColumnIndex(ItemsEntry.COLUMN_NAME_NUMBER));
		this.toppings = c.getString(c.getColumnIndex(ItemsEntry.COLUMN_NAME_TOPPINGS));
		this.price = c.getInt(c.getColumnIndex(ItemsEntry.COLUMN_NAME_PRICE));
		this.altPrice = c.getInt(c.getColumnIndex(ItemsEntry.COLUMN_NAME_ALTPRICE));
		this.extra = c.getString(c.getColumnIndex(ItemsEntry.COLUMN_NAME_EXTRA));
	}
	
	
	public String toString()
	{
		return this.number + ". " + this.name + " - " + this.price + ":-";
	}
	
	//Getters
	
	public String getName()
	{
		return this.name;
	}
	
	public String getNumber()
	{
		return this.number;
	}
	
	public String getToppings()
	{
		return this.toppings;
	}
	
	public int getPrice()
	{
		return this.price;
	}
	
	public int getAltPrice()
	{
		return this.altPrice;
	}
	
	public String getExtra()
	{
		return this.extra;
	}
	
	public String getIdName()
	{
		return this.idname;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public String getAdded()
	{
		return this.added;
	}
	
	public String getRemoved()
	{
		return this.removed;
	}
	
	
	//Setters
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setNumber(String number)
	{
		this.number = number;
	}
	
	public void setToppings(String toppings)
	{
		this.toppings = toppings;
	}
	
	public void setPrice(int price)
	{
		this.price = price;
	}
	
	public void setaltPrice(int altPrice)
	{
		this.altPrice = altPrice;
	}
	
	public void setExtra(String extra)
	{
		this.extra = extra;
	}
}