package com.styrdal.sbgmeny;

import java.io.IOException;

import com.styrdal.sbgmeny.ShopListContract.ShopListEntry;

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
import android.widget.Button;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.TextView;

public class ShopListFragment extends ListFragment {
	
	protected static final String TAG = "ShopListFragment";
	public final static String EXTRA_MESSAGE = "com.styrdal.SbgMeny.MESSAGE";
	public final static String EXTRA_MESSAGE2 = "com.styrdal.SbgMeny.MESSAGE2";
	private SQLiteDatabase db;
	
	private View myFragment;
	private String idname;
	private Cursor c;
	
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
		myFragment = inflater.inflate(R.layout.activity_shop_list_fragment, container, false);
		
		SharedPreferences setting = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0);
		idname = setting.getString(MainActivity.EXTRA_MESSAGE, null);
		
		ShopListDBHelper dbHelper = new ShopListDBHelper(getActivity());
		db = dbHelper.getWritableDatabase();
		
		createListView();
		return myFragment;
	}
	
	//Clear Button listener
	private View.OnClickListener clearButton = new View.OnClickListener() {
        public void onClick(View view) {
    		clearShopList();
        }
    };
	
    //Clear the shoplist and recreate the view
	private void clearShopList() 
	{ 
		String table = ShopListEntry.TABLE_NAME;
		String whereClause = ShopListEntry.COLUMN_NAME_IDNAME + " = ?";
		String[] whereArgs = {idname};
		
		db.delete(table, whereClause, whereArgs);
		Log.i(TAG, "Removed all entries with idname " + idname);
		
		createListView();
	}
	
	//Creating and populating the ListView
	private void createListView()
	{
		c = createCursor();
		
		String[] selection = {ShopListEntry.COLUMN_NAME_NAME, ShopListEntry.COLUMN_NAME_TOPPINGS, ShopListEntry.COLUMN_NAME_PRICE, ShopListEntry.COLUMN_NAME_NUMBER, ShopListEntry.COLUMN_NAME_EXTRA, ShopListEntry.COLUMN_NAME_ADDED, ShopListEntry.COLUMN_NAME_REMOVED};
		int[] displays =  {R.id.shop_list_name, R.id.shop_list_toppings, R.id.shop_list_price, R.id.shop_list_number, R.id.shop_list_extra, R.id.shop_list_added2, R.id.shop_list_removed2};
		
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.shop_list, c, selection, displays, 0);

		if(c.getCount() != 0)
		{
			Button button = (Button) myFragment.findViewById(R.id.shop_list_clear_button);
			button.setVisibility(Button.VISIBLE);
			button.setOnClickListener(clearButton);
			
			TextView totalPrice1 = (TextView) myFragment.findViewById(R.id.shop_list_totalprice1);
			totalPrice1.setVisibility(TextView.VISIBLE);
			
			TextView totalPrice2 = (TextView) myFragment.findViewById(R.id.shop_list_totalprice2);
			int totalPrice = getTotalPrice(c);
			String totalPriceString = Integer.toString(totalPrice) + ":-";
			totalPrice2.setText(totalPriceString);
		}
		else
		{
			Button button = (Button) myFragment.findViewById(R.id.shop_list_clear_button);
			button.setVisibility(Button.INVISIBLE);
			
			TextView totalPrice1 = (TextView) myFragment.findViewById(R.id.shop_list_totalprice1);
			totalPrice1.setVisibility(TextView.INVISIBLE);
			
			TextView totalPrice2 = (TextView) myFragment.findViewById(R.id.shop_list_totalprice2);
			totalPrice2.setVisibility(TextView.INVISIBLE);
		}

		setListAdapter(adapter);
	}
	
	//Getting total price
	private int getTotalPrice(Cursor c)
	{
		c.moveToFirst();
		int totalPrice = c.getInt(c.getColumnIndex(ShopListEntry.COLUMN_NAME_PRICE));
		
		while(c.moveToNext())
		{
			totalPrice = totalPrice + c.getInt(c.getColumnIndex(ShopListEntry.COLUMN_NAME_PRICE));
			Log.i(TAG, "Totalt pris: " + Integer.toString(totalPrice) + ":-");
		}
		return totalPrice;
	}
	
	//Creating the cursor
	private Cursor createCursor()
	{
		String[] projection = { ShopListEntry._ID,
				ShopListEntry.COLUMN_NAME_NAME,
				ShopListEntry.COLUMN_NAME_NUMBER,
				ShopListEntry.COLUMN_NAME_TOPPINGS,
				ShopListEntry.COLUMN_NAME_EXTRA,
				ShopListEntry.COLUMN_NAME_PRICE,
				ShopListEntry.COLUMN_NAME_ALTPRICE,
				ShopListEntry.COLUMN_NAME_IDNAME,
				ShopListEntry.COLUMN_NAME_ADDED,
				ShopListEntry.COLUMN_NAME_REMOVED };
		String table = ShopListEntry.TABLE_NAME;
		String selection = ShopListEntry.COLUMN_NAME_IDNAME + " = ?";
		String[] selectionArgs = {idname};
		String sortOrder = ShopListEntry.COLUMN_NAME_NAME + " ASC";
		
		Cursor c = db.query(table,
				projection,
				selection,
				selectionArgs,
				null,
				null,
				sortOrder);
		Log.i(TAG, "Cursor created.");
		return c;
	}
	
	//Removing a single item
	private void removeItem(int position)
	{
		c.moveToPosition(position);
		int id = c.getInt(c.getColumnIndex(ShopListEntry._ID));
		
		String table = ShopListEntry.TABLE_NAME;
		String whereClause = ShopListEntry._ID + " = ?";
		String[] whereArgs = {Integer.toString(id)};
		
		db.delete(table, whereClause, whereArgs);
		Log.i(TAG, "Removed entry with id: " + id);
		
		createListView();
	}
	
	//Create context menu
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.shop_list_context, menu);
	}
	
	//Handling the context menu
	@Override
	public boolean onContextItemSelected(MenuItem item) 
	{
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId())
		{
			case R.id.context_remove:
				removeItem(info.position);
				return true;
			case R.id.context_edit_added:
				showAddedDialog(item);
				return true;
			case R.id.context_edit_removed:
				showRemovedDialog(item);
				return true;
			default:
				return super.onContextItemSelected(item);
		}
	}
	
	//Bringing up the Added Dialog
	private void showAddedDialog(MenuItem item)
	{
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		c.moveToPosition(info.position);
		
		String added = c.getString(c.getColumnIndex(ShopListEntry.COLUMN_NAME_ADDED));
		
		Bundle bundle = new Bundle();
		bundle.putString(EXTRA_MESSAGE2, added);
		int id = c.getInt(c.getColumnIndex(ShopListEntry._ID));
		bundle.putInt(EXTRA_MESSAGE, id);
		
		AddedDialog dialog = new AddedDialog();
		dialog.setArguments(bundle);
		dialog.showDialog(db, getFragmentManager());
	}
	
	//Bringing up the Added Dialog
	private void showRemovedDialog(MenuItem item)
	{
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		c.moveToPosition(info.position);
		
		String removed = c.getString(c.getColumnIndex(ShopListEntry.COLUMN_NAME_REMOVED));
		
		Bundle bundle = new Bundle();
		bundle.putString(EXTRA_MESSAGE2, removed);
		int id = c.getInt(c.getColumnIndex(ShopListEntry._ID));
		bundle.putInt(EXTRA_MESSAGE, id);
		
		RemovedDialog dialog = new RemovedDialog();
		dialog.setArguments(bundle);
		dialog.showDialog(db, getFragmentManager());
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.shop_list_clear:
			Log.i(TAG, "Clearing list from ShopListFragment");
			clearShopList();
			return true;
		case R.id.shop_list_to_restaurant:
			Log.i(TAG, "Navigating to restaurant from ShopListFragment");
			Intent intent = new Intent(getActivity(), DisplayRestaurant.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
