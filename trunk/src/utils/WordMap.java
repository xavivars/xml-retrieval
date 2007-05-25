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
 * @author ebenimeli
 * 
 */
public class WordMap extends HashMap<String, WordList> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param word
	 */
	public final void addWord(final Word word) {
		WordList wordList = null;
		if (containsKey(word.getValue())) {
			wordList = get(word.getValue());
			wordList.add(word);
		} else {
			// System.out.println("New word: " + word.getValue());
			wordList = new WordList();
			wordList.add(word);
			put(word.getValue(), wordList);
		}
	}

	/**
	 * 
	 * 
	 */
	public final void print() {
		final Set keySet = keySet();
		final Iterator it = keySet.iterator();

		int nWords = 0;
		while (it.hasNext()) {
			final String key = (String) it.next();
			final WordList wordList = get(key);
			wordList.print();
			nWords++;
		}
		System.out.println("Words: " + nWords);
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
				final WordList wordList = get(key);
				wordList.printXML(dos);
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
