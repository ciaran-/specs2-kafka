name := "specs2-kafka"

version := "0.1.0"

scalaVersion := "2.11.8"

resolvers += "Apache repo" at "https://repository.apache.org/content/repositories/releases/"

libraryDependencies ++= Seq (
  "org.specs2" %% "specs2-core" % "3.7.2" % "test",
  "org.apache.kafka" % "kafka_2.11" % "0.10.0.0",
  "org.apache.curator" % "curator-test" % "2.7.0"
)