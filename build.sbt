lazy val commonSettings = Seq(
  organization := "de.htwg.sa",
  version := "2.0.0",
  scalaVersion := "2.12.8"
)

lazy val tankCommander = (project in file("."))
  .settings(
    name := "TankCommander"
  )
  .aggregate(
    game,
    database
  )

lazy val game = project
  .settings(
    commonSettings,
    libraryDependencies ++= List(
      "com.typesafe.slick" %% "slick" % "3.2.0",
      "org.slf4j" % "slf4j-nop" % "1.7.10",
      "com.h2database" % "h2" % "1.4.187"
    ),
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test",
    libraryDependencies += "junit" % "junit" % "4.8" % "test",
    libraryDependencies += "org.scoverage" %% "scalac-scoverage-plugin" % "1.4.0" % "provided",
    libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.5.23" % Test,
    libraryDependencies += "org.scala-lang.modules" % "scala-swing_2.12" % "2.0.3",
    libraryDependencies += "com.google.inject" % "guice" % "4.1.0",
    libraryDependencies += "net.codingwell" %% "scala-guice" % "4.1.0",
    libraryDependencies += "org.scala-lang.modules" % "scala-xml_2.12" % "1.0.6",
    libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.6",
    libraryDependencies += "com.google.inject.extensions" % "guice-assistedinject" % "4.1.0",
    libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.1.8",
    libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.8",
    libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.22",
    libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.144-R12"
  )

lazy val database = project
  .settings(
    commonSettings,
    mainClass in Compile := Some("main"),
    libraryDependencies ++= List(
      "com.typesafe.slick" %% "slick" % "3.2.0",
      "org.slf4j" % "slf4j-nop" % "1.7.10",
      "com.h2database" % "h2" % "1.4.187"
    ),
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test",
    libraryDependencies += "junit" % "junit" % "4.8" % "test",
    libraryDependencies += "org.scoverage" %% "scalac-scoverage-plugin" % "1.4.0" % "provided",
    libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.5.23" % Test,
    libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "2.6.0",
    libraryDependencies += "com.google.inject" % "guice" % "4.1.0",
    libraryDependencies += "net.codingwell" %% "scala-guice" % "4.1.0",
    libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.6",
    libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.1.8",
    libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.8",
    libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.22",
    libraryDependencies += "com.typesafe.play" %% "play-slick" % "4.0.0",
    libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "4.0.0"
  )
