organization := "com.example"
scalaVersion  := "2.11.7"

enablePlugins(ScalaJSPlugin)

libraryDependencies += 	"org.scala-js" %%% "scalajs-dom" % "0.8.2"  withSources()
libraryDependencies += 	"org.scalatest" %% "scalatest" % "2.2.6" % "test"  withSources()	
libraryDependencies += 	"com.github.japgolly.scalajs-react" %%% "core" % "0.10.4" withSources()
libraryDependencies += 	"com.github.japgolly.scalajs-react" %%% "extra" % "0.10.4" withSources()
libraryDependencies += 	"com.github.chandu0101.scalajs-react-components" %%% "core" % "0.4.0" withSources()
libraryDependencies += 	"com.lihaoyi" %%% "upickle" % "0.3.8" withSources()

jsDependencies += RuntimeDOM
//jsDependencies += "org.webjars.npm" % "scalajs-react-components-webjar" % "1.0.1" / "assets/react-components.js"
//jsDependencies += "org.webjars.npm" % "scalajs-react-components-webjar" % "1.0.1" / "assets/mui.js"
// React JS itself (Note the filenames, adjust as needed, eg. to remove addons.)
jsDependencies += "org.webjars.bower" % "react" % "0.14.7" / "react-with-addons.js" minified "react-with-addons.min.js" commonJSName "React"
jsDependencies += "org.webjars.bower" % "react" % "0.14.7" / "react-dom.js" minified "react-dom.min.js" dependsOn "react-with-addons.js" commonJSName "ReactDOM"

// Indicate that unit tests will access the DOM
requiresDOM := true
 
fork in run := true

scalaJSStage in Global := FastOptStage

persistLauncher in Compile := true
persistLauncher in Test := false

crossTarget in (Compile, fullOptJS) := file(baseDirectory.value + "/js")
crossTarget in (Compile, fastOptJS) := file(baseDirectory.value + "/js")
crossTarget in (Compile, packageJSDependencies) := file(baseDirectory.value + "/js")
crossTarget in (Compile, packageScalaJSLauncher) := file(baseDirectory.value + "/js")
crossTarget in (Compile, packageMinifiedJSDependencies) := file(baseDirectory.value + "/js")

artifactPath in (Compile, fastOptJS) := ((crossTarget in (Compile, fastOptJS)).value /
  ((moduleName in fastOptJS).value + "-opt.js"))

scalacOptions += "-feature"
