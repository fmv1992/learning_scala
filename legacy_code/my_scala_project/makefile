# Set variables.
SHELL := /bin/bash
ROOT_DIR := $(shell dirname $(realpath $(lastword $(MAKEFILE_LIST))))

# Set scala compilation flags.
SCALAC_CFLAGS = -cp $$PWD

# Find all scala files.
SCALA_FILES := $(shell find ./ -iname "*.scala")
CLASS_FILES := $(shell find ./ -iname "*.class")

all: $(SCALA_FILES)

clean:
	find . -iname '*.class' -print0 | xargs -0 rm -rf
	((find . -type d -print0 | xargs -0 -I % rmdir % 2>/dev/null 1>/dev/null) || true)

%.scala: .FORCE
	scalac $(SCALAC_CFLAGS) ./my_timer.scala

.FORCE:

.PHONY: all clean
