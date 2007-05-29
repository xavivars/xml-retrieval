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

import java.util.HashMap;
import query.Query;
import utils.RootIndexMap;
import utils.ReferenceList;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import javax.xml.parsers.*;
import utils.SimpleWord;

/**
 * 
 * @author ebenimeli
 *
 */
public class RootIndexReader extends SAXReader {
    
    private boolean value, reference, occurrence;
    private String tempWord, fileName;
    private ReferenceList tempReference;
    private HashMap < String, String > queryWords;
    	
    /**
     * 
     */
    private RootIndexMap rootIndexMap;
    
    
    public RootIndexReader (String file) {
        value = false;
        reference = false;
        occurrence = false;
        tempReference = new ReferenceList ();
        tempWord = "";
        rootIndexMap = new RootIndexMap ();
        fileName = file;
        queryWords = new HashMap < String, String > ();
    }
    
    
    private void readQuery (Query query) {
        for (SimpleWord word: query) {
            queryWords.put(word.getValue(), null);
        }
    }
    

    public RootIndexMap search (Query query) {
        readQuery(query);
        getText(fileName);
        return getRootIndexMap();
    }    
    
    /**
     * 
     * @return
     */
    private RootIndexMap getRootIndexMap() {
            return rootIndexMap;
    }

    /**
     * 
     * @param rootIndexMap
     */
    private void setRootIndexMap(RootIndexMap rootIndexMap) {
            this.rootIndexMap = rootIndexMap;
    }

    public void characters(final char[] c, final int start, final int length) {
        if (length > 0) {
            try {
                if (value) {
                    buffer.append(c, start, length);
                    tempWord = buffer.toString();
                    buffer = new StringBuilder (kStringBuilder);
                }
                else if (reference && queryWords.containsKey(tempWord)) {
                    buffer.append(c, start, length);
                    tempReference.add(new Integer((buffer.toString())));
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
        }
        else if (tag.equals("ref")) {
            reference = true;
        }
        else if (tag.equals("ocu")) {
            occurrence = true;
        }
    }
    
    public void endElement(final String uri, final String localName, final String tag) { 
        if(value) {
            value = false;
        }
        if (reference) {
            reference = false;
        }
        if (occurrence) {
            occurrence = false;
            if (queryWords.containsKey(tempWord)) {
                rootIndexMap.put(tempWord, tempReference);
                if(!queryWords.isEmpty()) {
                    queryWords.remove(tempWord);
                    // Si hemos encontrado todas las palabras, forzamos al parser a acabar
                    if (queryWords.isEmpty()) {
                        reader = null;
                    }
                }
            }
            tempWord = "";
            tempReference = new ReferenceList ();
        }
    }
}

 



