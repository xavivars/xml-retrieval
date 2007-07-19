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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * @author ebenimeli
 * 
 */
public class Relevance extends ArrayList<DocumentRelevance> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private HashMap<String, Integer> ocs = null;

	/**
	 * 
	 */
	public final boolean contains(final String str) {

		boolean ret = false;

		for (final DocumentRelevance dr : this) {
			if (dr.getName().equals(str)) {
				ret = true;
				break;
			}
		}

		return ret;
	}

	/**
	 * 
	 */
	public final DocumentRelevance get(String name) {
		DocumentRelevance ret = null;

		for (final DocumentRelevance dr : this) {
			if (dr.getName().equals(name)) {
				ret = dr;
				break;
			}
		}

		return ret;
	}

	/**
	 * 
	 */
	public final void print() {
		for (final DocumentRelevance dr : this) {
			System.out.println("Document: " + dr.getName() + " (weight: "
					+ dr.getWeight() + ")");
			final ArrayList<WordPaths> wps = dr.getWordPaths();
			for (final WordPaths wp : wps) {
				System.out.println("\tWord: " + wp.getName());
				final ArrayList<String> paths = wp.getPaths();
				for (final String path : paths) {
					System.out.println("\t\tPath: " + path);
				}
			}
			System.out.println("");
		}
	}

	/**
	 * 
	 * 
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
			dos.writeBytes("<search-result>\n");

			for (final DocumentRelevance dr : this) {
				// System.out.println("Document: " + dr.getName() + " (weight: "
				// + dr.getWeight() + ")");
				dos.writeBytes("\t<document relevance=\"" + dr.getWeight()
						+ "\" name=\"" + dr.getName() + "\">\n");
				final ArrayList<WordPaths> wps = dr.getWordPaths();
				for (final WordPaths wp : wps) {
					// System.out.println("\tWord: "+ wp.getName());
					dos.writeBytes("\t\t<word>\n");
					dos.writeBytes("\t\t\t<value>" + wp.getName()
							+ "</value>\n");
					dos.writeBytes("\t\t\t<paths>\n");
					final ArrayList<String> paths = wp.getPaths();
					for (final String path : paths) {
						dos.writeBytes("\t\t\t\t<path>" + path + "</path>\n");
					}
					dos.writeBytes("\t\t\t</paths>\n");
					dos.writeBytes("\t\t<word>\n");
				}
				dos.writeBytes("\t</document>\n");
			}
			dos.writeBytes("</search-result>\n");

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

	/**
	 * 
	 */
	public final void setRelevance() {

		int totalOcs = 0;

		Iterator it = ocs.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();

			totalOcs += (Integer) e.getValue();
		}

		for (final DocumentRelevance dr : this) {
			dr.calcWeight(totalOcs);
		}
	}

	/**
	 * @param ocs
	 *            the ocs to set
	 */
	public void setOcs(HashMap<String, Integer> ocs) {
		this.ocs = ocs;
	}

}
