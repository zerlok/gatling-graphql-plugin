val gatlingVersion = "3.9.5"

lazy val root = (project in file("."))
  .settings(
    name := "gatling-graphql-plugin",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := "2.13.12",
    libraryDependencies ++= Seq(
      "io.gatling.highcharts" % "gatling-charts-highcharts" % gatlingVersion,
      "io.gatling" % "gatling-test-framework" % gatlingVersion,
    ),
    scalacOptions ++= Seq(
      "-deprecation",
      "-unchecked",
      "-language:implicitConversions",
    ),
  )

//enablePlugins(GatlingPlugin)
