import sbt._
import Keys._
import bintray.Plugin.bintraySettings

object SpireTimeBuild extends Build {

  override lazy val settings = super.settings ++ Seq(
    organization := "org.spire-math",
    version := "0.0.2",
    scalaVersion := "2.11.4",
    licenses := Seq("BSD-style" -> url("http://opensource.org/licenses/MIT")),
    homepage := Some(url("http://spire-math.org")),

    scalacOptions ++= (
      //"-deprecation" ::
      "-encoding" :: "UTF-8" ::
      "-feature" ::
      "-language:existentials" ::
      "-language:higherKinds" ::
      "-language:implicitConversions" ::
      "-unchecked" ::
      //"-Xfatal-warnings" ::
      "-Xlint" ::
      "-Yno-adapted-args" ::
      "-Ywarn-dead-code" ::
      "-Ywarn-numeric-widen" ::
      "-Ywarn-value-discard" ::
      "-Xfuture" ::
      Nil
    ),

    libraryDependencies ++= Seq(
      //"org.scala-lang" % "scala-reflect" % scalaVersion.value,
      "joda-time" % "joda-time" % "2.3",
      "org.joda" % "joda-convert" % "1.2",
      "org.spire-math" %% "spire" % "0.9.0",
      "org.spire-math" %% "spire-scalacheck-binding" % "0.9.0",
      "org.scalatest" %% "scalatest" % "2.2.1" % "test",
      "org.scalacheck" %% "scalacheck" % "1.11.6" % "test"))

  lazy val macros = Project("macros", file("macros")).
    settings(name := "spire-time-macros").settings(bintraySettings: _*)

  lazy val core = Project("core", file("core")).
    settings(name := "spire-time").settings(bintraySettings: _*).dependsOn(macros)

  lazy val root = Project("root", file(".")).
    settings(noPublish: _*).aggregate(macros, core)

  lazy val noPublish = Seq(
    publish := (),
    publishLocal := (),
    publishArtifact := false)
}
