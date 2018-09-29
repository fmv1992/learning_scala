# Set variables.
SHELL := /bin/bash
ROOT_DIR := $(shell dirname $(realpath $(lastword $(MAKEFILE_LIST))))

all:

run: Main.class
	clear
	scala Main < ./input.txt
	scala Main < ./input2.txt

clean:
	find . -iname '*.class' -print0 | xargs -0 rm -rf

Main.class: words1_play_on_words.scala
	scalac ./words1_play_on_words.scala

.PHONY: all run clean
