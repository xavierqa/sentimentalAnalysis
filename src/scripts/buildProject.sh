#!/bin/bash +x 
#Script to build project, get dependencies. 

. ./config.sh 

function runApp
{
   read -p "Do you want to install Maven (y/n)? " choice
        case "$choice" in
                y|Y ) echo "installing maven" 
                        installingMaven
                        deployApp
                ;;
                n|N ) echo "Deploying application" 
                	    deployApp
                ;;
                * ) echo "Unknow code "+$choice
                ;;
        esac

}

function installingMaven
{
        wget http://mirror.olnevhost.net/pub/apache/maven/maven-3/3.0.5/binaries/apache-maven-3.0.5-bin.tar.gz
        sudo tar xzf apache-maven-3.0.5-bin.tar.gz -C /usr/local/
        sudo ln -s /usr/local/apache-maven-3.0.5 /usr/local/maven
        echo -e "export M2_HOME=/usr/local/maven" >> maven.conf
        echo -e "export PATH=/usr/local/maven/bin:$PATH" >> maven.conf
        sudo cp maven.conf /etc/profile.d/maven.sh
        rm maven.conf

}

function deployApp
{

mvn install:install-file -Dfile=$SENTIMENTJAR -DpomFile=$POMFILE

if [ ! -f $CLASSPATH_PATH ]
then
        echo "generating classpath"
        mvn dependency:build-classpath -Dmdep.outputFile=$CLASSPATH_PATH
fi


}


runApp
