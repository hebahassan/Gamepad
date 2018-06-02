package bluetooth_Connection;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gamepad.Check;
import com.gamepad.FileManger;
import com.gamepad.MainActivity;
import com.gamepad.R;


@SuppressLint("HandlerLeak") public class Bluetooth_activity {
    //private String mConnectedDeviceName = null;
    public static BluetoothAdapter mBluetoothAdapter = null;
    public static  BluetoothConnectionService mCommandService = null;
    private String mConnectedDeviceName = null;
	public static TextView mTitleTextView;
	public static int orient;
	
	ImageView blu_img;
	ImageButton wifiCheck;

    public static TextView mTitl;
	public static long end;

    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
    
	public boolean availability(){
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
        	return false;
          
        }
        else
        	return true;
	}
	
	public void Enable(){
		//Activity ac = null;
		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			Check.ac.startActivityForResult(enableIntent, _Global.REQUEST_ENABLE_BT);
			Toast.makeText(Check.ac, "After enable Click Again for your Paired Devices ", Toast.LENGTH_LONG).show();
		}

		else {
			if (mCommandService==null)
				setupCommand();
		}
		
	}
	
	public void setupCommand() {
        mCommandService = new BluetoothConnectionService(mHandler);
	}
	
	public final Handler mHandler = new Handler() {
        @Override
        
        public void handleMessage(Message msg) {
        	mTitleTextView = (TextView) Check.ac.findViewById(R.id.title_right_text);

            switch (msg.what) {
            
            case _Global.MESSAGE_STATE_CHANGE:
                switch (msg.arg1) {
                
                case _Global.STATE_CONNECTED:
 
                    	mTitleTextView.setText(R.string.title_connected_to);
                    	mTitleTextView.append(mConnectedDeviceName);
                    	mTitleTextView.setBackgroundResource(R.drawable.on);
                    	Check.blu_img.setImageResource(R.drawable.bluetooth_on);
                    	Check.switchBt.setChecked(true);
                    	Check.switchBt.setClickable(true);
                    	if(orient==Configuration.ORIENTATION_LANDSCAPE){
                    	MainActivity.main_con.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    	FileManger.file_con.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                    	}
                    	if(orient==Configuration.ORIENTATION_PORTRAIT){
                        	MainActivity.main_con.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
               //         	FileManger.file_con.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                    	}
                    //3amal connect 5alas haykteb fe el label foo2 connected:NitroPC
                    break;
                
                case _Global.STATE_CONNECTING:
                	mTitleTextView.setText(R.string.title_connecting);
                	mTitleTextView.setBackgroundResource(R.drawable.off);
                	Check.blu_img.setImageResource(R.drawable.bluetooth);
                	orient=MainActivity.main_con.getResources().getConfiguration().orientation;
                	Check.switchBt.setChecked(false);
                	Check.switchBt.setClickable(false);
                	Log.e("connecting", ""+orient);
                    //by3mel connect lsa hayktep foo2 connection...
                    break;
                case _Global.STATE_LISTEN:
                	Log.e("listing", "listing");

                case _Global.STATE_NONE:
                	orient=MainActivity.main_con.getResources().getConfiguration().orientation;
                		mTitleTextView.setBackgroundResource(R.drawable.off);
                    	Check.blu_img.setImageResource(R.drawable.bluetooth);
                    	Check.switchBt.setChecked(false);
                    	Check.switchBt.setClickable(false);
                		Log.e("not connected", ""+orient);
                    //el connection baz
                    break;                
                }
                break;
            
            case _Global.MESSAGE_DEVICE_NAME:
               mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
              Toast.makeText(MainActivity.main_con.getApplicationContext() , "Connected to " + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                //awel may3mel connect haytala3ly toast maktop feha "connected to NiTroOoPc"
                break;
           
            case _Global.MESSAGE_TOAST:
                Toast.makeText(MainActivity.main_con.getApplicationContext() , msg.getData().getString(TOAST),
                              Toast.LENGTH_SHORT).show();
                break;
            }
        }
    };
        public void resume(){
		if (mCommandService != null) {
			if (mCommandService.getState() == _Global.STATE_NONE) {
				mCommandService.start();
			}
		}
    }
    
    public void destRoy(){
		if (mCommandService != null)
			mCommandService.stop();
    }

    public void ensureDiscoverable(Activity act) {
        if (mBluetoothAdapter.getScanMode() !=
            BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            act.startActivity(discoverableIntent);
        }
    }
    
    public void Request_THEN_connect(Intent data){
        String address = data.getExtras().getString(BluetoothListActivity.EXTRA_DEVICE_ADDRESS);
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        /** connect de function elle mas2ola 2n elrabt htla2eha  
         fe class BluetoothconnectionService **/
      mCommandService.connect(device);
        
       
    }

}
