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

import java.util.Collections;
import query.QueryManager;
import query.Relevance;
import query.WordResultList;

/**
 * 
 * @author david
 */
public class Search {

	/** Creates a new instance of Search */
	public Search() {

	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		String stopFile, rootFile, queryFile, sourceIndexDir, resultXMLFile;

		stopFile = "";
		rootFile = "";
		queryFile = "";

		if (args.length == 5) {
			stopFile = args[0];
			System.out.println("Stop-words file: " + stopFile);
			rootFile = args[1];
			System.out.println("Root index file: " + rootFile);
			queryFile = args[2];
			System.out.println("Query file: " + queryFile);
			sourceIndexDir = args[3];
			System.out.println("Source index directory: " + sourceIndexDir);
			resultXMLFile = args[4];

			QueryManager qm = new QueryManager(stopFile, rootFile);
			qm.setIndexSourceDir(sourceIndexDir);
			// qm.setResultXMLFile("");
			System.out.println("Searching...");

			WordResultList wrl = qm.processQuery(queryFile);
			Relevance relevance = qm.transform(wrl);
			relevance.setRelevance();
			Collections.sort(relevance);
			relevance.printXML(resultXMLFile);

			System.out.println("Result XML file: " + resultXMLFile);

		} else {
			System.err
					.println("Usage: java Search <stop-words-file> <root-index-file> <query-file> <src-index-dir> <result-xml-file>");
		}

	}
}
