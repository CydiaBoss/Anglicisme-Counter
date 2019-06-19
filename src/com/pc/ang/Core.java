package com.pc.ang;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

public class Core {
	
	// File Database
	public static File[] f;
	public static ArrayList<String> ss;
	// File Folder
	public static File fold = new File("Articles");
	public static File reslt = new File("Results.txt");
	// Online Client
	public static Checker dict;
	// Pauser
	public static Scanner sc = new Scanner(System.in);
	
	// Main
	public static void main(String[] args) throws IOException, FailingHttpStatusCodeException, InterruptedException {
		// Test for Folder
		if(!(fold.exists() && fold.isDirectory())) {
			fold.mkdirs();
			System.out.println("Folder Created.\n"
					+ "Please fill folder with text files.");
			// Pause
			sc.nextLine();
			sc.close();
			// Close
			System.exit(2);
		}
		if(!(reslt.exists() && !reslt.isDirectory()))
			reslt.createNewFile();
		// Collect the files
		f = fold.listFiles();
		if(f.length == 0) {
			System.out.println("No Files Found\n");
			// Pause
			sc.nextLine();
			sc.close();
			// Close
			System.exit(1);
		}else
			System.out.println(f.length + " File(s) Found\n");
		ss = new ArrayList<String>();
		// Reading time
		for(File file : f) {
			BufferedReader s = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("ISO-8859-1")));
			String part;
			while ((part = s.readLine()) != null) 
				ss.add(part);
			s.close();
		}
		// Filtering & Analysising
		Hashtable<String, Integer> cLt = new Hashtable<>();
		int wrdCnt = 0;
		for(String art : ss) {
			// Word Count
			wrdCnt += art.split(" ").length;
			// No Capitals
			art = art.toLowerCase();
			// No Articles
			art = art.replaceAll("les |l[ea] |l'|un(e)? |de(s)? |d'|[Àà] |au(x)? |ce(tte)?(s)? ", "");
//			// No Pronouns
//			art = art.replaceAll("(-)?(je |tu |j'|noun |vous |(qu')?il(s)? |(qu')?elle(s)? |[(qu') ]on |qui )|[mts](e |')|[' ]en |[' ]y ", " ");
			// No Numbers
			art = art.replaceAll("[0-9](%)?", "");
			// No Punctuation
			art = art.replaceAll("[\\.,!\\?:;«»]", "");
			// No Random Whitespace
			art = art.replaceAll("( )+", " ");
			// Trim
			art = art.trim();
			// Counting
			for(String wrd : art.split(" ")) {
				if(!cLt.containsKey(wrd))
					cLt.put(wrd, 1);
				else
					cLt.replace(wrd, cLt.get(wrd) + 1);
			}
		}
		// Link to Dictionary established
		dict = new Checker();
		// Create Results
		String toPrint = "Les Anglicismes qui existent dans les articles:\n\n";
		for(String ky : cLt.keySet()) {
			if (dict.check(ky)) {
				toPrint += ky + " - " + cLt.get(ky) + "\n";
				System.err.println("\"" + ky + "\" - Anglicisme");
			}else
				System.out.println("\"" + ky + "\" - Pas d'Anglicisme");
		}
		toPrint += "\n\nWord Count: " + wrdCnt;
		// Writer
		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(reslt), StandardCharsets.ISO_8859_1);
		writer.write(toPrint);
		writer.flush();
		writer.close();
		// Close Browser
		dict.close();
		// Finish
		System.out.println("\n" + toPrint + "\nDone");
		// Pause
		sc.nextLine();
		sc.close();
		System.exit(0);
	}
}
