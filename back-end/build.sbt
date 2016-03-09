organization := "com.example"
scalaVersion  := "2.11.7"

//Required for jsonquote
resolvers += "bintray-jcenter" at "http://jcenter.bintray.com/"

libraryDependencies +=  "com.typesafe.akka" %% "akka-stream" % "2.4.2" withSources()
libraryDependencies +=	"com.typesafe.akka" %% "akka-http-core" % "2.4.2"  withSources()
libraryDependencies +=	"com.typesafe.akka" %% "akka-http-spray-json-experimental" % "2.4.2"  withSources()
libraryDependencies +=	"com.typesafe.akka" %% "akka-http-experimental" % "2.4.2"  withSources()
libraryDependencies +=	"net.maffoo"        %% "jsonquote-core" % "0.3.0" withSources()
libraryDependencies +=	"net.maffoo"        %% "jsonquote-spray" % "0.3.0" withSources()
libraryDependencies +=   "com.lihaoyi"      %% "upickle" % "0.3.8" withSources()

//Testing
libraryDependencies +=	"com.typesafe.akka" %% "akka-http-testkit" % "2.4.2" % "compile,  test"  withSources()
libraryDependencies +=	"com.typesafe.akka" %% "akka-testkit" % "2.4.2" % "compile,  test" withSources()
libraryDependencies +=	"org.scalatest"     %% "scalatest" % "2.2.6" % "compile,  test" withSources()

Revolver.settings

scalacOptions += "-feature"
scalacOptions += "-deprecation"
