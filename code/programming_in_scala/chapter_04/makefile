SCALAC_FLAGS = -cp .

.PHONY: test

all: Summer.class test

Summer.class: ChecksumAccumulator.scala Summer.scala
	scalac ${SCALAC_FLAGS} ./ChecksumAccumulator.scala ./Summer.scala

test: test_summer test_snippet

test_summer:
	scala Summer a b abcde xyz 1234 'anystring'

test_snippet:
	scalac ./chapter_04_snippet.scala
