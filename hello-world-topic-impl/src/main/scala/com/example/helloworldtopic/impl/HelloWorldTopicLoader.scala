package com.example.helloworldtopic.impl

import com.example.helloworld.api.HelloWorldService
import com.example.helloworldtopic.api.HelloWorldTopicService
import com.example.helloworldtopic.impl.subscriber.HelloWorldSubscriber
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.broker.kafka.LagomKafkaComponents
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraPersistenceComponents
import com.lightbend.lagom.scaladsl.playjson.JsonSerializerRegistry
import com.lightbend.lagom.scaladsl.server._
import com.softwaremill.macwire._
import play.api.libs.ws.ahc.AhcWSComponents

class HelloWorldTopicLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new HelloWorldTopicApplication(context) {
      override def serviceLocator: NoServiceLocator.type = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new HelloWorldTopicApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[HelloWorldTopicService])
}

abstract class HelloWorldTopicApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with CassandraPersistenceComponents
    with LagomKafkaComponents
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer: LagomServer = serverFor[HelloWorldTopicService](wire[HelloWorldTopicServiceImpl])

  // Register the JSON serializer registry
  override lazy val jsonSerializerRegistry: JsonSerializerRegistry = HelloWorldTopicSerializerRegistry

  // Bind the HelloWorldService client
  lazy val helloWorldService: HelloWorldService = serviceClient.implement[HelloWorldService]

  wire[HelloWorldSubscriber]
}
