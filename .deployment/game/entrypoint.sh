#!/usr/bin/env bash

# Tweak to run gui in container
Xvfb :1 -screen 0 1024x768x16&

"sbt" "project game" "run"
