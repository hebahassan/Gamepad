package bluetooth_Connection;

import java.io.IOException;
import java.util.ArrayList;

import android.util.Log;

import wifi_connection.classes.fileManger;

public class fileMangerWork {

	public static String errorMsg;


	public static ArrayList<String> readFm(final ArrayList<fileManger> list) {

		final ArrayList<String> files = new ArrayList<String>();
	//	String Read = Bluetooth_activity.mCommandService.getRead2();
		String Read ;
		String specialCh = "&";
		String[] Lines;
		for(int i =0;i<BluetoothConnectionService.name_file.size();i++){
			Read = BluetoothConnectionService.name_file.get(i);
		
		if (!Read.isEmpty()) {
			Log.e("Read", Read);
			if (Read.contains("\n")) {
				 Lines = Read.split("\n");
				for (String line : Lines) {
					if (line.contains(specialCh)) {
						String[] file = line.split(specialCh);
						fileManger fm = new fileManger();
						fm.setName(file[0]);
						fm.setPath(file[1]);
						files.add(fm.getName());
						list.add(fm);
					}
				}
			}
		}
		}

		return files;
	}

	public static void sendMsg(String str) throws IOException {
		Bluetooth_activity.mCommandService.writeString(str);
	}

	public static void sendMotion(Motion motion) {
		Bluetooth_activity.mCommandService.write(motion);
	}

}
