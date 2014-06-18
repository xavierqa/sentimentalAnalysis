#!/bin/bash
#Script to get Twitts. 

. ./config.sh

hadoop dfs -put $englishPCFG $HDFS_englishPCFG
hadoop dfs -put $sentiment $HDFS_sentiment

while :
do
        hadoop dfs -rm -r $NLP_OUTPUT
        hadoop dfs -mkdir $NLP

        yarn jar $SENTIMENTJAR com.orange.analysis.twitter.SentimentDriver $HDFS_englishPCFG $HDFS_sentiment $HADOOPTWETT $NLP_OUTPUT -files englishPCFG.ser.gz,sentiment.ser.gz

        Version=`date -d "next minute" +%s`

        mkdir $EXCELOUTPUT+$Version

        hadoop dfs -copyToLocal $NLP_OUTPUT/*  $EXCELOUTPUT+$Version

startTime=$(date +%s)
endTime=$(date -d "next minute" +%s)
timeToWait=$(((($endTime- $startTime)) * 20))
echo $timeToWait 
sleep $timeToWait

done
