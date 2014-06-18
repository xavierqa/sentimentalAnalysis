package com.orange.analysis.twitter;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
//import org.apache.hadoop.mapred.lib.IdentityReducer;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class SentimentDriver {

	public static void main(String[] args) throws Exception {

		String englishPCFG = null;
		String sentiment = null;
		String inputPath = null;
		String outputPath = null;
		System.out.println(args.length);
		if ( args.length < 4){
			System.out.println("hdfs://<location>/englishPCFG.ser.gz  hdfs://<location>/sentiment.ser.gz hdfs://<path>/twitterstream hdfs://ouput");
			return;
		}else{
			englishPCFG = args[0];
			sentiment = args[1];
			inputPath = args[2];
			outputPath = args[3];
		}
		System.out.println(englishPCFG);
		System.out.println(sentiment);
		Configuration conf = new Configuration();

		Job job = new Job(conf, "SentimentAnalysis");

		// JobConf job = new JobConf();
		// 
		// DistributedCache.addCacheFile(new
		// URI("hdfs://home/xavier/workspace/SentimentalTwitter/src/main/resources/edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz#edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz"),
		// conf);
		//version 2.2
		DistributedCache.addCacheFile(new URI(englishPCFG),conf);
		DistributedCache.createSymlink(conf);
		DistributedCache.addCacheFile(new URI(sentiment),conf);
		DistributedCache.createSymlink(conf);
		//version 2.4 
//		job.addCacheFile(new URI(englishPCFG));
//		job.createSymlink();
//		job.addCacheFile(new URI(sentiment));
//		job.createSymlink();
//		job.addFileToClassPath(new Path(englishPCFG));
//		job.addFileToClassPath(new Path(sentiment));
		job.setJarByClass(SentimentDriver.class);

		job.setMapperClass(SentimentalDeviceMap.class);
		// job.setCombinerClass(SentimentalDeviceReducer.class);
		job.setReducerClass(SentimentalDeviceReducer.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);

		job.setNumReduceTasks(4);
		//

		// job.setNumReduceTasks(10);

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		String s = inputPath;//"/home/xavier/workspace/SentimentalTwitter/twitterstream.txt";
		String ss = outputPath;//"/home/xavier/hadoop2.4/hadoop-2.4.0/output2";
		FileInputFormat.addInputPath(job, new Path(s));
		FileOutputFormat.setOutputPath(job, new Path(ss));

		job.waitForCompletion(true);
	}
}
