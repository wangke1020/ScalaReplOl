import sbt._
import Keys._
import org.scalatra.sbt._
import org.scalatra.sbt.PluginKeys._
import com.earldouglas.xwp.JettyPlugin
import com.mojolly.scalate.ScalatePlugin._
import ScalateKeys._

object ScalareplolBuild extends Build {
  val Organization = "com.scalaReplOl"
  val Name = "ScalaReplOl"
  val Version = "0.1.0-SNAPSHOT"
  val ScalaVersion = "2.11.0"
  val ScalatraVersion = "2.4.0"

  lazy val project = Project (
    "scalareplol",
    file("."),
    settings = ScalatraPlugin.scalatraSettings ++ scalateSettings ++ Seq(
      organization := Organization,
      name := Name,
      version := Version,
      scalaVersion := ScalaVersion,
      autoScalaLibrary := true,
      resolvers += Classpaths.typesafeReleases,
      resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases",
      resolvers += "Twitter repo" at "http://maven.twttr.com/",
      libraryDependencies ++= Seq(
        "org.json4s"   %% "json4s-native" % "3.3.0",
        "org.scalatra" %% "scalatra-swagger"  % ScalatraVersion,
        "org.scalatra" %% "scalatra" % ScalatraVersion,
        "org.scalatra" %% "scalatra-scalate" % ScalatraVersion,
        "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",

        "org.scala-lang" % "scala-compiler" % ScalatraVersion,
        "org.scala-lang" % "scala-library" % ScalatraVersion,
        "org.scala-lang" % "scala-reflect" % ScalatraVersion,

        "ch.qos.logback" % "logback-classic" % "1.1.3" % "runtime",
        "org.eclipse.jetty" % "jetty-webapp" % "9.2.14.v20151106" % "container",
        "org.eclipse.jetty" % "jetty-plus" % "9.1.5.v20140505" % "container",
        "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
        "com.twitter" % "util-eval" % "1.12.13",
        "ch.qos.logback" % "logback-classic" % "1.1.1" % "runtime"

      ),
      scalateTemplateConfig in Compile <<= (sourceDirectory in Compile){ base =>
        Seq(
          TemplateConfig(
            base / "webapp" / "WEB-INF" / "templates",
            Seq.empty,  /* default imports should be added here */
            Seq(
              Binding("context", "_root_.org.scalatra.scalate.ScalatraRenderContext", importMembers = true, isImplicit = true)
            ),  /* add extra bindings here */
            Some("templates")
          )
        )
      }
    )
  ).enablePlugins(JettyPlugin)
}
