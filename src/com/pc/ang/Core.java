package com.pc.ang;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Hashtable;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

public class Core {
	
	// File Database
	public static File[] f;
	public static ArrayList<String> ss;
	// File Folder
	public static File fold = new File("Articles");
	public static File reslt = new File("Results");
	// Online Client
	public static Checker dict;
	
	// Main
	public static void main(String[] args) throws IOException, FailingHttpStatusCodeException, InterruptedException {
		// Test for Folder
		if(!(fold.exists() && fold.isDirectory()))
			fold.mkdirs();
		if(!(reslt.exists() && !reslt.isDirectory()))
			reslt.createNewFile();
		System.out.println("Files Found\n");
		// Collect the files
		f = fold.listFiles();
		ss = new ArrayList<String>();
		// Reading time
		BufferedReader s = new BufferedReader(new InputStreamReader(System.in));
		for(File file : f) {
			s = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
			String part;
			while ((part = s.readLine()) != null)
				ss.add(part);
		}
		s.close();
		// Filtering & Analysising
		Hashtable<String, Integer> cLt = new Hashtable<>();
		for(String art : ss) {
			// No Capitals
			art = art.toLowerCase();
			// No Accents
			art = art.replaceAll("à|â", "a");
			art = art.replaceAll("ç", "c");
			art = art.replaceAll("é|è|ê|ë", "e");
			art = art.replaceAll("î|ï", "i");
			art = art.replaceAll("ô", "o");
			art = art.replaceAll("û|ù", "u");
			// No Articles
			art = art.replaceAll("les |l[ea] |l'|un(e)? |de(s)? |d'|[Àà] |au(x)? |ce(tte)?(s)? ", " ");
			// No Pronouns
			art = art.replaceAll("(-)?(je |tu |j'|noun |vous |(qu')?il(s)? |(qu')?elle(s)? |[(qu') ]on |qui )|[mts](e |')|[' ]en |[' ]y ", " ");
			// No Numbers
			art = art.replaceAll("[0-9](%)?", " ");
			// No Punctuation
			art = art.replaceAll("[\\.,]|( )?[!\\?:;]| [«»]", " ");
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
		String toPrint = "";
		int wrdCnt = 0;
		for(String ky : cLt.keySet()) {
			if (dict.check(ky)) {
				toPrint += ky + " - " + cLt.get(ky) + "\n";
				System.err.println("\"" + ky + "\" - Anglicisme");
			}else
				System.out.println("\"" + ky + "\" - Pas d'Anglicisme");
			wrdCnt += cLt.get(ky);
		}
		toPrint += "\n\nWord Count: " + wrdCnt;
		// Writer
		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(reslt), StandardCharsets.UTF_8);
		writer.write(toPrint);
		writer.flush();
		writer.close();
		// Close Browser
		dict.close();
		// Finish
		System.out.println(toPrint + "\nDone");
	}
}
