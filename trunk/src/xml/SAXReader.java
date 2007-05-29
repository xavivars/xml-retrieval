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


import java.io.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import javax.xml.parsers.*;


/**
 *
 * @author david
 */
public abstract class SAXReader extends DefaultHandler {
    
	/**
	 * 
	 */
    protected final int kStringBuilder = 10000;
    
    /**
     * 
     */
    protected StringBuilder buffer = new StringBuilder(kStringBuilder);
    protected org.xml.sax.XMLReader reader = null;  
    
    
    /**
     * Creates a new instance of SaxReader
     *
     */
    public SAXReader() {
    }
    
    /**
     * 
     */
    public void characters(final char[] c, final int start, final int length) {
    }

    /**
     * 
     */
    public void startElement(final String uri, final String localName,
                             final String tag, final Attributes attributes) {
	}

    /**
     * 
     */
	public void endElement(final String uri, final String localName, final String tag) { 
	}


        /**
	 * 
	 * @return
	 */
	public void getXMLReader() {
                try {
			reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			reader.setContentHandler(this);
		} catch (Exception x) {
			System.err.println(x.getMessage());
		}
        }

	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public String getText(final String fileName) {

		getXMLReader();
		buffer = new StringBuilder(kStringBuilder);
		try {
                    reader.parse(fileName);
		} catch (Exception x) {
			System.err.println("Error parsing " + fileName + ": " + x);
		}
		return buffer.toString();
	}
}

