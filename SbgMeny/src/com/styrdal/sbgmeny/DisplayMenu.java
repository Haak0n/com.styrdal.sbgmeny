package com.styrdal.sbgmeny;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;


public class DisplayMenu extends ActionBarActivity
{
	protected static final String TAG = "DisplayMenu";
	public final static String EXTRA_MESSAGE = "com.styrdal.SbgMeny.MESSAGE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_menu);
	
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@SuppressLint("NewApi")
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.display_menu, menu);
		
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		MenuItem searchMenuItem = menu.findItem(R.id.menu_search);
		SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
		
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		return true;
	}
	
	//Handling up button, going back to the menu if searched and parent if full menu
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {		
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:	
			if(isSearched())
			{
				Intent sameIntent = new Intent(this, DisplayMenu.class);
				startActivity(sameIntent);		
			}
			else
			{
		 		NavUtils.navigateUpFromSameTask(this);
			}

	        return true;
	        
	    case R.id.menu_search:
	    	return true;
	    	
	    case R.id.shop_list:
	    	Intent shopListIntent = new Intent(this, ShopList.class);
	    	startActivity(shopListIntent);
	    	return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
	
	//Checking if there is a search query
	private boolean isSearched()
	{
		Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
}