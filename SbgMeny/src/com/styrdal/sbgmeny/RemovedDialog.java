package com.styrdal.sbgmeny;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class RemovedDialog extends DialogFragment 
{
	
	protected static final String TAG = "RemovedDialog";
	public final static String EXTRA_MESSAGE = "com.styrdal.SbgMeny.MESSAGE";
	private SQLiteDatabase db;
	
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    // Get the layout inflater
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    
	    View view = inflater.inflate(R.layout.removed_dialog, null);
	    Bundle bundle = getArguments();
	    String addedString = bundle.getString(ShopListFragment.EXTRA_MESSAGE2);

	    final EditText editText = (EditText) view.findViewById(R.id.removed_text);
	    final int itemId = bundle.getInt(ShopListFragment.EXTRA_MESSAGE);
	    editText.setText(addedString);

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(view)
	    		.setTitle("Ändra Utan")
	    // Add action buttons
	           .setPositiveButton(R.string.edit, new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	   ((ShopList)getActivity()).doRemovedClick(editText.getText().toString(), itemId, db);
	               }
	           })
	           .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                   
	               }
	           });

	    return builder.create();
	}
	
	public void showDialog(SQLiteDatabase db, FragmentManager manager)
	{
		this.db = db;
		this.show(manager, "added");
	}
}
