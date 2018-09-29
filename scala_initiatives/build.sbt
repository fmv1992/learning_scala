name := "LearningScala"

lazy val commonSettings = Seq(
  version := "0.0.1-SNAPSHOT",
  scalaVersion := "2.12.5",
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test
)

lazy val common = (project in file("common"))
  .settings(
      commonSettings)

// Spoj project.
// Website: https://www.spoj.com
lazy val spoj = (project in file("spoj"))
  .settings(
      commonSettings)
  .dependsOn(common)
