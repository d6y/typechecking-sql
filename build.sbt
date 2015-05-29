name := "typechecked-sql"

scalaVersion := "2.11.6"

resolvers ++= Seq(
  "tpolecat" at "http://dl.bintray.com/tpolecat/maven",
  "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"
)

libraryDependencies ++= slick ++ postgresql ++ h2 ++ doobie ++ nopLogging

val slick = Seq("com.typesafe.slick" %% "slick" % "3.0.0")

lazy val doobieVersion = "0.2.1"

lazy val doobie = Seq(
  "org.tpolecat"   %% "doobie-core"               % doobieVersion,
  "org.tpolecat"   %% "doobie-contrib-postgresql" % doobieVersion,
  "org.tpolecat"   %% "doobie-contrib-h2"         % doobieVersion,
  "org.tpolecat"   %% "doobie-contrib-specs2"     % doobieVersion
)

lazy val postgresql = Seq("org.postgresql" % "postgresql"      % "9.3-1100-jdbc41")
lazy val h2         = Seq("com.h2database" % "h2"              % "1.4.185")


// Marked as runtime to prevent "No configuration setting found for key 'slick'" when using tsql macro
lazy val logging    = Seq("ch.qos.logback" % "logback-classic" % "1.1.2" % Runtime)
lazy val nopLogging = Seq("org.slf4j" % "slf4j-nop" % "1.6.4")


scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-unchecked",
  "-feature",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-Ywarn-dead-code",
  "-Xlint",
  "-Xfatal-warnings"
)