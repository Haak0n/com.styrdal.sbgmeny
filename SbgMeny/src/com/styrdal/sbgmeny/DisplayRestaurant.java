package com.styrdal.sbgmeny;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


public class DisplayRestaurant extends ActionBarActivity
{
	protected static final String TAG = "DisplayRestaurant";
	public final static String EXTRA_MESSAGE = "com.styrdal.SbgMeny.MESSAGE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_restaurant);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	//Menu button listener
	public void showMenu(View view) {
		Intent intent = new Intent(this, DisplayMenu.class);
		startActivity(intent);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.display_restaurant, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:	
	    	NavUtils.navigateUpFromSameTask(this);
	        return true;
	    case R.id.shop_list:
	    	Intent shopListIntent = new Intent(this, ShopList.class);
	    	startActivity(shopListIntent);
	    	return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
}