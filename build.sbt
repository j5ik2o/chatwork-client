net.virtualvoid.sbt.graph.Plugin.graphSettings

name := "chatwork-client"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.10.3"

resolvers ++= Seq(
  "Eaio Repository" at "http://eaio.com/maven2",
  "Twitter Repository" at "http://maven.twttr.com/",
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Sonatype Snapshot Repository" at "http://oss.sonatype.org/content/repositories/snapshots/",
  "Sonatype Release Repository" at "http://oss.sonatype.org/content/repositories/releases/",
  "Seasar Repository" at "http://maven.seasar.org/maven2/"
)

libraryDependencies ++= Seq(
  "org.jsoup" % "jsoup" % "1.7.2",
  //"net.sourceforge.htmlunit" % "htmlunit" % "2.13",
  "org.apache.commons" % "commons-lang3" % "3.1",
  "org.scala-lang" % "scala-reflect" % "2.10.3",
  "org.sisioh" %% "scala-dddbase-core" % "0.1.25",
  "org.sisioh" %% "scala-toolbox" % "0.0.7",
  "com.twitter" %% "finagle-core" % "6.4.1" excludeAll(
    ExclusionRule(organization = "log4j", name = "log4j"),
    ExclusionRule(organization = "org.slf4j", name = "slf4j-api"),
    ExclusionRule(organization = "org.slf4j", name = "slf4j-jdk14"),
    ExclusionRule(organization = "org.slf4j", name = "slf4j-log4j12"),
    ExclusionRule(organization = "com.google.guava", name = "guava"),
    ExclusionRule(organization = "commons-lang", name = "commons-lang")
    ),
  "com.twitter" %% "finagle-http" % "6.4.1" excludeAll(
    ExclusionRule(organization = "log4j", name = "log4j"),
    ExclusionRule(organization = "org.slf4j", name = "slf4j-api"),
    ExclusionRule(organization = "org.slf4j", name = "slf4j-jdk14"),
    ExclusionRule(organization = "org.slf4j", name = "slf4j-log4j12"),
    ExclusionRule(organization = "commons-lang", name = "commons-lang")
    ),
  "org.json4s" %% "json4s-jackson" % "3.2.2",
  "org.specs2" %% "specs2" % "2.0" % "test",
  "org.mockito" % "mockito-core" % "1.9.5" % "test",
  "com.google.guava" % "guava" % "15.0",
  "org.slf4j" % "slf4j-api" % "1.6.6",
  "org.slf4j" % "log4j-over-slf4j" % "1.6.6",
  "org.slf4j" % "jul-to-slf4j" % "1.6.6",
  "org.clapper" %% "grizzled-slf4j" % "1.0.1",
  "ch.qos.logback" % "logback-core" % "1.0.7" exclude("org.slf4j", "slf4j-api"),
  "ch.qos.logback" % "logback-classic" % "1.0.7"
)
