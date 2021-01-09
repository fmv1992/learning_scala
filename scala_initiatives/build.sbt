// https://www.scala-sbt.org/1.0/docs/Howto-Project-Metadata.html

name := "FMV1992LearningScala"

organization := "org.fmv1992"

licenses += "GPLv2" -> url("https://www.gnu.org/licenses/gpl-2.0.html")

inThisBuild(
  List(
    scalaVersion := "2.12.12", // 2.11.12, or 2.13.4
    semanticdbEnabled := true, // enable SemanticDB
    semanticdbVersion := scalafixSemanticdb.revision // use Scalafix compatible version
  )
)

lazy val commonSettings = Seq(
  version := "0.0.1-SNAPSHOT",
  // scalaVersion := "2.13.4",
  scalaVersion := "2.12.12",
  pollInterval := scala.concurrent.duration.FiniteDuration(50L, "ms"),
  maxErrors := 10,
  excludeFilter in unmanagedSources :=
    "*.orig"
      || "*BACKUP*.scala"
      || "*BASE*.scala"
      || "*LOCAL*.scala"
      || "*REMOTE*.scala",
  // This final part makes test artifacts being only importable by the test files
  // libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test,
  //                                                                   ↑↑↑↑↑
  // Removed on commit 'cd9d482' to enable 'trait ScalaInitiativesTest' define
  // 'namedTest'.
  libraryDependencies ++= Seq(
    // "org.scalatest" %% "scalatest" % "3.1.0" % Test,
    "org.scalatest" %% "scalatest" % "3.1.0"
  ),
  scalacOptions ++= Seq("-feature", "-deprecation", "-Xfatal-warnings")
)

// Common to all projects.
lazy val common = (project in file("common")).settings(commonSettings)

// Spoj project.
// Website: https://www.spoj.com
lazy val spoj =
  (project in file("spoj"))
    .settings(commonSettings)
    .settings(
      libraryDependencies += "org.scala-lang.modules" %% "scala-collection-compat" % "2.3.2"
    )
    .dependsOn(common)

// Programming in Scala
// Website: https://booksites.artima.com/programming_in_scala_3ed>
lazy val programmingInScala = (project in file("programming_in_scala"))
  .settings(commonSettings)
  .settings(
    libraryDependencies += "org.scala-lang.modules" %% "scala-collection-compat" % "2.3.2"
  )
  .dependsOn(common)

// Functional Programming in Scala.
// Website: https://www.manning.com/books/functional-programming-in-scala
lazy val fpis = (project in file("functional_programming_in_scala"))
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      "fpinscala" %% "fpinscala" % "dc04eb6"
    ),
    scalaVersion := "2.12.12"
  )
  .dependsOn(common)

// Functional Programming in Scala.
// Website: https://www.manning.com/books/functional-programming-in-scala
lazy val project_euler = (project in file("./project_euler"))
  .settings(commonSettings)
  .settings(
    libraryDependencies += "org.scala-lang.modules" %% "scala-collection-compat" % "2.3.2"
  )
  .dependsOn(common)

// Root project.
lazy val root = (project in file("."))
  .aggregate(
    common,
    fpis,
    programmingInScala,
    project_euler,
    spoj
  )
