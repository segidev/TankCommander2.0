#!/usr/bin/env bash
Xvfb :1 -screen 0 1024x768x16&
"sbt" "project game" "run"