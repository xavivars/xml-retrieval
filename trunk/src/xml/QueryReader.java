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

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import query.Query;
import utils.Position;
import utils.SimpleWord;
import utils.StopWordMap;

/**
 * 
 * @author
 * 
 */
public class QueryReader extends XMLReader {

	/**
	 * 
	 */
	private Query query;

	/**
	 * 
	 */
	private StopWordMap stopWords;

	/**
	 * 
	 * @param fileName
	 * @param swm
	 */
	public QueryReader(String fileName, StopWordMap swm) {
		super(fileName);
		query = new Query();
		stopWords = swm;
	}

	/**
	 * 
	 * @param e
	 * @param tagName
	 * @return
	 */
	protected Query readQuery(final Element e, final String path) {
		boolean exit = false;
		final HashMap<String, Position> positions = new HashMap<String, Position>();
		if (e.hasChildNodes()) {
			final NodeList children = e.getChildNodes();
			for (int i = 0; (i < children.getLength()) && !exit; i++) {
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
					readQuery(childElement, path + "/" + child.getNodeName()
							+ "[" + (pos.getPos()) + "]");
				}
				if ((child instanceof Text)
						&& (child.getParentNode().getNodeName().compareTo(
								"title") == 0)) {
					final Text textElement = (Text) child;
					final String text = textElement.getData();
					final String[] words = text.split("\\s+");
					for (String word : words) {
						word = word.toLowerCase();
						if (!word.isEmpty() && !stopWords.containsKey(word)) {
							query.add(new SimpleWord(word));
						}
						// System.out.println(word);

					}
					exit = true;
				}
			}
		}
		return query;
	}

	/**
	 * 
	 * @return
	 */
	public final Query readDocument() {
		analize();
		try {
			// System.out.println("Reading document '" + getFileName() + "'");
			final Element root = getDocument().getDocumentElement();
			final String path = "/article[1]";
			readQuery(root, path);
			return query;
		} catch (final NullPointerException npe) {
			npe.printStackTrace();
		}
		return null;
	}

}
