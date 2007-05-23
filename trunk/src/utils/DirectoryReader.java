package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;


/**
 * 
 * @author
 * 
 */
public class DirectoryReader {

	/**
	 * 
	 * 
	 */
	public DirectoryReader() {

	}

	/**
	 * 
	 * @param dir
	 * @return
	 */
	private ArrayList<File> readDirectory(File dir) {

		File[] childs;
		String name;
		ArrayList<File> list = new ArrayList<File>();

		if (dir.isDirectory()) {
			childs = dir.listFiles();
			for (File currentChild : childs) {
				list.addAll(readDirectory(currentChild));
			}
		} else {
			name = dir.getName();
			if (name.matches("\\w\\d{4}.xml"))
				list.add(dir);
		}
		return list;
	}

	private void removeStopWords (WordMap index) {
		
		
		ArrayList < WordFrequency > stopWords = new ArrayList < WordFrequency > ();
		Iterator it = index.entrySet().iterator();
		int thresold = 20;
		
		// Recorremos el WordMap y lo copiamos al ArrayList
		while (it.hasNext()) {
			Map.Entry <String, WordList> e = (Map.Entry <String, WordList>) it.next();
			stopWords.add(new WordFrequency(e.getKey(),e.getValue().size()));
			Collections.sort(stopWords);
		}
		
		// Eliminamos las palabras más frecuentes del WordMap
				
		for (int i = stopWords.size() - thresold -1 ; i < stopWords.size() -1 ; i++) {
			index.remove(stopWords.get(i).getValue());
		}
			
		
	}
	
	/**
	 * 
	 * @param directory
	 * @return
	 */
	public WordMap createIndex(final String directory) {
		WordMap wordMap = new WordMap();

		HashMap<String,String> notWellFormed = new HashMap<String,String>();
		// los siguientes archivos XML parecen no validar con la DTD
		// y dan problemas en el indexado. Por ahora los omitimos.
		notWellFormed.put("xml/cg/1995/g1069.xml", null);
		notWellFormed.put("xml/cg/1996/g5071.xml", null);
		notWellFormed.put("xml/cg/1997/g6110.xml", null);
		
		ArrayList<File> listFiles;
		DocumentReader currentDoc;
		WordList wl = new WordList();

		listFiles = readDirectory(new File(directory));

		System.out.println(listFiles.size() + " archivos.");
		for (File file : listFiles) {
			// wordMap se irá construyendo

			System.out.println("File: " + file.getPath());
			
			if (!notWellFormed.containsKey(file.getPath())) {
			currentDoc = new DocumentReader(file.getPath(), wordMap);

			currentDoc.analize();
			// además creamos un wordList (aunque no es necesario)

			WordList wordList = currentDoc.readDocument();
			currentDoc = null;
			}
			//index.addAll(wordList);

		}
		
		removeStopWords(wordMap);
		
		
		return wordMap;
	}

	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DirectoryReader dr;
		ArrayList<File> listFiles = new ArrayList<File>();

		if (args.length != 1) {
			System.err.println("Use: java DirectoryReader <directory>");
		} else {
			dr = new DirectoryReader();
			listFiles = dr.readDirectory(new File(args[0]));

			for (File file : listFiles) {
				System.out.println("File: " + file.getPath());
			}
		}
	}

}
