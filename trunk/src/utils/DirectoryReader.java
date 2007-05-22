package utils;

import java.io.File;
import java.util.ArrayList;

public class DirectoryReader {
		
	public DirectoryReader () {
		
	}
	
	public ArrayList < File > readDirectory (File dir) {
		
		File [] childs;
		ArrayList < File > listFiles = new ArrayList < File > ();
				
		if (dir.isDirectory()) {
			childs = dir.listFiles();
			for (File currentChild: childs) {
				listFiles.addAll(readDirectory(currentChild));
			}
		}
		else {
			listFiles.add(dir);
		}
		return listFiles;
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
