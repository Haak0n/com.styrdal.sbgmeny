package com.styrdal.sbgmeny;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RestaurantsDBHelper extends SQLiteOpenHelper {

  private static final String TAG = RestaurantsDBHelper.class.getSimpleName();

  private final Context context;
  private final String assetPath;
  private final String dbPath;
  
  public static final String DB_VERSION_NAME = "com.styrdal.dbVersionName";
  public static final String DB_VERSION_CURRENT = "com.styrdal.dbVersionCurrent";
  public static final String DB_VERSION_NEWEST = "com.styrdal.dbVersionNewest";
  
  private final int VERSION = 15;

  public RestaurantsDBHelper(Context context, String dbName, String assetPath) throws IOException {
    super(context, dbName, null, 1);
    this.context = context;
    this.assetPath = assetPath;
    this.dbPath = context.getApplicationInfo().dataDir + "/databases/" + dbName;

    checkExists();
    checkUpdated();
  }

  /**
   * Checks if the database asset needs to be copied and if so copies it to the
   * default location.
   * 
   * @throws IOException
   */
  private void checkExists() throws IOException {
    Log.i(TAG, "checkExists()");

    File dbFile = new File(dbPath);

    if (!dbFile.exists()) {

      Log.i(TAG, "creating database..");

      dbFile.getParentFile().mkdirs();
      copyStream(context.getAssets().open(assetPath), new FileOutputStream(
          dbFile));

      Log.i(TAG, assetPath + " has been copied to " + dbFile.getAbsolutePath());
    }

  }

  private void copyStream(InputStream is, OutputStream os) throws IOException {
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
  
  
  //Comparing VERSION to version number of the database, copying database if not matching
  private void checkUpdated() throws IOException
  {
	  SharedPreferences settings = context.getSharedPreferences(RestaurantsDBHelper.DB_VERSION_NAME, 0);
	  int currentVersion = settings.getInt(DB_VERSION_CURRENT, 0);
	  if(currentVersion < VERSION)
	  {
	  File dbFile = new File(dbPath);
	  
	  SQLiteDatabase db = this.getWritableDatabase();
	  
	  String[] cursorProjection = {"_id", "version"};
	  String sortOrder = "_id ASC";
	  
	  Cursor c = db.query("version", cursorProjection, null, null, null, null, sortOrder);
	  
	  c.moveToFirst();
	  
	  int dbVersion = c.getInt(c.getColumnIndex("version"));
	  Log.i(TAG, "Checking if " + dbPath + " is updated.");
	  
	  Log.i(TAG, "Current version: " + Integer.toString(dbVersion));
	  Log.i(TAG, "Newest version: " + Integer.toString(VERSION));
	  
	  if(dbVersion < VERSION)
	  {
			  Log.i(TAG, "updating database...");
		
			  dbFile.getParentFile().mkdirs();
			  copyStream(context.getAssets().open(assetPath), new FileOutputStream(dbFile));
		
			  Log.i(TAG, assetPath + " has been copied to " + dbFile.getAbsolutePath());
		  }
		  db.close();
	  }
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
  }
}