package com.pc.ang;

import java.io.File;
import java.util.ArrayList;

public class Core {

	public static ArrayList<File> f;
	public static File fold = new File("Articles");
	
	public static void main(String[] args) {
		if(!(fold.exists() && fold.isDirectory()))
			fold.mkdirs();
	}
}
