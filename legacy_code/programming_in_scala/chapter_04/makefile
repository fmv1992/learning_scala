# Set variables.
SHELL := /bin/bash
ROOT_DIR := $(shell dirname $(realpath $(lastword $(MAKEFILE_LIST))))

# Set scala compilation flags.
SCALAC_CFLAGS = -cp $$PWD

all: Summer.class test

Summer.class: ChecksumAccumulator.scala Summer.scala
	scalac ${SCALAC_CFLAGS} ./ChecksumAccumulator.scala ./Summer.scala

test: test_summer test_snippet

test_summer:
	scala Summer a b abcde xyz 1234 'anystring'

test_snippet:
	scalac ./chapter_04_snippet.scala

.PHONY: test
