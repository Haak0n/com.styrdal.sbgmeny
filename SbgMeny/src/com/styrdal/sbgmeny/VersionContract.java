package com.styrdal.sbgmeny;

import android.provider.BaseColumns;

public class VersionContract {
	
	public VersionContract() {}
	
	public static abstract class VersionEntry implements BaseColumns
	{
		public static final String COLUMN_NAME_VERSION = "version";
		
		public static final String TABLE_NAME = "version";
		
	}

}