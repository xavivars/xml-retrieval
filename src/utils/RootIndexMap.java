/*
 * RootIndexWord.java
 *
 * Created on 24 de mayo de 2007, 17:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utils;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * @author david
 */
public class RootIndexMap extends HashMap<String, ReferenceList> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance of RootIndexWord
	 * 
	 */
	public RootIndexMap() {

	}

	/**
	 * 
	 * @param word
	 */
	public final void addReference(final Word word, final Integer reference) {
		ReferenceList referenceList = null;
		if (containsKey(word.getValue())) {
			referenceList = get(word.getValue());
			referenceList.addReference(reference);
			put(word.getValue(), referenceList);
		} else {
			// System.out.println("New word: " + word.getValue());
			referenceList = new ReferenceList();
			referenceList.add(reference);
			put(word.getValue(), referenceList);
		}
	}

	/**
	 * 
	 * @param fileName
	 */
	public void printXML(final String fileName) {
		BufferedOutputStream bos;
		FileOutputStream fos;
		DataOutputStream dos;
		final Set keySet = keySet();
		final Iterator it = keySet.iterator();

		try {
			fos = new FileOutputStream(fileName);
			bos = new BufferedOutputStream(fos);
			dos = new DataOutputStream(bos);
			dos.writeBytes("<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>\n");
			dos.writeBytes("<index>\n");

			int nWords = 0;

			while (it.hasNext()) {
				final String key = (String) it.next();
				dos.writeBytes("<word>\n");
				dos.writeBytes("<value>" + key + "</value>\n");
				final ReferenceList referenceList = get(key);
				referenceList.printXML(dos);
				dos.writeBytes("</word>\n");
				nWords++;
			}

			dos.writeBytes("</index>\n");
			dos.writeBytes("<!-- " + nWords + " words -->\n");
			fos = null;
			bos = null;
			dos.close();
			dos = null;
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final Exception eg) {
			eg.printStackTrace();
		}
	}

}
