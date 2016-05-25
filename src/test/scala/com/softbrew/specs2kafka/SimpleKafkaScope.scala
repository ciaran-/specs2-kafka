package com.softbrew.specs2kafka

import java.net.ServerSocket
import java.nio.file.Files
import java.util.Properties

import _root_.kafka.server.KafkaConfig
import _root_.kafka.server.KafkaServerStartable
import org.apache.curator.test.TestingServer
import org.specs2.mutable.BeforeAfter
import org.specs2.specification.Scope

trait SimpleKafkaScope extends Scope with BeforeAfter{
  val zkServer = new TestingServer()
  val socket = new ServerSocket(0)
  val port = socket.getLocalPort.toString
  println(s"'port' looks like: $port")
  socket.close()
  val tmpDir = Files.createTempDirectory("kafka-test-logs")

  val serverProps = new Properties()
  serverProps.put("broker.id", port)
  serverProps.put("log.dirs", tmpDir.toAbsolutePath.toString)
  serverProps.put("host.name", "localhost")
  serverProps.put("zookeeper.connect", zkServer.getConnectString)
  serverProps.put("port", port)

  val config = new KafkaConfig(serverProps)
  val kafkaServer = new KafkaServerStartable(config)

  override def before: Unit = {
    kafkaServer.startup()
  }

  override def after: Unit = {
    kafkaServer.shutdown()
    zkServer.close()
    zkServer.stop()
  }

  println("zkServer: " + zkServer.getConnectString)
  println("Kafka broker port: " + port)
}
