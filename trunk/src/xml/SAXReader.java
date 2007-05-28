/*
 * SaxReader.java
 *
 * Created on 28 de mayo de 2007, 9:09
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
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
    
    protected final int kStringBuilder = 10000;
    protected StringBuilder buffer = new StringBuilder(kStringBuilder);
      
    /** Creates a new instance of SaxReader */
    public SAXReader() {
    }
    

    public void characters(final char[] c, final int start, final int length) {
    }

    public void startElement(final String uri, final String localName,
                             final String tag, final Attributes attributes) {
	}

	public void endElement(final String uri, final String localName, final String tag) { 
	}

	public org.xml.sax.XMLReader getXMLReader() {
		org.xml.sax.XMLReader reader = null;
		try {
			reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			reader.setContentHandler(this);
		} catch (Exception x) {
			System.err.println(x.getMessage());
		}
		return reader;
	}

	public String getText(final String fileName) {

		org.xml.sax.XMLReader reader = getXMLReader();
		buffer = new StringBuilder(kStringBuilder);
		try {
			reader.parse(fileName);
		} catch (Exception x) {
			System.err.println("Error parsing " + fileName + ": " + x.getMessage());
		}
		return buffer.toString();
	}
}

