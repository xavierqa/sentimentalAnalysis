package com.orange.twitter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

public class TesttwitterMapSentimental {

	
	//@Test
	public void TestTwitterMapSentimental(){
		BufferedReader br = null;
		System.out.println("hola");
		try {
			 
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader("/home/xavier/workspace/SentimentalTwitter/twitterstream.txt"));
 
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
 
				
	}
}
