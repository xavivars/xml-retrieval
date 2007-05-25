/*
 * ReferenceList.java
 *
 * Created on 24 de mayo de 2007, 17:39
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * @author david
 */
public class ReferenceList extends ArrayList<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance of ReferenceList
	 * 
	 */
	public ReferenceList() {
	}

	/**
	 * 
	 * @param reference
	 */
	public void addReference(Integer reference) {
		boolean find = false;

		for (int i = 0; (i < size()) && !find; i++) {
			if (i == reference) {
				find = true;
			}
		}
		if (!find) {
			add(reference);
		}
	}

	/**
	 * 
	 * @param dos
	 */
	public final void printXML(DataOutputStream dos) {
		try {

			for (final Integer w : this) {
				dos.writeBytes("<ref>" + w + "</ref>\n");
			}
		} catch (final IOException ioe) {
			ioe.printStackTrace();
		}

	}

}
