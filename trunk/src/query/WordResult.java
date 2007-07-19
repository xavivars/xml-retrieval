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

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * @author ebenimeli
 * 
 */
public class WordResult {

	/**
	 * 
	 */
	private String name;

	/**
	 * 
	 */
	private int times;

	/**
	 * 
	 */
	private ArrayList<Document> documents;

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public final void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the documents
	 */
	public final ArrayList<Document> getDocuments() {
		return documents;
	}

	/**
	 * @param documents
	 *            the documents to set
	 */
	public final void setDocuments(ArrayList<Document> documents) {
		this.documents = documents;
	}

	/**
	 * ¡¡¡¡¡ATENCIÓN RENDIMIENTO!!!!
	 * 
	 * @param document
	 */
	public final void addDocument(final Document document) {
		boolean find = false;
		Iterator it = documents.iterator();
		while (it.hasNext() && !find) {
			// for (int i = 0 ; i < documents.size() && !find ; i++) {
			Document current = (Document) it.next();
			// Document current = documents.get(i);
			if (document.getName().compareTo(current.getName()) == 0) {
				// documents.remove(i);
				System.out.println(current.getName());
				find = true;
				for (String path : document.getPaths()) {
					current.addPath(path);
				}

			}
		}
		if (!find) {
			documents.add(document);
		}
	}

	public final boolean containsDocument(final Document document) {
		boolean find = false;
		Iterator it = documents.iterator();
		while (it.hasNext() && !find) {
			Document current = (Document) it.next();
			if (document.getName().compareTo(current.getName()) == 0) {
				find = true;
			}
		}

		return find;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

}
