package com.gamepad;

import localDataBase.GLOBAL_DB;
import bluetooth_Connection.Bluetooth_activity;
import bluetooth_Connection.TouchPad_listener;
import gameBad_EDIT.Buttons_choice;


public class gamepad_Global {
	static public boolean checkConnection = false;
	static public String connectionType = null;
	static public String WIFI_CONNECTION = "WIFI";
	static public String BLEUTOOTH_CONNECTION = "BLUETOOTH";
	
	public static Buttons_choice ob = new Buttons_choice();
	public static Gamepad gp = new Gamepad();
	public static TouchPad_listener Tlis = new TouchPad_listener();
	public static GLOBAL_DB g_db = new GLOBAL_DB();
	public static Bluetooth_activity B_A = new Bluetooth_activity();
	
	
    public static long send;

	
	public static final int dir_NONE = R.drawable.gamepad_stick ;
	public static final int dir_UP   = R.drawable.up;
	public static final int dir_RIGHT =R.drawable.right;
	public static final int dir_DOWN = R.drawable.down;
	public static final int dir_LEFT = R.drawable.left;
}
