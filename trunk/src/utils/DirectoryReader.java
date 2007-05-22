package utils;

import java.io.File;
import java.util.ArrayList;

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

	/**
	 * 
	 * @param directory
	 * @return
	 */
	public WordMap createIndex(final String directory) {
		WordMap wordMap = new WordMap();

		ArrayList<File> listFiles;
		DocumentReader currentDoc;
		WordList index = new WordList();

		listFiles = readDirectory(new File(directory));

		for (File file : listFiles) {
			// wordMap se irá construyendo
			currentDoc = new DocumentReader(file.getPath(), wordMap);
			currentDoc.analize();
			// además creamos un wordList (aunque no es necesario)
			index.addAll(currentDoc.readDocument());
		}
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
