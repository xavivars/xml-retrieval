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
	
        
        private String searchWord, tempWord, tempDocument, tempReference;
        
        private boolean value, document, reference, find;
        
	/**
	 * 
	 * @param i
	 */
	public IndexReader(String indexFile) {
            fileName = indexFile;	
            value = false;
            document = false;
            reference = false;
            searchWord = "";
            tempWord = "";
            tempDocument = "";
            tempReference = "";
	}
	/**
	 * 
	 * @return
	 */
	public WordMap read() {
            getText(fileName);
            return getWordMap();
	}
	
        
        public WordMap search (String word) {
            searchWord = word;
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
        
         public void characters(final char[] c, final int start, final int length) {
             
             
        if (length > 0) {
            try {
                if (value) {
                    buffer.append(c, start, length);
                    tempWord = buffer.toString();
                    buffer = new StringBuilder (kStringBuilder);
                }
                else if (document && (tempWord.compareTo(searchWord) == 0 || searchWord.isEmpty())) {
                    buffer.append(c, start, length);
                    tempDocument = buffer.toString();
                    buffer = new StringBuilder (kStringBuilder);
                }
                else if (reference && (tempWord.compareTo(searchWord) == 0 || searchWord.isEmpty())) {
                    buffer.append(c, start, length);
                    tempReference = buffer.toString();
                    buffer = new StringBuilder (kStringBuilder);
                }
            }
            catch (java.nio.BufferOverflowException x) {
                System.err.println("Insufficient text buffer size");
                System.exit(1);
            }
        }
    }

    public void startElement(final String uri, final String localName,
                             final String tag, final Attributes attributes) {
        if (tag.equals("value")) {
            value = true;
            document = false;
            reference = false;
        }
        else if (tag.equals("doc")) {
            value = false;
            document = true;
            reference = false;
        }
        else if (tag.equals("ref")) {
            value = false;
            document = false;
            reference = true;
        }
    }
    
    public void endElement(final String uri, final String localName, final String tag) { 
        if (tempWord.compareTo(searchWord) == 0 || searchWord.isEmpty()) {
            rootIndexMap.put(tempWord, tempReference);
        }
        tempWord = "";
        tempReference = new ReferenceList ();
    }
        
}
