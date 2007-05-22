package utils;

import java.io.File;
import java.util.ArrayList;

public class DirectoryReader {
		
	public DirectoryReader () {
		
	}
	
	public ArrayList < File > readDirectory (File dir) {
		
		File [] childs;
		String name;
		ArrayList < File > listFiles = new ArrayList < File > ();
				
		if (dir.isDirectory()) {
			childs = dir.listFiles();
			for (File currentChild: childs) {
				listFiles.addAll(readDirectory(currentChild));
			}
		}
		else {
			name = dir.getName();
			if(name.matches("\\w\\d{4}.xml"))
				listFiles.add(dir);
		}
		return listFiles;
	}
	
	public void createIndex (ArrayList < File > files) {
		WordList index = new WordList();
		for ( File file: files) {
			index.addAll(arg0)
		}
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DirectoryReader dr;
		ArrayList < File > listFiles = new ArrayList < File > ();
		
		if (args.length != 1) {
			System.err.println("Use: java DirectoryReader <directory>");
		}
		else {
			dr = new DirectoryReader ();
			listFiles = dr.readDirectory(new File (args[0]));
			
			for (File file: listFiles) {
				System.out.println("File: " + file.getPath());
			}
		}
	}

}
