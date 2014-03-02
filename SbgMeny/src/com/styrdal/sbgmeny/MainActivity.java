package com.styrdal.sbgmeny;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;


public class MainActivity extends FragmentActivity
{
	
	public final static String EXTRA_MESSAGE = "com.styrdal.SbgMeny.MESSAGE";
	public static final String PREFS_NAME = "PrefsFile";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}