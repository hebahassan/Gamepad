package wifi_connection.classes;

import java.util.ArrayList;

public class fileManger {
	private long ID;
	private String name;
	private String path;

	public fileManger() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public String getPathByName(String str, ArrayList<fileManger> list) {
		String path = null;
		for (fileManger file : list) {
			if (file.getName() == str)
				path = file.getPath();
		}
		return path;
	}

	public ArrayList<String> GetNamesOfList(ArrayList<fileManger> list) {
		ArrayList<String> names = new ArrayList<String>();
		for (fileManger file : list) {
			names.add(file.getName());
		}
		return names;
	}

}
