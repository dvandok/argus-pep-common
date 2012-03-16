#!/bin/sh -e

# Download the parent project and the main project separately.  The
# main project is extracted in a subdirectory by dpkg-source(1) based
# on the naming of the tarball. This conforms to the normal structure
# of a maven project.

REPO=http://svn.cern.ch/guest/glxa

VERSION=$2
PARENTVERSION=1.5.2
DIR=argus-pep-common-$VERSION

TAR=../argus-pep-common_$VERSION.orig.tar.gz

TAG=`echo $PARENTVERSION | sed -e 's/\./_/g'`
svn export $REPO/argus-parent/tags/${TAG} $DIR

GZIP=--best tar -c -z -f $TAR --exclude '*.jar' --exclude '*.class' $DIR
rm -rf $DIR 

TAR=../argus-pep-common_$VERSION.orig-pep-common.tar.gz
TAG=`echo $VERSION | sed -e 's/\./_/g'`
REPO=http://svn.cern.ch/guest/glxa/pep-java-common
DIR=pep-common

svn export $REPO/tags/${TAG} $DIR
GZIP=--best tar -c -z -f $TAR --exclude '*.jar' --exclude '*.class' $DIR
rm -rf $DIR 

# Remove the uscan leftover
rm ../$TAG
