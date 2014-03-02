package com.styrdal.sbgmeny;

import java.io.IOException;

import com.styrdal.sbgmeny.ItemsContract.ItemsEntry;
import com.styrdal.sbgmeny.ShopListContract.ShopListEntry;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class DisplayMenuFragment extends ListFragment {
	
	protected static final String TAG = "DisplayMenuFragment";
	public final static String EXTRA_MESSAGE = "com.styrdal.SbgMeny.MESSAGE";	
	private String dbName = "restauranger.db";
	private SQLiteDatabase db;

	private String idname;
	private Cursor c;
	private View myFragment;

	//Enabling options menu and context menu
	@Override
	public void onActivityCreated(Bundle savedState)
	{
		super.onActivityCreated(savedState);
		registerForContextMenu(getListView());
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		myFragment = inflater.inflate(R.layout.activity_display_menu_fragment, container, false);
		
		SharedPreferences setting = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0);
		idname = setting.getString(MainActivity.EXTRA_MESSAGE, null);
		
		Log.i(TAG, "idname: " + idname);
		
		RestaurantsDBHelper dbHelper = null;
		
		try 
		{
			dbHelper = new RestaurantsDBHelper(getActivity(), dbName, dbName);
		}
		catch (IOException e)
		{
			Log.e(TAG, e.toString());
		}
		db = dbHelper.getWritableDatabase();
		
		Cursor c = createCursor(db, getActivity().getIntent());
		
		String[] selection = {ItemsEntry.COLUMN_NAME_NAME, ItemsEntry.COLUMN_NAME_TOPPINGS, ItemsEntry.COLUMN_NAME_PRICE, ItemsEntry.COLUMN_NAME_NUMBER, ItemsEntry.COLUMN_NAME_EXTRA};
		int[] displays =  {R.id.menu_name, R.id.menu_toppings, R.id.menu_price, R.id.menu_number, R.id.menu_extra};
		
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.menu_list, c, selection, displays, 0);
		
		setListAdapter(adapter);
		
		return myFragment;
	}
	
	//Checking if there is a search query
	private boolean isSearched()
	{
		Intent intent = getActivity().getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction()))
		{
			Log.i(TAG, "Activity is searched!");
			return true;
		}
		else
		{
			return false;
		}
	}
	
	//Creating the cursor from the database based on if there is a search query or not
	private Cursor createCursor(SQLiteDatabase db, Intent intent)
	{
		String[] cursorProjection = {ItemsEntry._ID,
				ItemsEntry.COLUMN_NAME_NAME, 
				ItemsEntry.COLUMN_NAME_NUMBER, 
				ItemsEntry.COLUMN_NAME_TOPPINGS, 
				ItemsEntry.COLUMN_NAME_EXTRA, 
				ItemsEntry.COLUMN_NAME_PRICE, 
				ItemsEntry.COLUMN_NAME_ALTPRICE};
		String sortOrder = ItemsEntry._ID + " ASC";
		String tableName = idname;
		
		
		if (isSearched())
		{
			String search = intent.getStringExtra(SearchManager.QUERY);
			String selection = ItemsEntry.COLUMN_NAME_NAME + " LIKE '%" + search + "%' OR " + ItemsEntry.COLUMN_NAME_TOPPINGS + " LIKE '%" + search + "%'";
			
			c = db.query(tableName,
					cursorProjection,
					selection,
					null,
					null,
					null,
					sortOrder);
		}
		else
		{
			c = db.query(tableName,
					cursorProjection,
					null,
					null,
					null,
					null,
					sortOrder);
			
		}
		return c;
	}
	
	//Create context menu
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.display_menu_context, menu);
	}
	
	//Handling the context menu
	@Override
	public boolean onContextItemSelected(MenuItem item) 
	{
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId())
		{
			case R.id.context_add:
				addItem(info.position, db);
				Log.e(TAG, "Added!");
				return true;
			default:
				return super.onContextItemSelected(item);
		}
	}
	
	//Adding item to shop list
	private void addItem(int position, SQLiteDatabase db)
	{
		c.moveToPosition(position);
		int entryId = c.getInt(c.getColumnIndex(ItemsEntry._ID));
		
		RestaurantMenuItem restaurantMenuItem = new RestaurantMenuItem(entryId, idname, getActivity(), db);
		
		ContentValues contentValues = createContentValues(restaurantMenuItem);
		
		insertShopListEntry(contentValues);
		
		int duration = Toast.LENGTH_SHORT;		
		CharSequence text = restaurantMenuItem.getName() + " tillagd i inköpslistan!";		
		Toast toast = Toast.makeText(getActivity(), text, duration);
		toast.show();
	}
	
	//Creating content values for inserting into shop list
	private ContentValues createContentValues(RestaurantMenuItem restaurantMenuItem)
	{
		String name = restaurantMenuItem.getName();
		String number = restaurantMenuItem.getNumber();
		String toppings = restaurantMenuItem.getToppings();
		String extra = restaurantMenuItem.getExtra();
		int price = restaurantMenuItem.getPrice();
		int altPrice = restaurantMenuItem.getAltPrice();
		String added = null;
		String removed = null;
		
		ContentValues contentValues = new ContentValues();
		
		contentValues.put(ShopListEntry.COLUMN_NAME_NAME, name);
		contentValues.put(ShopListEntry.COLUMN_NAME_NUMBER, number);
		contentValues.put(ShopListEntry.COLUMN_NAME_TOPPINGS, toppings);
		contentValues.put(ShopListEntry.COLUMN_NAME_EXTRA, extra);
		contentValues.put(ShopListEntry.COLUMN_NAME_PRICE, price);
		contentValues.put(ShopListEntry.COLUMN_NAME_ALTPRICE, altPrice);
		contentValues.put(ShopListEntry.COLUMN_NAME_IDNAME, idname);
		contentValues.put(ShopListEntry.COLUMN_NAME_ADDED, added);
		contentValues.put(ShopListEntry.COLUMN_NAME_REMOVED, removed);
		
		return contentValues;
	}
	
	//Performing the SQL query for adding to the shop lift
	private long insertShopListEntry(ContentValues contentValues)
	{
		ShopListDBHelper shopListDBHelper = new ShopListDBHelper(getActivity());
		
		SQLiteDatabase shoplistdb = shopListDBHelper.getWritableDatabase();
		
		long entryID = shoplistdb.insert(ShopListEntry.TABLE_NAME,
				null,
				contentValues);
		shoplistdb.close();
		return entryID;
	}
}
