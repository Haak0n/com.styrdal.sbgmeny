package com.styrdal.sbgmeny;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EntityUtils;

import com.styrdal.sbgmeny.VersionContract.VersionEntry;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

public class UpdateDatabase 
{

	private SQLiteDatabase db;
	private int currentVersion;
	private int newestVersion;
	protected static final String TAG = "UpdateDatabase";
	
	public UpdateDatabase(SQLiteDatabase db)
	{
		this.db = db;
		
		getCurrentVersion(db);
	}
	
	private int getCurrentVersion(SQLiteDatabase db)
	{
		String[] projection = { VersionEntry.COLUMN_NAME_VERSION };
		String table = VersionEntry.TABLE_NAME;
		String selection = VersionEntry._ID + " = 1";
		String[] selectionArgs = null;
		String sortOrder = VersionEntry._ID + " ASC";
		
		Cursor c = db.query(table,
				projection,
				selection,
				selectionArgs,
				null,
				null,
				sortOrder);
		c.moveToFirst();
		
		this.currentVersion = c.getInt(c.getColumnIndex(VersionEntry.COLUMN_NAME_VERSION));
		return currentVersion;
	}
	
	public boolean checkUpdate(FragmentManager manager, Context context)
	{
		try 
		{
			this.newestVersion = new CheckNewest().execute().get();
			SharedPreferences settings = context.getSharedPreferences(RestaurantsDBHelper.DB_VERSION_NAME, 0);
		   	SharedPreferences.Editor editor = settings.edit();
		   	editor.putInt(RestaurantsDBHelper.DB_VERSION_NEWEST, newestVersion);
		   	editor.commit();
		   	
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		} 
		catch (ExecutionException e) 
		{
			e.printStackTrace();
		}
		
		Log.i(TAG, "Current database version: " + Integer.toString(currentVersion));
		Log.i(TAG, "Newest database version: " + Integer.toString(newestVersion));
		
		if(newestVersion > currentVersion)
		{
			DownloadDialog dialog = new DownloadDialog();
			dialog.showDialog(this, manager, context);
			return true;
		}
		else
		{
			Toast.makeText(context, "Din meny behöver inte uppdateras!", Toast.LENGTH_SHORT).show();
			return false;
		}
	}
	
	public void updateDatabase(Context context)
	{
		Log.i(TAG, "Downloading database...");
		new DownloadDatabase().execute(context);
		this.currentVersion = this.newestVersion;
		
		SharedPreferences settings = context.getSharedPreferences(RestaurantsDBHelper.DB_VERSION_NAME, 0);
	   	SharedPreferences.Editor editor = settings.edit();
	   	editor.putInt(RestaurantsDBHelper.DB_VERSION_CURRENT,currentVersion);
	   	editor.commit();
	}
	
	public String toString()
	{
		String string = "Current version: " + Integer.toString(currentVersion) + ". Newest version: " + Integer.toString(newestVersion) + ".";
		return string;
	}
	
	//Check styrdal.com for newest database version.
	private class CheckNewest extends AsyncTask<Void, Void, Integer>
	{	
		@Override
		protected Integer doInBackground(Void... params) {
			URI websiteURI = null;
			try {
				websiteURI = new URI("http://www.styrdal.com/dbversion.php");
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			DefaultHttpClient http = new DefaultHttpClient();
			HttpGet httpMethod = new HttpGet();
			httpMethod.setURI(websiteURI);
			
			HttpResponse response = null;
			try 
			{
				response = http.execute(httpMethod);
			} 
			catch (ClientProtocolException e) 
			{
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
			int responseCode = response.getStatusLine().getStatusCode();
			String responseBody = null;
			switch(responseCode)
			{
			case 200:
				HttpEntity entity = response.getEntity();
				if(entity != null)
				{
					try 
					{
						responseBody = EntityUtils.toString(entity);						
					} 
					catch (ParseException e) 
					{
						e.printStackTrace();
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					}
				}
				break;
			default:
				responseBody = "0";
				break;
			}
			int currentVersion = Integer.parseInt(responseBody);
			return currentVersion;
		}
	}
	
	private class DownloadDatabase extends AsyncTask<Context, Void, Context>
	{
		@Override
		protected Context doInBackground(Context... context) 
		{
			Context thisContext = context[0];
			try 
			{
				//Downloading database
				URL url = new URL("http://www.styrdal.com/database/restauranger.db");
				URLConnection uCon = url.openConnection();
				
				InputStream is = uCon.getInputStream();
				BufferedInputStream  bufferedStream = new BufferedInputStream(is);
				
				ByteArrayBuffer baf = new ByteArrayBuffer(50);
				int current = 0;
				while ((current = bufferedStream.read()) != -1)
				{
					baf.append((byte) current);
				}
				FileOutputStream outputStream = null;
				outputStream = thisContext.openFileOutput("restauranger.db", Context.MODE_PRIVATE);
				
				outputStream.write(baf.toByteArray());
				Log.i(TAG, "Downloaded file to: " + thisContext.getFilesDir() + "restauranger.db");
				outputStream.close();
				
			} 
			catch (MalformedURLException e) 
			{
				e.printStackTrace();
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
			Log.i(TAG, "Database downloaded.");
			Log.i(TAG, "Copying database...");
			//Copy Database to /database/
			copyDatabase(thisContext);
			Log.i(TAG, "Database copied...");
			return thisContext;
		}
		
		//Copying downloaded database
		private void copyDatabase(Context context)
		{
			try 
			{
				String dbName = "restauranger.db";
				String dbPath = context.getApplicationInfo().dataDir + "/databases/" + dbName;
				
				File dbFile = new File(dbPath);
				InputStream is;
				
				is = context.openFileInput(dbName);
				Log.i(TAG, "Copying database from: " + context.getFilesDir().toString() + dbName +  " to: " + dbPath);
				
				FileOutputStream os = new FileOutputStream(dbFile);
				
				byte buf[] = new byte[1024];
			    int c = 0;
			    while (true) {
			      c = is.read(buf);
			      if (c == -1)
			        break;
			      os.write(buf, 0, c);
			    }
			    is.close();
			    os.close();	    
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Override
		
		protected void onPostExecute(Context context)
		{
			Toast.makeText(context, "Meny nerladdad!", Toast.LENGTH_SHORT).show();
			context.startActivity(new Intent(context, MainActivity.class));
		}
	}
}
