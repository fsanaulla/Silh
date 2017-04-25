name := """Silh"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala, SbtNativePackager)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "com.typesafe.play" %% "play-slick" % "2.0.0",
  "org.postgresql" % "postgresql" % "9.3-1100-jdbc41",
  "com.mohiva" %% "play-silhouette" % "4.0.0",
  "com.mohiva" %% "play-silhouette-password-bcrypt" % "4.0.0",
  "com.mohiva" %% "play-silhouette-persistence" % "4.0.0",
  "com.mohiva" %% "play-silhouette-crypto-jca" % "4.0.0",
  "org.reactivemongo" %% "play2-reactivemongo" % "0.12.1",
  "com.typesafe.play" % "play-mailer_2.11" % "5.0.0",
  "net.codingwell" % "scala-guice_2.10" % "3.0.2",
  "com.iheart" %% "ficus" % "1.4.0",
  "com.adrianhurt" %% "play-bootstrap3" % "0.4.4-P24"
)