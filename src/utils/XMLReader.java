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
import java.util.HashMap;
import java.util.StringTokenizer;

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
	 */
	protected String fileName;
	
	/**
	 * 
	 */
	protected WordList wordList;

	/**
	 * 
	 */
	protected WordMap wordMap;
	
	/**
	 * 
	 * @param fileName
	 */
	protected XMLReader(final String fileName) {
		wordList = new WordList();
		setFileName(fileName);
		setDicFile(new File(fileName));
		try {
			setFactory(DocumentBuilderFactory.newInstance());
			setBuilder(getFactory().newDocumentBuilder());
		} catch (final ParserConfigurationException pce) {
			pce.printStackTrace();
			System.err.println("Error parsing XML document.");
			System.exit(-1);
		} catch( final Exception e) {
			e.printStackTrace();
			System.exit(-1);
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
	protected void readChildren(final Element e, String path) {
		HashMap<String,Position> positions = new HashMap<String,Position>();
		if (e.hasChildNodes()) {
			final NodeList children = e.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				final Node child = children.item(i);
				if (child instanceof Element) {
					//System.out.println("Found: <" + child.getNodeName() + ">");
					String nodeName = child.getNodeName();
					Position pos = null;
					if (!positions.containsKey(nodeName)) {
						positions.put(nodeName, new Position());
						pos = positions.get(nodeName);
					} else {
						pos = positions.get(nodeName);
						pos.next();
					}
					
					final Element childElement = (Element) child;
					this.readChildren(childElement, path + "/" + child.getNodeName()+ "[" + (pos.getPos()) +"]");
				}
				if (child instanceof Text) {
					Text textElement = (Text)child;
					String text = textElement.getData();
					tokenize(text, this.getFileName(), path);
				}
				
			}
		}
	}

	/**
	 * En caso que fuera necesario acceder al valor de algún atributo.
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
	}

	/**
	 * 
	 * @param path
	 * @return
	 */
	private final void tokenize(final String text, final String document, final String path) {
		StringTokenizer tokens = new StringTokenizer(text, " ");
		while( tokens.hasMoreElements()) {
			String token = tokens.nextToken();
			if (isValidToken(token)) {
			token = token.toLowerCase();
			Word word = new Word(token);
			word.setPath(path);
			word.setDocument(document);
			getWordMap().addWord(word);
			addWord(word);
			} else {
				System.out.println("'" + token + "' is not a valid token");
			}
		}
	}
	
	/**
	 * Alguna forma de comprobar que el token NO es , . \n etc
	 * @param token
	 * @return
	 */
	private final boolean isValidToken(final String token) {
		// sólo para probar...
		if (token.length() == 1) {
			if (token.equals("\n")) {
				return false;
			}
			if (token.equals(",")) {
				return false;
			}
			
		}
		return true;
	}

	/**
	 * @return the fileName
	 */
	protected String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	protected void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * 
	 * @param word
	 */
	private final void addWord(final Word word) {
		wordList.add(word);
	}

	/**
	 * @return the wordList
	 */
	public WordList getWordList() {
		return wordList;
	}

	/**
	 * @param wordList the wordList to set
	 */
	public void setWordList(WordList wordList) {
		this.wordList = wordList;
	}

	/**
	 * @return the wordMap
	 */
	protected WordMap getWordMap() {
		return wordMap;
	}

	/**
	 * @param wordMap the wordMap to set
	 */
	protected void setWordMap(WordMap wordMap) {
		this.wordMap = wordMap;
	}


}