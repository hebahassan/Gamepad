package gameBad_EDIT;

import java.util.ArrayList;

import localDataBase.GLOBAL_DB;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.gamepad.Gamepad;
import com.gamepad.R;
import com.gamepad.gamepad_Global;

public class list_activity extends Activity {

    ArrayList<String> toReturn ;
    ArrayList<String> btn ;
    public Context list_activity_context;
    ArrayList<Button> del = new ArrayList<>();
   public ArrayList<String> details ;
   public SwipeListView mListView;
   public SwipeAdapter adapter;
public static boolean IS_EXIST;

public static String tbl_nm;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//	setContentView(R.layout.list_activity);
	    //  ListView animalList=(ListView)findViewById(R.id.list);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		 setContentView(R.layout.swipe_listview);
		 toReturn = new ArrayList<String>();
        getDBTables();
        list_activity_context = getApplicationContext();
        mListView = (SwipeListView)findViewById(R.id.listview);
        adapter = new SwipeAdapter(this, toReturn ,mListView.getRightViewWidth(),
                new SwipeAdapter.IOnItemRightClickListener() {
                    @Override
                    public void onRightClick(View v, int position) {
                        // TODO Auto-generated method stub
                    	Delete_layout(toReturn.get(position),list_activity.this);
                   // 	 onBackPressed() ;
                    	adapter.remove(position);
                    //	adapter.Hide(SwipeAdapter.delete_left, list_activity.this);
                    //	adapter.notifyDataSetChanged();
                    }
                });
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				for (int i = 0; i < Gamepad.mydraw.getChildCount(); i++) {
					if (Gamepad.mydraw.getChildAt(i) instanceof Button) {
						Button insert_v;
						insert_v = (Button) Gamepad.mydraw.getChildAt(i);
						Log.i(""+insert_v.getText(), "2bl m ams7");
						del.add(insert_v);
						
					}
				}
				for(int a=0;a<del.size();a++){
					Gamepad.mydraw.removeView(del.get(a));
				}
   	            Gamepad.Hide_popUpWindow();
				 String selectedAnimal;
				 selectedAnimal=toReturn.get(position);
				 Gamepad.the_last_lay = selectedAnimal;
                get_table_contetnt(selectedAnimal);
                onBackPressed() ;
               Toast.makeText(getApplicationContext(), "Layout Selected : "+selectedAnimal,   Toast.LENGTH_LONG).show();
				
			}	
			
    });
  
	}
	public void Delete_layout(final String layout,Context con){
        //Put up the Yes/No message box
    	AlertDialog.Builder builder = new AlertDialog.Builder(con);
    	builder
    	.setTitle("Delete '"+layout+"' !")
    	.setMessage("Are you sure?")
    	.setIcon(android.R.drawable.ic_dialog_alert)
    	.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int which) {			      	
    	    	//Yes button clicked, do something
    	    	onBackPressed();
    	    	GLOBAL_DB.Delete_table(layout);
    	    	Intent again = new Intent(Gamepad.context,list_activity.class);
    	    	again.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    			startActivity(again);
    	    }
    	})
    .setNegativeButton("No", new DialogInterface.OnClickListener() {
		
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				adapter.notifyDataSetChanged();
			}
		})
    	.show();
	}
	public void get_table_contetnt(String table_name){
		tbl_nm = table_name;
		try {
    	Cursor c = GLOBAL_DB.db.rawQuery("SELECT COLUMN_view_name FROM "+table_name+" ", null);
        if (c.getCount() >0 ) {
        	btn = new ArrayList<String>();
            while ( c.moveToNext() ) {
                btn.add( c.getString(c.getColumnIndex("COLUMN_view_name")) );
            }
            Log.d(""+btn, "NAMES");
            
        }
        select_details(); 
	}
        catch(SQLiteException e) {
            e.printStackTrace();
        }
	}
	
	public void select_details(){

		//Gamepad.mydraw.removeAllViews();
		for(int i = 0 ; i<btn.size();i++){
	    	details = new ArrayList<String>();

			try {
		    	Cursor c = GLOBAL_DB.db.rawQuery("SELECT COLUMN_X , COLUMN_Y ,COLUMN_drawable FROM "+tbl_nm+" WHERE COLUMN_view_name = '"+ btn.get(i) +"'", null);
		        if (c.getCount() >0 ) {
		            while ( c.moveToNext() ) {
		            	details.add( btn.get(i) );
		            	details.add( Float.toString(c.getFloat(c.getColumnIndex("COLUMN_X"))) );
		            	details.add( Float.toString(c.getFloat(c.getColumnIndex("COLUMN_Y"))) );
		            //	details.add( c.getString(c.getColumnIndex("COLUMN_drawable")) );
		            }
		            Log.d(""+details, "NAMES");
		       }
		        
			}
			  catch(SQLiteException e) {
		            e.printStackTrace();
		        }
			set_lay(details);
		}
	}
	
	public void set_lay(ArrayList<String> name_btn){
		Button bt ;
		String name = name_btn.get(0);
		float x = Float.parseFloat(name_btn.get(1)) , y = Float.parseFloat(name_btn.get(2));
			bt = GLOBAL_DB.ob.createButton(Gamepad.context, name,Buttons_choice.Position_of_listActivity);
			bt.setX(x);
			bt.setY(y);
			//MainActivity.mydraw.removeAllViews();
			Gamepad.mydraw.addView(bt);
			GLOBAL_DB.ob.Img_Bk(bt, bt.getText().toString());
			GLOBAL_DB.ob.Operaion(bt, bt.getText().toString());
bt.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.i("abdp", Gamepad.chek+" : yes");
					 Log.i("abdp", Gamepad.time+" :yes");
					 gamepad_Global.gp.vibrate(Gamepad.chek,Gamepad.time);
					
				}
			});
	}

    public void getDBTables() {
     //   ArrayList<String> toReturn = new ArrayList<String>();
        try {
        	Cursor c = GLOBAL_DB.db.rawQuery("SELECT name FROM sqlite_sequence ", null);

            if (c.moveToFirst()) {
                while ( !c.isAfterLast() ) {
                    toReturn.add( c.getString( 0) );
                    c.moveToNext();
                }
            }
        }
        catch(SQLiteException e) {
            e.printStackTrace();
        }
    	Log.i("table: "+toReturn,"TABLE");

      //  return toReturn;
  }
	public void is_exist(String table_name){
	ArrayList<String> T_N = new ArrayList<String>();
		 try {
	        	Cursor c = GLOBAL_DB.db.rawQuery("SELECT name FROM sqlite_sequence ", null);

	            if (c.moveToFirst()) {
	                while ( !c.isAfterLast() ) {
	                	T_N.add( c.getString( 0) );
	                    c.moveToNext();
	                }
	            }
	            for(int a =0;a<T_N.size();a++){
	            	if(T_N.get(a).equals(table_name)){
	            		IS_EXIST = true;
	            	}
	            	else
	            		IS_EXIST = false;

	            }
	        }
	        catch(SQLiteException e) {
	            e.printStackTrace();
	        }
	    	Log.i("table: "+T_N,"TABLE");
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
	//	getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
	//	MainActivity.datasource.open();
	}

	@Override
	protected void onPause() {
		super.onPause();
	//	MainActivity.datasource.close();
	}

	

	

}
