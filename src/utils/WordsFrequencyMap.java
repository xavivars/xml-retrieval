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
 * @author
 * 
 */
public class WordsFrequencyMap extends HashMap<String, Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param newToken
	 */
	protected void addWord(String newToken) {
		if (containsKey(newToken)) {
			Integer freq = get(newToken);
			freq++;
			put(newToken, freq);
		} else {
			put(newToken, new Integer(1));
		}
	}

	/**
	 * 
	 * 
	 */
	public final void print(int limit) {
		final Set keySet = keySet();
		final Iterator it = keySet.iterator();

		int nStopWords = 0;
		while (it.hasNext()) {
			final String key = (String) it.next();
			final Integer value = get(key);
			if (value <= limit) {
				System.out.println(key + " (" + value + ")");
				nStopWords++;
			}
		}

		System.out.println("Stop words: " + nStopWords);
	}

	/**
	 * 
	 * @param fileName
	 * @param top
	 * @param bottom
	 */
	public void printXML(final String fileName, int top, int bottom) {
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
			dos.writeBytes("<stop-words>\n");

			int nStopWords = 0;

			while (it.hasNext()) {
				final String key = (String) it.next();
				final Integer value = get(key);
				if ((value >= bottom) && (value <= top)) {
					dos.writeBytes("\t<word f=\"" + value + "\">" + key
							+ "</word>\n");
					nStopWords++;
				}
			}

			dos.writeBytes("</stop-words>\n");
			dos.writeBytes("<!-- " + nStopWords + " stop-words -->\n");
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
