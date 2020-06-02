#!/bin/sh
# -----------------------------------------------------------------------------
# Start Script for java proteinaco.jar
# -----------------------------------------------------------------------------


PRG=/usr/lib/jvm/java-1.11.0-openjdk-i386
EXECUTABLE=/home/sosyn/proteinaco.jar
CONFIG_FILE=/home/sosyn/proteinaco.config
PRGDIR=/home/sosyn

CD $PRGDIR 
$PRG jar $EXCUTABLE $CONFIG?FILE
