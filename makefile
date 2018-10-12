# Set variables.
SHELL := /bin/bash
ROOT_DIR := $(shell dirname $(realpath $(lastword $(MAKEFILE_LIST))))

# Find all scala files.
SCALA_FILES := $(shell find ./code -iname "*.scala")
CLASS_FILES := $(patsubst %.scala, %.class, $(SCALA_FILES))

# Ignore these files.
IGNORE_FILES := ./code/project_euler/id_0451/modular_inverses.scala
SCALA_FILES := $(filter-out $(IGNORE_FILES), $(SCALA_FILES))
CLASS_FILES := $(filter-out $(IGNORE_FILES), $(CLASS_FILES))

# Set scala compilation flags.
SCALAC_CFLAGS = -cp $$PWD:$(ROOT_DIR)/code/my_scala_project/

all:

clean: .FORCE
	find . -iname '*.class' -print0 | xargs -0 rm -rf
	find . -iname 'project' -print0 | xargs -0 rm -rf
	find . -iname 'target' -print0 | xargs -0 rm -rf
	find . -type d -empty -delete

run: $(CLASS_FILES) $(SCALA_FILES) .FORCE

# If there is any makefile in the directory of the same scala file, run it's
# "all" rule instead.
%.scala: .FORCE
	if [ -f $$(dirname $@)/makefile ];    \
	then    \
	    cd $$(dirname $@) && make all;    \
	else    \
	    cd $$(dirname $@) && (scalac ./$$(basename $@) 2>/dev/null 1>/dev/null || scala $(SCALAC_CFLAGS) ./$$(basename $@));    \
	fi;

# This brute force assumes that all .scala file generates a corrisponding
# .class file, which is not true.
%.class: %.scala
	cd $$(dirname $<) && (scalac ./$$(basename $<) 2>/dev/null 1>/dev/null || true)

.FORCE:

.PHONY: all clean run
