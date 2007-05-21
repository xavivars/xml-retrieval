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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 * 
 * @author Enrique Benimeli Bofarull
 * 
 */
public class XMLReader {

	/**
	 * 
	 */
	protected DocumentBuilderFactory factory;

	/**
	 * 
	 */
	protected DocumentBuilder builder;

	/**
	 * 
	 */
	protected Document document;

	/**
	 * 
	 */
	protected File dicFile;


	/**
	 * 
	 * @param fileName
	 */
	protected XMLReader(final String fileName) {
		setDicFile(new File(fileName));
		try {
			setFactory(DocumentBuilderFactory.newInstance());
			setBuilder(getFactory().newDocumentBuilder());
		} catch (final ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch( final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 *
	 */
	public final void analize() {
		try {
			setDocument(getBuilder().parse(getDicFile()));
		} catch( final FileNotFoundException fnfe) {
			System.err.println("Error: could not find '" + getDicFile() +"' file.");
			System.exit(-1);
		} catch (final SAXException saxE) {
			saxE.printStackTrace();
			System.err.println("Error: could not parse '" + getDicFile() + "'");
			System.exit(-1);
		} catch (final IOException ioE) {
			ioE.printStackTrace();
			System.err.println("I/O error");
			System.exit(-1);
		} catch (final Exception e) {
			e.printStackTrace();
			System.err.println("Error: the XML document is probably not well-formed");
			System.exit(-1);
		} finally {
			setBuilder(null);
			setFactory(null);
		}
	}

	/**
	 * 
	 * @param e
	 * @param tagName
	 * @return
	 */
	protected ArrayList<org.w3c.dom.Element> readChildren(final Element e) {
		final ArrayList<org.w3c.dom.Element> eList = new ArrayList<org.w3c.dom.Element>();
		if (e.hasChildNodes()) {
			final NodeList children = e.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				final Node child = children.item(i);
				if (child instanceof Element) {
					final Element childElement = (Element) child;
					eList.add(childElement);
				}
			}
		}
		return eList;
	}

	/**
	 * 
	 * @param e
	 * @param attrName
	 * @return
	 */
	protected String getAttributeValue(final Element e, final String attrName) {
		String value = "";
		if (e.hasAttributes()) {
			final NamedNodeMap attributes = e.getAttributes();
			for (int i = 0; i < attributes.getLength(); i++) {
				final Node attribute = attributes.item(i);
				final String name = attribute.getNodeName();
				value = attribute.getNodeValue();
				if (name.equals(attrName)) {
					return value;
				} // end-if
			} // end-for
		} // end-if
		return null;
	}

	/**
	 * @return the builder
	 */
	protected final DocumentBuilder getBuilder() {
		return builder;
	}

	/**
	 * @param builder the builder to set
	 */
	protected final void setBuilder(final DocumentBuilder builder) {
		this.builder = builder;
	}

	/**
	 * @return the dicFile
	 */
	protected final File getDicFile() {
		return dicFile;
	}

	/**
	 * @param dicFile the dicFile to set
	 */
	protected final void setDicFile(final File dicFile) {
		this.dicFile = dicFile;
	}

	/**
	 * @return the document
	 */
	protected final Document getDocument() {
		return document;
	}

	/**
	 * @param document the document to set
	 */
	protected final void setDocument(final Document document) {
		this.document = document;
	}

	/**
	 * @return the factory
	 */
	protected final DocumentBuilderFactory getFactory() {
		return factory;
	}

	/**
	 * @param factory the factory to set
	 */
	protected final void setFactory(final DocumentBuilderFactory factory) {
		this.factory = factory;
		factory.setValidating(false);
	}

}
