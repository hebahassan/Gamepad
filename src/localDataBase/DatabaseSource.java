package localDataBase;

import java.util.ArrayList;

import wifi_connection.classes.fileManger;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseSource {

	DatabaseOpenHelper dbHelper;
	SQLiteDatabase database;
	boolean check = false;

	@SuppressWarnings("unused")
	private final static String[] AllColumns = new String[] {
			DatabaseOpenHelper.COLUMN_ID, DatabaseOpenHelper.COLUMN_NAME,
			DatabaseOpenHelper.COLUMN_PATH, };

	public DatabaseSource(Context context) {
		dbHelper = new DatabaseOpenHelper(context);
	}

	public void open() {
		database = dbHelper.getWritableDatabase();
	}

	public void openForRead() {
		database = dbHelper.getReadableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	/* true if exist. */
	public boolean CheckTable(String name) {
		boolean check = false;
		String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name = '"
				+ name + "';";
		Cursor cursor = database.rawQuery(sql, null);

		if (cursor.getCount() > 0)
			check = true;

		return check;
	}

	public void CreateFolderTable(String name) {
		String Creation = "CREATE TABLE " + name + "( "
				+ DatabaseOpenHelper.COLUMN_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ DatabaseOpenHelper.COLUMN_NAME + " TEXT,"
				+ DatabaseOpenHelper.COLUMN_PATH + " TEXT" + " );";
		database.execSQL(Creation);
	}

	public ArrayList<fileManger> retrieve(final String tableName) {

		final ArrayList<fileManger> fms = new ArrayList<fileManger>();
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				String query = "SELECT * FROM " + tableName;
				Cursor cursor = database.rawQuery(query, null);
				// Cursor cursor = database.query(tableName,AllColumns, null,
				// null, null, null, null, null);
				if (cursor.getCount() > 0) {
					while (cursor.moveToNext()) {
						fileManger fm = new fileManger();
						fm.setID(cursor.getLong(cursor
								.getColumnIndex(DatabaseOpenHelper.COLUMN_ID)));
						fm.setName(cursor.getString(cursor
								.getColumnIndex(DatabaseOpenHelper.COLUMN_NAME)));
						fm.setPath(cursor.getString(cursor
								.getColumnIndex(DatabaseOpenHelper.COLUMN_PATH)));
						fms.add(fm);
					}
				}

			}
		});
		t.start();
		return fms;

	}

	public ArrayList<String> retrieveNames(String tableName) {
		ArrayList<String> names = new ArrayList<String>();
		String query = "SELECT " + DatabaseOpenHelper.COLUMN_NAME + " FROM "
				+ tableName;
		Cursor cursor = database.rawQuery(query, null);
		// Cursor cursor =
		// database.query(tableName,AllColumns,null,null,null,null,null);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext())
				names.add(cursor.getString(cursor
						.getColumnIndex((DatabaseOpenHelper.COLUMN_NAME))));
		}
		return names;
	}

	/* insert subfolder in folder or drive table */
	public boolean insert(final fileManger fm, final String tableName)
			throws InterruptedException {

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				ContentValues values = new ContentValues();

				values.put(DatabaseOpenHelper.COLUMN_NAME, fm.getName());
				values.put(DatabaseOpenHelper.COLUMN_PATH, fm.getPath());

				long insertId = database.insert(tableName,
						DatabaseOpenHelper.COLUMN_ID, values);
				fm.setID(insertId + 1);
				if (insertId > 0)
					check = true;

			}
		});
		t.start();
		return check;

	}

	public boolean EmptyTable(String TableName) {
		check = false;
		String query = "SELECT * FROM " + TableName;
		Cursor cursor = database.rawQuery(query, null);
		if (cursor.getCount() == 0)
			check = true;
		return check;
	}

	public String getFirstElement(String tableName) {
		String str = "no thing there!";
		String sql = "SELECT " + DatabaseOpenHelper.COLUMN_NAME + " FROM "
				+ tableName;
		Cursor cursor = database.rawQuery(sql, null);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext())
				str = cursor.getString(cursor
						.getColumnIndex(DatabaseOpenHelper.COLUMN_NAME));

		}
		return str;
	}

	public void Delete() {
		String sql = "DELETE From " + DatabaseOpenHelper.TABLE_NAME;

		database.execSQL(sql);
	}

}
