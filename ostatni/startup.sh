#!/bin/sh
# -----------------------------------------------------------------------------
# Start Script for java proteinaco.jar
# -----------------------------------------------------------------------------

	#echo CALASPAH=$CALSSPATH
	#echo

PRG=/usr/lib/jvm/java-1.11.0-openjdk-i386/bin/java
PRGDIR=/home/sosyn
EXECUTABLE=/home/sosyn/Proteinaco.jar
CONFIG_FILE=/home/sosyn/proteinaco.config
CP="/home/sosyn/log4j/log4j-core-2.13.3.jar;/home/sosyn/log4j/log4j-api-2.13.3.jar"

echo =Start=============== `date "+%F %H:%M:%S"` >> proteinaco.log

cd $PRGDIR
$PRG -classpath $CP -jar $EXECUTABLE $CONFIG_FILE >> proteinaco.log
	#$PRG $CP -jar $EXECUTABLE $CONFIG_FILE 
echo =Stop=============== `date "+%F %H:%M:%S"` >> proteinaco.log
