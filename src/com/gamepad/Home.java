package com.gamepad;

import wifi_connection.classes.Client;
import wifi_connection.classes.Wifi_Global;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.Toast;
import bluetooth_Connection.BluetoothConnectionService;
public class Home extends Fragment implements View.OnClickListener{

	ImageButton padbtn;
	ImageButton morebtn;
	ImageButton mousebtn;
	ImageButton updatebtn;
	ImageButton stngbtn;
	ImageButton helpbtn;
	

	/* wifi and filemanger works */
	Client client;
	Context context;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		
		View view = inflater.inflate(R.layout.home_layout, container, false);

		padbtn = (ImageButton) view.findViewById(R.id.home_imageButton1);
		morebtn = (ImageButton) view.findViewById(R.id.home_imageButton2);
		mousebtn = (ImageButton) view.findViewById(R.id.home_imageButton3);
		updatebtn = (ImageButton) view.findViewById(R.id.home_imageButton4);
		stngbtn = (ImageButton) view.findViewById(R.id.home_imageButton5);
		helpbtn = (ImageButton) view.findViewById(R.id.home_imageButton6);

		padbtn.setOnClickListener(this);
		morebtn.setOnClickListener(this);
		mousebtn.setOnClickListener(this);
		updatebtn.setOnClickListener(this);
		stngbtn.setOnClickListener(this);
		helpbtn.setOnClickListener(this);

	

		return view;

	}

	@Override
	public void onClick(View v) {
		padbtn = (ImageButton) v.findViewById(R.id.home_imageButton1);
		mousebtn = (ImageButton) v.findViewById(R.id.home_imageButton3);
		stngbtn = (ImageButton) v.findViewById(R.id.home_imageButton5);
		@SuppressWarnings("unused")
		Animation anim = AnimationUtils.loadAnimation(this.getActivity(),
				R.anim.btn_scale);
		switch (v.getId()) {

		/* gamepad work */
		case R.id.home_imageButton1:
			if (gamepad_Global.checkConnection) {
				if (gamepad_Global.connectionType == gamepad_Global.WIFI_CONNECTION) {
					init();
					try {
						client.sendMsg("gamepad turrned on");
					} catch (InterruptedException e2) {
						e2.printStackTrace();
					}
				}
				
			} else
				Toast.makeText(getActivity(), "Check your connection first",
						Toast.LENGTH_LONG).show();
			
			Intent intent = new Intent(getActivity(), Gamepad.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.animator.enterance_shrink_rotate,
					R.animator.exit_shrink_rotate);

			break;

			/*Slide show work*/
		case R.id.home_imageButton2:

			if (Wifi_Global.checkConnection == false)
				Toast.makeText(this.getActivity(),
						"check your connection first", Toast.LENGTH_LONG)
						.show();
			else {
				init();
				try {
					client.sendMsg("Presntation");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
 
			Intent intent5 = new Intent(getActivity(), Slideshow.class);
			startActivity(intent5);
			getActivity().overridePendingTransition(R.animator.enterance_shrink_rotate,
					R.animator.exit_shrink_rotate);
			break;

		/* mouse work */
		case R.id.home_imageButton3:
			if (gamepad_Global.connectionType == gamepad_Global.WIFI_CONNECTION) {
				init();
				try {
					client.sendMsg("mouse");
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			Intent intent2 = new Intent(getActivity(), Mouse.class);
BluetoothConnectionService.which_activity="mouse";
			startActivity(intent2);
			getActivity().overridePendingTransition(R.animator.enterance_shrink_rotate,
					R.animator.exit_shrink_rotate);
			break;

		/* Update */
		case R.id.home_imageButton4:
			init();
			// updatebtn.startAnimation(anim);
			Toast.makeText(this.getActivity(), "Search for updates",
					Toast.LENGTH_LONG).show();

			Toast.makeText(this.getActivity(), "No updates for now", Toast.LENGTH_SHORT);

			/*
			 * if (Wifi_Global.checkConnection == false)
			 * Toast.makeText(this.getActivity(),"check your connection first.",
			 * Toast.LENGTH_LONG) .show(); else { try {
			 * client.sendMsg("Presntation"); } catch (InterruptedException e) {
			 * e.printStackTrace(); } }
			 */
			break;

			/*Settings*/
		case R.id.home_imageButton5:
			// stngbtn.startAnimation(anim);
			// Toast.makeText(this.getActivity(),
			// "Manage settings",Toast.LENGTH_LONG).show();
			Intent intent4 = new Intent(getActivity(), Prefs.class);
			startActivity(intent4);
			getActivity().overridePendingTransition(R.animator.enterance_shrink_rotate,
					R.animator.exit_shrink_rotate);
			break;

			/* file manger */
		case R.id.home_imageButton6:
			init();
			Toast.makeText(this.getActivity(),
					"mange files of your device remotly.", Toast.LENGTH_LONG)
					.show();

			if (gamepad_Global.checkConnection == false)
				Toast.makeText(this.getActivity(),
						"check your connection first.", Toast.LENGTH_LONG)
						.show();
			else {
				if (gamepad_Global.connectionType == gamepad_Global.WIFI_CONNECTION
						&& Wifi_Global.checkFiles == false) {
					try {
						client.sendMsg("open file manger");
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
				} else if (gamepad_Global.connectionType == gamepad_Global.BLEUTOOTH_CONNECTION) {
					// Motion motion = new Motion(_Global.MESSAGE_fileManger, 0,
					// 0);
					// fileMangerWork.sendMotion(motion);
					BluetoothConnectionService.which_activity="file";
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				Intent intent1 = new Intent(this.getActivity(),
						FileManger.class);
				startActivity(intent1);
				getActivity().overridePendingTransition(R.animator.enterance_shrink_rotate,
						R.animator.exit_shrink_rotate);

			}
			break;
		}
	}

public void animation (){
	RotateAnimation pad_anim = new RotateAnimation(0f, 350f, 15f, 15f);
	
	pad_anim.setInterpolator(new LinearInterpolator());
	pad_anim.setRepeatCount(Animation.ABSOLUTE);
	pad_anim.setDuration(2000);
	padbtn.startAnimation(pad_anim);
	morebtn.startAnimation(pad_anim);
	mousebtn.startAnimation(pad_anim);
	updatebtn.startAnimation(pad_anim);
	stngbtn.startAnimation(pad_anim);
	helpbtn.startAnimation(pad_anim);
}
	private void init() {
		context = getActivity().getApplicationContext();
		client = Wifi_Global.client;
	}
}
