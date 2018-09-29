#! /bin/bash

# Go to root folder.
cd $(dirname $0)
cd ../../

# Fix indents.
find . -iname '*.scala' -print0 | xargs -0 -I @ vim -c ":execute 'normal! gg=G'" -c ":x!" @
