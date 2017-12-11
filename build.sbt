name := "SearchForFunda"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.1",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
  "org.seleniumhq.selenium" % "selenium-java" % "3.4.0",
  "com.typesafe" % "config" % "1.3.1",
  "com.typesafe.play" %% "play-ws" % "2.5.15",
  "org.seleniumhq.selenium" % "selenium-htmlunit-driver" % "2.52.0",
  "org.scala-lang.modules" %% "scala-async" % "0.9.6",
  "org.pegdown" % "pegdown" % "1.6.0"
)