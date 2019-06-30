[![Build Status](https://travis-ci.org/segidev/TankCommander2.0.svg?branch=master)](https://travis-ci.org/segidev/TankCommander2.0)
[![Coverage Status](https://coveralls.io/repos/github/segidev/TankCommander2.0/badge.svg)](https://coveralls.io/github/segidev/TankCommander2.0)

# Htwg TankCommander Project 

# Commands to play
* Start: Start the game
* Exit: Leave the Application
* up,down,right,left: Move your Tank accordingly
* shoot: shoot the enemy's tank
* end turn: End your turn
* undo: undo your last command
* redo: redo your last command
* save: save the current state of the game
* load: load the game from a file

# Docker

## Game Service
1. `docker build -t tank-commander-game -f .deployment/game/Dockerfile .`
2. `docker run -it -e DISPLAY=:1.0 -p 9002:9002 --rm tank-commander-game`

## Database Service
1. `docker build -t tank-commander-database -f .deployment/database/Dockerfile .`
2. `docker run -it -p 9001:9001 --rm tank-commander-database`

# Docker-Compose
2. `docker-compose up`

# Routes

## Game Service
Visit http://localhost:9002/ for more routes

## Database Service
Visit http://localhost:9001/ for more routes

These routes are used by the GameService but can also be used via Postman.
