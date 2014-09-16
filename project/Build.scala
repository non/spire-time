import sbt._
import Keys._
import bintray.Plugin.bintraySettings

object SpireTimeBuild extends Build {

  override lazy val settings = super.settings ++ Seq(
    organization := "org.spire-math",
    version := "0.0.2",
    scalaVersion := "2.11.2",
    crossScalaVersions := Seq("2.10.4", "2.11.2"),
    licenses := Seq("BSD-style" -> url("http://opensource.org/licenses/MIT")),
    homepage := Some(url("http://spire-math.org")),

    scalacOptions ++= Seq(
      "-deprecation"
    ),

    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect" % scalaVersion.value,
      "joda-time" % "joda-time" % "2.3",
      "org.joda" % "joda-convert" % "1.2",
      "org.spire-math" %% "spire" % "0.8.2",
      "org.scalatest" %% "scalatest" % "2.1.3" % "test",
      "org.scalacheck" %% "scalacheck" % "1.11.3" % "test"))


  lazy val noPublish = Seq(
    publish := (),
    publishLocal := (),
    publishArtifact := false)

  lazy val macros = Project("macros", file("macros")).
    settings(name := "spire-time-macros").settings(bintraySettings: _*)

  lazy val core = Project("core", file("core")).
    settings(name := "spire-time").settings(bintraySettings: _*).dependsOn(macros)

  lazy val root = Project("spire-time", file(".")).
    settings(noPublish: _*).aggregate(macros, core)
}
