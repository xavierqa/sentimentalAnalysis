package com.orange.analysis.twitter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SentimentalDeviceMap extends
		Mapper<LongWritable, Text, Text, Text> {
	Text tweetval = new Text();
	Text devicekey = new Text();

	public static Logger log = LoggerFactory
			.getLogger(SentimentalDeviceMap.class);

	@Override
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {

		if (value == null) {
			return;
		} else if (value.toString().contains("READ:")) {
			return;
		} else {
			List<String> entries = Arrays.asList(value.toString().split("\t"));
			if (entries.size() > 2) {
				String id = entries.get(1);
				if (id != null) {
					String device = extractDevice(entries.get(0));
					String text = entries.get(2);
					devicekey.set(device);
					if(normalizeText(text)){
						tweetval.set(text);
						context.write(tweetval,devicekey);
					}
					
				}else{
					return;
				}
			} else {
			//	log.info("value {}", value);
				return;
			}
		}

	}

	private String extractDevice(String d) {
		String device = null;
	//	log.info("device {} length {}", d, d.length());
		if (!d.contains("WATCH:")) {
			if (d.contains("href") && d.length() > 0) {
				String[] tmp = null;
				if (d.contains("http://")) {
					tmp = d.split("http://");
				} else if (d.contains("https://")) {
					tmp = d.split("https://");
				}

				if (tmp.length > 0) {
					if (tmp[1].contains("\"")) {
						String v = tmp[1].split("\"")[0];
						if (v.contains("/")) {
							String output = v.split("/")[v.split("/").length - 1];
							device = output;
						} else {
							device = v;
						}
					}

					// System.out.println(v[v.length-1]);

				}
			} else if (!d.contains("href") && d.length() > 0) {
				device = d;
			}
		} else {
			device = "nodevice";

		}
//		log.info("device {}", device);
		return device;
	}
	
	private boolean normalizeText(String text){
		
		
		if (text.contains("twitter4j")){
			return false;
		}else{
			return true;
		}
		
		
	}
}
