package com.styrdal.sbgmeny;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

public class DownloadDialog extends DialogFragment {
	
	private UpdateDatabase updateDatabase;
	private Context context;
	protected static final String TAG = "AddedDialog";
	
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.download_message_positive)
        		.setTitle("Ladda ner meny?")
               .setPositiveButton(R.string.download, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   updateDatabase.updateDatabase(context);
                   }
               })
               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // User cancelled the dialog
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
    
    public void showDialog(UpdateDatabase updateDatabase, FragmentManager manager, Context context)
    {
    	Log.i(TAG, updateDatabase.toString());
    	this.updateDatabase = updateDatabase;
    	this.context = context;
    	this.show(manager, "download");
    }
}