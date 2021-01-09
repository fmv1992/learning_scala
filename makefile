# Set variables.
SHELL := /bin/bash
ROOT_DIR := $(shell dirname $(realpath $(lastword $(MAKEFILE_LIST))))

# Ignore these files.
IGNORE_FILES := ./code/project_euler/id_0451/modular_inverses.scala
SCALA_FILES := $(filter-out $(IGNORE_FILES), $(SCALA_FILES))
CLASS_FILES := $(filter-out $(IGNORE_FILES), $(CLASS_FILES))

# Set scala compilation flags.
SCALAC_CFLAGS = -cp $$PWD:$(ROOT_DIR)/code/my_scala_project/

export _JAVA_OPTIONS := -Xms3072m -Xmx6144m

# ???: Google drive link to download ~/.sbt needed to compile this project.
# https://drive.google.com/open?id=1FoY3kQi52PWllwc3ytYU9452qJ4ack1u

all: tmp/.fpinscala test

tmp/.fpinscala:
	bash ./other_code/travis_ci/install_fpinscala.sh
	touch -m $@

format:
	find . \( -iname '*.scala' -o -iname '*.sbt' \) -print0 \
        | xargs --verbose -0 \
            scalafmt --config ./scala_initiatives/.scalafmt.conf
	cd ./scala_initiatives && sbt 'scalafixAll'

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

test:
	cd ./scala_initiatives && sbt test

sbt:
	cd ./scala_initiatives && sbt

tmp/.testcomplete:
	touch $@

dev:
	cp -f ./other_code/git_hooks/git_pre_commit_hook.sh ./.git/hooks/pre-commit || true
	cp -f ./other_code/git_hooks/git_pre_push.sh ./.git/hooks/pre-push || true
	chmod a+x ./.git/hooks/pre-commit
	chmod a+x ./.git/hooks/pre-push

.FORCE:

.PHONY: all clean test

# vim: set noexpandtab foldmethod=marker fileformat=unix filetype=make wrap foldtext=foldtext():
