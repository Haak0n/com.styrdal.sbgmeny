package com.styrdal.sbgmeny;

import com.styrdal.sbgmeny.RestaurantsContract.RestaurantsEntry;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainListAdapter extends CursorAdapter
{
	private Cursor mCursor;
	private Context mContext;
	private final LayoutInflater mInflater;
	private String openColumn;
	private String closeColumn;
	
	public MainListAdapter(Context context, Cursor c, String openColumn, String closeColumn)
	{
		super(context, c);
		mInflater = LayoutInflater.from(context);
		mContext = context;
		this.openColumn = openColumn;
		this.closeColumn = closeColumn;
	}
	
	@Override
	public void bindView(View view, Context context, Cursor cursor)
	{
		if(cursor.getString(cursor.getColumnIndex(openColumn)).equals("Stängt"))
		{
			TextView mainName = (TextView) view.findViewById(R.id.main_name);
			mainName.setText(cursor.getString(cursor.getColumnIndex(RestaurantsEntry.COLUMN_NAME_NAME)));
			
			TextView closeTime = (TextView) view.findViewById(R.id.main_list_close);
			closeTime.setText("Stängt");
		}
		else
		{
			TextView mainName = (TextView) view.findViewById(R.id.main_name);
			mainName.setText(cursor.getString(cursor.getColumnIndex(RestaurantsEntry.COLUMN_NAME_NAME)));
			
			TextView openTime = (TextView) view.findViewById(R.id.main_open);
			openTime.setText(cursor.getString(cursor.getColumnIndex(openColumn)));
			
			TextView separator = (TextView) view.findViewById(R.id.main_list_separator);
			separator.setText(" - ");
			
			TextView closeTime = (TextView) view.findViewById(R.id.main_list_close);
			closeTime.setText(cursor.getString(cursor.getColumnIndex(closeColumn)));
		}
	}
	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent)
	{
		final View view = mInflater.inflate(R.layout.main_list, parent, false);
		return view;
	}
	

}
