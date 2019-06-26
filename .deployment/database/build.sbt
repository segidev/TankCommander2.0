lazy val commonSettings = Seq(
  organization := "de.htwg.sa",
  version := "2.0.0",
  scalaVersion := "2.12.8"
)

lazy val database = project
  .settings(
    name := "TankCommander Database",
    commonSettings,
    mainClass in Compile := Some("de.htwg.sa.database.restAPI.TankDatabase"),
    libraryDependencies ++= List(
      "com.typesafe.slick" %% "slick" % "3.2.0",
      "org.slf4j" % "slf4j-nop" % "1.7.10",
      "com.h2database" % "h2" % "1.4.187"
    ),
    libraryDependencies += "com.google.inject" % "guice" % "4.1.0",
    libraryDependencies += "net.codingwell" %% "scala-guice" % "4.1.0",
    libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.6",
    libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.1.8",
    libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.8",
    libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.22",
    libraryDependencies += "com.typesafe.play" %% "play-slick" % "4.0.0",
    libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "4.0.0"
  )
