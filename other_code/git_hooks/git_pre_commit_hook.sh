#! /bin/bash

# This git pre commit hook is intended to work on both cygwin and unix
# machines.
# It should be symlinked to ../../.git/hooks/pre-commit.

# Halt on error.
set -e

# Go to execution directory.
# cd $(dirname $0)

# https://stackoverflow.com/questions/3466166/how-to-check-if-running-in-cygwin-mac-or-linux
cd ./scala_initiatives/
if [[ $(uname -s) == CYGWIN* ]];then
    # Use 'test' for full tests and 'testQuick' for incremental testing.
    sbt.bat testQuick
else
    # Use 'test' for full tests and 'testQuick' for incremental testing.
    sbt testQuick
fi

# echo "Ret code $?"
# exit 1
