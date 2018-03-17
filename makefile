# Set variables.
SHELL := /bin/bash
ROOT_DIR := $(shell dirname $(realpath $(lastword $(MAKEFILE_LIST))))

# Find all scala files.
SCALA_FILES := $(shell find ./code -iname "*.scala")

all:

clean:
	find . -iname '*.class' -print0 | xargs -0 rm -rf

run: $(SCALA_FILES)
	echo $(SCALA_FILES)

# TODO: not working because all files must be compiled before.
# Eg: Summer.scala and ChecksumAccumulator.scala.
%.scala: .FORCE
	cd $$(dirname $@) && (scalac ./$$(basename $@) 2>/dev/null 1>/dev/null || scala -cp $$PWD ./$$(basename $@))

.FORCE:

.PHONY: all clean run
