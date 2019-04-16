name := """pincette-jsonfilter"""
organization := "net.pincette"
version := "0.2.0"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "javax.json" % "javax.json-api" % "1.1.4",
  "net.pincette" % "pincette-common" % "1.5.4"
)

pomIncludeRepository := { _ => false }
licenses := Seq("BSD-style" -> url("http://www.opensource.org/licenses/bsd-license.php"))
homepage := Some(url("https://pincette.net"))

scmInfo := Some(
  ScmInfo(
    url("https://github.com/wdonne/pincette-jsonfilter"),
    "scm:git@github.com:wdonne/pincette-jsonfilter.git"
  )
)

developers := List(
  Developer(
    id    = "wdonne",
    name  = "Werner Donné",
    email = "werner.donne@pincette.biz",
    url   = url("https://pincette.net")
  )
)

publishMavenStyle := true
crossPaths := false

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

credentials += Credentials(Path.userHome / ".sbt" / ".sonatype_credentials")
