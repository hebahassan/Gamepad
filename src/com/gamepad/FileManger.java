






package com.gamepad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import wifi_connection.classes.Client;
import wifi_connection.classes.Wifi_Global;
import wifi_connection.classes.fileManger;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import bluetooth_Connection.BluetoothConnectionService;
import bluetooth_Connection.Motion;
import bluetooth_Connection.fileMangerWork;

public class FileManger extends ActionBarActivity {

	ArrayList<fileManger> fms;
	ArrayList<String> names;
	fileManger fm;
	ListView lv;
	TextView tv;
	ArrayAdapter<String> adapter;
	Context context;
	Client client;
	final int secs = 1;
	String msg;
	String cleanName;
	List<Task> tasks;
	Task task;
	ProgressBar pb;
public static Activity file_con;

	/* bluetooth work */
	public static final int MESSAGE_OPENDRIVES = 550;
	public static final int MESSAGE_fileManger = 500;
	private static final String TAG = "FILEMANAGER";
	
	ActionBar actionbar;

	@SuppressLint("InflateParams")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.filemanger_ac);
		file_con = (Activity)this;
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1C1C1C")));

		init();
		
		actionbar = getActionBar();
		actionbar.setIcon(R.drawable.ic_logo);
		actionbar.setDisplayHomeAsUpEnabled(true);
		
		pb.setVisibility(View.INVISIBLE);

		Wifi_Global.checkFiles = true;
		task = new Task();
		task.execute("open file manger");
	if(gamepad_Global.connectionType == gamepad_Global.BLEUTOOTH_CONNECTION&&BluetoothConnectionService.name_file.size()>0){
			//setRequestedOrientation(MainActivity.main_con.getResources().getConfiguration().orientation);
			BluetoothConnectionService.name_file.clear();
			}
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String item = adapter.getItem(position);
				try {
					onClick(position, item);
	if(gamepad_Global.connectionType == gamepad_Global.BLEUTOOTH_CONNECTION){
				//	setRequestedOrientation(MainActivity.main_con.getResources().getConfiguration().orientation);
					BluetoothConnectionService.name_file.clear();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

	}
	
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

	private void onClick(int position, String item) throws InterruptedException {
		final String path = fm.getPathByName(item, fms);
		if (gamepad_Global.connectionType == gamepad_Global.WIFI_CONNECTION) {
			client.sendMsg("Open Drives");
		} else if (gamepad_Global.connectionType == gamepad_Global.BLEUTOOTH_CONNECTION) {
			Task task2 = new Task();
			task2.execute("Open Drives");
		}
		task = new Task();
		task.execute(path);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position1, long id) {
				String subitem = adapter.getItem(position1);
				try {
					onClick(position1, subitem);
	if(gamepad_Global.connectionType == gamepad_Global.BLEUTOOTH_CONNECTION){
					BluetoothConnectionService.name_file.clear();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

	}

	/* takes string to send and return the recieved msg */
	class Task extends AsyncTask<String, String, ArrayList<String>> {

		@Override
		protected ArrayList<String> doInBackground(String... params) {
			if (gamepad_Global.connectionType == gamepad_Global.WIFI_CONNECTION) {
				try {
					client.sendMsg(params[0]);
					names = client.ReadMsg(fms);
					Thread.sleep(500);
					Log.i(TAG, "in Task");
				} catch (InterruptedException e) {
					gamepad_Global.checkConnection = false;
					gamepad_Global.connectionType = null;
					Client.mTitleTextView.setText("not connected");
					Client.mTitleTextView.setBackgroundResource(R.drawable.off);
				}
			} else if (gamepad_Global.connectionType == gamepad_Global.BLEUTOOTH_CONNECTION) {
				if (params[0] == "open file manger") {
					try {
						fileMangerWork.sendMotion(new Motion(
								MESSAGE_fileManger, 0, 0));

						Thread.sleep(5000);

						names = fileMangerWork.readFm(fms);
					} catch (InterruptedException e) {
						

						e.printStackTrace();
					}

				} else if (params[0] == "Open Drives") {
					fileMangerWork.sendMotion(new Motion(MESSAGE_OPENDRIVES, 0,
							0));
					try {
						Thread.sleep(5000);

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					try {
						fileMangerWork.sendMsg(params[0]);
						Log.i("BLUETOOTH ", "Sending " + params[0]);
						Thread.sleep(1000);
						names = fileMangerWork.readFm(fms);
						Thread.sleep(1000);
					} catch (IOException e) {
						Log.e("BLUETOOTH ", "Sending " + params[0], e);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}

			return names;
		}

		@Override
		protected void onPreExecute() {
			if (tasks.size() == 0)
				pb.setVisibility(View.VISIBLE);
			tasks.add(this);
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			tasks.remove(this);
			if (tasks.size() == 0)
				pb.setVisibility(View.INVISIBLE);
			if (!names.isEmpty())
				updateDisplay(result);
		}

	}

	private void init() {
		lv = (ListView) findViewById(R.id.listFiles);
		pb = (ProgressBar) findViewById(R.id.progressBar1);
		fm = new fileManger();
		fms = new ArrayList<fileManger>();
		names = new ArrayList<String>();
		context = getApplicationContext();
		if (gamepad_Global.connectionType == gamepad_Global.WIFI_CONNECTION)
			client = Wifi_Global.client;
		tasks = new ArrayList<FileManger.Task>();
		adapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_activated_1);

	}

	void updateDisplay(List<String> list) {
		adapter.clear();
		adapter.addAll(names);
		adapter.notifyDataSetChanged();
		lv.setAdapter(adapter);

	}

	@Override
	protected void onStop() {
		if (gamepad_Global.connectionType == gamepad_Global.WIFI_CONNECTION) {
			try {
				client.sendMsg("file Manger turned off");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (gamepad_Global.connectionType == gamepad_Global.BLEUTOOTH_CONNECTION) {
			// Wifi_Global.checkFiles = false;
		}

		super.onStop();
	}

}
