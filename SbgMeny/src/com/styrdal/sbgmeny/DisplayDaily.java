package com.styrdal.sbgmeny;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.view.menu.MenuView.ItemView;

public class DisplayDaily extends ActionBarActivity {

	public final static String EXTRA_MESSAGE = "com.styrdal.SbgMeny.MESSAGE";
	protected static final String TAG = "DailyMenuFragment";
	private int week;
	private int currentWeek;
	private int nextWeek;
	private int prevWeek;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_daily);
		
		Intent intent = getIntent();
		
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		currentWeek = today.getWeekNumber();
		
		week = intent.getIntExtra(EXTRA_MESSAGE, currentWeek);
		
		nextWeek = week + 1;
		prevWeek = week - 1;
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	public void prevWeek(View view)
	{
		Intent prevIntent = new Intent(this, DisplayDaily.class);
    	prevIntent.putExtra(EXTRA_MESSAGE, prevWeek);
    	startActivity(prevIntent);
	}
	
	public void nextWeek(View view)
	{
		Intent nextIntent = new Intent(this, DisplayDaily.class);
    	nextIntent.putExtra(EXTRA_MESSAGE, nextWeek);
    	startActivity(nextIntent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.display_daily, menu);
		
		//ItemView currentWeek = (ItemView) findViewById(R.id.current_week);
		//currentWeek.setEnabled(true);
		return true;
	}
	
	public boolean onPrepareOptionsMenu(Menu menu)
	{
	    MenuItem currentWeekItem = menu.findItem(R.id.current_week);      
	    if(week != currentWeek) 
	    {           
	        currentWeekItem.setVisible(true);
	    }
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:	
	    	NavUtils.navigateUpFromSameTask(this);
	        return true;
	    case R.id.next_week:	    	
	    	Intent nextIntent = new Intent(this, DisplayDaily.class);
	    	nextIntent.putExtra(EXTRA_MESSAGE, nextWeek);
	    	startActivity(nextIntent);
	    	return true;
	    case R.id.current_week:	    	
	    	Intent currentIntent = new Intent(this, DisplayDaily.class);
	    	currentIntent.putExtra(EXTRA_MESSAGE, currentWeek);
	    	startActivity(currentIntent);
	    	return true;
	    case R.id.prev_week:	    	
	    	Intent prevIntent = new Intent(this, DisplayDaily.class);
	    	prevIntent.putExtra(EXTRA_MESSAGE, prevWeek);
	    	startActivity(prevIntent);
	    	return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
}
