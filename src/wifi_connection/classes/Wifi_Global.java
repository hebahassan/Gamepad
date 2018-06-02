package wifi_connection.classes;

import java.util.ArrayList;

import localDataBase.DatabaseSource;

public class Wifi_Global {
	public static Client client ;
	public static boolean checkFiles = false;
	public static ArrayList<fileManger> drives = null;
	public static ArrayList<String> names = null;
	public static DatabaseSource dbsource;
	public static boolean checkConnection = false;
	public static boolean reConnect = false;
}
