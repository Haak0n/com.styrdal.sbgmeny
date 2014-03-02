package com.styrdal.sbgmeny;

import java.io.IOException;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayRestaurantFragment extends Fragment {

	protected static final String TAG = "DisplayRestaurantFragment";
	public final static String EXTRA_MESSAGE = "com.styrdal.SbgMeny.MESSAGE";
	private String dbName = "restauranger.db";
	private SQLiteDatabase db;
	
	private String idname;

	public void onActivityCreated(Bundle savedState)
	{
		super.onActivityCreated(savedState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		
		View fragmentView = inflater.inflate(R.layout.activity_display_restaurant_fragment, container, false);

		SharedPreferences setting = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0);
		idname = setting.getString(MainActivity.EXTRA_MESSAGE, null);
		
		RestaurantsDBHelper dbHelper = null;
		try {
			dbHelper = new RestaurantsDBHelper(getActivity(), dbName, dbName);
		}
		catch (IOException e)
		{
			Log.e(TAG, e.toString() + " IOException db create.");
		}
		db = dbHelper.getWritableDatabase();
		
		Restaurant restaurant = new Restaurant(idname, getActivity(), db);
		
		
		ImageView openIcon = (ImageView) fragmentView.findViewById(R.id.display_restaurant_open_icon);
		
		//Drawing open or closed button
		Resources res = getResources();
		Drawable icon;
		if(restaurant.isOpen())
		{
			icon = res.getDrawable(R.drawable.ic_green);
		}
		else
		{
			icon = res.getDrawable(R.drawable.ic_red);
		}
		
		openIcon.setImageDrawable(icon);
		
		TextView title = (TextView) fragmentView.findViewById(R.id.display_restaurant_title);
		title.setText(restaurant.getName());
		title.setOnClickListener(showTimes);
		
		TextView timesToday = (TextView) fragmentView.findViewById(R.id.display_restaurant_times);
		timesToday.setText(restaurant.getToday());
		title.setOnClickListener(showTimes);
		
		TextView address2 = (TextView) fragmentView.findViewById(R.id.display_restaurant_address2);
		address2.setText(restaurant.getAddress());
		address2.setOnClickListener(showMap);
		
		TextView number2 = (TextView) fragmentView.findViewById(R.id.display_restaurant_number2);
		number2.setText(restaurant.getNumber());
		number2.setOnClickListener(callNumber);
		
		TextView url2 = (TextView) fragmentView.findViewById(R.id.display_restaurant_url2);
		url2.setText(restaurant.getUrl());
		if(!restaurant.getUrl().equals(null))
		{
			url2.setOnClickListener(showUrl);
		}
		
		TextView extra2 = (TextView) fragmentView.findViewById(R.id.display_restaurant_extra);
		extra2.setText(restaurant.getExtra());
		
		getActivity().setTitle(restaurant.getName());
		return fragmentView;
	}
	
	//Menu button listener
	public void showMenu(View view) {
		Intent intent = new Intent(getActivity(), DisplayMenu.class);
		startActivity(intent);
	}
	
	private View.OnClickListener showTimes = new View.OnClickListener() {
        public void onClick(View view) {
    		Intent intent = new Intent(view.getContext(), DisplayTimes.class);
    		startActivity(intent);
        }
    };
    
	private View.OnClickListener showMap = new View.OnClickListener() {
        public void onClick(View view) {
        	TextView textView = (TextView) view;
            String rawlocation = textView.getText().toString();
            
            
            String location = "geo:0,0?q=" + rawlocation;
            
            Uri locationUri = Uri.parse(location);
            Intent callIntent = new Intent(Intent.ACTION_VIEW, locationUri);
            startActivity(callIntent);
        }
    };
    
	private View.OnClickListener callNumber = new View.OnClickListener() {
        public void onClick(View view) {
        	TextView textView = (TextView) view;
            String rawNumber = textView.getText().toString();
            String splitNumber[] = rawNumber.split("-");
            
            
            String number = "tel:" + splitNumber[0] + splitNumber[1];
            
            Uri numberUri = Uri.parse(number);
            Intent callIntent = new Intent(Intent.ACTION_DIAL, numberUri);
            startActivity(callIntent);
        }
    };
    
	private View.OnClickListener showUrl = new View.OnClickListener() {
        public void onClick(View view) {
        	TextView textView = (TextView) view;
            String url = textView.getText().toString();
            
            Uri urlUri = Uri.parse(url);
            Intent callIntent = new Intent(Intent.ACTION_VIEW, urlUri);
            startActivity(callIntent);
        }
    };
}
