package com.pc.ang;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Core {

	// File Database
	public static File[] f;
	public static ArrayList<String> ss;
	// File Folder
	public static File fold = new File("Articles");
	
	// Main
	public static void main(String[] args) throws FileNotFoundException {
		// Test for Folder
		if(!(fold.exists() && fold.isDirectory()))
			fold.mkdirs();
		// Collect the files
		f = fold.listFiles();
		ss = new ArrayList<String>();
		// Reading time
		for(File file : f) {
			Scanner s = new Scanner(file);
			//Reads the Files
			String tempS = "";
			while(s.hasNextLine)
			        tempS =+ s.nextLine();
			ss.add(tempS);
		}
	}
}
