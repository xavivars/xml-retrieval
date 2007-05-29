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

import java.util.StringTokenizer;

import org.w3c.dom.Element;

import xml.DOMReader;

/**
 * 
 * @author
 * 
 */
public class WordCounter extends DOMReader {

	/**
	 * 
	 */
	private WordsFrequencyMap wordsFrequency;

	/**
	 * 
	 * @param fileName
	 * @param wf
	 */
	public WordCounter(String fileName, WordsFrequencyMap wf) {
		super(fileName);
		setWordsFrequency(wf);
	}

	/**
	 * 
	 * @param path
	 * @return
	 */
	@Override
	protected void tokenize(final String text, final String document,
			final String path) {
		StringTokenizer tokens = new StringTokenizer(text, " ");
		while (tokens.hasMoreElements()) {
			String token = tokens.nextToken();
			String newToken = formatToken(token);
			if (newToken != null) {
				getWordsFrequency().addWord(newToken);
			} else {
				// System.out.println("'" + token + "' is not a valid token");
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public final WordList readDocument() {
		analize();
		try {
			Element root = getDocument().getDocumentElement();
			String path = "/article[1]";
			readChildren(root, path);
			// System.out.println("WordMap size: " + getWordMap().size());
		} catch (final NullPointerException npe) {
			npe.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public WordsFrequencyMap getWordsFrequency() {
		return wordsFrequency;
	}

	/**
	 * 
	 * @param wordsFrequency
	 */
	public void setWordsFrequency(WordsFrequencyMap wordsFrequency) {
		this.wordsFrequency = wordsFrequency;
	}
}
