name := """pincette-jsonfilter"""
organization := "net.pincette"
version := "0.0.1"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "javax.json" % "javax.json-api" % "1.1.2",
  "net.pincette" % "pincette-common" % "1.2.6"
)

publishTo := Some(Resolver.file("file",  new File(Path.userHome.absolutePath + "/.m2/repository")))
crossPaths := false
