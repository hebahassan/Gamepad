package com.gamepad;

import bluetooth_Connection.TouchPad_listener;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class Slideshow extends Activity {

	//public SharedPreferences share;
	ActionBar actionbar;
	ImageButton play,stop,next,prev,fade;
	int clicked = 0;
	//public int chek=0 , time = 47;
	//int name,enbl;
	//public static final String MY_PREFS_NAME = "MyPrefsFile";//abdjdkdkklff
	//protected static final int TIME  = 45;
	//protected static final int ENABLE = 1;

	//Prefs an = new Prefs();
	@SuppressLint("NewApi")
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.slideshow_layout);
		
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1C1C1C")));
		actionbar = getActionBar();	 
		actionbar.setIcon(R.drawable.ic_logo);
		actionbar.setDisplayHomeAsUpEnabled(true);
		
		play = (ImageButton) findViewById(R.id.playbtn);
		stop = (ImageButton) findViewById(R.id.stopbtn);
		next = (ImageButton) findViewById(R.id.nxtbtn);
		prev = (ImageButton) findViewById(R.id.backbtn);
		fade = (ImageButton) findViewById(R.id.fadebtn);
		
		final TouchPad_listener Tlis = new TouchPad_listener();
		/*share = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE); 
	    Editor editor = share.edit();
	    
	    
	    chek = Prefs.check;
	    time = SeekBarPreference.mCurrentValue;
	    
	    Log.i("abdp", chek+" : abdooo");
		 Log.i("abdp", time+" :abdooo");
	    
	    if (chek !=0 || time !=47 )
	    {
	    	Log.i("abdp", chek+" : no");
			 Log.i("abdp", time+" :no");
	    	editor.putInt("ENABLE", Prefs.check);        // Saving integer
	    	editor.putInt("TIME", SeekBarPreference.mCurrentValue);        // Saving integer
	    	editor.commit();
	    }
	    
	 // If value for key not exist then return second param value - In this case null
	
	    	chek = share.getInt("ENABLE", 0);   // getting Integer
	    	time = share.getInt("TIME", 47);
	    
	    
		share = getSharedPreferences(MY_PREFS_NAME,
				Context.MODE_PRIVATE);
		
		if (share.getInt("ENABLE", 1) != 1)
	
			
			
		
		
		SharedPreferences prefs = getSharedPreferences(s, MODE_PRIVATE); 
		String restoredText = prefs.getString("text", null);
		if (restoredText != null) {
		   name = prefs.getInt("time", 45);//"No name defined" is the default value.
		   enbl = prefs.getInt("enble", 1); //0 is the default value.
		}
		
		// chek = Prefs.check;
		//time= SeekBarPreference.mCurrentValue;
	    	Log.i("abdp", chek+" : yes");
			 Log.i("abdp", time+" :yes");
		 vibrate(chek);*/

		
		
		
		
		play.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Tlis.F5();
			}
		});
		
		stop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Tlis.Esc();
				
			}
		});
		
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Tlis.down();
				
			}
		});
		
		
		prev.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Tlis.up();
				
			}
		});
		
		fade.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clicked++;
				
				if((clicked % 2) != 0)
					fade.setImageResource(R.drawable.slide_fade_2);
				else
					fade.setImageResource(R.drawable.slide_fade_3);
				
			}
		});
		
		
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case android.R.id.home:
			Intent parentActivityIntent = new Intent(this, MainActivity.class);
            parentActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(parentActivityIntent);
            overridePendingTransition(R.animator.enterance_shrink_rotate,R.animator.exit_shrink_rotate);
            return true;
		}
		return super.onOptionsItemSelected(item);
	}	
	
	public void vibrate(int check) {
		
		
		if (check == 1)
		{
			Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE) ;
			v.vibrate(100);
		}
	}
	
}
