package com.orange.analysis.twitter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

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
	
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub

		 ConfigurationBuilder cb = new ConfigurationBuilder();
	        cb.setOAuthConsumerKey("ER7jwnsbGQVf0LGhaVc9lg");
	        cb.setOAuthConsumerSecret("JgfoyLflaOY6Yh58AqDLKxdJSemvEDeVMKPTlfR0");
	        cb.setOAuthAccessToken("15522316-RMVD0Ep9ah8WCkqFUfVp8N6xFlCxnGLSFVa0950U");
	        cb.setOAuthAccessTokenSecret("TyUPjZXIig48VoqypHTR43t1uGtGFGw6AiWSgGKWSQ4");
	         
	        Twitter twitter = new TwitterFactory(cb.build()).getInstance();
		
		
	        FileWriter fstream = new FileWriter("twitterstream.txt",true);
	        BufferedWriter out = new BufferedWriter(fstream);
		    
			while(true)
			{

			for (int page = 1; page <= 15; page++) {
			System.out.println("\nPage: " + page);
			Query query = new Query("#49ers");
			
	/*				query.setQuery("#superbowl");
					query.setQuery("#nfl");
					query.setQuery("#football");
					query.setQuery("#defence");
					query.setQuery("#affence");
					query.setQuery("#quarterback");
					query.setQuery("#touchdown");
					query.setQuery("#baltimore");
					query.setQuery("#ravens");
	*/				/*query.setQuery("#california");
					query.setQuery("#beyonce");
					query.setQuery("#beyonce novels");
					query.setQuery("#Alex Smith");

					query.setQuery("#Scott Tolzien");*/
					 
			// set tweets per page to 1000
			
			QueryResult qr = twitter.search(query);
			List<Status> qrTweets = qr.getTweets();

			// break out of the loop early if there are no more tweets
			if(qrTweets.size() == 0) break;

			for(Status t : qrTweets) {
			
			out.write("\n"+t);
			
			
		
		/*	out.write("\n"+t.getId()+",");
            out.write("\t"+t.getText()+",");
           // out.write("\t"+t.getLocation()+",");
            out.write("\t"+t.getUser()+",");
            //out.write("\t"+user.toString());
*/            
			}
			
			}
			try{
				Thread.sleep(1000*60*15);
			}catch(Exception e) {}
			
	}//while

}

}
