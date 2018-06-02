package bluetooth_Connection;

import android.os.Vibrator;

public class _Global {
	
	
	// Constants that indicate the current connection state
	public static final int EXIT_CMD = -1;
	public static final int STATE_NONE = 0;
	public static final int STATE_LISTEN = 1;
	public static final int STATE_CONNECTING = 2;
	public static final int STATE_CONNECTED = 3;

	// BluetoothConnectionService
	public static final int REQUEST_CONNECT_DEVICE = 1;
	public static final int REQUEST_ENABLE_BT = 2;
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;
	public static final int MESSAGE_Up = 22;
	public static final int MESSAGE_Down = 23;
	public static final int MESSAGE_right = 24;
	public static final int MESSAGE_left = 25;
	
	
	public static final int MESSAGE_one = 26;
	public static final int MESSAGE_two = 27;
	public static final int MESSAGE_three = 28;
	public static final int MESSAGE_four = 29;
	public static final int MESSAGE_start = 30;
	public static final int MESSAGE_select = 31;
	public static final int MESSAG_A = 32;
	public static final int MESSAG_B = 33;
	public static final int MESSAG_C = 34;
	public static final int MESSAG_D = 35;
	public static final int MESSAG_E = 36;
	public static final int MESSAG_F = 37;
	public static final int MESSAG_G = 38;
	public static final int MESSAG_H = 39;
	public static final int MESSAG_I = 40;
	public static final int MESSAG_J = 41;
	public static final int MESSAG_K = 42;
	public static final int MESSAG_L = 43;
	public static final int MESSAG_M = 44;
	public static final int MESSAG_N = 45;
	public static final int MESSAG_O = 46;
	public static final int MESSAG_P = 47;
	public static final int MESSAG_Q = 48;
	public static final int MESSAG_R = 49;
	public static final int MESSAG_S = 50;
	public static final int MESSAG_T = 51;
	public static final int MESSAG_U = 52;
	public static final int MESSAG_V = 53;
	public static final int MESSAG_X = 54;
	public static final int MESSAG_Y = 55;
	public static final int MESSAG_Z = 56;
	public static final int MESSAG_Alt = 57;
	public static final int MESSAG_Tab = 58;
	public static final int MESSAG_Shift = 59;
	public static final int MESSAG_Caps = 60;
	public static final int MESSAG_Ctrl = 61;
	public static final int MESSAG_0 = 62;
	public static final int MESSAG_1 = 63;
	public static final int MESSAG_2 = 64;
	public static final int MESSAG_3 = 65;
	public static final int MESSAG_4 = 66;
	public static final int MESSAG_5 = 67;
	public static final int MESSAG_6 = 68;
	public static final int MESSAG_7 = 69;
	public static final int MESSAG_8 = 70;
	public static final int MESSAG_9 = 71;
	public static final int MESSAG_Esc = 72;
	public static final int MESSAG_F1 = 73;
	public static final int MESSAG_F2 = 74;
	public static final int MESSAG_F3 = 75;
	public static final int MESSAG_F4 = 76;
	public static final int MESSAG_F5 = 77;
	public static final int MESSAG_F6 = 78;
	public static final int MESSAG_F7 = 79;
	public static final int MESSAG_F8 = 80;
	public static final int MESSAG_F9 = 81;
	public static final int MESSAG_F10 = 82;
	public static final int MESSAG_F11 = 83;
	public static final int MESSAG_F12 = 84;
	public static final int MESSAG_W = 85;
	
	

	/* stop Messages. */
	public static final int MessageOneStop = 260;
	public static final int MESSAGE_threeStop = 280;
	public static final int MESSAGE_fourStop = 290;
	
	
	public static final int MESSAG_Astop = 320;
	public static final int MESSAG_Bstop = 330;
	public static final int MESSAG_Cstop = 340;
	public static final int MESSAG_Dstop= 350;
	public static final int MESSAG_Estop = 360;
	public static final int MESSAG_Fstop = 370;
	public static final int MESSAG_Gstop = 380;
	public static final int MESSAG_Hstop = 390;
	public static final int MESSAG_Istop = 400;
	public static final int MESSAG_Jstop = 410;
	public static final int MESSAG_Kstop = 420;
	public static final int MESSAG_Lstop = 430;
	public static final int MESSAG_Mstop = 440;
	public static final int MESSAG_Nstop = 450;
	public static final int MESSAG_Ostop = 460;
	public static final int MESSAG_Pstop = 470;
	public static final int MESSAG_Qstop = 480;
	public static final int MESSAG_Rstop = 490;
	public static final int MESSAG_Sstop = 5000;
	public static final int MESSAG_Tstop = 510;
	public static final int MESSAG_Ustop = 520;
	public static final int MESSAG_Vstop = 530;
	public static final int MESSAG_Xstop = 540;
	public static final int MESSAG_Ystop = 5500;
	public static final int MESSAG_Zstop = 560;
	public static final int MESSAG_Altstop = 570;
	public static final int MESSAG_Tabstop = 580;
	public static final int MESSAG_Shiftstop = 590;
	public static final int MESSAG_Capsstop = 600;
	public static final int MESSAG_Ctrlstop = 610;
	public static final int MESSAG_0stop = 620;
	public static final int MESSAG_1stop = 630;
	public static final int MESSAG_2stop = 640;
	public static final int MESSAG_3stop = 650;
	public static final int MESSAG_4stop = 660;
	public static final int MESSAG_5stop = 670;
	public static final int MESSAG_6stop = 680;
	public static final int MESSAG_7stop = 690;
	public static final int MESSAG_8stop = 700;
	public static final int MESSAG_9stop = 710;
	public static final int MESSAG_Escstop = 720;
	public static final int MESSAG_F1stop = 730;
	public static final int MESSAG_F2stop = 740;
	public static final int MESSAG_F3stop = 750;
	public static final int MESSAG_F4stop = 760;
	public static final int MESSAG_F5stop = 770;
	public static final int MESSAG_F6stop = 780;
	public static final int MESSAG_F7stop = 790;
	public static final int MESSAG_F8stop = 800;
	public static final int MESSAG_F9stop = 810;
	public static final int MESSAG_F10stop = 820;
	public static final int MESSAG_F11stop = 830;
	public static final int MESSAG_F12stop = 840;
	public static final int MESSAG_Wstop = 850;
	

	// Touchpad
	public static final int LEFT_CLICK = 8;
	public static final int LEFT_CLICK_DOWN = 9;
	public static final int LEFT_CLICK_UP = 10;
	//public static final int MESSAGE_RIGHT_CLICK = 11;
	public static final int RIGHT_CLICK_DOWN = 111;
	public static final int RIGHT_CLICK_UP = 110;
	public static final int MESSAGE_MOVE = 7;
	/*sensor*/
	public static final int MESSAGE_sensor = 15;
	public static final int MESSAGE_StopArrows = 1000;
	public static final int MESSAGE_scroll = 120;


	public static Vibrator vibrator;

}
