package utils;

import java.util.ArrayList;

public class Query extends ArrayList < SimpleWord >{

	/**
	 * 
	 */
	private static final long serialVersionUID = -660371061917981165L;

	public final void print() {
		for (SimpleWord w : this) {
			w.printInfo();
		}
	}
	
}
