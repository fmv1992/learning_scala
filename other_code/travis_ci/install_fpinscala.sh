#! /bin/bash

# Halt on error.
set -e

# Go to execution directory.
rootdir="$PWD"
cd $(dirname $0)

cd $(mktemp -d)

git clone https://github.com/fpinscala/fpinscala
cd fpinscala

git checkout 7a43335a04679e140c8c4cf7c359fd8a39bbe39f

git am -3 -k "${rootdir}/other_code/fpinscala_git_patch/git_patch.txt"

sbt publishLocal
