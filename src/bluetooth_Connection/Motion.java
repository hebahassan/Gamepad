package bluetooth_Connection;

import java.io.Serializable;

public class Motion implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8260334689396475951L;
	public Motion(int type, int x, int y) {
        this.type = type;
		this.x = x;
        this.y = y;
    }
	
	public int type;
    public int x;
    public int y;
}
