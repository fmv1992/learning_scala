(SHELL) := /bin/bash

clean:
	find . -iname '*.class' -print0 | xargs -0 rm -rf
