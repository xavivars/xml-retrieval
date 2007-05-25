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
public class WordResultList extends ArrayList<WordResult>{

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
				System.out.println("\tDocument: "+ document.getName());
				final ArrayList<String> paths = document.getPaths();
				for (String path : paths) {
					System.out.println("\t\tPath: " + path);
				}
			}
			System.out.println("");
		}
	}
}
