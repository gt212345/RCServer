package RCServer;

import java.io.Serializable;

public class ChoicesServer implements Serializable{
     
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