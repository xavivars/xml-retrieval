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

/**
 * 
 * @author ebenimeli
 *
 */
public class DocumentRelevance {

	/**
	 * 
	 */
	private double weight;
	
	/**
	 * 
	 */
	private WordPaths wordPaths;

	/**
	 * @return the weight
	 */
	public final double getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public final void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * @return the wordPaths
	 */
	public final WordPaths getWordPaths() {
		return wordPaths;
	}

	/**
	 * @param wordPaths the wordPaths to set
	 */
	public final void setWordPaths(WordPaths wordPaths) {
		this.wordPaths = wordPaths;
	}
	
	/**
	 * 
	 * @param path
	 */
	public final void addPath(final String path) {
		this.addPath(path);
	}
	
	
	
}