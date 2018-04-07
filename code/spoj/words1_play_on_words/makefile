# Set variables.
SHELL := /bin/bash
ROOT_DIR := $(shell dirname $(realpath $(lastword $(MAKEFILE_LIST))))

all:

run: Main.class
	clear
	scala Main < ./input.txt

Main.class: words1_play_on_words.scala
	scalac ./words1_play_on_words.scala

.PHONY: all run
