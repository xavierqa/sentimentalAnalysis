package com.orange.twitter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TesttwitterMapSentimental {

	public static Logger log = LoggerFactory
			.getLogger(TesttwitterMapSentimental.class);

	//@Test
	public void TestTwitterMapSentimental() {
		BufferedReader br = null;
		System.out.println("hola");
		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(
					"/home/xavier/workspace/SentimentalTwitter/data.tmt"));

			while ((sCurrentLine = br.readLine()) != null) {
				// System.out.println(sCurrentLine);
			
				if (!sCurrentLine.startsWith("READ:")) {
					
					List<String> entries = Arrays.asList(sCurrentLine
							.split("\t"));
					for (String e : entries){
						System.out.println(e);
					}
					if (entries.size() > 2) {
						String device = entries.get(0);
						String text = entries.get(2);
						// System.out.println(device);
						String o = extractDevice(device);
						Assert.assertNotNull(o);
						// System.out.println(text);
					}
				} else {
					
				}

				int count = 0;
				// while (tokens.hasMoreTokens()) {
				// count++;
				// System.out.println(sCurrentLine);

				// }
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

	private String extractDevice(String d) {
		String device = null;
		log.info("device {} length {}", d, d.length());
		if (!d.contains("WATCH:")) {
			if (d.contains("href") && d.length() > 0) {
				String [] tmp = null;
				if ( d.contains("http://")){
						tmp = d.split("http://");
				}else if (d.contains("https://")){
					tmp = d.split("https://");
				}
				
				if (tmp.length > 0) {
					if (tmp[1].contains("\"")) {
						System.out.println("---------:"+tmp[1]);
						String v = tmp[1].split("\"")[0];
						if (v.contains("/")) {
							String output = v.split("/")[v.split("/").length - 1];
							device = output;
						} else {
							device = v;
						}
					}else{
						System.out.println("here");
					}

					// System.out.println(v[v.length-1]);

				}
			} else if (!d.contains("href") && d.length() > 0) {
				device = d;
			}
		} else {
			device = "nodevice";

		}
		log.info("device {}", device);
		return device;
	}
}
