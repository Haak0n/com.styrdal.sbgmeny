package com.styrdal.sbgmeny;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class DisplayTimes extends ActionBarActivity
{
	protected static final String TAG = "DisplayTimes";
	public final static String EXTRA_MESSAGE = "com.styrdal.SbgMeny.MESSAGE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_times);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_times, menu);
		return true;
	}
	
	//Handling the up button
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:	    	
	    	NavUtils.navigateUpFromSameTask(this);
	    	return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
	
}