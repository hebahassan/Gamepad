package wifi_connection.classes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import bluetooth_Connection._Global;

import com.gamepad.Check;
import com.gamepad.R;
import com.gamepad.gamepad_Global;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

@SuppressLint("HandlerLeak")
public class Client extends ServerSocket implements Runnable {

	protected static final String TAG = "FILEMANAGER.";
	static InputStream in;
	static OutputStream out;
	static DataOutputStream outToServer;
	static DataInputStream inFromServer;
	String sentence, modifiedSentence;
	private Socket socket;
	static private String serverIpAddress = "192.168.0.3";
	static private int port = 2001;
	public String Read;

	/* others */
	public static TextView mTitleTextView;
	public static ImageButton wifiCheck;
	public static Switch switchBt;
	public static boolean checkConnection = false;

	private static String specialCh = "&";

	public Client() throws IOException {
		super();
	}

	public String getServerIpAddress() {
		return serverIpAddress;
	}

	public void setServerIpAddress(String serverIpAddress) {
		Client.serverIpAddress = serverIpAddress;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		Client.port = port;
	}

	@Override
	public void run() {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					socket = new Socket();

					socket.connect(
							new InetSocketAddress(serverIpAddress, port),
							900000);
					in = socket.getInputStream();
					inFromServer = new DataInputStream(in);

					out = socket.getOutputStream();
					outToServer = new DataOutputStream(out);
					if (socket.isConnected()) {
						checkConnection = true;
					}
					while (true) {
						if (in.available() > 0) {
							Read();
						} else
							break;

					}

				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
		mTitleTextView = (TextView) Check.ac
				.findViewById(R.id.title_right_text);
		switchBt = (Switch) Check.ac.findViewById(R.id.switch1);
		wifiCheck = (ImageButton) Check.ac.findViewById(R.id.Wifi_check);
	}

	@SuppressLint("HandlerLeak")
	public final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			mTitleTextView = (TextView) Check.ac
					.findViewById(R.id.title_right_text);
			mTitleTextView.setBackgroundResource(R.drawable.off);
			switchBt.setChecked(false);
			switchBt.setClickable(false);
			wifiCheck
			.setImageResource(R.drawable.wifi);
			
		}

	};

	public void stop() throws IOException, InterruptedException {
		sendMsg("Bye");
		Wifi_Global.client.close();
		checkConnection = false;

	}

	public void sendMsg(final String str) throws InterruptedException {

		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					outToServer.writeUTF(str);
					outToServer.flush();
				} catch (IOException e) {
					Read += " Error 5: Error in Sending To Server.";
					connectionLost();
				}
			}
		});
		t.start();
		Thread.sleep(1000);
	}

	public void sendObject(bluetooth_Connection.Motion motion) {
		try {
			outToServer.writeUTF(motion.type + "&" + motion.x + "&" + motion.y);
		} catch (IOException e) {
			Read += "couldn't send";
			connectionLost();
		}
	}

	public String Read() {

		String str;
		try {
			str = inFromServer.readUTF();
		} catch (IOException e) {
			str = "Error 10: error in reading message from client.";
			connectionLost();
		}
		return str;
	}

	private void connectionLost() {
		gamepad_Global.checkConnection = false;
		gamepad_Global.connectionType = null;

		Message msg = mHandler.obtainMessage(_Global.MESSAGE_STATE_CHANGE);
		Bundle bundle = new Bundle();
		bundle.putString(Check.TOAST, "Device connection was lost");
		msg.setData(bundle);
		mHandler.sendMessage(msg);
	}

	// to create a list of files.
	public ArrayList<String> ReadMsg(final ArrayList<fileManger> list) {

		final ArrayList<String> files = new ArrayList<String>();
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Read = inFromServer.readUTF();
					while (!Read.isEmpty() && Read.contains(specialCh)) {
						String[] lines = Read.split("\n");
						for (String line : lines) {
							fileManger file = new fileManger();
							String[] parts = line.split(specialCh);
							file.setName(parts[0]);
							file.setPath(parts[1]);
							files.add(file.getName());
							list.add(file);
						}
						Read = inFromServer.readUTF();
						Log.i(TAG, Read);

					}
					if (Read.isEmpty()) {
						fileManger file = new fileManger();
						file.setName("empty");
						file.setPath("Empty");
						list.add(file);
					}
				} catch (IOException e) {
					connectionLost();
				}
			}
		});
		t.start();
		return files;
	}

}
