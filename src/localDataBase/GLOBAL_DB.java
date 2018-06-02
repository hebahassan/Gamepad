package localDataBase;

import gameBad_EDIT.Buttons_choice;
import gameBad_EDIT.list_activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import bluetooth_Connection.TouchPad_listener;

import com.gamepad.Gamepad;

public class GLOBAL_DB {
	public static TextView mTitleTextView;
	public static View mCustomView;
	public static SQLiteDatabase db;
	public static Buttons_choice ob = new Buttons_choice();
	public static list_activity la = new list_activity();
	public static TouchPad_listener Tlis = new TouchPad_listener();


	
	public static void Open_DB(Context context)
	{
		db = context.openOrCreateDatabase("MY_JOYSTICKS.db", Context.MODE_PRIVATE, null);
		Log.i("DataBase Open", "Global DB");
	}
	
	public static void Delete_table(String name){
		db.execSQL("DROP TABLE IF EXISTS " + name);
	}
	
	public void Create_layout(){
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ Gamepad.new_lay
				+ " (COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, COLUMN_view_name VARCHAR, COLUMN_drawable VARCHAR, COLUMN_X  VARCHAR,COLUMN_Y VARCHAR )");
	}
	
	public void Insert(float x, float y, String pic_name, String pic_draw) {

		db.execSQL("INSERT INTO "
				+ Gamepad.new_lay
				+ "(COLUMN_X  , COLUMN_Y  , COLUMN_drawable , COLUMN_view_name ) VALUES ('"
				+ x + "','" + y + "','" + pic_draw + "','" + pic_name + "');");


	}

}
