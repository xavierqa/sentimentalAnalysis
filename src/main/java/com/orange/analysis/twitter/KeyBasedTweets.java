package com.orange.analysis.twitter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.stanford.nlp.ling.tokensregex.types.Tags;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class KeyBasedTweets {

	/**
	 * @param args
	 */

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		String filename = "twitterstream.txt";
		String OAuthConsumerKey = null;
		String OAuthConsumerSecret = null; 
		String OAuthAccessToken = null;
		String OAuthAccessTokenSecret = null;
		
		List<String> hashtags = null;
		System.out.println(args.length);
		if (args.length < 2) {
			System.out
					.println("<localpath twitterstream.txt>  <#NBA,#Playoffs> <OAuthConsumerKey> <OAuthConsumerSecret> <OAuthAccessToken> <OAuthAccessTokenSecret>");
			return;
		} else {
			filename = args[0];
			String tags = args[1];
			OAuthConsumerKey = args[2];
			OAuthConsumerSecret = args[3];
			OAuthAccessToken = args[4];
			OAuthAccessTokenSecret = args[5];

			if (tags != null) {
				hashtags = Arrays.asList(tags.split(","));
				
			}
		}

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setOAuthConsumerKey(OAuthConsumerKey);//("ER7jwnsbGQVf0LGhaVc9lg");
		cb.setOAuthConsumerSecret(OAuthConsumerSecret);//("JgfoyLflaOY6Yh58AqDLKxdJSemvEDeVMKPTlfR0");
		cb.setOAuthAccessToken(OAuthAccessToken);//("15522316-RMVD0Ep9ah8WCkqFUfVp8N6xFlCxnGLSFVa0950U");
		cb.setOAuthAccessTokenSecret(OAuthAccessTokenSecret);//("TyUPjZXIig48VoqypHTR43t1uGtGFGw6AiWSgGKWSQ4");

		Twitter twitter = new TwitterFactory(cb.build()).getInstance();

		FileWriter fstream = new FileWriter(filename, true);
		BufferedWriter out = new BufferedWriter(fstream);

//		while (true) {

			for (int page = 1; page <= 15; page++) {
				System.out.println("\nPage: " + page);
				Query query = new Query();
				for (String q :  hashtags){
					System.out.println(q);
					query.setQuery(q);
				}
				
				/*
				 * query.setQuery("#superbowl"); query.setQuery("#nfl");
				 * query.setQuery("#football"); query.setQuery("#defence");
				 * query.setQuery("#affence"); query.setQuery("#quarterback");
				 * query.setQuery("#touchdown"); query.setQuery("#baltimore");
				 * query.setQuery("#ravens");
				 *//*
					 * query.setQuery("#california");
					 * query.setQuery("#beyonce");
					 * query.setQuery("#beyonce novels");
					 * query.setQuery("#Alex Smith");
					 * 
					 * query.setQuery("#Scott Tolzien");
					 */

				// set tweets per page to 1000

				QueryResult qr = twitter.search(query);
				List<Status> qrTweets = qr.getTweets();

				// break out of the loop early if there are no more tweets
				if (qrTweets.size() == 0)
					break;

				for (Status t : qrTweets) {

					out.write("\n" + t.getSource());
					out.write("\t" + t.getId());
					out.write("\t" + t.getText());
					out.write("\t" + t.getGeoLocation());
					out.write("\t" + t.getHashtagEntities().toString());
					out.write("\t" + t.getUser());
					

					/*
					 * out.write("\n"+t.getId()+",");
					 * out.write("\t"+t.getText()+","); //
					 * out.write("\t"+t.getLocation()+",");
					 * out.write("\t"+t.getUser()+",");
					 * //out.write("\t"+user.toString());
					 */
				}
				
//			}
//			try {
//				Thread.sleep(1000 * 60 * 15);
//			} catch (Exception e) {
//			}

		}// while
			out.close();
	}

	
}
