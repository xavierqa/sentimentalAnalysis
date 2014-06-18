package com.orange.twitter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.hadoop.io.Text;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.stanford.nlp.ling.CoreAnnotations;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import edu.stanford.nlp.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

public class TestTwitterReduceSentiment {

	public static Logger Log = LoggerFactory
			.getLogger(TestTwitterReduceSentiment.class);

	//@Test
	public void TestSentiment() throws IOException {
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
	
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		int mainSentiment = 0;
		BufferedReader br = null;
		br = new BufferedReader(new FileReader(
				"/home/xavier/hadoop2.4/hadoop-2.4.0/test.data"));
		String line;
		
		while ((line = br.readLine()) != null) {
			
			if (line != null && line.length() > 0) {
				int longest = 0;
				Annotation annotation = pipeline.process(line);
				for (CoreMap sentence : annotation
						.get(CoreAnnotations.SentencesAnnotation.class)) {
					Tree tree = sentence
							.get(SentimentCoreAnnotations.AnnotatedTree.class);
					int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
					String partText = sentence.toString();
					
					if (partText.length() > longest) {
						mainSentiment = sentiment;
						longest = partText.length();
						System.out.println("partText: "+ partText + " Sentiment "+toCss(sentiment));
					}
					
				}
			}
			
			if (mainSentiment == 2 || mainSentiment > 4 || mainSentiment < 0) {

			}
			TweetWithSentiment tweetWithSentiment = new TweetWithSentiment(
					line, toCss(mainSentiment));
		}
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
	 
	 class TweetWithSentiment {

			private String line;
			private String cssClass;

			public TweetWithSentiment() {
			}

			public TweetWithSentiment(String line, String cssClass) {
				super();
				this.line = line;
				this.cssClass = cssClass;
			}

			public String getLine() {
				return line;
			}

			public String getCssClass() {
				return cssClass;
			}

			@Override
			public String toString() {
				return "TweetWithSentiment [line=" + line + ", cssClass="
						+ cssClass + "]";
			}

		}
}
