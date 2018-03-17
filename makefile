# Set variables.
SHELL := /bin/bash
ROOT_DIR := $(shell dirname $(realpath $(lastword $(MAKEFILE_LIST))))

# Find all scala files.
SCALA_FILES := $(find . -iname ".scala")

all:

clean:
	find . -iname '*.class' -print0 | xargs -0 rm -rf

run: $(SCALA_FILES)

.PHONY: all clean run
