/*
 * Copyright (C) 2007
 *
 * Authors:
 *  Enrique Benimeli Bofarull <ebenimeli@gmail.com>
 *  David Ortega Parilla <dortegaparrilla@gmail.com>
 *  Xavier Ivars i Ribes <xavi@infobenissa.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
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
			if (!referenceList.exists(reference)) {
				referenceList.addReference(reference);
			}
			referenceList.incOccurrences();
			put(word.getValue(), referenceList);
		} else {
			// System.out.println("New word: " + word.getValue());
			referenceList = new ReferenceList();
			referenceList.add(reference);
			referenceList.setOccurrences(1);
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
