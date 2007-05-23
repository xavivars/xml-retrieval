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
	public ArrayList<File> readDirectory(File dir) {
		File[] childs;
		String name;
		final ArrayList<File> list = new ArrayList<File>();

		if (dir.isDirectory()) {
			childs = dir.listFiles();
			for (final File currentChild : childs) {
				list.addAll(readDirectory(currentChild));
			}
		} else {
			name = dir.getName();
			if (name.matches("\\w\\d{4}.xml")) {
				list.add(dir);
			}
		}
		return list;
	}

	/**
	 * 
	 * @param directory
	 * @return
	 */
	public WordMap createIndex(final String directory) {
		final WordMap wordMap = new WordMap();
		final WordsFrequencyMap wordsFrequencyMap = new WordsFrequencyMap();

		final HashMap<String,String> notWellFormed = new HashMap<String,String>();
		// los siguientes archivos XML parecen no validar con la DTD
		// y dan problemas en el indexado. Por ahora los omitimos.
		notWellFormed.put("xml/cg/1995/g1069.xml", null);
		notWellFormed.put("xml/cg/1996/g5071.xml", null);
		notWellFormed.put("xml/cg/1997/g6110.xml", null);
		notWellFormed.put("xml/dt/1995/d1024.xml", null);
		notWellFormed.put("xml/pd/1995/p4008.xml", null);
		notWellFormed.put("xml/so/1995/s6088.xml", null);
		notWellFormed.put("xml/tc/1995/t0453.xml", null);
		notWellFormed.put("xml/tc/1995/t0624.xml", null);
		notWellFormed.put("xml/tc/1995/t0957.xml", null);
		notWellFormed.put("xml/tc/1995/t1073.xml", null);
		notWellFormed.put("xml/tc/1996/t0714.xml", null);
		notWellFormed.put("xml/tc/1996/t0892.xml", null);
		notWellFormed.put("xml/tc/1997/t0162.xml", null);
		notWellFormed.put("xml/tc/1998/t0527.xml", null);

		ArrayList<File> listFiles;
		DocumentReader currentDoc;
		WordCounter wordCounter;
		final WordList wl = new WordList();

		try {
			listFiles = readDirectory(new File(directory));

			System.out.println(listFiles.size() + " archivos.");
			int i = 0;
			for (final File file : listFiles) {
				// wordMap se irá construyendo
				System.out.println("[" + i + "]: " + file.getPath());

				System.out.println("File: " + file.getPath());

				if (!notWellFormed.containsKey(file.getPath())) {

					// 1. Extraer palabra-frecuencia
					
					wordCounter = new WordCounter(file.getPath(), wordsFrequencyMap);
					wordCounter.analize();
					wordCounter.readDocument();

					// 2. Indexar (habría que pasar un parámetro nuevo - wordsFrequencyMap)
					
					//currentDoc = new DocumentReader(file.getPath(), wordMap);
					//currentDoc.analize();
					// además creamos un wordList (aunque no es necesario)
					//final WordList wordList = currentDoc.readDocument();
					currentDoc = null;
				}
				//index.addAll(wordList);
				i++;

			}
		} catch( Exception e ) {
			e.printStackTrace();
		}

		wordsFrequencyMap.print(150);

		return wordMap;
	}



	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		DirectoryReader dr;
		ArrayList<File> listFiles = new ArrayList<File>();

		if (args.length != 1) {
			System.err.println("Use: java DirectoryReader <directory>");
		} else {
			dr = new DirectoryReader();
			listFiles = dr.readDirectory(new File(args[0]));

			for (final File file : listFiles) {
				System.out.println("File: " + file.getPath());
			}
		}
	}

}
