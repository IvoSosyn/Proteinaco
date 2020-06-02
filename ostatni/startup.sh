#!/bin/sh
# -----------------------------------------------------------------------------
# Start Script for java proteinaco.jar
# -----------------------------------------------------------------------------

echo CALASPAH=$CALSSPATH
echo

PRG=/usr/lib/jvm/java-1.11.0-openjdk-i386/bin/java
EXECUTABLE=/home/sosyn/Proteinaco.jar
CONFIG_FILE=/home/sosyn/proteinaco.config
PRGDIR=/home/sosyn
CP=/home/sosyn/log4j/log4j-core-2.13.3.jar;/home/sosyn/log4j/log4j-api-2.13.3.jar


cd $PRGDIR
$PRG -classpath $CP -jar $EXECUTABLE $CONFIG_FILE
#$PRG $CP -jar $EXECUTABLE $CONFIG_FILE
