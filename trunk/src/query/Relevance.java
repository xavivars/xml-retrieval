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

/**
 *
 * @author ebenimeli
 *
 */
public class Relevance extends ArrayList<DocumentRelevance>{

	/**
	 *
	 */
	public final boolean contains(final String str) {

		boolean ret = false;

		for(final DocumentRelevance dr : this)
		{
			if(dr.getName()==str) {
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

		for(final DocumentRelevance dr : this)
		{
			if(dr.getName()==name) {
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
			System.out.println("Document: " + dr.getName());
			final ArrayList<WordPaths> wps = dr.getWordPaths();
			for (final WordPaths wp : wps) {
				System.out.println("\tWord: "+ wp.getName());
				final ArrayList<String> paths = wp.getPaths();
				for (String path : paths) {
					System.out.println("\t\tPath: " + path);
				}
			}
			System.out.println("");
		}
	}

	/**
	 *
	 */
	public final void setRelevance() {

		for (final DocumentRelevance dr : this) {
			dr.calcWeight();
		}
	}


}
