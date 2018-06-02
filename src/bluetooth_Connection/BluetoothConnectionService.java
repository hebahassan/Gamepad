/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package bluetooth_Connection;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import com.gamepad.Check;
import com.gamepad.MainActivity;

public class BluetoothConnectionService {
	// Debugging
	private static final String TAG = "BluetoothCommandService";
	private static final boolean D = true;
	public static ArrayList<String> name_file;

	// Unique UUID for this application
	private static final UUID MY_UUID = UUID
			.fromString("00001101-0000-1000-8000-00805F9B34FB");

	// Member fields
	public static String which_activity;
	private final BluetoothAdapter mAdapter;
	private final Handler mHandler;
	private ConnectThread mConnectThread;
	private ConnectedThread mConnectedThread;
	private int mState;

	private boolean AllowStringInput = true;

	/**
	 * Constructor. Prepares a new BluetoothChat session.
	 * 
	 * @param context
	 *            The UI Activity Context
	 * @param handler
	 *            A Handler to send messages back to the UI Activity
	 */
	public BluetoothConnectionService(Handler handler) {
		mAdapter = BluetoothAdapter.getDefaultAdapter();
		mState = _Global.STATE_NONE;
		mHandler = handler;
	}

	/**
	 * Set the current state of the chat connection
	 * 
	 * @param state
	 *            An integer defining the current connection state
	 */
	private synchronized void setState(int state) {
		if (D)
			Log.d(TAG, "setState() " + mState + " -> " + state);
		mState = state;

		// Give the new state to the Handler so the UI Activity can update
		mHandler.obtainMessage(_Global.MESSAGE_STATE_CHANGE, state, -1)
				.sendToTarget();
	}

	/**
	 * Return the current connection state.
	 */
	public synchronized int getState() {
		return mState;
	}

	/**
	 * Start the chat service. Specifically start AcceptThread to begin a
	 * session in listening (server) mode. Called by the Activity onResume()
	 */
	public synchronized void start() {
		if (D)
			Log.d(TAG, "start");

		// Cancel any thread attempting to make a connection
		if (mConnectThread != null) {
			mConnectThread.cancel();
			mConnectThread = null;
		}

		// Cancel any thread currently running a connection
		if (mConnectedThread != null) {
			mConnectedThread.cancel();
			mConnectedThread = null;
		}

		setState(_Global.STATE_LISTEN);
	}

	/**
	 * Start the ConnectThread to initiate a connection to a remote device.
	 * 
	 * @param device
	 *            The BluetoothDevice to connect
	 */
	public synchronized void connect(BluetoothDevice device) {
		if (D)
			Log.d(TAG, "connect to: " + device);

		// Cancel any thread attempting to make a connection
		if (mState == _Global.STATE_CONNECTING) {
			if (mConnectThread != null) {
				mConnectThread.cancel();
				mConnectThread = null;
			}
		}

		// Cancel any thread currently running a connection
		if (mConnectedThread != null) {
			mConnectedThread.cancel();
			mConnectedThread = null;
		}

		// Start the thread to connect with the given device
		mConnectThread = new ConnectThread(device);

		/* de elthread */
		mConnectThread.start();
		setState(_Global.STATE_CONNECTING);
	}

	/**
	 * Start the ConnectedThread to begin managing a Bluetooth connection
	 * 
	 * @param socket
	 *            The BluetoothSocket on which the connection was made
	 * @param device
	 *            The BluetoothDevice that has been connected
	 */
	public synchronized void connected(BluetoothSocket socket,
			BluetoothDevice device) {
		if (D)
			Log.d(TAG, "connected");

		// Cancel the thread that completed the connection
		if (mConnectThread != null) {
			mConnectThread.cancel();
			mConnectThread = null;
		}

		// Cancel any thread currently running a connection
		if (mConnectedThread != null) {
			mConnectedThread.cancel();
			mConnectedThread = null;
		}

		// Start the thread to manage the connection and perform transmissions
		mConnectedThread = new ConnectedThread(socket);
		mConnectedThread.start();

		// Send the name of the connected device back to the UI Activity
		Message msg = mHandler.obtainMessage(_Global.MESSAGE_DEVICE_NAME);
		Bundle bundle = new Bundle();
		bundle.putString(Check.DEVICE_NAME, device.getName());
		msg.setData(bundle);
		mHandler.sendMessage(msg);

		// save connected device
		// mSavedDevice = device;
		// reset connection lost count
		// mConnectionLostCount = 0;

		setState(_Global.STATE_CONNECTED);
	}

	/**
	 * Stop all threads
	 */
	public synchronized void stop() {
		if (D)
			Log.d(TAG, "stop");
		if (mConnectThread != null) {
			mConnectThread.cancel();
			mConnectThread = null;
		}
		if (mConnectedThread != null) {
			mConnectedThread.cancel();
			mConnectedThread = null;
		}

		setState(_Global.STATE_NONE);
	}

	/**
	 * Write to the ConnectedThread in an unsynchronized manner
	 * 
	 * @param out
	 *            The bytes to write
	 * @see ConnectedThread#write(byte[])
	 */

	public void write(Motion out) {
		ConnectedThread r;

		// Synchronize a copy of the ConnectedThread
		synchronized (this) {
			if (mState != _Global.STATE_CONNECTED)
				return;
			r = mConnectedThread;
		}

		r.write(out);
	}

	public String readFile() throws OptionalDataException,
			ClassNotFoundException, IOException {
		ConnectedThread r;
		synchronized (this) {
			if (mState != _Global.STATE_CONNECTED) {
				String fm = "Not Connected";
				return fm;
			}

			r = mConnectedThread;

		}
		String fm = r.read();
		return fm;
	}

	public void writeString(String str) throws IOException {
		ConnectedThread r;

		// Synchronize a copy of the ConnectedThread
		synchronized (this) {
			if (mState != _Global.STATE_CONNECTED)
				return;
			r = mConnectedThread;
		}
		r.write(str);
	}

	/**
	 * Indicate that the connection attempt failed and notify the UI Activity.
	 */
	private void connectionFailed() {
		setState(_Global.STATE_LISTEN);

		// Send a failure message back to the Activity
		Message msg = mHandler.obtainMessage(_Global.MESSAGE_TOAST);
		Bundle bundle = new Bundle();
		 bundle.putString(Check.TOAST, "Unable to connect device");
		msg.setData(bundle);
		mHandler.sendMessage(msg);
	}

	/**
	 * Indicate that the connection was lost and notify the UI Activity.
	 */
	private void connectionLost() {
		// mConnectionLostCount++;
		// if (mConnectionLostCount < 3) {
		// // Send a reconnect message back to the Activity
		// Message msg = mHandler.obtainMessage(RemoteBluetooth.MESSAGE_TOAST);
		// Bundle bundle = new Bundle();
		// bundle.putString(RemoteBluetooth.TOAST,
		// "Device connection was lost. Reconnecting...");
		// msg.setData(bundle);
		// mHandler.sendMessage(msg);
		//
		// connect(mSavedDevice);
		// } else {
		setState(_Global.STATE_LISTEN);
		// Send a failure message back to the Activity
		Message msg = mHandler.obtainMessage(_Global.MESSAGE_TOAST);
		Bundle bundle = new Bundle();
    	MainActivity.main_con.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
		 bundle.putString(Check.TOAST, "Device connection was lost");
		msg.setData(bundle);
		mHandler.sendMessage(msg);
		// }
	}

	public boolean isAllowStringInput() {
		return AllowStringInput;
	}

	public void setAllowStringInput(boolean allowStringInput) {
		AllowStringInput = allowStringInput;
	}

	/**
	 * This thread runs while attempting to make an outgoing connection with a
	 * device. It runs straight through; the connection either succeeds or
	 * fails.
	 */
	private class ConnectThread extends Thread {
		private final BluetoothSocket mmSocket;
		private final BluetoothDevice mmDevice;

		public ConnectThread(BluetoothDevice device) {
			mmDevice = device;
			BluetoothSocket tmp = null;

			// Get a BluetoothSocket for a connection with the
			// given BluetoothDevice
			try {
				tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
			} catch (IOException e) {
				Log.e(TAG, "create() failed", e);
			}
			mmSocket = tmp;
		}

		@Override
		public void run() {
			Log.i(TAG, "BEGIN mConnectThread");
			setName("ConnectThread");

			// Always cancel discovery because it will slow down a connection
			mAdapter.cancelDiscovery();

			// Make a connection to the BluetoothSocket
			try {
				// This is a blocking call and will only return on a
				// successful connection or an exception
				mmSocket.connect();
			} catch (IOException e) {
				connectionFailed();
				// Close the socket
				try {
					mmSocket.close();
				} catch (IOException e2) {
					Log.e(TAG,
							"unable to close() socket during connection failure",
							e2);
				}
				// Start the service over to restart listening mode
				BluetoothConnectionService.this.start();
				return;
			}

			// Reset the ConnectThread because we're done
			synchronized (BluetoothConnectionService.this) {
				mConnectThread = null;
			}

			// Start the connected thread
			connected(mmSocket, mmDevice);
		}

		public void cancel() {
			try {
				mmSocket.close();
			} catch (IOException e) {
				Log.e(TAG, "close() of connect socket failed", e);
			}
		}
	}

	/**
	 * This thread runs during a connection with a remote device. It handles all
	 * incoming and outgoing transmissions.
	 */
	private class ConnectedThread extends Thread {
		private final BluetoothSocket mmSocket;

		private final InputStream mmInStream;
		private final DataInputStream mmObjectInStream;

		private final OutputStream mmOutStream;
		private final ObjectOutputStream mmObjectOutStream;

		/* added in others. */
		private String read;

		public String getRead() {
			return read;
		}

		public void setRead(String read) {
			this.read = read;
		}

		

		@SuppressWarnings("unused")
		public void setAllowStringInput(boolean allowStringInput) {
			AllowStringInput = allowStringInput;
		}

		public ConnectedThread(BluetoothSocket socket) {
			Log.d(TAG, "create ConnectedThread");
			mmSocket = socket;
			InputStream tmpIn = null;
			OutputStream tmpOut = null;
			ObjectOutputStream tmpObjOut = null;
			DataInputStream tmpObjIn = null;

			// Get the BluetoothSocket input and output streams
			try {
				tmpIn = socket.getInputStream();
				tmpOut = socket.getOutputStream();
				tmpObjOut = new ObjectOutputStream(tmpOut);
				tmpObjIn = new DataInputStream(tmpIn);
			} catch (IOException e) {
				Log.e(TAG, "temp sockets not created", e);
			}

			mmInStream = tmpIn;
			mmOutStream = tmpOut;
			mmObjectOutStream = tmpObjOut;
			mmObjectInStream = tmpObjIn;
		}

		@Override
		public void run() {
			Log.i(TAG, "BEGIN mConnectedThread");
			int bytes;
			byte[] buffer = new byte[1024];
			String readMessage ;
			 name_file = new ArrayList<>();
			while (true) {
				try {
			    bytes = mmInStream.read(buffer);
				if(which_activity=="file"){
					readMessage = new  String(buffer,0,bytes);
					name_file.add(readMessage);
					setRead(readMessage);
					Log.i("readMessage", readMessage);

					if (mmInStream.available()>1) {
						Log.i(TAG, readMessage);
						mHandler.obtainMessage(_Global.MESSAGE_READ, bytes, -1, buffer).sendToTarget();   
						if (mmInStream.available()==1){
							SystemClock.sleep(6000);
				            }
					}

				}
					if(which_activity=="mouse"){
	                    mHandler.obtainMessage(_Global.MESSAGE_READ, bytes, -1, buffer)
	                            .sendToTarget();
					}
				} catch (Exception e) {
					 Log.e(TAG, "disconnected because of bytes input", e); connectionLost();
					 break;

				}
			}
		}

		/**
		 * Write to the connected OutStream.
		 * 
		 * @param buffer
		 *            The bytes to write
		 */
		public void write(Motion out) {
			try {
				mmObjectOutStream.writeObject(out);
			} catch (IOException e) {
				Log.e(TAG, "Exception during write", e);
			}
		}

		public void write(String str) {
			try {
				byte[] b = str.getBytes();
				mmObjectOutStream.write(b);
				//mmObjectOutStream.writeUTF(str);
				mmObjectOutStream.flush();
				/*String bStr = new String(b); 
				Log.i(TAG, "Sting that written "+ bStr);*/
			} catch (Exception e) {
				Log.e(TAG, "Exception during write string", e);
			}
		}

		public String read() {
			try {
				return mmObjectInStream.readUTF();
			} catch (Exception e) {
				Log.e(TAG, "Exception during reading string", e);
				return null;
			}
		}

		public void cancel() {
			try {
				mmOutStream.write(_Global.EXIT_CMD);
				mmSocket.close();
			} catch (IOException e) {
				Log.e(TAG, "close() of connect socket failed", e);
			}
		}

	}

	public String getRead2() {
		return mConnectedThread.getRead();
	}

}