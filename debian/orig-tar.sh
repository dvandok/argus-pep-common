#!/bin/sh -e

VERSION=$2
TAR=../argus-pep-common_$VERSION.orig.tar.gz
DIR=argus-pep-common-$VERSION
TAG=$VERSION
REPO=http://svn.cern.ch/guest/glxa/pep-java-common

svn export $REPO/tags/${TAG} $DIR
GZIP=--best tar -c -z -f $TAR --exclude '*.jar' --exclude '*.class' $DIR
rm -rf $DIR 
rm ../$TAG
