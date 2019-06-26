[![Build Status](https://travis-ci.org/segidev/TankCommander2.0.svg?branch=master)](https://travis-ci.org/segidev/TankCommander2.0)
[![Coverage Status](https://coveralls.io/repos/github/segidev/TankCommander2.0/badge.svg)](https://coveralls.io/github/segidev/TankCommander2.0)

# Htwg TankCommander Project 
This is a seed project to create a basic scala project as used in the
class Software Engineering at the University of Applied Science HTWG Konstanz.

It requires Java 8 on your local platform.
The project has
* a folder structure prepared for a MVC-style application
* *ScalaTest* and as dependency aswell as dependencies to other libraries in the build.sbt (commented out at start).
* *scalastyle-sbt-plugin* and *sbt-scoverage* sbt plugins
* .gitignore defaults

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
1. `docker build -t tank-commander-database -f .deployment/database/Dockerfile .`
2. `docker run -it -p 9001:80 --rm tank-commander-database`

# Docker-Compose
1. `docker build -t tank-commander-database -f .deployment/database/Dockerfile .`
2. `docker-compose up`

# Routes
1. Create a save game http://localhost:9001/save?id=1&aPlayer=Player%201&pPlayer=Player%201&mapSelected=Map1&movesLeft=1&posATankX=0&posATankY=5&posBTankX=10&posBTankY=5&aTankHP=100&pTankHP=100
2. Load a save game http://localhost:9001/load/1/
3. Delete a save game http://localhost:9001/delete/1/