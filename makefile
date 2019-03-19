# Set variables.
SHELL := /bin/bash
ROOT_DIR := $(shell dirname $(realpath $(lastword $(MAKEFILE_LIST))))

# Find all scala files.
SBT_FILES := $(shell find ./scala_initiatives -iname "build.sbt")
SBT_FOLDERS := $(dir $(SBT_FILES))

# Ignore these files.
IGNORE_FILES := ./code/project_euler/id_0451/modular_inverses.scala
SCALA_FILES := $(filter-out $(IGNORE_FILES), $(SCALA_FILES))
CLASS_FILES := $(filter-out $(IGNORE_FILES), $(CLASS_FILES))

# Set scala compilation flags.
SCALAC_CFLAGS = -cp $$PWD:$(ROOT_DIR)/code/my_scala_project/

# ???: Google drive link to download ~/.sbt needed to compile this project.
# https://drive.google.com/open?id=1FoY3kQi52PWllwc3ytYU9452qJ4ack1u

all: test

clean_fpis_chapter:
ifdef LEARNING_SCALA_CHAPTER
	(find . -path '*/target/*FPIS*Chapter$(LEARNING_SCALA_CHAPTER)*.class' -type f -print0 | xargs -0 rm) || true
else
	echo "Env var LEARNING_SCALA_CHAPTER not defined."
	exit 1
endif

clean:
	find . -iname '*.class' -print0 | xargs -0 rm -rf
	find . -path '*/project/*' -type d -prune -print0 | xargs -0 rm -rf
	find . -iname 'target' -print0 | xargs -0 rm -rf
	find . -type d -empty -delete
	rm ./tmp/.testcomplete || true

test: $(SBT_FILES) tmp/.testcomplete

tmp/.testcomplete:
	touch $@

$(SBT_FILES): $(shell find $(dir $@) -iname '*.scala') ./tmp/.testcomplete
	cd $(dir $@) && sbt compile
	cd $(dir $@) && sbt test
	touch $@

.FORCE:

.PHONY: all clean test

# vim: set noexpandtab foldmethod=marker fileformat=unix filetype=make wrap foldtext=foldtext():
