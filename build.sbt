name := "advanced-spark-ch2"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.11.7"

javacOptions ++= Seq(
  "-source", "1.8",
  "-target", "1.7"
)

javaOptions ++= Seq("-Xmx2G")

scalacOptions ++= Seq(
  "-unchecked",
  "-Xlint",
  "-deprecation",
  "-target:jvm-1.7",
  "-encoding", "UTF-8",
  "-Ywarn-dead-code",
  "-language:_",
  "-feature"
)

licenses := Seq("Apache-2.0" -> url("http://opensource.org/licenses/Apache-2.0"))

val sparkVersion = "1.6.0"

val sparkDependencyScope = "provided"

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core"  % "7.1.1",
  "org.apache.spark" %% "spark-core" % sparkVersion % sparkDependencyScope,
  "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
  "org.scalactic" %% "scalactic" % "2.2.6",
  "org.scalatest" %% "scalatest" % "2.2.6" % "test",
  "org.scalacheck" %% "scalacheck" % "1.12.2" % "test"
)

addCompilerPlugin("org.psywerx.hairyfotr" %% "linter" % "0.1.12")

test in assembly := {}

mainClass in assembly := Some("com.briefscala.Main")