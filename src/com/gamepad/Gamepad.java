package com.gamepad;

import gameBad_EDIT.list_activity;

import java.util.ArrayList;

import localDataBase.GLOBAL_DB;
import wifi_connection.classes.Client;
import wifi_connection.classes.Wifi_Global;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;
import bluetooth_Connection.TouchPad_listener;

public class Gamepad extends Activity implements SensorEventListener {
	/** Called when the activity is first created. */
	ActionBar actionbar;

	boolean flag = false;
	int clicked = 0;

	public static RelativeLayout mydraw;
	public static Context context;
	public static Context context_this;
	EditText new_name;
	Button svDialog;
	int check_vibrate;

	/* Edit & Save */
	public static ArrayList<Button> resume = new ArrayList<>();
	public static String the_last_lay = null;
	public static boolean move = false;
	public static boolean edit_btn = false;
	public static boolean delete = false;
	public static boolean save = true;
	public static int width_btn, height_btn, select_h, select_w;
	public static Button which_edit;
	public static String new_lay;
	public static String what_do = "empty";
	public static float x_sensor, y_sensor;
	public static PopupWindow popupWindow;
	public static ImageButton editbtn;
	/* sensor */
	SensorManager sensorManager;
	long eventTime;
	public boolean sensor_check;
	ImageView sensorchek;

	public boolean startSensor = false;

	private Button btnA, btnB, btnX, btnY, btnStart, btnSelect;
	public Drawable up, down, right, left, center;
	RelativeLayout layout_joystick;
	RelativeLayout layot_directions;
	JoyStickClass js;
	JoyStickClass dir;
	public View popupView;
	public SharedPreferences share;
	public static Vibrator v;
	public static int chek=1 , time =  95;	

	@SuppressLint({ "NewApi", "InflateParams" })
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gamepad_layout);
		context = getApplicationContext();
		context_this = Gamepad.this;
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1C1C1C")));
		GLOBAL_DB.Open_DB(getApplicationContext());
		init();
		js.Center();
		dir.Center();
		actionbar = getActionBar();
		actionbar.setIcon(R.drawable.ic_logo);
		actionbar.setDisplayHomeAsUpEnabled(true);
    	v = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE) ;
		LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		popupView = layoutInflater.inflate(R.layout.edit_layout, null);
		popupView.setAnimation(AnimationUtils.loadAnimation(this, R.animator.animation3));
		popupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
				
		share = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE); 
	    Editor editor = share.edit();
	    
	    
	    chek = Prefs.check;
	    time = SeekBarPreference.mCurrentValue;
	    
	    
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
	 	

		actionbar.setDisplayHomeAsUpEnabled(true);
		final TouchPad_listener Tlis = new TouchPad_listener();
	
		mydraw.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//if(mydraw.isInTouchMode()){
					if(move||delete||edit_btn&&!save){
					Log.e("das 3la ellayout", "das");
					Show_popUpWindow();
					}
			//	}
				
			}
		});
		btnX.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View view, MotionEvent event) {
				// TouchPad_listener Tlis = new TouchPad_listener();
				// if (event.getAction() == MotionEvent.ACTION_DOWN)
				Tlis.Click_X();
				if (event.getAction() == MotionEvent.ACTION_UP)
					Tlis.X_stop();
				return false;
			}
		});

		btnX.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				switch (what_do) {
				case "delete":
					GLOBAL_DB.ob.Yes_No_option(btnX, Gamepad.this);
					break;
				case "move":
					GLOBAL_DB.ob.Move_Button(btnX);

					break;
				case "editButton":
					Intent editIntent = new Intent(getApplicationContext(),
							gameBad_EDIT.Buttons_choice.class);
					Bundle b = new Bundle();
					b.putString("what", "edit");
					b.putFloat("x", btnX.getX());
					b.putFloat("Y", btnX.getY());
					which_edit = btnX;
					editIntent.putExtras(b);
					startActivity(editIntent);
					break;
				case "empty":
					btnX.setOnKeyListener(new OnKeyListener() {

						@Override
						public boolean onKey(View v, int keyCode, KeyEvent event) {
							// if (event.getAction() == MotionEvent.ACTION_DOWN)
							Tlis.Click_X();
							if (event.getAction() == MotionEvent.ACTION_UP) {
								Tlis.X_stop();
								Log.i("Buttons","buton x in key listener on release.");
							}
							return false;
						}
					});
					break;
				default:
					break;
				}
				return false;
			}

		});

		btnX.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN)
					Tlis.Click_X();
				if (event.getAction() == MotionEvent.ACTION_UP)
					Tlis.X_stop();
				return false;
			}
		});
		btnX.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("abdp", chek+" : yes");
				 Log.i("abdp", time+" :yes");
			 vibrate(chek,time);
			}
		});

		btnA.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				Tlis.Click_A(event);
				if (event.getAction() == MotionEvent.ACTION_UP)
					Tlis.A_stop();
				return false;
			}
		});
		btnA.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {

				Log.i("" + btnA.getText(), "onlong");
				Log.i("" + delete, "DELETE");
				if (delete == true) {
					Log.i("" + btnA.getText(), "DELETE");
					GLOBAL_DB.ob.Yes_No_option(btnA, Gamepad.this);
				} else if (move == true) {
					GLOBAL_DB.ob.Move_Button(btnA);
				} else if (edit_btn == true) {
					Intent editIntent = new Intent(getApplicationContext(),
							gameBad_EDIT.Buttons_choice.class);
					Bundle b = new Bundle();
					b.putString("what", "edit");
					b.putFloat("x", btnA.getX());
					b.putFloat("Y", btnA.getY());
					which_edit = btnA;
					editIntent.putExtras(b);
					startActivity(editIntent);
				} else if (what_do == "empty") {
					btnA.setOnTouchListener(new OnTouchListener() {

						@Override
						public boolean onTouch(View v, MotionEvent event) {
							// if (event.getAction() == MotionEvent.ACTION_DOWN)
							Tlis.Click_A();
							if (event.getAction() == MotionEvent.ACTION_UP)
								Tlis.A_stop();
							return false;
						}
					});
				}
				return false;
			}
		});
		btnA.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("abdp", chek+" : yes");
				 Log.i("abdp", time+" :yes");
				 vibrate(chek,time);
				
			}
		});
		
		/*
		 * btnA.setOnKeyListener(new OnKeyListener() {
		 * 
		 * @Override public boolean onKey(View v, int keyCode, KeyEvent event) {
		 * if (event.getAction() == MotionEvent.ACTION_DOWN) Tlis.Click_A(); if
		 * (event.getAction() == MotionEvent.ACTION_UP) Tlis.A_stop(); return
		 * false; } });
		 */

		btnB.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				Tlis.Click_B(event);
				if (event.getAction() == MotionEvent.ACTION_UP)
					Tlis.B_stop();
				return false;
			}
		});
		btnB.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {

				Log.i("" + btnB.getText(), "onlong");
				Log.i("" + delete, "DELETE");
				if (delete == true) {
					Log.i("" + btnB.getText(), "DELETE");
					GLOBAL_DB.ob.Yes_No_option(btnB, Gamepad.this);
				} else if (move == true) {
					GLOBAL_DB.ob.Move_Button(btnB);
				} else if (edit_btn == true) {
					Intent editIntent = new Intent(getApplicationContext(),
							gameBad_EDIT.Buttons_choice.class);
					Bundle b = new Bundle();
					b.putString("what", "edit");
					b.putFloat("x", btnB.getX());
					b.putFloat("Y", btnB.getY());
					which_edit = btnB;
					editIntent.putExtras(b);
					startActivity(editIntent);

				} else if (what_do == "empty") {
					btnB.setOnTouchListener(new OnTouchListener() {

						@Override
						public boolean onTouch(View v, MotionEvent event) {
							// if (event.getAction() == MotionEvent.ACTION_DOWN)
							Tlis.Click_B();
							if (event.getAction() == MotionEvent.ACTION_UP)
								Tlis.B_stop();
							return false;
						}
					});
				}
				return false;
			}
		});
		
		btnB .setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("abdp", chek+" : yes");
				 Log.i("abdp", time+" :yes");
			 vibrate(chek,time);
			}
		});
		/*
		 * btnB.setOnKeyListener(new OnKeyListener() {
		 * 
		 * @Override public boolean onKey(View v, int keyCode, KeyEvent event) {
		 * if (event.getAction() == MotionEvent.ACTION_DOWN) Tlis.Click_B(); if
		 * (event.getAction() == MotionEvent.ACTION_UP) Tlis.B_stop(); return
		 * false; } });
		 */

		btnY.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				Tlis.Click_Y(event);
				if (event.getAction() == MotionEvent.ACTION_UP)
					Tlis.Y_stop();
				return false;
			}
		});
		btnY.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {

				Log.i("" + btnY.getText(), "onlong");
				Log.i("" + delete, "DELETE");
				if (delete == true) {
					Log.i("" + btnY.getText(), "DELETE");
					GLOBAL_DB.ob.Yes_No_option(btnY, Gamepad.this);
				} else if (move == true) {
					GLOBAL_DB.ob.Move_Button(btnY);
				} else if (edit_btn == true) {
					Intent editIntent = new Intent(getApplicationContext(),
							gameBad_EDIT.Buttons_choice.class);
					Bundle b = new Bundle();
					b.putString("what", "edit");
					b.putFloat("x", btnY.getX());
					b.putFloat("Y", btnY.getY());
					which_edit = btnY;
					editIntent.putExtras(b);
					startActivity(editIntent);

				} else if (what_do == "empty") {
					btnY.setOnTouchListener(new OnTouchListener() {

						@Override
						public boolean onTouch(View v, MotionEvent event) {
							// if (event.getAction() == MotionEvent.ACTION_DOWN)
							Tlis.Click_Y();
							if (event.getAction() == MotionEvent.ACTION_UP)
								Tlis.Y_stop();
							return false;
						}
					});
				}
				return false;
			}
		});
		btnY.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN)
					Tlis.Click_Y();
				if (event.getAction() == MotionEvent.ACTION_UP)
					Tlis.Y_stop();
				return false;
			}
		});
		
		btnY.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("abdp", chek+" : yes");
				 Log.i("abdp", time+" :yes");
			 vibrate(chek,time);
			}
		});
		
		
		btnStart.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View view, MotionEvent event) {
				TouchPad_listener Tlis = new TouchPad_listener();

				Tlis.start(event);
				return false;
			}
		});
		btnStart.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {

				Log.i("" + btnStart.getText(), "onlong");
				Log.i("" + delete, "DELETE");
				if (delete == true) {
					Log.i("" + btnStart.getText(), "DELETE");
					GLOBAL_DB.ob.Yes_No_option(btnStart, Gamepad.this);
				} else if (move == true) {
					GLOBAL_DB.ob.Move_Button(btnStart);
				} else if (edit_btn == true) {
					Intent editIntent = new Intent(getApplicationContext(),
							gameBad_EDIT.Buttons_choice.class);
					Bundle b = new Bundle();
					b.putString("what", "edit");
					b.putFloat("x", btnStart.getX());
					b.putFloat("Y", btnStart.getY());
					which_edit = btnStart;
					editIntent.putExtras(b);
					startActivity(editIntent);
				}
				return false;
			}
		});
		
		btnStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("abdp", chek+" : yes");
				 Log.i("abdp", time+" :yes");
			 vibrate(chek,time);
			}
		});
		
		
		btnSelect.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View view, MotionEvent event) {
				TouchPad_listener Tlis = new TouchPad_listener();

				Tlis.select(event);
				return false;
			}
		});
		btnSelect.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {

				Log.i("" + btnSelect.getText(), "onlong");
				Log.i("" + delete, "DELETE");
				if (delete == true) {
					Log.i("" + btnSelect.getText(), "DELETE");
					GLOBAL_DB.ob.Yes_No_option(btnSelect, Gamepad.this);
				} else if (move == true) {
					GLOBAL_DB.ob.Move_Button(btnSelect);
				} else if (edit_btn == true) {
					Intent editIntent = new Intent(getApplicationContext(),
							gameBad_EDIT.Buttons_choice.class);
					Bundle b = new Bundle();
					b.putString("what", "edit");
					b.putFloat("x", btnSelect.getX());
					b.putFloat("Y", btnSelect.getY());
					which_edit = btnSelect;
					editIntent.putExtras(b);
					startActivity(editIntent);
				}
				return false;
			}
		});
		btnSelect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("abdp", chek+" : yes");
				 Log.i("abdp", time+" :yes");
			 vibrate(chek,time);
			}
		});

		layout_joystick.setOnTouchListener(new OnTouchListener() {

			TouchPad_listener Tlis = new TouchPad_listener();

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int eid = event.getAction();
				js.drawStick(event);
				switch (eid) {
				case MotionEvent.ACTION_MOVE:
					Tlis.OnActionMove(event);
					break;
				case MotionEvent.ACTION_DOWN:
					Tlis.OnActionDown(event);
					break;
				case MotionEvent.ACTION_UP:
					// Nothing have to do
					Tlis.wheelUp(event);

					break;
				default:
					break;
				}

				return true;
			}
		});

		layot_directions.setOnTouchListener(new OnTouchListener() {

			TouchPad_listener Tlis = new TouchPad_listener();

			public boolean onTouch(View v, MotionEvent event) {
				int eid = event.getAction();
				dir.drawStick(event);
				switch (eid) {
				case MotionEvent.ACTION_MOVE:
					gamepad_Global.send = 1;
					if (dir.distance > dir.min_distance && dir.touch_state) {
						if (dir.angle >= 247.5 && dir.angle < 292.5) {
							layot_directions.setBackground(up);
							Tlis.Click_W();
							// return false;
						}
						/*
						 * else if (dir.angle >= 292.5 && dir.angle < 337.5) {
						 * Tlis.Click_W(); Tlis.Click_D(); // return false; }
						 */
						else if (dir.angle >= 337.5 || dir.angle < 22.5) {
							layot_directions.setBackground(right);
							Tlis.Click_D();
							// return false;
						}
						/*
						 * else if (dir.angle >= 22.5 && dir.angle < 67.5) {
						 * Tlis.Click_S(); Tlis.Click_D(); // return false; }
						 */
						else if (dir.angle >= 67.5 && dir.angle < 112.5) {
							layot_directions.setBackground(down);
							Tlis.Click_S();
						}
						/*
						 * else if (dir.angle >= 112.5 && dir.angle < 157.5) {
						 * Tlis.Click_S(); Tlis.Click_A(); // return false; }
						 */
						else if (dir.angle >= 157.5 && dir.angle < 202.5) {
							layot_directions.setBackground(left);
							Tlis.Click_A(); // return false;
						}

					}
					break;
				case MotionEvent.ACTION_DOWN:
					gamepad_Global.send = 1;
					if (dir.distance > dir.min_distance && dir.touch_state) {
						if (dir.angle >= 247.5 && dir.angle < 292.5) {
							layot_directions.setBackground(up);
							Tlis.Click_W();
							// return false;
						}
						/*
						 * else if (dir.angle >= 292.5 && dir.angle < 337.5) {
						 * Tlis.Click_W(); Tlis.Click_D(); // return false; }
						 */
						else if (dir.angle >= 337.5 || dir.angle < 22.5) {
							layot_directions.setBackground(right);
							Tlis.Click_D();
							// return false;
						}
						/*
						 * else if (dir.angle >= 22.5 && dir.angle < 67.5) {
						 * Tlis.Click_S(); Tlis.Click_D(); // return false; }
						 */
						else if (dir.angle >= 67.5 && dir.angle < 112.5) {
							layot_directions.setBackground(down);
							Tlis.Click_S();
						}
						/*
						 * else if (dir.angle >= 112.5 && dir.angle < 157.5) {
						 * Tlis.Click_S(); Tlis.Click_A(); // return false; }
						 */
						else if (dir.angle >= 157.5 && dir.angle < 202.5) {
							layot_directions.setBackground(left);
							Tlis.Click_A(); // return false;
						}

					}
					break;
				case MotionEvent.ACTION_UP:
					layot_directions.setBackground(center);
					Tlis.Send_nothing(event);
					Tlis.stopArrows();

					break;
				default:
					break;
				}

				return true;
			}
		});
		sensorchek.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				startSensor = true;
				sensorchek.setImageResource(R.drawable.sensor_on);
				if (event.getAction() == MotionEvent.ACTION_UP) {
					startSensor = false;
					sensorchek.setImageResource(R.drawable.sensor);
					Tlis.sensor(100, 100);
					Log.i("BLUE","in touch and it's off");
				}
				
				return false;
			}
		});
		
		sensorchek.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN){
					startSensor = true;
					sensorchek.setImageResource(R.drawable.sensor_on);
				}
				if (event.getAction() == MotionEvent.ACTION_UP){
					startSensor = false;
					sensorchek.setImageResource(R.drawable.sensor);
					Tlis.sensor(100, 100);
					Log.i("BLUE","in key and it's off");
				}
				
				return false;
			}
		});
		
		sensorchek.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				vibrate(chek, time);
				Log.i("BLUE", "in long and it's off");
				return false;
			}
		});

		

		ViewTreeObserver vto = btnA.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {

				width_btn = btnA.getWidth();
				height_btn = btnA.getHeight();

			}
		});
		ViewTreeObserver btn_se = btnSelect.getViewTreeObserver();
		btn_se.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {

				select_w = btnSelect.getWidth();
				select_h = btnSelect.getHeight();

			}
		});

	}

	private void init() {
		layout_joystick = (RelativeLayout) findViewById(R.id.layout_joystick);
		js = new JoyStickClass(getApplicationContext(), layout_joystick,
				R.drawable.gamepad_stick);

		js.setStickAlpha(250);
		js.setOffset(60);
		js.setMinimumDistance(40);

		layot_directions = (RelativeLayout) findViewById(R.id.layout_Direction);
		dir = new JoyStickClass(getApplicationContext(), layot_directions,
				R.drawable.gamepad_stick);

		dir.setStickAlpha(250);
		dir.setOffset(60);
		dir.setMinimumDistance(40);

		btnX = (Button) findViewById(R.id.imageButton8);
		btnA = (Button) findViewById(R.id.imageButton11);
		btnB = (Button) findViewById(R.id.imageButton10);
		btnY = (Button) findViewById(R.id.imageButton9);
		btnStart = (Button) findViewById(R.id.imageButton6);
		btnSelect = (Button) findViewById(R.id.imageButton7);
		mydraw = (RelativeLayout) findViewById(R.id.draw);
		Resources res = getResources(); // resource handle
		up = res.getDrawable(R.drawable.up);
		down = res.getDrawable(R.drawable.down);
		left = res.getDrawable(R.drawable.left);
		right = res.getDrawable(R.drawable.right);
		center = res.getDrawable(R.drawable.shom);
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensorchek = (ImageView) findViewById(R.id.sensor);

		editbtn = (ImageButton) findViewById(R.id.edit);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		// inflater.inflate(R.menu.layout_setting, menu);
		inflater.inflate(R.menu.gamepad_menu, menu);

		return super.onCreateOptionsMenu(menu);
	}
public static void Show_popUpWindow() {
		popupWindow.setOutsideTouchable(true);
	//	popupWindow.setFocusable(false);
		popupWindow.showAsDropDown(editbtn);
		popupWindow.setAnimationStyle(R.animator.animation3);
	}
	
	public static void Hide_popUpWindow() {
		popupWindow.setOutsideTouchable(false);
		popupWindow.setFocusable(false);
		popupWindow.dismiss();
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.menu_edit) {
			clicked++;

			if (! move && ! delete && ! edit_btn
					&& (clicked % 2) == 0) {
				popupWindow.setBackgroundDrawable(new BitmapDrawable());
				Hide_popUpWindow();
			}

			else if ((clicked % 2) != 0) {
				
				popupWindow.showAsDropDown(editbtn);
				popupWindow.setAnimationStyle(R.animator.animation3);

			}

			final ImageButton addbtn = (ImageButton) popupView
					.findViewById(R.id.imageadd);
			addbtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent serverIntent = new Intent(getApplicationContext(),
							gameBad_EDIT.Buttons_choice.class);
					serverIntent.putExtra("what", "add");
					startActivity(serverIntent);

				}
			});

			final ImageButton deletebtn = (ImageButton) popupView
					.findViewById(R.id.imagedelete);
			deletebtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					move = false;
					delete = true;
					edit_btn = false;
save = false;
					what_do = "delete";
					Hide_popUpWindow();
					Toast.makeText(getApplicationContext(),
							"click long on the button you want to delete",
							Toast.LENGTH_LONG).show();

				}
			});

			final ImageButton editbtn = (ImageButton) popupView
					.findViewById(R.id.imageedit);
			editbtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					move = false;
					delete = false;
					edit_btn = true;
	save = false;
					what_do = "editButton";
					Hide_popUpWindow();
					Toast.makeText(
							getApplicationContext(),
							"click long on the button you want to Edit its Operation",
							Toast.LENGTH_LONG).show();

				}
			});

			final ImageButton movebtn = (ImageButton) popupView
					.findViewById(R.id.imagemove);
			movebtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					move = true;
					delete = false;
					edit_btn = false;
	save = false;
					Hide_popUpWindow();
					what_do = "move";
					Toast.makeText(getApplicationContext(),
							"Hold long the button you want to move",
							Toast.LENGTH_LONG).show();

				}
			});

			final ImageButton savebtn = (ImageButton) popupView
					.findViewById(R.id.imagesave);
			savebtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					move = false;
					delete = false;
					edit_btn = false;
save = true;
					save();
					Hide_popUpWindow();
				}
			});

		}

		else if (item.getItemId() == R.id.menu_show) {

			Intent showIntent = new Intent(getApplicationContext(),
					gameBad_EDIT.list_activity.class);
			startActivity(showIntent);
		}
		
		switch(item.getItemId())
		 {
	
		 case android.R.id.home:
			 Intent parentActivityIntent = new Intent(this, MainActivity.class);
            parentActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(parentActivityIntent);
            overridePendingTransition(R.animator.enterance_shrink_rotate, R.animator.exit_shrink_rotate);
           return true;
		 }

		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onBackPressed()  
	{
	    if (popupWindow != null && popupWindow.isShowing()) 
	    {	    	
	    	popupWindow.dismiss(); 
	        clicked++;
	    } 
	    else {
	    	super.onBackPressed();
	    
	    	overridePendingTransition(R.animator.enterance_shrink_rotate,R.animator.exit_shrink_rotate);   
	    }
	}

	public void save() {
		@SuppressWarnings("unused")
		final GLOBAL_DB ob = new GLOBAL_DB();
		final Dialog dialog = new Dialog(this);
	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.new_layot_save);
	dialog.setCancelable(false);

		svDialog = (Button) dialog.findViewById(R.id.sv_dialog);
	new_name = (EditText) dialog.findViewById(R.id.new_save_txt);

		svDialog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
		if(new_name.getText().toString().isEmpty() || new_name.getText().toString().equals(""))
				{
					if(new_name.getText().toString().isEmpty())
					   new_name.setError("set a name");
					if(new_name.getText().toString().equals(""))
						new_name.setError("spaces are not allowed");
				}
                else {
					new_lay = new_name.getText().toString();

					ArrayList<Button> del = new ArrayList<>();

					GLOBAL_DB.la.is_exist(new_lay);
					if (list_activity.IS_EXIST) {
						Toast.makeText(getApplicationContext(),
								"This name There is already exist",
								Toast.LENGTH_LONG).show();
					}
					if (!list_activity.IS_EXIST) {
						for (int i = 0; i < mydraw.getChildCount(); i++) {
							if (mydraw.getChildAt(i) instanceof Button) {
								Button insert_v;
								Drawable draw;

								insert_v = (Button) mydraw.getChildAt(i);
								draw = insert_v.getBackground();
								Log.i("name= " + insert_v.getText(), "id= "
										+ insert_v.getId());
								Log.i("x= " + insert_v.getX(),
										"y= " + insert_v.getY());
								Log.i("background= " + draw, "nnnnnnn");

								gamepad_Global.g_db.Create_layout();
								gamepad_Global.g_db.Insert(insert_v.getX(),
										insert_v.getY(), insert_v.getText()
												.toString(), draw.toString());

							}
						}
						for (int i = 0; i < mydraw.getChildCount(); i++) {
							if (mydraw.getChildAt(i) instanceof Button) {
								Button insert_v;
								insert_v = (Button) mydraw.getChildAt(i);
								Log.i("" + insert_v.getText(), "2bl m ams7");
								del.add(insert_v);

							}
						}
						for (int a = 0; a < del.size(); a++) {
							mydraw.removeView(del.get(a));
						}
						GLOBAL_DB.la.get_table_contetnt(new_lay);
						the_last_lay = new_lay;
						dialog.cancel();
					}
				}
			}
		});
		dialog.show();
		Log.i("img_bk " + move, "img_bk " + delete);

	}
	@Override
	protected void onStop() {
		if (gamepad_Global.connectionType == gamepad_Global.WIFI_CONNECTION) {

			Client client = Wifi_Global.client;
			try {
				client.sendMsg("Gamepad is turned off");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		super.onStop();
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (startSensor) {

			int moveXFinal = Math.round(event.values[0]); // x value ya5wana
															// bta3 elsensor
			int moveYFinal = Math.round(event.values[1]); // y value
			TouchPad_listener Tlis = new TouchPad_listener();

			if (moveXFinal != 0 || moveYFinal != 0) {
				Tlis.sensor(moveXFinal, moveYFinal);
			}

		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	@Override
	protected void onStart() {

		if (the_last_lay != null) {
			if (resume.size() != 0) {
				for (int i = 0; i < mydraw.getChildCount(); i++) {
					if (mydraw.getChildAt(i) instanceof Button) {
						Button insert_v;
						insert_v = (Button) mydraw.getChildAt(i);
						Log.i("" + insert_v.getText(), "onStart");
						resume.add(insert_v);

					}
				}
				for (int a = 0; a < resume.size(); a++) {
					mydraw.removeView(resume.get(a));
				}
			}
			GLOBAL_DB.la.get_table_contetnt(the_last_lay);
		}

		// mydraw.addView(del.get(a));

		super.onStart();
	}

	@Override
	protected void onPause() {
		sensorManager.unregisterListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
		for (int i = 0; i < mydraw.getChildCount(); i++) {
			if (mydraw.getChildAt(i) instanceof Button) {
				Button insert_v;
				insert_v = (Button) mydraw.getChildAt(i);
				Log.i("" + insert_v.getText(), "onPause");
				resume.add(insert_v);
			}
		}
		Log.d("w " + width_btn, "h " + height_btn);
		super.onPause();
	}

	@Override
	protected void onRestart() {
		Log.i("onRestart", "onRestart");
		Log.d("w " + width_btn, "h " + height_btn);
		super.onRestart();
	}

	@Override
	protected void onResume() {
		Log.d("Resume", "r");
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_GAME);
		super.onResume();
	}
	
	public void vibrate(int check,long t) {
		
		
		if (check == 1)
		{
			Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE) ;
			v.vibrate(t);
		}
	}

}
