package com.pc.ang;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
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
		
		// Filtering & Analysising
		Hashtable<String, Integer> cLt = new Hashtable<>();
		for(String art : ss) {
			// No Articles
			art = art.replaceAll("[Ll]es |[Ll][ea] |[Ll]'|[uU]n(e)? |[dD]e(s)? |[Dd]'|[Àà] ", "");
			// Counting
			for(String wrd : art.split(" ")) {
				if(!cLt.containsKey(wrd))
					cLt.put(wrd, 1);
				else
					cLt.replace(wrd, cLt.get(wrd) + 1);
			}
			
		}
		// Create Results
		String toPrint = "";
		for(String ky : cLt.keySet())
			toPrint += ky + " - " + clt.get(ky) + "\n";
		FileWriter rsult = new FileWriter("Results");
		rsult.write(toPrint);
		rsult.flush();
		rsult.close();
	}
}
