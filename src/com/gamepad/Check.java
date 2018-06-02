package com.gamepad;

import java.io.IOException;

import wifi_connection.classes.Client;
import wifi_connection.classes.Wifi_Global;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import bluetooth_Connection.BluetoothListActivity;
import bluetooth_Connection.Bluetooth_activity;
import bluetooth_Connection._Global;

public class Check extends Fragment implements View.OnClickListener {
	ImageButton blucheck;
	public static ImageView blu_img;
	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST = "toast";
	public SharedPreferences preferences;
	Bluetooth_activity ob = new Bluetooth_activity();
	public static Activity ac;
	View view;

	/* wifi */
	ImageButton wifiCheck;
	Client client;
	Thread clientThread;
	public static Switch switchBt;
	LayoutInflater inflater2;
	View DialogView;
	EditText ip, port;
	AlertDialog.Builder builder;

	static String ipAddress;
	static int portNum;
	Button connectBt;
	SharedPreferences session;
	private static final String PREF = "WIFI_CONNECTION";
	protected static final String IPADDRESS_PREF = "ip address";
	protected static final String IPPORT_PREF = "port";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.check_layout, container, false);

		preferences = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		ac = getActivity();

		inflater2 = getActivity().getLayoutInflater();
		DialogView = inflater2.inflate(R.layout.wifi_layout, container, false);

		switchBt = (Switch) view.findViewById(R.id.switch1);
		switchBt.setClickable(false);

		init();
		blucheck = (ImageButton) view.findViewById(R.id.Bluetooth_check);
		blu_img = (ImageView) view.findViewById(R.id.imageview1);
		Bluetooth_activity.mTitleTextView = (TextView) Check.ac
				.findViewById(R.id.title_right_text);

		blucheck.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (ob.availability() == false) {
					Toast.makeText(getActivity(), "Bluetooth is not available",
							Toast.LENGTH_LONG).show();
					switchBt.setClickable(false);
					switchBt.setChecked(false);
				} else {
					ob.Enable();
					if (Bluetooth_activity.mBluetoothAdapter.isEnabled()) {
						Intent serverIntent = new Intent(getActivity(),
								BluetoothListActivity.class);
						startActivityForResult(serverIntent,
								_Global.REQUEST_CONNECT_DEVICE);
					
					}					
					switchBt.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

							wifiCheck.setImageResource(R.drawable.wifi);
							blu_img.setImageResource(R.drawable.bluetooth);
							gamepad_Global.checkConnection = false;
							gamepad_Global.connectionType = null;
							Bluetooth_activity.mCommandService.stop();
							switchBt.setClickable(false);
							Toast.makeText(getActivity(), "Connection is off",
									Toast.LENGTH_SHORT).show();
						}
					});

/*					final int secs = Bluetooth_activity.end-Bluetooth_activity.start;
					new CountDownTimer((secs) * 1000, 1000) {

						public void onTick(long millisUntilFinished) {
							Toast.makeText(Check.ac, "Wait for your Paired Devices "+millisUntilFinished/10000, Toast.LENGTH_SHORT).show();
							Log.e("sa3a","" +millisUntilFinished/10000);
						}

						@Override
						public void onFinish() {
							Intent serverIntent = new Intent(getActivity(),
									BluetoothListActivity.class);
							startActivityForResult(serverIntent,
									_Global.REQUEST_CONNECT_DEVICE);

						}
					}.start();*/
				}
			}
		});

		/* wifi work */

		wifiCheck.setOnClickListener(new OnClickListener() {

			@SuppressLint("InflateParams")
			@Override
			public void onClick(View v) {

				WifiManager wifiManager = (WifiManager) getActivity()
						.getSystemService(Context.WIFI_SERVICE);

				if (!wifiManager.isWifiEnabled()) {
					wifiManager.setWifiEnabled(true);
				}

				if (!gamepad_Global.checkConnection) {
					final Dialog dialog = new Dialog(ac);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.wifi_layout);
					ip = (EditText) dialog.findViewById(R.id.ipText);
					port = (EditText) dialog.findViewById(R.id.portText);
					connectBt = (Button) dialog.findViewById(R.id.connect);
					session = getActivity().getSharedPreferences(PREF,
							Context.MODE_PRIVATE);
					if (session.getString(IPADDRESS_PREF, null) != null) {
						ip.setText(session.getString(IPADDRESS_PREF, null));

					}
					if (session.getInt(IPPORT_PREF, 0) != 0)
						port.setText("2001");
					connectBt.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// switchBt.setChecked(true);
							if (ip.getText().toString().isEmpty()
									|| port.getText().toString().isEmpty()) {
								if (ip.getText().toString().isEmpty())
									ip.setError("!");
								if (port.getText().toString().isEmpty())
									port.setError("!");
							} else {
								ipAddress = ip.getText().toString();
								if (session.getString(IPADDRESS_PREF, null) == null)
									session.edit()
											.putString(IPADDRESS_PREF,
													ipAddress).commit();

								portNum = Integer.parseInt(port.getText()
										.toString());

								if (session.getInt(IPPORT_PREF, 0) == 0)
									session.edit().putInt(IPPORT_PREF, portNum)
											.commit();

								try {
									client = new Client();
									clientThread = new Thread(client);
									client.setPort(portNum);
									client.setServerIpAddress(ipAddress);
									clientThread.start();
									Thread.sleep(500);
									if (Client.checkConnection) {
										switchBt.setChecked(true);
										Wifi_Global.checkConnection = true;
										gamepad_Global.checkConnection = true;
										Wifi_Global.client = client;
										wifiCheck
												.setImageResource(R.drawable.wifi_on);
										gamepad_Global.connectionType = gamepad_Global.WIFI_CONNECTION;
										Toast.makeText(ac, "connected",
												Toast.LENGTH_LONG).show();
										switchBt.setClickable(true);
										/*Bluetooth_activity.mTitleTextView
												.setText("connected");*/
										Bluetooth_activity.mTitleTextView.setBackgroundResource(R.drawable.on);
										switchBt.setOnClickListener(new OnClickListener() {
											@Override
											public void onClick(View v) {
												if (gamepad_Global.checkConnection) {
													try {
														if (gamepad_Global.connectionType == gamepad_Global.WIFI_CONNECTION) {
															client.stop();
															wifiCheck
																	.setImageResource(R.drawable.wifi);
														}
														if (gamepad_Global.connectionType == gamepad_Global.BLEUTOOTH_CONNECTION) {
															blu_img.setImageResource(R.drawable.bluetooth);
														}
														switchBt.setClickable(false);
														Toast.makeText(
																getActivity(),
																"Connection is off",
																Toast.LENGTH_SHORT)
																.show();
														gamepad_Global.checkConnection = false;
														gamepad_Global.connectionType = null;
														/*Bluetooth_activity.mTitleTextView
																.setText("not connected");*/
														Bluetooth_activity.mTitleTextView.setBackgroundResource(R.drawable.off);
													} catch (IOException e) {
														e.printStackTrace();
													} catch (InterruptedException e) {

														e.printStackTrace();
													}

												}
											}
										});
									} else
										Toast.makeText(ac, "not connected",
												Toast.LENGTH_LONG).show();

									dialog.cancel();
								} catch (IOException e) {
									Toast.makeText(
											getActivity(),
											"Couldn't connect.. try turning of firewall on you laptop and try again. ",
											Toast.LENGTH_LONG).show();
								} catch (InterruptedException e) {
									//
									e.printStackTrace();
								}

							}
						}
					});
					dialog.show();
				}

			}
		});

		return view;
	}

public void  onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case _Global.REQUEST_CONNECT_DEVICE:
			if (resultCode == Activity.RESULT_OK) {
				ob.Request_THEN_connect(data);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				gamepad_Global.connectionType = gamepad_Global.BLEUTOOTH_CONNECTION;
				gamepad_Global.checkConnection = true;
				
			}
			break;

		case _Global.REQUEST_ENABLE_BT:
			if (resultCode == Activity.RESULT_OK) {
				ob.setupCommand();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onResume() {
		super.onResume();
		ob.resume();
	}

	@Override
	public void onStart() {
		super.onStart();
		// ob.Enable(getActivity());
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		ob.destRoy();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		}
	}

	private void init() {
		wifiCheck = (ImageButton) view.findViewById(R.id.Wifi_check);
		// switchBt = (Switch) view.findViewById(R.id.switch1);

	}

}
