package RCServer;

import java.io.Serializable;

public class ChoicesServer implements Serializable{
     
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private int key;
 
    public ChoicesServer(int key) {
        super();
        this.key = key;
    }
 
    public int getKey() {
        return key;
    }
 
    public void setKey(int key) {
        this.key = key;
    }
     
}