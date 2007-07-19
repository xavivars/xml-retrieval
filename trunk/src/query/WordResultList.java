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

package query;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * @author ebenimeli
 * 
 */
public class WordResultList extends ArrayList<WordResult> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * 
	 */
	public final void print() {
		for (final WordResult wordResult : this) {
			System.out.println("Word: " + wordResult.getName());
			final ArrayList<Document> documents = wordResult.getDocuments();
			for (final Document document : documents) {
				System.out.println("\tDocument: " + document.getName());
				final ArrayList<String> paths = document.getPaths();
				for (String path : paths) {
					System.out.println("\t\tPath: " + path);
				}
			}
			System.out.println("");
		}
	}

	/**
	 * 
	 * @param fileName
	 */
	public final void printXML(final String fileName) {
		BufferedOutputStream bos;
		FileOutputStream fos;
		DataOutputStream dos;

		try {
			fos = new FileOutputStream(fileName);
			bos = new BufferedOutputStream(fos);
			dos = new DataOutputStream(bos);
			dos.writeBytes("<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>\n");
			dos.writeBytes("<word-list>\n");

			int nWords = 0;

			for (final WordResult wordResult : this) {
				dos.writeBytes("\t<word>\n");
				dos.writeBytes("\t\t<value>" + wordResult.getName()
						+ "</value>\n");
				final ArrayList<Document> documents = wordResult.getDocuments();
				for (final Document document : documents) {
					dos.writeBytes("\t\t<document id=\"" + document.getName()
							+ "\">\n");
					final ArrayList<String> paths = document.getPaths();
					for (String path : paths) {
						dos.writeBytes("\t\t\t<path ref=\"" + path + "\"/>\n");
					}
					dos.writeBytes("\t\t</document>\n");
				}
				dos.writeBytes("\t</word>\n\n");
			}

			dos.writeBytes("</word-list>\n");
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

	public void addWordResultList(WordResultList wrl) {
		boolean find;
		WordResult oldwr = new WordResult();

		for (WordResult newwr : wrl) {
			find = false;
			Iterator it = iterator();
			while (it.hasNext() && !find) {
				oldwr = (WordResult) it.next();
				if (oldwr.getName().compareTo(newwr.getName()) == 0) {
					find = true;
				}
			}
			if (!find) {
				add(newwr);
			} else {
				for (Document doc : newwr.getDocuments()) {
					oldwr.addDocument(doc);
				}
			}
		}
	}

}
