/*
 * Copyright (C) 2007 
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

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.w3c.dom.Element;

/**
 * 
 * @author Enrique Benimeli Bofarull
 *
 */
public class DocumentReader extends XMLReader {

	/**
	 * 
	 * @param fileName
	 */
	public DocumentReader(final String fileName, WordMap wordMap) {
		super(fileName);
		setWordMap(wordMap);
	}
	
	/**
	 * 
	 * @return Por ahora devuelve un Object (null)
	 */
	public final WordList readDocument() {
		System.out.println("Reading document '" + getFileName() + "'");
		Element root = getDocument().getDocumentElement();
		String path = "/article[1]";
		readChildren(root, path);
		WordList wordList = getWordList();
		return wordList;
	}

	
}
