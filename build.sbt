name := "sbr-int-test"
organization := "uk.gov.ons"
version := "1.0"

scalaVersion := "2.12.6"

libraryDependencies += "com.typesafe" % "config" % "1.3.3"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
libraryDependencies += "com.fasterxml.jackson.dataformat" % "jackson-dataformat-csv" % "2.9.7"
libraryDependencies += "com.typesafe.play" %% "play-ahc-ws-standalone" % "1.1.10"
libraryDependencies += "com.typesafe.play" %% "play-ws-standalone-json" % "1.1.10"

libraryDependencies += "org.scalatest" % "scalatest_2.12" % "3.0.5" % Test
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.0" % Test
