package localDataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
	
	public static final String DATABASE_NAME = "FILEMANGER";
	public static final int DATABASE_VERSION = 1;
	public static final String TABLE_NAME = "LocalDrives";
	public static final String COLUMN_ID = "ID";
	public static final String COLUMN_NAME = "Name";
	public static final String COLUMN_PATH = "Path";
	
	
	
	public static final String TABLE_CREATE =
			"CREATE TABLE " + TABLE_NAME +" ( "+
			COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
			COLUMN_NAME + " TEXT,"+
			COLUMN_PATH + " TEXT"+
			" );";
		
				
	
	

	public DatabaseOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);

	}

}
