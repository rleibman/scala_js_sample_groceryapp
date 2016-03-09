scalaVersion := "2.11.7"

lazy val commonSettings = Seq(
     organization := "com.example",
     version      := "0.1.0",
     scalaVersion := "2.11.7"
)

lazy val common = project.in(file("common"))
lazy val front_end = project.in(file("front-end")).dependsOn(common)
lazy val back_end = project.in(file("back-end")).dependsOn(common)
lazy val root = (project in file(".")).aggregate(common, front_end, back_end)
