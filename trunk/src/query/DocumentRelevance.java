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
public class DocumentRelevance implements Comparable<DocumentRelevance>{

	/**
	 *
	 */
	private double weight;

	/**
	 *
	 */
	private String name;

	/**
	 *
	 */
	private ArrayList<WordPaths> wordPaths;

	/**
	 *
	 */
	public final void calcWeight() {

		this.weight = 0;

		if(this.wordPaths.size()>0) {
			for(WordPaths wp : wordPaths) {
				this.weight += wp.calcWeight();
			}
		}

	}

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
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public final void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the wordPaths
	 */
	public final ArrayList<WordPaths> getWordPaths() {
		return wordPaths;
	}

	/**
	 * @param wordPaths the wordPaths to set
	 */
	public final void setWordPaths(ArrayList<WordPaths> wordPaths) {
		this.wordPaths = wordPaths;
	}

	/**
	 *
	 */
	public final int compareTo(DocumentRelevance dr) {

		int ret = 0;

		if(this.weight > dr.weight)
			ret = -1;
		else
			if(this.weight < dr.weight)
				ret = 1;

		return ret;
	}

	public final void addWordPaths(WordPaths wp) {

		if(wordPaths == null)
			wordPaths = new ArrayList<WordPaths> () ;

			wordPaths.add(wp);

	}


}
