package com.styrdal.sbgmeny;

import com.styrdal.sbgmeny.ShopListContract.ShopListEntry;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class ShopList extends ActionBarActivity {
	
	protected static final String TAG = "ShopList";
	public final static String EXTRA_MESSAGE = "com.styrdal.SbgMeny.MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_list);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	public void doAddedClick(String added, int id, SQLiteDatabase db){
	    String table = ShopListEntry.TABLE_NAME;
	    ContentValues values = new ContentValues();
	    values.put(ShopListEntry.COLUMN_NAME_ADDED, added);
	    String whereClause = ShopListEntry._ID + " = ?";
	    String[] whereArgs = {Integer.toString(id)};
	    
	    db.update(table, values, whereClause, whereArgs);
	    
	    Intent intent = getIntent();
	    startActivity(intent);
	}
	
	public void doRemovedClick(String removed, int id, SQLiteDatabase db){	    
	    String table = ShopListEntry.TABLE_NAME;
	    ContentValues values = new ContentValues();
	    values.put(ShopListEntry.COLUMN_NAME_REMOVED, removed);
	    String whereClause = ShopListEntry._ID + " = ?";
	    String[] whereArgs = {Integer.toString(id)};
	    
	    db.update(table, values, whereClause, whereArgs);
	    
	    db.close();
	    Intent intent = getIntent();
	    startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shop_list, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
