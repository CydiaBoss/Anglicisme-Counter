package com.pc.ang;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Core {

	// File Database
	public static File[] f;
	public static ArrayList<String> ss;
	// File Folder
	public static File fold = new File("Articles");
	public static File result = new File("Data");
	
	// Main
	public static void main(String[] args) throws FileNotFoundException {
		// Test for Folder
		if(!(fold.exists() && fold.isDirectory()))
			fold.mkdirs();
		// Collect the files
		f = fold.listFiles();
		ss = new ArrayList<String>();
		// Reading time
		Scanner s = new Scanner(System.in);
		for(File file : f) {
			s = new Scanner(file);
			//Reads the Files
			String tempS = "";
			while(s.hasNextLine())
			        tempS += s.nextLine();
			ss.add(tempS);
		}
		s.close();
		
		/*Counting*/
		// Test for Folder
		if(!(result.exists() && result.isDirectory()))
			result.mkdirs();
		// Filtering & Analysising
		int count = 0;
		for(String art : ss) {
			// No Articles
			art = art.replaceAll("[Ll]es |[Ll][ea] |[Ll]'|[uU]n(e)? |[dD]e(s)? |[Dd]'|[¿‡] ", "");
			// Create New File
			File re = new File("result\"" + f[count]);
		}
		
	}
}
