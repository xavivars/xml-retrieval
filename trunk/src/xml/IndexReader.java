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

package xml;

import utils.WordMap;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import javax.xml.parsers.*;

/**
 * 
 * @author ebenimeli
 *
 */
public class IndexReader extends SAXReader {
	
	/**
	 * 
	 */
	private WordMap wordMap;
	
	/**
	 * 
	 */
	private String fileName;
	
	/**
	 * 
	 * @param i
	 */
	public IndexReader(String indexFile) {
            fileName = indexFile;		
	}
	/**
	 * 
	 * @return
	 */
	public WordMap read() {
            getText(fileName);
            return getWordMap();
	}
	
	/**
	 * 
	 * @return
	 */
	private WordMap getWordMap() {
		return wordMap;
	}
	
	/**
	 * 
	 * @param wordMap
	 */
	private void setWordMap(WordMap wordMap) {
		this.wordMap = wordMap;
	}
}
