package com.orange.analysis.twitter;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Properties;


import org.apache.commons.collections.map.HashedMap;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.filecache.DistributedCache;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.stanford.nlp.ling.CoreAnnotations;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import edu.stanford.nlp.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

public class SentimentalDeviceReducer extends Reducer<Text, Text, Text, Text> {
	
	
	public static Logger log = LoggerFactory
			.getLogger(SentimentalDeviceReducer.class);

	
	
	
//	@Override
//	protected void setup(org.apache.hadoop.mapreduce.Reducer.Context context)
//			throws IOException, InterruptedException {
//		Configuration conf = context.getConfiguration();
//		URI[] l = context.getCacheFiles();
//		if ( l != null){
//		for (URI x : l){
//			log.info("printh ------------------------------{}" , x);
//			System.out.println("printh ------------------------------{}"+ x.toString());
//		}
//		}else{
//			log.info("NULL VALLLLLL");
//		}
//	}
//	
	protected void reduce(Text key, Iterable<Text> values,	Context context)
			throws IOException, InterruptedException {
		log.info("STARTING REDUCE");
		
		
		
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
		props.setProperty("parse.model", "englishPCFG.ser.gz");//getParsePath());
		props.setProperty("sentiment.model","sentiment.ser.gz");
		
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		
		int mainSentiment = 0;
		StringBuffer devices = new StringBuffer();
		Map<String,Integer> count = new HashedMap();
		for (Text v : values) {
			int i = 1;
			if ( count.containsKey(v.toString())){
				int c = count.get(v.toString()) + i;
				count.put(v.toString(), c);
			}else{
				count.put(v.toString(), i);
			}
			
		}
		//	log.info("value---------------");
			String line = key.toString();
			if (line != null && line.length() > 0) {
				int longest = 0;
				Annotation annotation = pipeline.process(line);
				for (CoreMap sentence : annotation
						.get(CoreAnnotations.SentencesAnnotation.class)) {
					//log.info("processing anotations");
					Tree tree = sentence
							.get(SentimentCoreAnnotations.AnnotatedTree.class);
					int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
					String partText = sentence.toString();

					if (partText.length() > longest) {
						mainSentiment = sentiment;
						longest = partText.length();
						
						// System.out.println("partText: "+ partText +
						// " Sentiment "+toCss(sentiment));
					}
					Text output = new Text();
					Text vl = new Text();
					vl.set(","+line+ ","+count.toString());
					output.set(toCss(sentiment));
					context.write(output, vl);
				}
			}
		

		
		/*
		 * HashMap<String, String> unique = new HashMap<String, String>();
		 * StringBuffer outText = new StringBuffer(); for (Text v : values) {
		 * outText.append(v.toString()); if (!unique.containsKey(v.toString()))
		 * { unique.put(v.toString(), v.toString()); } } for (String k :
		 * unique.keySet()) { outText.append(k); }
		 * 
		 * 
		 */

	}

	private String toCss(int sentiment) {
		switch (sentiment) {
		case 0:
			return "very negative";
		case 1:
			return "negative";
		case 2:
			return "neutral";
		case 3:
			return "positive";
		case 4:
			return "very possitive";
		default:
			return "";
		}
	}
}
