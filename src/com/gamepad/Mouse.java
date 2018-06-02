package com.gamepad;

import wifi_connection.classes.Client;
import wifi_connection.classes.Wifi_Global;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import bluetooth_Connection.TouchPad_listener;

public class Mouse extends Activity implements OnClickListener{
	
	/** Called when the activity is first created. */
	ActionBar actionbar;
	// Layout applications
	private ImageView mImageView,scroll;
    private Button mLeftClick;
    @SuppressWarnings("unused")
	private Button mRightClick,Upclick,Downclick;
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
    public static Context mouse_context;
    public static Activity mouse_activity;
    public SharedPreferences mousespeed;
   	public static String speed=null;	
   	float mousepeed,Mouse_speed;
   	boolean check=true;
//	public static TextView mTitleTextView;

	//public static TextView mTitleTextView;
    @SuppressLint({ "NewApi", "InflateParams", "ClickableViewAccessibility" })
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mouse_layout);
   		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1C1C1C")));
		
		mouse_context = Mouse.this;
		mouse_activity = Mouse.this;
		
        
        mImageView = (ImageView) findViewById(R.id.touchView);
        scroll = (ImageView) findViewById(R.id.scroll);

        
        mLeftClick = (Button) findViewById(R.id.leftClick);
        TouchPad_listener.left = mLeftClick;
        
        mRightClick = (Button) findViewById(R.id.rightClick);
        TouchPad_listener.right = mRightClick;
		
		actionbar = getActionBar();
		actionbar.setIcon(R.drawable.ic_logo);
		actionbar.setDisplayHomeAsUpEnabled(true);

		 /*Elbluetooth*/
	 //LayoutInflater mInflater = LayoutInflater.from(this);
	// View mCustomView = mInflater.inflate(R.layout.custom_title , null);
	// MainActivity.mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_right_text);
	/*	MainActivity.mInflater = LayoutInflater.from(this);
		MainActivity.mCustomView =  MainActivity.mInflater.inflate(R.layout.custom_title , null);
		MainActivity.mTitleTextView = (TextView) MainActivity.mCustomView.findViewById(R.id.title_right_text);
	 actionbar.setCustomView(MainActivity.mCustomView);
	 actionbar.setDisplayShowCustomEnabled(true);*/
	 /*a5er elbluetooth*/
		
		
//////////////////////////////////////////////////////////////////////////////////////////
mousespeed = PreferenceManager.getDefaultSharedPreferences(this); 
//SharedPreferences.Editor editor = mousespeed.edit();




speed = mousespeed.getString("listPref", "0.5");
//Toast.makeText(getApplicationContext(), speed, Toast.LENGTH_LONG).show();
mousepeed=Float.valueOf(speed);

if(check==true){
Mouse_speed=mousepeed;
check=false;
}
	 
	      // Touchpad - listeners
mImageView.setOnTouchListener(new OnTouchListener() {
    @SuppressLint("ClickableViewAccessibility") @Override
    public boolean onTouch(View view, MotionEvent event) {
		switch (event.getAction())
		{	
			case MotionEvent.ACTION_DOWN:
			{
				gamepad_Global.Tlis.OnActionDown(event);
				break;
			}
			case MotionEvent.ACTION_MOVE:
			{    					
				gamepad_Global.Tlis.OnActionMove(event,Mouse_speed);
	         	break;
			}
				
			case MotionEvent.ACTION_UP:
			{	 
				gamepad_Global.Tlis.OnActionUp(event);
				break;
			}

			default:
				break;
		}
		return true;
    }
});
scroll.setOnTouchListener(new OnTouchListener() {
	    @SuppressLint("ClickableViewAccessibility") @Override
	    public boolean onTouch(View view, MotionEvent event) {
			switch (event.getAction())
			{	
  			case MotionEvent.ACTION_DOWN:
				{
					//touchpadOnActionDown(event); 
					gamepad_Global.Tlis.OnActionDown(event);
					break;
				}
				case MotionEvent.ACTION_MOVE:
				{    					
					//touchpadOnActionMove(event);
					gamepad_Global.Tlis.scroll(event);
		         	break;
				}
					
				case MotionEvent.ACTION_UP:
				{	 
				//	touchpadOnActionUp(event);
					gamepad_Global.Tlis.OnActionUp(event);
					break;
				}

				default:
					break;
			}
			return true;
	    }
	});


mLeftClick.setOnTouchListener(new OnTouchListener() { 
    @Override
    public boolean onTouch(View view, MotionEvent event) {
		switch (event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
			{
				gamepad_Global.Tlis.leftClickDown(event);
				break;
			}

			case MotionEvent.ACTION_UP:
			{
				gamepad_Global.Tlis.leftClickUp(event);
				break;
			}
				
			default:
				break;
		}
		return false;
    }
});      

mRightClick.setOnTouchListener(new OnTouchListener() {
	
	@Override
	public boolean onTouch(View view, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			gamepad_Global.Tlis.rightClickDown(event);
			break;
		}
		case MotionEvent.ACTION_UP: {
			gamepad_Global.Tlis.rightClickUp(event);
			break;
		}

		default:
			break;
		}
		return false;
	}
});

}
    
    
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);
 
        return super.onCreateOptionsMenu(menu);
    }*/
    
    @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
	  //return super.onOptionsItemSelected(item);
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void onStop() {
		if (gamepad_Global.connectionType == gamepad_Global.WIFI_CONNECTION) {
//			Toast.makeText(getApplicationContext(), "turrned off", Toast.LENGTH_LONG).show();
			Client client = Wifi_Global.client;
			try {
				client.sendMsg("Mouse is turned off");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		super.onStop();
	}
}
