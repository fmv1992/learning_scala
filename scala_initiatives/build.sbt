name := "LearningScala"

lazy val commonSettings = Seq(
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.8",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test,
    scalacOptions ++= Seq("-feature", "-deprecation", "-Xfatal-warnings")
    )

// Common to all projects.
lazy val common = (project in file("common")).settings(commonSettings)

// Spoj project.
// Website: https://www.spoj.com
lazy val spoj = (project in file("spoj")).settings(
    commonSettings).dependsOn(common)

// Programming in Scala
// Website: https://booksites.artima.com/programming_in_scala_3ed>
lazy val programmingInScala = (project in file("programming_in_scala")).settings(
    commonSettings).dependsOn(common)

// Functional Programming in Scala.
// Website: https://www.manning.com/books/functional-programming-in-scala
lazy val fpis = (project in file("functional_programming_in_scala")).settings(
    commonSettings).dependsOn(common)

// Root project.
lazy val root = (project in file(".")).aggregate(
    common,
    spoj,
    programmingInScala,
    fpis
    )
