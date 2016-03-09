organization := "com.example"
scalaVersion  := "2.11.7"

enablePlugins(ScalaJSPlugin)

val library = crossProject.settings(
	unmanagedSourceDirectories in Compile += baseDirectory.value  / "main" / "scala"
).jsSettings(
  // JS-specific settings here
).jvmSettings(
  // JVM-specific settings here
)

lazy val js = library.js

lazy val jvm = library.jvm
