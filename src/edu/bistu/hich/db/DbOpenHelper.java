package edu.bistu.hich.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/** 
 * @ClassName: DbOpenHelper 
 * @Description: create or update tables of the specific database. 
 * @author 仇之东  hich.cn@gmail.com 
 * @date May 17, 2014 4:29:36 PM 
 *  
 */ 
public class DbOpenHelper extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "logs.db";
	private static final int DATABASE_VERSION = 1;

	public DbOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String logsTable = "CREATE TABLE logs (" +
				"\"id\" integer NOT NULL PRIMARY KEY AUTOINCREMENT," +
				"\"name\" text," +
				"\"number\" text NOT NULL," +
				"\"duration\" integer NOT NULL," +
				"\"date\" text NOT NULL," +
				"\"type\" integer NOT NULL," +
				"\"uid\" text NOT NULL," +
				"\"record_flag\" boolean NOT NULL," +
				"\"record_state_flag\" boolean NOT NULL," +
				"\"log_state_flag\" integer NOT NULL," +
				"\"update_date\" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP)";
		db.execSQL(logsTable);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS logs";
		db.execSQL(sql);
		onCreate(db);
	}
	
}
