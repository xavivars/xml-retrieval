package query;

import java.util.ArrayList;

import utils.SimpleWord;

/**
 * 
 * @author
 * 
 */
public class Query extends ArrayList<SimpleWord> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -660371061917981165L;

	/**
	 * 
	 * 
	 */
	public final void print() {
		for (final SimpleWord w : this) {
			w.printInfo();
		}
	}

}