package com.gamepad;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import bluetooth_Connection.TouchPad_listener;

public class Pdf extends Activity implements OnClickListener{
	
	/** Called when the activity is first created. */
	ActionBar actionbar;
	Button nxt,prvs, st;

	//public static TextView mTitleTextView;
    @SuppressLint({ "NewApi", "InflateParams", "ClickableViewAccessibility" })
	@Override
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf);
   		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1C1C1C")));
		
		nxt = (Button) findViewById(R.id.nxt);
		prvs = (Button) findViewById(R.id.prvs);
		st = (Button) findViewById(R.id.srt);
		
		prvs.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				TouchPad_listener Tlis = new TouchPad_listener();
 
				Tlis.up();

			}
		});
	/*	nxt.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View view, MotionEvent event) {
				TouchPad_listener Tlis = new TouchPad_listener();

				Tlis.up(event);
				return true;
			}
		});*/
		
		nxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				TouchPad_listener Tlis = new TouchPad_listener();

				Tlis.down();

			}
		});
	/*	prvs.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View view, MotionEvent event) {
				TouchPad_listener Tlis = new TouchPad_listener();

				Tlis.down(event);
				return true;
			}
		});*/
		
		
		
		
		
		
		
		
        
        
        


    }
    
    

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
