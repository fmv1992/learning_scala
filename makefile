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

all:

clean:
	find . -iname '*.class' -print0 | xargs -0 rm -rf

run: $(CLASS_FILES) $(SCALA_FILES) .FORCE

class: $(CLASS_FILES)

scala: $(SCALA_FILES)

# TODO: not working because all files must be compiled before.
# Eg: Summer.scala and ChecksumAccumulator.scala.
%.scala: .FORCE
	cd $$(dirname $@) && (scalac ./$$(basename $@) 2>/dev/null 1>/dev/null || scala -cp $$PWD ./$$(basename $@))

# This brute force assumes that all .scala file generates a corrisponding
# .class file, which is not true.
%.class: %.scala
	cd $$(dirname $<) && (scalac ./$$(basename $<) 2>/dev/null 1>/dev/null || true)

.FORCE:

.PHONY: all clean run
