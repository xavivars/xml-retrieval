/*
 * Copyright (C) 2007 
 * Authors:
 *  Enrique Benimeli Bofarull <ebenimeli@gmail.com>
 *  David Ortega Parilla <dortega@gmail.com>
 *  Xavi Ivars <>
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

import utils.DocumentReader;
import utils.StopWordMap;
import utils.StopWordReader;
import utils.Word;
import utils.WordCounter;
import utils.WordList;
import utils.WordMap;
import utils.DirectoryReader;

/**
 * 
 * @author ebenimeli
 * 
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		WordMap wm = new WordMap();
		DocumentReader docReader = new DocumentReader("xml/cg/1995/g1062.xml", wm);
		docReader.analize();
		docReader.readDocument();
		*/
		
		
		DirectoryReader dirReader = new DirectoryReader();
		
		
		WordMap wordMap = dirReader.createIndex(args[0]);
		
		/*
		StopWordReader swr = new StopWordReader("stop-words.xml");
		swr.analize();
		StopWordMap swm = swr.readDocument();
		swm.print();
		*/
		

		/*
		 * for (Word word : wordList) { word.printInfo();
		 * System.out.println(""); }
		 */
	}

}
