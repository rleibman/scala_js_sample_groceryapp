LET'S CODE
============

## Create directories
	mkdir -p grocery2/
	mkdir -p grocery2/project
	
	mkdir -p grocery2/front-end
	mkdir -p grocery2/front-end/src
	mkdir -p grocery2/front-end/src/main
	mkdir -p grocery2/front-end/src/main/scala
	
	mkdir -p grocery2/back-end
	mkdir -p grocery2/back-end/src
	mkdir -p grocery2/back-end/src/main
	mkdir -p grocery2/back-end/src/main/resources
	mkdir -p grocery2/back-end/src/main/scala
	
	mkdir -p grocery2/common
	mkdir -p grocery2/common/src
	mkdir -p grocery2/common/src/mainp	
	mkdir -p grocery2/common/src/main/resources
	mkdir -p grocery2/common/src/main/scala
	mkdir -p grocery2/common/src/main/scala/grocery
	mkdir -p grocery2/common/src/main/scala/grocery/common
	mkdir -p grocery2/common/project

## Write some basic build.sbt
	grocery2/front-end/build.sbt
	grocery2/build.sbt
	grocery2/back-end/build.sbt
	grocery2/common/build.sbt

## Add some plugins we'll need
	grocery2/project/plugins.sbt
	
## Get it all running

	sbt eclipse
	sbt 
		~reStart
	sbt
		~fastOptJS

## Build a simple model
	grocery2/common/src/main/scala/grocery/common/model.scala
	
## And a simple server
	grocery2/back-end/src/main/scala/Server.scala
	
## And a simple client
	grocery2/front-end/src/main/scala/Grocery.scala
	
## Expand the simple client into it's own table	
	grocery2/front-end/src/main/scala/GroceryTable.scala

## What else if there's time?
	future for comprehensions?
	reactiveMongo?
	websockets? 	
	