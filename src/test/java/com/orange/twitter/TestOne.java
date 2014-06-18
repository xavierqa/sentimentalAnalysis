package com.orange.twitter;

import java.util.List;

import org.junit.Test;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import com.orange.analysis.twitter.KeyBasedTweets;

public class TestOne {

	//@Test 
	public void TestKeyBasedTweets(){
		 ConfigurationBuilder cb = new ConfigurationBuilder();
	        cb.setOAuthConsumerKey("ER7jwnsbGQVf0LGhaVc9lg");
	        cb.setOAuthConsumerSecret("JgfoyLflaOY6Yh58AqDLKxdJSemvEDeVMKPTlfR0");
	        cb.setOAuthAccessToken("15522316-RMVD0Ep9ah8WCkqFUfVp8N6xFlCxnGLSFVa0950U");
	        cb.setOAuthAccessTokenSecret("TyUPjZXIig48VoqypHTR43t1uGtGFGw6AiWSgGKWSQ4");
	        cb.setUseSSL(true); 
	        Twitter twitter = new TwitterFactory(cb.build()).getInstance();
	        List<Status> s;
			try {
				s = twitter.getHomeTimeline();
				 System.out.println("Showing home timeline.");
			        for (Status status : s) {
			            System.out.println(status.getUser().getName() + ":" +
			                               status.getText());
			        }
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       
	}
	//@Test
	public void testKeyBesedTweets(){
		try {
			KeyBasedTweets.main(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
