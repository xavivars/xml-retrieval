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

package xml;

import java.io.File;
import java.io.IOException;
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

import utils.Position;
import utils.RootIndexMap;
import utils.StopWordMap;
import utils.Word;
import utils.WordList;
import utils.WordMap;

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
	 */
	protected StopWordMap stopWordMap;

	/**
	 * 
	 * 
	 */
	protected RootIndexMap rootIndexMap;

	/**
	 * 
	 */
	protected Integer reference;

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
		} catch (final Exception e) {
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
		} catch (final FileNotFoundException fnfe) {
			System.err.println("Error: could not find '" + getDicFile()
					+ "' file.");
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
			System.err
					.println("Error: the XML document is probably not well-formed");
			// System.exit(-1);
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
	protected void readChildren(final Element e, final String path) {
		final HashMap<String, Position> positions = new HashMap<String, Position>();
		if (e.hasChildNodes()) {
			final NodeList children = e.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				final Node child = children.item(i);
				if (child instanceof Element) {
					// System.out.println("Found: <" + child.getNodeName() +
					// ">");
					final String nodeName = child.getNodeName();
					Position pos = null;
					if (!positions.containsKey(nodeName)) {
						positions.put(nodeName, new Position());
						pos = positions.get(nodeName);
					} else {
						pos = positions.get(nodeName);
						pos.next();
					}

					final Element childElement = (Element) child;
					readChildren(childElement, path + "/" + child.getNodeName()
							+ "[" + (pos.getPos()) + "]");
				}
				if (child instanceof Text) {
					final Text textElement = (Text) child;
					final String text = textElement.getData();
					tokenize(text, getFileName(), path);
				}

			}
		}
	}

	/**
	 * En caso que fuera necesario acceder al valor de algún atributo.
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
	 * @param builder
	 *            the builder to set
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
	 * @param dicFile
	 *            the dicFile to set
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
	 * @param document
	 *            the document to set
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
	 * @param factory
	 *            the factory to set
	 */
	protected final void setFactory(final DocumentBuilderFactory factory) {
		this.factory = factory;
	}

	/**
	 * 
	 * @param path
	 * @return
	 */
	protected void tokenize(final String text, final String document,
			final String path) {
		final StringTokenizer tokens = new StringTokenizer(text, " ");
		while (tokens.hasMoreElements()) {
			final String token = tokens.nextToken();
			final String newToken = formatToken(token);
			if (newToken != null) {
				final Word word = new Word(newToken);
				word.setPath(path);
				word.setDocument(document);
				// añadimos el 'word' al WordMap
				if (getStopWordMap().containsKey(word.getValue())) {
					getWordMap().addWord(word);
					// System.out.println("Add '" + word.getValue() + "'");
				}
				getRootIndexMap().addReference(word, reference);

				// añadimos el 'word' al WordList
				// addWord(word);
			} else {
				// System.out.println("'" + token + "' is not a valid token");
			}
		}
	}

	/**
	 * Formateamos el token, eliminando comas, puntos, etc.
	 * 
	 * @param token
	 * @return
	 */
	protected final String formatToken(final String token) {
		String newToken;
		String[] temp;
		final String finalPunct = "[\\.,?!;:\\]\\)\\}]";
		final String beginningPunct = "[\\(\\[\\{]";

		newToken = token.toLowerCase();
		// Quitar signos de puntuación de final y principio de palabra
		if (newToken.matches(beginningPunct + "\\w+")
				|| newToken.matches("\\w+" + finalPunct)
				|| newToken.matches(beginningPunct + "\\w+" + finalPunct)) {
			temp = newToken.split("\\W");
			for (final String t : temp) {
				if ((t.compareTo(" ") != 0) && (t.compareTo("") != 0)) {
					newToken = t;
				}
			}
		}
		// Si no es un número o una palabra
		else if (!newToken.matches("\\d+")
				&& !newToken.matches("[\\w&&[^\\d]]+")) {
			newToken = null;
		}

		return newToken;
	}

	/**
	 * @return the fileName
	 */
	protected String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	protected void setFileName(final String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the wordList
	 */
	public WordList getWordList() {
		return wordList;
	}

	/**
	 * @param wordList
	 *            the wordList to set
	 */
	public void setWordList(final WordList wordList) {
		this.wordList = wordList;
	}

	/**
	 * @return the wordMap
	 */
	protected WordMap getWordMap() {
		return wordMap;
	}

	/**
	 * @param wordMap
	 *            the wordMap to set
	 */
	protected void setWordMap(final WordMap wordMap) {
		this.wordMap = wordMap;
	}

	/**
	 * @return the stopWordMap
	 */
	protected StopWordMap getStopWordMap() {
		return stopWordMap;
	}

	/**
	 * @param stopWordMap
	 *            the stopWordMap to set
	 */
	protected void setStopWordMap(StopWordMap stopWordMap) {
		this.stopWordMap = stopWordMap;
	}

	/**
	 * @return the rootIndexMap
	 */
	protected RootIndexMap getRootIndexMap() {
		return rootIndexMap;
	}

	/**
	 * @param WordMap
	 *            the rootIndexMap to set
	 */
	protected void setRootIndexMap(RootIndexMap map) {
		rootIndexMap = map;
	}

	/**
	 * @return the referemce
	 */
	protected Integer getReference() {
		return reference;
	}

	/**
	 * @param Integer
	 *            the reference to set
	 */
	protected void setReference(Integer ref) {
		reference = ref;
	}

}
