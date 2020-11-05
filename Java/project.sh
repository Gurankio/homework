#!/bin/zsh
read "name?Project name: " && find . -name \#\* | sort -r | head -1 | egrep -o '\d' | head -3 | tr -d '\n' | xargs -I _ echo _+1 | bc -l | xargs -I _ printf "#%03d %s" _ $name | xargs -I _ echo "cp -R '#000 Template' '_'; git add '_'; echo 'Created: _'; git status" | sh
